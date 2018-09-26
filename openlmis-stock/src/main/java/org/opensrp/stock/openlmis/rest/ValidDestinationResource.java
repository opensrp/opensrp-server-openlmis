package org.opensrp.stock.openlmis.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.ValidDestinationMetaData;
import org.opensrp.stock.openlmis.service.ValidDestinationService;
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
@RequestMapping(value = "/rest/valid-destinations")
public class ValidDestinationResource {
    
    @Autowired
    private ValidDestinationService validDestinationService;

    private static Logger logger = LoggerFactory.getLogger(OrderableResource.class.toString());
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    protected List<ValidDestinationMetaData> getAll() {
        return validDestinationService.getAll();
    }

    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    @ResponseBody
    protected List<ValidDestinationMetaData> sync(HttpServletRequest request) {
        try {
            long serverVersion = getLongFilter(SYNC_SERVER_VERSION, request);
            return validDestinationService.get(serverVersion);
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
            if (!postData.has(VALID_DESTINATIONS)) {
                return new ResponseEntity<>(BAD_REQUEST);
            }

            List<ValidDestinationMetaData> entries = (ArrayList<ValidDestinationMetaData>) gson.fromJson(postData.getString(VALID_DESTINATIONS),
                    new TypeToken<ArrayList<ValidDestinationMetaData>>() {}.getType());
            long serverVersion = getCurrentTime();
            for (ValidDestinationMetaData entry : entries) {
                try {
                    entry.setServerVersion(serverVersion);
                    validDestinationService.addOrUpdate(entry);
                    serverVersion++;
                } catch (Exception e) {
                    logger.error("ValidDestination " + entry.getId() == null ? "" : entry.getId() + " failed to sync", e);
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
            if (!postData.has(VALID_DESTINATIONS)) {
                return new ResponseEntity<>(BAD_REQUEST);
            }

            List<ValidDestinationMetaData> validDestinations = (ArrayList<ValidDestinationMetaData>) gson.fromJson(postData.getString(VALID_DESTINATIONS),
                    new TypeToken<ArrayList<ValidDestinationMetaData>>() {}.getType());
            long serverVersion = getCurrentTime();
            for (ValidDestinationMetaData validDestination : validDestinations) {
                try {
                    MasterTableEntry entry = validDestinationService.get(VALID_DESTINATION, validDestination.getId());
                    entry.setJson(validDestination);
                    entry.setServerVersion(serverVersion);
                    validDestinationService.update(entry);
                    serverVersion++;
                } catch (Exception e) {
                    logger.error("ValidDestination " + validDestination.getId() == null ? "" : validDestination.getId() + " failed to sync", e);
                }
            }
        } catch (Exception e) {
            logger.error(format("Sync data processing failed with exception {0}.- ", e));
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(CREATED);
    }
}
