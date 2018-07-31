package org.opensrp.stock.openlmis.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.TradeItemMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class TradeItemResourceTest extends BaseResourceTest {

    private final static String BASE_URL = "/rest/trade-items/";

    @Autowired
    private MasterTableRepository repository;

    @Autowired
    private TradeItemResource tradeItemResource;

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
        repository.add(expectedTradeItem);
        expectedTradeItems.add(expectedTradeItem);

        expectedTradeItem = new TradeItemMetaData(
                "identifier_1"
        );
        repository.add(expectedTradeItem);
        expectedTradeItems.add(expectedTradeItem);

        List<Object> actualTradeItems = getResponseAsList(BASE_URL, null, status().isOk());

        assertTwoListAreSameIgnoringOrder(actualTradeItems, expectedTradeItems);
    }

    @Test
    public void testSyncShouldRetrieveAllTradeItemsAfterACertainTime() throws Exception {


        List<Object> expectedTradeItems = new ArrayList<>();

        // this trade item should not sync
        TradeItemMetaData expectedTradeItem = new TradeItemMetaData(
                "identifier"
        );
        repository.add(expectedTradeItem);

        // these trade items should sync
        long timeBefore = getCurrentTime();
        expectedTradeItem = new TradeItemMetaData(
                "identifier_1"
        );
        repository.add(expectedTradeItem);
        expectedTradeItems.add(expectedTradeItem);

        expectedTradeItem = new TradeItemMetaData(
                "identifier_2"
        );
        repository.add(expectedTradeItem);
        expectedTradeItems.add(expectedTradeItem);

        List<Object> actualTradeItems = getResponseAsList(BASE_URL + "sync", "last_server_version=" + timeBefore, status().isOk());

        assertTwoListAreSameIgnoringOrder(actualTradeItems, expectedTradeItems);
    }

    @Test
    public void testPostShouldCreateNewTradeItemsInDb() throws Exception {
//
//        List<Object> expectedTradeItems = new ArrayList<>();
//        TradeItemMetaData expectedTradeItem = new TradeItemMetaData(
//                "identifier"
//        );
//        expectedTradeItems.add(expectedTradeItem);
//
//        postRequestWithJsonContent(BASE_URL + "add", mapper.writeValueAsString(expectedTradeItem), status().isCreated());
//
//        List<Object> actualTradeItems = new ArrayList<>();
//        for (MasterTableEntry entry : repository.getAll()) {
//            actualTradeItems.add((TradeItemMetaData) entry.getJson());
//        }
//
//        assertTwoListAreSameIgnoringOrder(expectedTradeItems, actualTradeItems);
    }
}

