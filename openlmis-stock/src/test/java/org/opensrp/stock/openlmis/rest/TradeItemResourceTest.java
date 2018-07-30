package org.opensrp.stock.openlmis.rest;

import org.codehaus.jackson.JsonNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.metadata.TradeItemMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
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

        TradeItemMetaData expectedTradeItem = new TradeItemMetaData(
                "identifier"
        );
        repository.add(expectedTradeItem);

        JsonNode actualObj = getCallAsJsonNode(BASE_URL, null, status().isOk());
        TradeItemMetaData actualTradeItem = mapper.treeToValue(actualObj, TradeItemMetaData.class);

        assertEquals(actualTradeItem, expectedTradeItem);
    }
}

