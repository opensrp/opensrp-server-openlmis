package org.opensrp.stock.openlmis.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.TradeItemClassificationMetaData;
import org.opensrp.stock.openlmis.domain.metadata.TradeItemMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.opensrp.stock.openlmis.util.Utils.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class TradeItemClassificationResourceTest extends BaseResourceTest {

    private final static String BASE_URL = "/rest/trade-item-classifications/";

    @Autowired
    private MasterTableRepository repository;

    @Before
    public void bootStrap() {
        tableNames.add("core.master_table");
        tableNames.add("core.master_metadata");
        truncateTables();
    }

    @After
    public void tearDown() {
        truncateTables();
    }

    @Test
    public void testGetAllShouldReturnAllTradeItemClassifications() throws Exception {

        List<Object> expectedTradeItemClassifications = new ArrayList<>();

        TradeItemClassificationMetaData expectedTradeItemClassification = new TradeItemClassificationMetaData(
                "identifier"
        );
        expectedTradeItemClassification.setClassificationId("classification_id");
        expectedTradeItemClassification.setClassificationSystem("classification_system");
        expectedTradeItemClassification.setTradeItem(new TradeItemMetaData("trade_item"));

        repository.add(expectedTradeItemClassification);
        expectedTradeItemClassifications.add(expectedTradeItemClassification);

        expectedTradeItemClassification = new TradeItemClassificationMetaData(
                "identifier_1"
        );
        expectedTradeItemClassification.setClassificationId("classification_id_1");
        expectedTradeItemClassification.setClassificationSystem("classification_system_1");
        expectedTradeItemClassification.setTradeItem(new TradeItemMetaData("trade_item_1"));

        repository.add(expectedTradeItemClassification);
        expectedTradeItemClassifications.add(expectedTradeItemClassification);

        List<Object> actualTradeItemClassifications = getResponseAsList(BASE_URL, null, status().isOk());

        assertTwoListsAreSameIgnoringOrder(actualTradeItemClassifications, expectedTradeItemClassifications, false);
    }

    @Test
    public void testSyncShouldRetrieveAllTradeItemClassificationsAfterACertainTime() throws Exception {

        List<Object> expectedTradeItemClassifications = new ArrayList<>();

        // this trade item classification should not sync
        TradeItemClassificationMetaData expectedTradeItemClassification = new TradeItemClassificationMetaData(
                "identifier"
        );
        expectedTradeItemClassification.setClassificationId("classification_id");
        expectedTradeItemClassification.setClassificationSystem("classification_system");
        expectedTradeItemClassification.setTradeItem(new TradeItemMetaData("trade_item"));
        repository.add(expectedTradeItemClassification);

        // these trade item classifications should sync
        long timeBefore = getCurrentTime();
        expectedTradeItemClassification = new TradeItemClassificationMetaData(
                "identifier_1"
        );
        expectedTradeItemClassification.setClassificationId("classification_id_1");
        expectedTradeItemClassification.setClassificationSystem("classification_system_1");
        expectedTradeItemClassification.setTradeItem(new TradeItemMetaData("trade_item_1"));

        repository.add(expectedTradeItemClassification);
        expectedTradeItemClassifications.add(expectedTradeItemClassification);

        expectedTradeItemClassification = new TradeItemClassificationMetaData(
                "identifier_2"
        );
        expectedTradeItemClassification.setClassificationId("classification_id_2");
        expectedTradeItemClassification.setClassificationSystem("classification_system_2");
        expectedTradeItemClassification.setTradeItem(new TradeItemMetaData("trade_item_2"));

        repository.add(expectedTradeItemClassification);
        expectedTradeItemClassifications.add(expectedTradeItemClassification);

        List<Object> actualTradeItemClassifications = getResponseAsList(BASE_URL + "sync", SYNC_SERVER_VERSION + "=" + timeBefore, status().isOk());

        assertTwoListsAreSameIgnoringOrder(expectedTradeItemClassifications, actualTradeItemClassifications,true);
    }

    @Test
    public void testPostShouldCreateNewTradeItemClassificationsInDb() throws Exception {

        List<Object> expectedTradeItemClassifications = new ArrayList<>();
        JSONArray tradeItemClassificationsArr = new JSONArray();

        // trade item classification 1
        TradeItemClassificationMetaData expectedTradeItemClassification = new TradeItemClassificationMetaData(
                "identifier"
        );
        expectedTradeItemClassification.setClassificationId("classification_id");
        expectedTradeItemClassification.setClassificationSystem("classification_system");
        expectedTradeItemClassification.setTradeItem(new TradeItemMetaData("trade_item"));

        expectedTradeItemClassifications.add(expectedTradeItemClassification);
        tradeItemClassificationsArr.put(mapper.writeValueAsString(expectedTradeItemClassification));

        // trade item 2
        expectedTradeItemClassification = new TradeItemClassificationMetaData(
                "identifier_1"
        );
        expectedTradeItemClassification.setClassificationId("classification_id_1");
        expectedTradeItemClassification.setClassificationSystem("classification_system_1");
        expectedTradeItemClassification.setTradeItem(new TradeItemMetaData("trade_item_1"));

        expectedTradeItemClassifications.add(expectedTradeItemClassification);
        tradeItemClassificationsArr.put(mapper.writeValueAsString(expectedTradeItemClassification));

        // trade item 3
        expectedTradeItemClassification = new TradeItemClassificationMetaData(
                "identifier_2"
        );
        expectedTradeItemClassification.setClassificationId("classification_id_2");
        expectedTradeItemClassification.setClassificationSystem("classification_system_2");
        expectedTradeItemClassification.setTradeItem(new TradeItemMetaData("trade_item_2"));

        expectedTradeItemClassifications.add(expectedTradeItemClassification);
        tradeItemClassificationsArr.put(mapper.writeValueAsString(expectedTradeItemClassification));

        JSONObject data = new JSONObject();
        data.put(TRADE_ITEM_CLASSIFICATIONS, tradeItemClassificationsArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");
        postRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        List<Object> actualTradeItemClassifications = new ArrayList<>();
        for (MasterTableEntry entry : repository.getAll()) {
            actualTradeItemClassifications.add(entry.getJson());
        }

        assertTwoListsAreSameIgnoringOrder(expectedTradeItemClassifications, actualTradeItemClassifications, false);
    }


    @Test
    public void testPutShouldUpdateTradeItemClassificationsInDb() throws Exception {
        // TradeItemClassification 1
        TradeItemClassificationMetaData tradeItemClassification = new TradeItemClassificationMetaData(
                "identifier"
        );
        tradeItemClassification.setClassificationId("classification_id");
        tradeItemClassification.setClassificationSystem("classification_system");
        tradeItemClassification.setTradeItem(new TradeItemMetaData("trade_item"));

        MasterTableEntry entry = repository.add(tradeItemClassification);

        // updated TradeItemClassification
        TradeItemClassificationMetaData expectedTradeItemClassification = new TradeItemClassificationMetaData(
                "identifier"
        );
        expectedTradeItemClassification.setClassificationId("classification_id_1");
        expectedTradeItemClassification.setClassificationSystem("classification_system_1");
        expectedTradeItemClassification.setTradeItem(new TradeItemMetaData("trade_item_1"));

        JSONArray tradeItemClassificationsArr = new JSONArray();
        tradeItemClassificationsArr.put(mapper.writeValueAsString(expectedTradeItemClassification));

        JSONObject data = new JSONObject();
        data.put(TRADE_ITEM_CLASSIFICATIONS, tradeItemClassificationsArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");

        putRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        tradeItemClassification = (TradeItemClassificationMetaData) repository.get(entry.getId()).getJson();
        assertEquals(expectedTradeItemClassification.getId(), tradeItemClassification.getId());
        assertEquals(expectedTradeItemClassification.getClassificationId(), tradeItemClassification.getClassificationId());
        assertEquals(expectedTradeItemClassification.getClassificationSystem(), tradeItemClassification.getClassificationSystem());
        assertEquals(expectedTradeItemClassification.getTradeItem().getId(), tradeItemClassification.getTradeItem().getId());
    }
}
