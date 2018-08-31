package org.opensrp.stock.openlmis.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.Orderable;
import org.opensrp.stock.openlmis.repository.OrderableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.opensrp.stock.openlmis.util.Utils.ORDERABLES;
import static org.opensrp.stock.openlmis.util.Utils.SYNC_SERVER_VERSION;
import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class OrderableResourceTest extends BaseResourceTest {

    private final static String BASE_URL = "/rest/orderables/";

    @Autowired
    private OrderableRepository repository;

    @Before
    public void bootStrap() {
        tableNames.add("core.orderable");
        truncateTables();
    }

    @After
    public void tearDown() {
        truncateTables();
    }

    @Test
    public void testGetAllShouldReturnAllOrderables() throws Exception {

        List<Object> expectedOrderables = new ArrayList<>();

        Orderable expectedOrderable = new Orderable();

        expectedOrderable.setFullProductCode("full_product_code");
        expectedOrderable.setCommodityTypeId("commodity_type_id");
        expectedOrderable.setDispensableId("dispensble_id");
        expectedOrderable.setFullProductName("full_product_code");
        expectedOrderable.setId("id");
        expectedOrderable.setNetContent(18);
        expectedOrderable.setRoundToZero(true);
        expectedOrderable.setPackRoundingThreshold(2);
        expectedOrderable.setTradeItemId("trade_item");

        repository.addOrUpdate(expectedOrderable);
        expectedOrderables.add(expectedOrderable);

        expectedOrderable = new Orderable();

        expectedOrderable.setFullProductCode("full_product_code_1");
        expectedOrderable.setCommodityTypeId("commodity_type_id_1");
        expectedOrderable.setDispensableId("dispensble_id");
        expectedOrderable.setFullProductName("full_product_code_1");
        expectedOrderable.setId("id_1");
        expectedOrderable.setNetContent(12);
        expectedOrderable.setRoundToZero(true);
        expectedOrderable.setPackRoundingThreshold(3);
        expectedOrderable.setTradeItemId("trade_item_1");

        repository.addOrUpdate(expectedOrderable);
        expectedOrderables.add(expectedOrderable);

        String actualOrderablesString = getResponseAsString(BASE_URL, null, status().isOk());
        List<Object> actualOrderables = new Gson().fromJson(actualOrderablesString, new TypeToken<List<Orderable>>(){}.getType());

        assertListsAreSameIgnoringOrder(actualOrderables, expectedOrderables);
    }

    @Test
    public void testSyncShouldRetrieveAllOrderablesAfterACertainTime() throws Exception {

        List<Object> expectedOrderables = new ArrayList<>();

        // this trade item should not sync

        // orderable 1
        Orderable expectedOrderable = new Orderable();

        expectedOrderable.setFullProductCode("full_product_code");
        expectedOrderable.setCommodityTypeId("commodity_type_id");
        expectedOrderable.setDispensableId("dispensble_id");
        expectedOrderable.setFullProductName("full_product_code");
        expectedOrderable.setId("id");
        expectedOrderable.setNetContent(18);
        expectedOrderable.setRoundToZero(true);
        expectedOrderable.setPackRoundingThreshold(2);
        expectedOrderable.setTradeItemId("trade_item");

        repository.addOrUpdate(expectedOrderable);

        // these trade items should sync
        long timeBefore = getCurrentTime();

        // orderable 2
        expectedOrderable = new Orderable();
        expectedOrderable.setFullProductCode("full_product_code_1");
        expectedOrderable.setCommodityTypeId("commodity_type_id_1");
        expectedOrderable.setDispensableId("dispensble_id");
        expectedOrderable.setFullProductName("full_product_code_1");
        expectedOrderable.setId("id_1");
        expectedOrderable.setNetContent(12);
        expectedOrderable.setRoundToZero(true);
        expectedOrderable.setPackRoundingThreshold(3);
        expectedOrderable.setTradeItemId("trade_item_1");

        repository.addOrUpdate(expectedOrderable);
        expectedOrderables.add(expectedOrderable);

        // orderable 3
        expectedOrderable = new Orderable();
        expectedOrderable.setFullProductCode("full_product_code_2");
        expectedOrderable.setCommodityTypeId("commodity_type_id_2");
        expectedOrderable.setDispensableId("dispensble_id");
        expectedOrderable.setFullProductName("full_product_code_2");
        expectedOrderable.setId("id_2");
        expectedOrderable.setNetContent(12);
        expectedOrderable.setRoundToZero(true);
        expectedOrderable.setPackRoundingThreshold(5);
        expectedOrderable.setTradeItemId("trade_item_2");

        repository.addOrUpdate(expectedOrderable);
        expectedOrderables.add(expectedOrderable);

        String actualOrderablesString = getResponseAsString(BASE_URL + "sync", SYNC_SERVER_VERSION + "="+ timeBefore, status().isOk());
        List<Object> actualOrderables = new Gson().fromJson(actualOrderablesString, new TypeToken<List<Orderable>>(){}.getType());

        assertListsAreSameIgnoringOrder(actualOrderables, expectedOrderables);
    }

    @Test
    public void testPostShouldCreateNewOrderablesInDb() throws Exception {

        List<Object> expectedOrderables = new ArrayList<>();
        JSONArray orderablesArr = new JSONArray();

        // orderable 1
        Orderable expectedOrderable = new Orderable();

        expectedOrderable.setFullProductCode("full_product_code");
        expectedOrderable.setCommodityTypeId("commodity_type_id");
        expectedOrderable.setDispensableId("dispensble_id");
        expectedOrderable.setFullProductName("full_product_code");
        expectedOrderable.setId("id");
        expectedOrderable.setNetContent(18);
        expectedOrderable.setRoundToZero(true);
        expectedOrderable.setPackRoundingThreshold(2);
        expectedOrderable.setTradeItemId("trade_item");

        expectedOrderables.add(expectedOrderable);
        orderablesArr.put(mapper.writeValueAsString(expectedOrderable));

        // orderable 2
        expectedOrderable = new Orderable();
        expectedOrderable.setFullProductCode("full_product_code_1");
        expectedOrderable.setCommodityTypeId("commodity_type_id_1");
        expectedOrderable.setDispensableId("dispensble_id");
        expectedOrderable.setFullProductName("full_product_code_1");
        expectedOrderable.setId("id_1");
        expectedOrderable.setNetContent(12);
        expectedOrderable.setRoundToZero(true);
        expectedOrderable.setPackRoundingThreshold(3);
        expectedOrderable.setTradeItemId("trade_item_1");

        expectedOrderables.add(expectedOrderable);
        orderablesArr.put(mapper.writeValueAsString(expectedOrderable));

        // orderable 3
        expectedOrderable = new Orderable();
        expectedOrderable.setFullProductCode("full_product_code_2");
        expectedOrderable.setCommodityTypeId("commodity_type_id_2");
        expectedOrderable.setDispensableId("dispensble_id");
        expectedOrderable.setFullProductName("full_product_code_2");
        expectedOrderable.setId("id_2");
        expectedOrderable.setNetContent(12);
        expectedOrderable.setRoundToZero(true);
        expectedOrderable.setPackRoundingThreshold(5);
        expectedOrderable.setTradeItemId("trade_item_2");

        expectedOrderables.add(expectedOrderable);
        orderablesArr.put(mapper.writeValueAsString(expectedOrderable));

        JSONObject data = new JSONObject();
        data.put(ORDERABLES, orderablesArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");
        postRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        List<Object> actualOrderables = new ArrayList<>();
        for (Orderable orderable : repository.getAll()) {
            actualOrderables.add(orderable);
        }

        assertListsAreSameIgnoringOrder(expectedOrderables, actualOrderables);
    }

    @Test
    public void testPutShouldUpdateOrderablesInDb() throws Exception {

        // orderable 1
        Orderable orderable = new Orderable();

        orderable.setFullProductCode("full_product_code");
        orderable.setCommodityTypeId("commodity_type_id");
        orderable.setDispensableId("dispensble_id");
        orderable.setFullProductName("full_product_code");
        orderable.setId("id");
        orderable.setNetContent(18);
        orderable.setRoundToZero(true);
        orderable.setPackRoundingThreshold(2);
        orderable.setTradeItemId("trade_item");

        repository.addOrUpdate(orderable);

        // orderable 2
        Orderable expectedOrderable = new Orderable();

        expectedOrderable.setFullProductCode("full_product_code_1");
        expectedOrderable.setCommodityTypeId("commodity_type_id_1");
        expectedOrderable.setDispensableId("dispensble_id");
        expectedOrderable.setFullProductName("full_product_code_1");
        expectedOrderable.setId("id");
        expectedOrderable.setNetContent(12);
        expectedOrderable.setRoundToZero(true);
        expectedOrderable.setPackRoundingThreshold(3);
        expectedOrderable.setTradeItemId("trade_item_1");

        JSONArray orderablesArr = new JSONArray();
        orderablesArr.put(mapper.writeValueAsString(expectedOrderable));

        JSONObject data = new JSONObject();
        data.put(ORDERABLES, orderablesArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");

        putRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        orderable = repository.get("id");
        assertEquals(orderable.getFullProductCode(), expectedOrderable.getFullProductCode());
        assertEquals(orderable.getFullProductName(), expectedOrderable.getFullProductName());
        assertEquals(orderable.getCommodityTypeId(), expectedOrderable.getCommodityTypeId());
        assertEquals(orderable.getTradeItemId(), expectedOrderable.getTradeItemId());
    }


    public void assertListsAreSameIgnoringOrder(List<Object> expectedList, List<Object> actualList) {

        assertEquals(expectedList.size(), actualList.size());

        Set<String> expectedIds = new HashSet<>();
        for (Object expected : expectedList) {
            expectedIds.add(((Orderable) expected).getId());
        }
        for (Object actual : actualList) {
            assertTrue(expectedIds.contains(((Orderable) actual).getId()));
        }
    }
}
