package org.opensrp.stock.openlmis.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.opensrp.stock.openlmis.domain.metadata.ProgramMetaData;
import org.opensrp.stock.openlmis.service.ProgramService;
import org.opensrp.stock.openlmis.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;
import static org.opensrp.stock.openlmis.util.Utils.getLongFilter;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/rest/programs")
public class ProgramResource {

    private static final String LAST_SERVER_VERSION = "last_server_version";

    @Autowired
    ProgramService programService;
    private static Logger logger = LoggerFactory.getLogger(ProgramResource.class.toString());
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .registerTypeAdapter(DateTime.class, new Utils.DateTimeTypeConverter()).create();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    protected List<ProgramMetaData> getAll() {
        return programService.getAll();
    }

    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    @ResponseBody
    protected List<ProgramMetaData> sync(HttpServletRequest request) {
        try {
            long serverVersion = getLongFilter(LAST_SERVER_VERSION, request);
            return programService.get(serverVersion);
        } catch (Exception e) {
            logger.error("", e);
            return new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/add")
    public ResponseEntity<HttpStatus> add(@RequestBody String data) {

        try {
            JSONObject postData = new JSONObject(data);
            if (!postData.has("programs")) {
                return new ResponseEntity<>(BAD_REQUEST);
            }

            List<ProgramMetaData> entries = (ArrayList<ProgramMetaData>) gson.fromJson(postData.getString("programs"),
                    new TypeToken<ArrayList<ProgramMetaData>>() {}.getType());
            for (ProgramMetaData entry : entries) {
                try {
                    programService.add(entry);
                } catch (Exception e) {
                    logger.error("Program " + entry.getUuid() == null ? "" : entry.getUuid() + " failed to sync", e);
                }
            }
        } catch (Exception e) {
            logger.error(format("Sync data processing failed with exception {0}.- ", e));
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(CREATED);
    }
}
