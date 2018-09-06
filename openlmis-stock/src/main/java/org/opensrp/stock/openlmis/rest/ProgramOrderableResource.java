package org.opensrp.stock.openlmis.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.opensrp.stock.openlmis.domain.ProgramOrderable;
import org.opensrp.stock.openlmis.service.ProgramOrderableService;
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
import static org.opensrp.stock.openlmis.util.Utils.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
@RequestMapping(value = "/rest/program-orderables")
public class ProgramOrderableResource {

    @Autowired
    private ProgramOrderableService programOrderableService;

    private static Logger logger = LoggerFactory.getLogger(OrderableResource.class.toString());
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    protected List<ProgramOrderable> getAll() {
        return programOrderableService.getAll();
    }

    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    @ResponseBody
    protected List<ProgramOrderable> sync(HttpServletRequest request) {
        try {
            long serverVersion = getLongFilter(SYNC_SERVER_VERSION, request);
            return programOrderableService.get(serverVersion);
        } catch (Exception e) {
            logger.error("", e);
            return new ArrayList<>();
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(headers = { "Accept=application/json" }, method = POST)
    public ResponseEntity<HttpStatus> add(@RequestBody String data) {

        try {
            JSONObject postData = new JSONObject(data);
            if (!postData.has(PROGRAM_ORDERABLES)) {
                return new ResponseEntity<>(BAD_REQUEST);
            }

            List<ProgramOrderable> entries = (ArrayList<ProgramOrderable>) gson.fromJson(postData.getString(PROGRAM_ORDERABLES),
                    new TypeToken<ArrayList<ProgramOrderable>>() {}.getType());
            long serverVersion = getCurrentTime();
            for (ProgramOrderable entry : entries) {
                try {
                    entry.setServerVersion(serverVersion);
                    programOrderableService.addOrUpdate(entry);
                    serverVersion++;
                } catch (Exception e) {
                    logger.error("ProgramOrderable " + entry.getId() == null ? "" : entry.getId() + " failed to sync", e);
                }
            }
        } catch (Exception e) {
            logger.error(format("Sync data processing failed with exception {0}.- ", e));
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(CREATED);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(headers = { "Accept=application/json" }, method = PUT)
    public ResponseEntity<HttpStatus> update(@RequestBody String data) {

        try {
            JSONObject postData = new JSONObject(data);
            if (!postData.has(PROGRAM_ORDERABLES)) {
                return new ResponseEntity<>(BAD_REQUEST);
            }

            List<ProgramOrderable> programOrderables = (ArrayList<ProgramOrderable>) gson.fromJson(postData.getString(PROGRAM_ORDERABLES),
                    new TypeToken<ArrayList<ProgramOrderable>>() {}.getType());
            long serverVersion = getCurrentTime();
            for (ProgramOrderable programOrderable : programOrderables) {
                try {
                    programOrderable.setServerVersion(serverVersion);
                    programOrderableService.update(programOrderable);
                    serverVersion++;
                } catch (Exception e) {
                    logger.error("ProgramOrderable " + programOrderable.getId() == null ? "" : programOrderable.getId() + " failed to sync", e);
                }
            }
        } catch (Exception e) {
            logger.error(format("Sync data processing failed with exception {0}.- ", e));
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(CREATED);
    }
}
