package org.opensrp.stock.openlmis.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.Lot;
import org.opensrp.stock.openlmis.repository.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.opensrp.stock.openlmis.util.Utils.SYNC_SERVER_VERSION;
import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class LotResourceTest extends BaseResourceTest {

    private final static String BASE_URL = "/rest/lots/";

    @Autowired
    private LotRepository repository;

    @Autowired
    private LotResource lotmResource;

    @Before
    public void bootStrap() {
        truncateTable("core.lot");
    }

    @After
    public void tearDown() {
        truncateTable("core.lot");
    }

    @Test
    public void testGetAllShouldReturnAllLots() throws Exception {

        List<Object> expectedLots = new ArrayList<>();

        Lot expectedLot = new Lot();
        expectedLot.setActive(false);
        expectedLot.setId("id");
        expectedLot.setTradeItemId("trade_item_id");
        expectedLot.setManufuctureDate(getCurrentTime() - 20000L);
        expectedLot.setExpirationDate(getCurrentTime() - 20000L);
        expectedLot.setLotCode("lot_code");
        expectedLot.setTradeItemId("trade_item");

        repository.add(expectedLot);
        expectedLots.add(expectedLot);

        expectedLot = new Lot();
        expectedLot.setActive(true);
        expectedLot.setExpirationDate(getCurrentTime());
        expectedLot.setId("id_1");
        expectedLot.setLotCode("lot_code_2");
        expectedLot.setManufuctureDate(getCurrentTime() - 20000L);
        expectedLot.setTradeItemId("trade_item_id_2");

        repository.add(expectedLot);
        expectedLots.add(expectedLot);

        String actualLotsString = getResponseAsString(BASE_URL, null, status().isOk());
        List<Object> actualLots = new Gson().fromJson(actualLotsString, new TypeToken<List<Lot>>(){}.getType());

        assertListsAreSameIgnoringOrder(actualLots, expectedLots);
    }

    @Test
    public void testSyncShouldRetrieveAllLotsAfterACertainTime() throws Exception {

        List<Object> expectedLots = new ArrayList<>();

        // this trade item should not sync

        // lot 1
        Lot expectedLot = new Lot();
        expectedLot.setActive(false);
        expectedLot.setId("id");
        expectedLot.setTradeItemId("trade_item_id");
        expectedLot.setManufuctureDate(getCurrentTime() - 20000L);
        expectedLot.setExpirationDate(getCurrentTime() - 20000L);
        expectedLot.setLotCode("lot_code");
        expectedLot.setTradeItemId("trade_item");

        repository.add(expectedLot);

        // these trade items should sync
        long timeBefore = getCurrentTime();

        // lot 2
        expectedLot = new Lot();
        expectedLot.setActive(true);
        expectedLot.setExpirationDate(getCurrentTime());
        expectedLot.setId("id_1");
        expectedLot.setLotCode("lot_code_1");
        expectedLot.setManufuctureDate(getCurrentTime() - 20000L);
        expectedLot.setTradeItemId("trade_item_id_1");

        repository.add(expectedLot);
        expectedLots.add(expectedLot);

        // lot 3
        expectedLot = new Lot();
        expectedLot.setActive(false);
        expectedLot.setExpirationDate(getCurrentTime());
        expectedLot.setId("id_2");
        expectedLot.setLotCode("lot_code_2");
        expectedLot.setManufuctureDate(getCurrentTime() - 20000L);
        expectedLot.setTradeItemId("trade_item_id_2");

        repository.add(expectedLot);
        expectedLots.add(expectedLot);

        String actualLotsString = getResponseAsString(BASE_URL + "sync", SYNC_SERVER_VERSION + "=" + timeBefore, status().isOk());
        List<Object> actualLots = new Gson().fromJson(actualLotsString, new TypeToken<List<Lot>>(){}.getType());

        assertListsAreSameIgnoringOrder(actualLots, expectedLots);
    }

    @Test
    public void testPostShouldCreateNewLotsInDb() throws Exception {

        List<Object> expectedLots = new ArrayList<>();
        JSONArray lotsArr = new JSONArray();

        // lot 1
        Lot expectedLot = new Lot();
        expectedLot.setActive(false);
        expectedLot.setId("id");
        expectedLot.setTradeItemId("trade_item_id");
        expectedLot.setManufuctureDate(getCurrentTime() - 20000L);
        expectedLot.setExpirationDate(getCurrentTime() - 20000L);
        expectedLot.setLotCode("lot_code");
        expectedLot.setTradeItemId("trade_item");

        expectedLots.add(expectedLot);
        lotsArr.put(mapper.writeValueAsString(expectedLot));

        // lot 2
        expectedLot = new Lot();
        expectedLot.setActive(true);
        expectedLot.setExpirationDate(getCurrentTime());
        expectedLot.setId("id_1");
        expectedLot.setLotCode("lot_code_1");
        expectedLot.setManufuctureDate(getCurrentTime() - 20000L);
        expectedLot.setTradeItemId("trade_item_id_1");

        expectedLots.add(expectedLot);
        lotsArr.put(mapper.writeValueAsString(expectedLot));

        // lot 3
        expectedLot = new Lot();
        expectedLot.setActive(false);
        expectedLot.setExpirationDate(getCurrentTime());
        expectedLot.setId("id_2");
        expectedLot.setLotCode("lot_code_2");
        expectedLot.setManufuctureDate(getCurrentTime() - 20000L);
        expectedLot.setTradeItemId("trade_item_id_2");

        expectedLots.add(expectedLot);
        lotsArr.put(mapper.writeValueAsString(expectedLot));

        JSONObject data = new JSONObject();
        data.put("lots", lotsArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");
        postRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        List<Object> actualLots = new ArrayList<>();
        for (Lot lot : repository.getAll()) {
            actualLots.add(lot);
        }

        assertListsAreSameIgnoringOrder(expectedLots, actualLots);
    }

    public void assertListsAreSameIgnoringOrder(List<Object> expectedList, List<Object> actualList) {

        assertEquals(expectedList.size(), actualList.size());

        Set<String> expectedIds = new HashSet<>();
        for (Object expected : expectedList) {
            expectedIds.add(((Lot) expected).getId());
        }
        for (Object actual : actualList) {
            assertTrue(expectedIds.contains(((Lot) actual).getId()));
        }
    }
}
