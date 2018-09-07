package org.opensrp.stock.openlmis.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.TradeItemClassificationMetaData;
import org.opensrp.stock.openlmis.service.TradeItemClassificationService;
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
@RequestMapping(value = "/rest/trade-item-classifications")
public class TradeItemClassificationResource {

    @Autowired
    private TradeItemClassificationService tradeItemClassificationService;

    private static Logger logger = LoggerFactory.getLogger(TradeItemClassificationResource.class.toString());

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    protected List<TradeItemClassificationMetaData> getAll() {
        return tradeItemClassificationService.getAll();
    }

    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    @ResponseBody
    protected List<TradeItemClassificationMetaData> sync(HttpServletRequest request) {
        try {
            long serverVersion = getLongFilter(SYNC_SERVER_VERSION, request);
            return tradeItemClassificationService.get(serverVersion);
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
            if (!postData.has(TRADE_ITEM_CLASSIFICATIONS)) {
                return new ResponseEntity<>(BAD_REQUEST);
            }

            List<TradeItemClassificationMetaData> entries = (ArrayList<TradeItemClassificationMetaData>) gson.fromJson(postData.getString(TRADE_ITEM_CLASSIFICATIONS),
                    new TypeToken<ArrayList<TradeItemClassificationMetaData>>() {}.getType());
            long serverVersion = getCurrentTime();
            for (TradeItemClassificationMetaData entry : entries) {
                try {
                    entry.setServerVersion(serverVersion);
                    tradeItemClassificationService.addOrUpdate(entry);
                    serverVersion++;
                } catch (Exception e) {
                    logger.error("TradeItemClassification " + entry.getId() == null ? "" : entry.getId() + " failed to sync", e);
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
            if (!postData.has(TRADE_ITEM_CLASSIFICATIONS)) {
                return new ResponseEntity<>(BAD_REQUEST);
            }

            List<TradeItemClassificationMetaData> tradeItemClassifications = (ArrayList<TradeItemClassificationMetaData>) gson.fromJson(postData.getString(TRADE_ITEM_CLASSIFICATIONS),
                    new TypeToken<ArrayList<TradeItemClassificationMetaData>>() {}.getType());
            long serverVersion = getCurrentTime();
            for (TradeItemClassificationMetaData tradeItemClassification : tradeItemClassifications) {
                try {
                    MasterTableEntry entry = tradeItemClassificationService.get(TRADE_ITEM_CLASSIFICATION, tradeItemClassification.getId());
                    entry.setJson(tradeItemClassification);
                    entry.setServerVersion(serverVersion);
                    tradeItemClassificationService.update(entry);
                    serverVersion++;
                } catch (Exception e) {
                    logger.error("TradeItemClassification " + tradeItemClassification.getId() == null ? "" : tradeItemClassification.getId() + " failed to sync", e);
                }
            }
        } catch (Exception e) {
            logger.error(format("Sync data processing failed with exception {0}.- ", e));
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(CREATED);
    }
}
