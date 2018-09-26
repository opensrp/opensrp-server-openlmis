package org.opensrp.stock.openlmis.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.FacilityProgramMetaData;
import org.opensrp.stock.openlmis.service.FacilityProgramService;
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
import java.util.Map;

import static java.text.MessageFormat.format;
import static org.opensrp.stock.openlmis.util.Utils.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
@RequestMapping(value = "/rest/facility-programs")
public class FacilityProgramResource {
    
    @Autowired
    private FacilityProgramService facilityProgramService;

    private static Logger logger = LoggerFactory.getLogger(FacilityProgramResource.class.toString());

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    protected List<FacilityProgramMetaData> getAll(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        if (parameters.size() == 0) {
            return facilityProgramService.getAll();
        }
        String openlmisUuid = getStringFilter(OPENLMIS_UUID, request);
        String facilityTypeUuid = getStringFilter(FACILITY_TYPE_UUID, request);
        return facilityProgramService.getFiltered(openlmisUuid, facilityTypeUuid);
    }


    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    @ResponseBody
    protected List<FacilityProgramMetaData> sync(HttpServletRequest request) {
        try {
            long serverVersion = getLongFilter(SYNC_SERVER_VERSION, request);
            String openlmisUuid = getStringFilter(OPENLMIS_UUID, request);
            String facilityTypeUuid = getStringFilter(FACILITY_TYPE_UUID, request);
            return facilityProgramService.get(serverVersion, openlmisUuid, facilityTypeUuid);
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
            if (!postData.has(FACILITY_PROGRAMS)) {
                return new ResponseEntity<>(BAD_REQUEST);
            }

            List<FacilityProgramMetaData> entries = (ArrayList<FacilityProgramMetaData>) gson.fromJson(postData.getString(FACILITY_PROGRAMS),
                    new TypeToken<ArrayList<FacilityProgramMetaData>>() {}.getType());
            long serverVersion = getCurrentTime();
            for (FacilityProgramMetaData entry : entries) {
                try {
                    entry.setId(entry.getOpenlmisUuid());
                    entry.setServerVersion(serverVersion);
                    facilityProgramService.addOrUpdate(entry);
                    serverVersion++;
                } catch (Exception e) {
                    logger.error("FacilityProgram " + entry.getId() == null ? "" : entry.getId() + " failed to sync", e);
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
            if (!postData.has(FACILITY_PROGRAMS)) {
                return new ResponseEntity<>(BAD_REQUEST);
            }

            List<FacilityProgramMetaData> facilityPrograms = (ArrayList<FacilityProgramMetaData>) gson.fromJson(postData.getString(FACILITY_PROGRAMS),
                    new TypeToken<ArrayList<FacilityProgramMetaData>>() {}.getType());
            long serverVersion = getCurrentTime();
            for (FacilityProgramMetaData facilityProgram : facilityPrograms) {
                try {
                    MasterTableEntry entry = facilityProgramService.get(FACILITY_PROGRAM, facilityProgram.getId());
                    entry.setJson(facilityProgram);
                    entry.setServerVersion(serverVersion);
                    facilityProgramService.update(entry);
                    serverVersion++;
                } catch (Exception e) {
                    logger.error("FacilityProgram " + facilityProgram.getId() == null ? "" : facilityProgram.getId() + " failed to sync", e);
                }
            }
        } catch (Exception e) {
            logger.error(format("Sync data processing failed with exception {0}.- ", e));
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(CREATED);
    }
}
