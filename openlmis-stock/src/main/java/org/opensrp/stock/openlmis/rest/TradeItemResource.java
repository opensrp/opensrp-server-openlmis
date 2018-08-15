package org.opensrp.stock.openlmis.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.TradeItemMetaData;
import org.opensrp.stock.openlmis.service.TradeItemService;
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
import static org.opensrp.stock.openlmis.util.Utils.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
@RequestMapping(value = "/rest/trade-items")
public class TradeItemResource {

    @Autowired
    private TradeItemService tradeItemService;

    private static Logger logger = LoggerFactory.getLogger(TradeItemResource.class.toString());

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .registerTypeAdapter(DateTime.class, new Utils.DateTimeTypeConverter()).create();

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    protected List<TradeItemMetaData> getAll() {
        return tradeItemService.getAll();
    }

    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    @ResponseBody
    protected List<TradeItemMetaData> sync(HttpServletRequest request) {
        try {
            long serverVersion = getLongFilter(SYNC_SERVER_VERSION, request);
            return tradeItemService.get(serverVersion);
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
            if (!postData.has(TRADE_ITEMS)) {
                return new ResponseEntity<>(BAD_REQUEST);
            }

            List<TradeItemMetaData> entries = (ArrayList<TradeItemMetaData>) gson.fromJson(postData.getString(TRADE_ITEMS),
                    new TypeToken<ArrayList<TradeItemMetaData>>() {}.getType());
            for (TradeItemMetaData entry : entries) {
                try {
                    tradeItemService.add(entry);
                } catch (Exception e) {
                    logger.error("TradeItem " + entry.getId() == null ? "" : entry.getId() + " failed to sync", e);
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
            if (!postData.has(TRADE_ITEMS)) {
                return new ResponseEntity<>(BAD_REQUEST);
            }

            List<TradeItemMetaData> tradeItems = (ArrayList<TradeItemMetaData>) gson.fromJson(postData.getString(TRADE_ITEMS),
                    new TypeToken<ArrayList<TradeItemMetaData>>() {}.getType());
            for (TradeItemMetaData tradeItem : tradeItems) {
                try {
                    MasterTableEntry entry = tradeItemService.get(TRADE_ITEM, tradeItem.getId());
                    entry.setJson(tradeItem);
                    tradeItemService.update(entry);
                } catch (Exception e) {
                    logger.error("TradeItem " + tradeItem.getId() == null ? "" : tradeItem.getId() + " failed to sync", e);
                }
            }
        } catch (Exception e) {
            logger.error(format("Sync data processing failed with exception {0}.- ", e));
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(CREATED);
    }
}

