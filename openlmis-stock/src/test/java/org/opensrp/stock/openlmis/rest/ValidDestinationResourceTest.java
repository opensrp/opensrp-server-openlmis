package org.opensrp.stock.openlmis.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.ValidDestinationMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.opensrp.stock.openlmis.util.Utils.SYNC_SERVER_VERSION;
import static org.opensrp.stock.openlmis.util.Utils.VALID_DESTINATIONS;
import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class ValidDestinationResourceTest extends BaseResourceTest {

    private final static String BASE_URL = "/rest/valid-destinations/";

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
    public void testGetAllShouldReturnAllValidDestinations() throws Exception {

        List<Object> expectedValidDestinations = new ArrayList<>();

        ValidDestinationMetaData expectedValidDestination = new ValidDestinationMetaData();
        expectedValidDestination.setId("openlmis_uuid");
        expectedValidDestination.setFacilityName("facility_name");
        expectedValidDestination.setFacilityTypeUuid("facility_type_uuid");
        expectedValidDestination.setOpenlmisUuid("openlmis_uuid");
        expectedValidDestination.setProgramUuid("program_uuid");

        repository.add(expectedValidDestination);
        expectedValidDestinations.add(expectedValidDestination);

        expectedValidDestination = new ValidDestinationMetaData();
        expectedValidDestination.setFacilityName("facility_name_1");
        expectedValidDestination.setFacilityTypeUuid("facility_type_uuid_1");
        expectedValidDestination.setOpenlmisUuid("openlmis_uuid_1");
        expectedValidDestination.setId("openlmis_uuid_1");
        expectedValidDestination.setProgramUuid("program_uuid_1");

        repository.add(expectedValidDestination);
        expectedValidDestinations.add(expectedValidDestination);

        List<Object> actualValidDestinations = getResponseAsList(BASE_URL, null, status().isOk());

        assertTwoListsAreSameIgnoringOrder(actualValidDestinations, expectedValidDestinations, false);
    }

    @Test
    public void testSyncShouldRetrieveAllValidDestinationsAfterACertainTime() throws Exception {

        List<Object> expectedValidDestinations = new ArrayList<>();

        // this validDestination should not sync
        ValidDestinationMetaData expectedValidDestination = new ValidDestinationMetaData();
        expectedValidDestination.setId("openlmis_uuid");
        expectedValidDestination.setFacilityName("facility_name");
        expectedValidDestination.setFacilityTypeUuid("facility_type_uuid");
        expectedValidDestination.setOpenlmisUuid("openlmis_uuid");
        expectedValidDestination.setProgramUuid("program_uuid");

        repository.add(expectedValidDestination);

        // these validDestinations should sync
        long timeBefore = getCurrentTime();
        expectedValidDestination = new ValidDestinationMetaData();
        expectedValidDestination.setFacilityName("facility_name_1");
        expectedValidDestination.setFacilityTypeUuid("facility_type_uuid_1");
        expectedValidDestination.setOpenlmisUuid("openlmis_uuid_1");
        expectedValidDestination.setId("openlmis_uuid_1");
        expectedValidDestination.setProgramUuid("program_uuid");
        expectedValidDestination.setServerVersion(timeBefore + 1);

        repository.add(expectedValidDestination);
        expectedValidDestinations.add(expectedValidDestination);

        expectedValidDestination = new ValidDestinationMetaData();
        expectedValidDestination.setFacilityName("facility_name_2");
        expectedValidDestination.setFacilityTypeUuid("facility_type_uuid_2");
        expectedValidDestination.setOpenlmisUuid("openlmis_uuid_2");
        expectedValidDestination.setId("openlmis_uuid_2");
        expectedValidDestination.setProgramUuid("program_uuid");
        expectedValidDestination.setServerVersion(timeBefore + 2);

        repository.add(expectedValidDestination);
        expectedValidDestinations.add(expectedValidDestination);

        List<Object> actualValidDestinations = getResponseAsList(BASE_URL + "sync", SYNC_SERVER_VERSION + "=" + timeBefore, status().isOk());

        assertTwoListsAreSameIgnoringOrder(expectedValidDestinations, actualValidDestinations, true);
    }

    @Test
    public void testPostShouldCreateNewValidDestinationsInDb() throws Exception {

        List<Object> expectedValidDestinations = new ArrayList<>();
        JSONArray validDestinationsArr = new JSONArray();

        // validDestination 1
        ValidDestinationMetaData expectedValidDestination = new ValidDestinationMetaData();
        expectedValidDestination.setId("openlmis_uuid");
        expectedValidDestination.setFacilityName("facility_name");
        expectedValidDestination.setFacilityTypeUuid("facility_type_uuid");
        expectedValidDestination.setOpenlmisUuid("openlmis_uuid");
        expectedValidDestination.setProgramUuid("program_uuid");

        validDestinationsArr.put(mapper.writeValueAsString(expectedValidDestination));
        expectedValidDestinations.add(expectedValidDestination);

        // validDestination 2
        expectedValidDestination = new ValidDestinationMetaData();
        expectedValidDestination.setFacilityName("facility_name_1");
        expectedValidDestination.setFacilityTypeUuid("facility_type_uuid_1");
        expectedValidDestination.setOpenlmisUuid("openlmis_uuid_1");
        expectedValidDestination.setId("openlmis_uuid_1");
        expectedValidDestination.setProgramUuid("program_uuid_1");

        validDestinationsArr.put(mapper.writeValueAsString(expectedValidDestination));
        expectedValidDestinations.add(expectedValidDestination);

        // validDestination 3
        expectedValidDestination = new ValidDestinationMetaData();
        expectedValidDestination.setFacilityName("facility_name_2");
        expectedValidDestination.setFacilityTypeUuid("facility_type_uuid_2");
        expectedValidDestination.setOpenlmisUuid("openlmis_uuid_2");
        expectedValidDestination.setId("openlmis_uuid_2");
        expectedValidDestination.setProgramUuid("program_uuid_2");

        validDestinationsArr.put(mapper.writeValueAsString(expectedValidDestination));
        expectedValidDestinations.add(expectedValidDestination);

        JSONObject data = new JSONObject();
        data.put(VALID_DESTINATIONS, validDestinationsArr);
        String dataString =
                data.toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");
        postRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        List<Object> actualValidDestinations = new ArrayList<>();
        for (MasterTableEntry entry : repository.getAll()) {
            actualValidDestinations.add(entry.getJson());
        }

        assertTwoListsAreSameIgnoringOrder(expectedValidDestinations, actualValidDestinations, false);
    }

    @Test
    public void testPutShouldUpdateValidDestinationsInDb() throws Exception {

        // validDestination 1
        ValidDestinationMetaData validDestination = new ValidDestinationMetaData();
        validDestination.setId("openlmis_uuid");
        validDestination.setFacilityName("facility_name");
        validDestination.setFacilityTypeUuid("facility_type_uuid");
        validDestination.setOpenlmisUuid("openlmis_uuid");
        validDestination.setProgramUuid("program_uuid");

        MasterTableEntry entry = repository.add(validDestination);

        // ValidDestination 2
        ValidDestinationMetaData expectedValidDestination = new ValidDestinationMetaData();
        expectedValidDestination.setFacilityName("facility_name_2");
        expectedValidDestination.setFacilityTypeUuid("facility_type_uuid_2");
        expectedValidDestination.setOpenlmisUuid("openlmis_uuid_2");
        expectedValidDestination.setId("openlmis_uuid");
        expectedValidDestination.setProgramUuid("program_uuid_2");

        JSONArray validDestinationsArr = new JSONArray();
        validDestinationsArr.put(mapper.writeValueAsString(expectedValidDestination));

        JSONObject data = new JSONObject();
        data.put(VALID_DESTINATIONS, validDestinationsArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");

        putRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        validDestination = (ValidDestinationMetaData) repository.get(entry.getId()).getJson();
        assertEquals(expectedValidDestination.getId(), validDestination.getId());
        assertEquals(expectedValidDestination.getFacilityName(), validDestination.getFacilityName());
        assertEquals(expectedValidDestination.getFacilityTypeUuid(), validDestination.getFacilityTypeUuid());
        assertEquals(expectedValidDestination.getOpenlmisUuid(), validDestination.getOpenlmisUuid());
    }
    
}
