package org.opensrp.stock.openlmis.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.Gtin;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.DispensableMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.opensrp.stock.openlmis.util.Utils.DISPENSABLES;
import static org.opensrp.stock.openlmis.util.Utils.SYNC_SERVER_VERSION;
import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class DispensableResourceTest extends BaseResourceTest {

    private final static String BASE_URL = "/rest/dispensables/";

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
    public void testGetAllShouldReturnAllDispensables() throws Exception {

        List<Object> expectedDispensables = new ArrayList<>();

        DispensableMetaData expectedDispensable = new DispensableMetaData(
                "identifier"
        );
        expectedDispensable.setKeySizeCode("size_code");
        expectedDispensable.setKeyRouteOfAdministration("route_of_administration");
        expectedDispensable.setKeyDispensingUnit("dispensing_unit");
        repository.add(expectedDispensable);
        expectedDispensables.add(expectedDispensable);

        expectedDispensable = new DispensableMetaData(
                "identifier_1"
        );
        expectedDispensable.setKeySizeCode("size_code_1");
        expectedDispensable.setKeyRouteOfAdministration("route_of_administration_1");
        expectedDispensable.setKeyDispensingUnit("dispensing_unit_1");
        repository.add(expectedDispensable);
        expectedDispensables.add(expectedDispensable);

        List<Object> actualDispensables = getResponseAsList(BASE_URL, null, status().isOk());

        assertTwoListsAreSameIgnoringOrder(actualDispensables, expectedDispensables, false);
    }

    @Test
    public void testSyncShouldRetrieveAllDispensablesAfterACertainTime() throws Exception {

        List<Object> expectedDispensables = new ArrayList<>();

        // this dispensable should not sync
        DispensableMetaData expectedDispensable = new DispensableMetaData(
                "identifier"
        );
        expectedDispensable.setKeySizeCode("size_code");
        expectedDispensable.setKeyRouteOfAdministration("route_of_administration");
        expectedDispensable.setKeyDispensingUnit("dispensing_unit");
        repository.add(expectedDispensable);

        // these trade items should sync
        long timeBefore = getCurrentTime();
        expectedDispensable = new DispensableMetaData(
                "identifier_1"
        );
        expectedDispensable.setKeySizeCode("size_code_1");
        expectedDispensable.setKeyRouteOfAdministration("route_of_administration_1");
        expectedDispensable.setKeyDispensingUnit("dispensing_unit_1");

        repository.add(expectedDispensable);
        expectedDispensables.add(expectedDispensable);

        expectedDispensable = new DispensableMetaData(
                "identifier_2"
        );
        expectedDispensable.setKeySizeCode("size_code_2");
        expectedDispensable.setKeyRouteOfAdministration("route_of_administration_2");
        expectedDispensable.setKeyDispensingUnit("dispensing_unit_2");

        repository.add(expectedDispensable);
        expectedDispensables.add(expectedDispensable);

        List<Object> actualDispensables = getResponseAsList(BASE_URL + "sync", SYNC_SERVER_VERSION + "=" + timeBefore, status().isOk());

        assertTwoListsAreSameIgnoringOrder(expectedDispensables, actualDispensables,true);
    }

    @Test
    public void testPostShouldCreateNewDispensablesInDb() throws Exception {

        DispensableMetaData dispensableMetaData = new DispensableMetaData();
        dispensableMetaData.setId("dispensable_uuid");
        dispensableMetaData.setKeyDispensingUnit("key_dispensing_unit");
        dispensableMetaData.setKeyRouteOfAdministration("route_of_administration");
        dispensableMetaData.setKeySizeCode("size_code");

        List<Object> expectedDispensables = new ArrayList<>();
        JSONArray dispensablesArr = new JSONArray();

        // dispensable 1
        DispensableMetaData expectedDispensable = new DispensableMetaData(
                "identifier"
        );
        expectedDispensable.setKeySizeCode("size_code");
        expectedDispensable.setKeyRouteOfAdministration("route_of_administration");
        expectedDispensable.setKeyDispensingUnit("dispensing_unit");

        expectedDispensables.add(expectedDispensable);
        dispensablesArr.put(mapper.writeValueAsString(expectedDispensable));

        // trade item 2
        expectedDispensable = new DispensableMetaData(
                "identifier_1"
        );
        expectedDispensable.setKeySizeCode("size_code_1");
        expectedDispensable.setKeyRouteOfAdministration("route_of_administration_1");
        expectedDispensable.setKeyDispensingUnit("dispensing_unit_1");

        expectedDispensables.add(expectedDispensable);
        dispensablesArr.put(mapper.writeValueAsString(expectedDispensable));

        // trade item 3
        expectedDispensable = new DispensableMetaData(
                "identifier_2"
        );
        expectedDispensable.setKeySizeCode("size_code_2");
        expectedDispensable.setKeyRouteOfAdministration("route_of_administration_2");
        expectedDispensable.setKeyDispensingUnit("dispensing_unit_2");

        expectedDispensables.add(expectedDispensable);
        dispensablesArr.put(mapper.writeValueAsString(expectedDispensable));

        JSONObject data = new JSONObject();
        data.put(DISPENSABLES, dispensablesArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");
        postRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        List<Object> actualDispensables = new ArrayList<>();
        for (MasterTableEntry entry : repository.getAll()) {
            actualDispensables.add(entry.getJson());
        }

        assertTwoListsAreSameIgnoringOrder(expectedDispensables, actualDispensables, false);
    }


    @Test
    public void testPutShouldUpdateDispensablesInDb() throws Exception {
        // Dispensable 1
        DispensableMetaData dispensable = new DispensableMetaData(
                "identifier"
        );
        dispensable.setKeySizeCode("size_code");
        dispensable.setKeyRouteOfAdministration("route_of_administration");
        dispensable.setKeyDispensingUnit("dispensing_unit");

        MasterTableEntry entry = repository.add(dispensable);

        // updated Dispensable
        DispensableMetaData expectedDispensable = new DispensableMetaData(
                "identifier"
        );
        expectedDispensable.setKeySizeCode("size_code_1");
        expectedDispensable.setKeyRouteOfAdministration("route_of_administration_1");
        expectedDispensable.setKeyDispensingUnit("dispensing_unit");

        JSONArray dispensablesArr = new JSONArray();
        dispensablesArr.put(mapper.writeValueAsString(expectedDispensable));

        JSONObject data = new JSONObject();
        data.put(DISPENSABLES, dispensablesArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");

        putRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        dispensable = (DispensableMetaData) repository.get(entry.getId()).getJson();
        assertEquals(expectedDispensable.getId(), dispensable.getId());
        assertEquals(expectedDispensable.getKeyDispensingUnit(), dispensable.getKeyDispensingUnit());
        assertEquals(expectedDispensable.getKeyRouteOfAdministration(), dispensable.getKeyRouteOfAdministration());
        assertEquals(expectedDispensable.getKeySizeCode(), dispensable.getKeySizeCode());
    }
}
