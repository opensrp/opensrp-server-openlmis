package org.opensrp.stock.openlmis.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.Gtin;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.DispensableMetaData;
import org.opensrp.stock.openlmis.domain.metadata.TradeItemClassificationMetaData;
import org.opensrp.stock.openlmis.domain.metadata.TradeItemMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.opensrp.stock.openlmis.util.Utils.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class TradeItemResourceTest extends BaseResourceTest {

    private final static String BASE_URL = "/rest/trade-items/";

    @Autowired
    private MasterTableRepository repository;

    @Before
    public void bootStrap() {
        truncateTable("core.master_table");
    }

    @After
    public void tearDown() {
        truncateTable("core.master_table");
    }

    @Test
    public void testGetAllShouldReturnAllTradeItems() throws Exception {

        List<Object> expectedTradeItems = new ArrayList<>();

        TradeItemMetaData expectedTradeItem = new TradeItemMetaData(
                "identifier"
        );
        expectedTradeItem.setManufacturerOfTradeItem("manufactuter");
        setGtinAndClassifications(expectedTradeItem);
        repository.add(expectedTradeItem);
        expectedTradeItems.add(expectedTradeItem);

        expectedTradeItem = new TradeItemMetaData(
                "identifier_1"
        );
        expectedTradeItem.setManufacturerOfTradeItem("manufactuter_1");
        setGtinAndClassifications(expectedTradeItem);
        repository.add(expectedTradeItem);
        expectedTradeItems.add(expectedTradeItem);

        List<Object> actualTradeItems = getResponseAsList(BASE_URL, null, status().isOk());

        assertTwoListsAreSameIgnoringOrder(actualTradeItems, expectedTradeItems, false);
    }

    @Test
    public void testSyncShouldRetrieveAllTradeItemsAfterACertainTime() throws Exception {

        DispensableMetaData dispensableMetaData = new DispensableMetaData();
        dispensableMetaData.setId("dispensable_uuid");
        dispensableMetaData.setKeyDispensingUnit("key_dispensing_unit");
        dispensableMetaData.setKeyRouteOfAdministration("route_of_administration");
        dispensableMetaData.setKeySizeCode("size_code");

        List<Object> expectedTradeItems = new ArrayList<>();

        // this trade item should not sync
        TradeItemMetaData expectedTradeItem = new TradeItemMetaData(
                "identifier"
        );
        expectedTradeItem.setManufacturerOfTradeItem("manufactuter");
        setGtinAndClassifications(expectedTradeItem);
        repository.add(expectedTradeItem);

        // these trade items should sync
        long timeBefore = getCurrentTime();
        expectedTradeItem = new TradeItemMetaData(
                "identifier_1"
        );
        expectedTradeItem.setManufacturerOfTradeItem("manufactuter_1");
        setGtinAndClassifications(expectedTradeItem);
        repository.add(expectedTradeItem);
        expectedTradeItems.add(expectedTradeItem);

        expectedTradeItem = new TradeItemMetaData(
                "identifier_2"
        );
        expectedTradeItem.setManufacturerOfTradeItem("manufactuter_2");
        setGtinAndClassifications(expectedTradeItem);
        repository.add(expectedTradeItem);
        expectedTradeItems.add(expectedTradeItem);

        List<Object> actualTradeItems = getResponseAsList(BASE_URL + "sync", SYNC_SERVER_VERSION + "=" + timeBefore, status().isOk());

        assertTwoListsAreSameIgnoringOrder(expectedTradeItems, actualTradeItems,true);
    }

    @Test
    public void testPostShouldCreateNewTradeItemsInDb() throws Exception {

        DispensableMetaData dispensableMetaData = new DispensableMetaData();
        dispensableMetaData.setId("dispensable_uuid");
        dispensableMetaData.setKeyDispensingUnit("key_dispensing_unit");
        dispensableMetaData.setKeyRouteOfAdministration("route_of_administration");
        dispensableMetaData.setKeySizeCode("size_code");

        List<Object> expectedTradeItems = new ArrayList<>();
        JSONArray tradeItemsArr = new JSONArray();
        // trade item 1
        TradeItemMetaData expectedTradeItem = new TradeItemMetaData(
                "identifier_1"
        );
        expectedTradeItem.setManufacturerOfTradeItem("manufactuter_1");
        setGtinAndClassifications(expectedTradeItem);

        expectedTradeItems.add(expectedTradeItem);
        tradeItemsArr.put(mapper.writeValueAsString(expectedTradeItem));

        // trade item 2
        expectedTradeItem = new TradeItemMetaData(
                "identifier_2"
        );
        expectedTradeItem.setManufacturerOfTradeItem("manufactuter_2");
        setGtinAndClassifications(expectedTradeItem);

        expectedTradeItems.add(expectedTradeItem);
        tradeItemsArr.put(mapper.writeValueAsString(expectedTradeItem));

        // trade item 3
        expectedTradeItem = new TradeItemMetaData(
                "identifier_3"
        );
        expectedTradeItem.setManufacturerOfTradeItem("manufactuter_3");
        setGtinAndClassifications(expectedTradeItem);

        expectedTradeItems.add(expectedTradeItem);
        tradeItemsArr.put(mapper.writeValueAsString(expectedTradeItem));

        JSONObject data = new JSONObject();
        data.put(TRADE_ITEMS, tradeItemsArr);
        String dataString =
                data
                .toString()
                .replace("\"{", "{")
                .replace("}\"", "}")
                .replace("\\", "")
                .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");
        postRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        List<Object> actualTradeItems = new ArrayList<>();
        for (MasterTableEntry entry : repository.getAll()) {
            actualTradeItems.add(entry.getJson());
        }

        assertTwoListsAreSameIgnoringOrder(expectedTradeItems, actualTradeItems, false);
    }


    @Test
    public void testPutShouldUpdateTradeItemsInDb() throws Exception {
        // TradeItem 1
        TradeItemMetaData tradeItem = new TradeItemMetaData(
                "identifier"
        );
        tradeItem.setManufacturerOfTradeItem("manufactuter_1");
        setGtinAndClassifications(tradeItem);

        MasterTableEntry entry = repository.add(tradeItem);

        // updated TradeItem
        TradeItemMetaData expectedTradeItem = new TradeItemMetaData(
                "identifier"
        );
        expectedTradeItem.setManufacturerOfTradeItem("manufactuter_2");
        setGtinAndClassifications(expectedTradeItem);

        JSONArray tradeItemsArr = new JSONArray();
        tradeItemsArr.put(mapper.writeValueAsString(expectedTradeItem));

        JSONObject data = new JSONObject();
        data.put(TRADE_ITEMS, tradeItemsArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");

        putRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        tradeItem = (TradeItemMetaData) repository.get(entry.getId()).getJson();
        assertEquals(expectedTradeItem.getId(), tradeItem.getId());
        assertEquals(expectedTradeItem.getManufacturerOfTradeItem(), tradeItem.getManufacturerOfTradeItem());
    }


    private void setGtinAndClassifications(TradeItemMetaData tradeItemMetaData) throws Exception {

        Gtin gtin = new Gtin("859245888");
        List<TradeItemClassificationMetaData> tradeItemClassificationMetaDatas = new ArrayList<>();
        TradeItemClassificationMetaData tradeItemClassificationMetaData = new TradeItemClassificationMetaData(
                "identifier_1",
                null,
                "classification_system",
                "classification_id");
        tradeItemClassificationMetaDatas.add(tradeItemClassificationMetaData);

        tradeItemClassificationMetaData = new TradeItemClassificationMetaData(
                "identifier_2",
                null,
                "classification_system",
                "classification_id");
        tradeItemClassificationMetaDatas.add(tradeItemClassificationMetaData);
        tradeItemMetaData.setClassifications(tradeItemClassificationMetaDatas);
        tradeItemMetaData.setGtin(gtin);
    }
}

