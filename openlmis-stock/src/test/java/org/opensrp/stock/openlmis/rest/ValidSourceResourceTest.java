package org.opensrp.stock.openlmis.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.ValidSourceMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.opensrp.stock.openlmis.util.Utils.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class ValidSourceResourceTest extends BaseResourceTest {

    private final static String BASE_URL = "/rest/valid-sources/";

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
    public void testGetAllShouldReturnAllValidSources() throws Exception {

        List<Object> expectedValidSources = new ArrayList<>();

        ValidSourceMetaData expectedValidSource = new ValidSourceMetaData();
        expectedValidSource.setId("openlmis_uuid");
        expectedValidSource.setFacilityName("facility_name");
        expectedValidSource.setFacilityTypeUuid("facility_type_uuid");
        expectedValidSource.setOpenlmisUuid("openlmis_uuid");
        expectedValidSource.setProgramUuid("program_uuid");

        repository.add(expectedValidSource);
        expectedValidSources.add(expectedValidSource);

        expectedValidSource = new ValidSourceMetaData();
        expectedValidSource.setFacilityName("facility_name_1");
        expectedValidSource.setFacilityTypeUuid("facility_type_uuid_1");
        expectedValidSource.setOpenlmisUuid("openlmis_uuid_1");
        expectedValidSource.setId("openlmis_uuid_1");
        expectedValidSource.setProgramUuid("program_uuid_1");

        repository.add(expectedValidSource);
        expectedValidSources.add(expectedValidSource);

        List<Object> actualValidSources = getResponseAsList(BASE_URL, null, status().isOk());

        assertTwoListsAreSameIgnoringOrder(actualValidSources, expectedValidSources, false);
    }

    @Test
    public void testGetAllShouldReturnAllFilteredValidSources() throws Exception {

        List<Object> expectedValidSources = new ArrayList<>();

        ValidSourceMetaData expectedValidSource = new ValidSourceMetaData();
        expectedValidSource.setId("openlmis_uuid");
        expectedValidSource.setFacilityName("facility_name");
        expectedValidSource.setFacilityTypeUuid("facility_type_uuid");
        expectedValidSource.setOpenlmisUuid("openlmis_uuid");
        expectedValidSource.setProgramUuid("program_uuid");

        repository.add(expectedValidSource);
        expectedValidSources.add(expectedValidSource);

        expectedValidSource = new ValidSourceMetaData();
        expectedValidSource.setFacilityName("facility_name_1");
        expectedValidSource.setFacilityTypeUuid("facility_type_uuid_1");
        expectedValidSource.setOpenlmisUuid("openlmis_uuid_1");
        expectedValidSource.setId("openlmis_uuid_1");
        expectedValidSource.setProgramUuid("program_uuid_1");

        repository.add(expectedValidSource);

        List<Object> actualValidSources = getResponseAsList(BASE_URL, OPENLMIS_UUID + "=" + "openlmis_uuid" + "&" + FACILITY_TYPE_UUID + "=" + "facility_type_uuid", status().isOk());

        assertTwoListsAreSameIgnoringOrder(actualValidSources, expectedValidSources, false);
    }

    @Test
    public void testSyncShouldRetrieveAllValidSourcesAfterACertainTime() throws Exception {

        List<Object> expectedValidSources = new ArrayList<>();

        // this validSource should not sync
        ValidSourceMetaData expectedValidSource = new ValidSourceMetaData();
        expectedValidSource.setId("openlmis_uuid");
        expectedValidSource.setFacilityName("facility_name");
        expectedValidSource.setFacilityTypeUuid("facility_type_uuid");
        expectedValidSource.setOpenlmisUuid("openlmis_uuid");
        expectedValidSource.setProgramUuid("program_uuid");

        repository.add(expectedValidSource);

        // these validSources should sync
        long timeBefore = getCurrentTime();
        expectedValidSource = new ValidSourceMetaData();
        expectedValidSource.setFacilityName("facility_name_1");
        expectedValidSource.setFacilityTypeUuid("facility_type_uuid_1");
        expectedValidSource.setOpenlmisUuid("openlmis_uuid_1");
        expectedValidSource.setId("openlmis_uuid_1");
        expectedValidSource.setProgramUuid("program_uuid");
        expectedValidSource.setServerVersion(timeBefore + 1);

        repository.add(expectedValidSource);
        expectedValidSources.add(expectedValidSource);

        expectedValidSource = new ValidSourceMetaData();
        expectedValidSource.setFacilityName("facility_name_2");
        expectedValidSource.setFacilityTypeUuid("facility_type_uuid_2");
        expectedValidSource.setOpenlmisUuid("openlmis_uuid_2");
        expectedValidSource.setId("openlmis_uuid_2");
        expectedValidSource.setProgramUuid("program_uuid");
        expectedValidSource.setServerVersion(timeBefore + 2);

        repository.add(expectedValidSource);
        expectedValidSources.add(expectedValidSource);

        List<Object> actualValidSources = getResponseAsList(BASE_URL + "sync", SYNC_SERVER_VERSION + "=" + timeBefore, status().isOk());

        assertTwoListsAreSameIgnoringOrder(expectedValidSources, actualValidSources, true);
    }

    @Test
    public void testPostShouldCreateNewValidSourcesInDb() throws Exception {

        List<Object> expectedValidSources = new ArrayList<>();
        JSONArray validSourcesArr = new JSONArray();

        // validSource 1
        ValidSourceMetaData expectedValidSource = new ValidSourceMetaData();
        expectedValidSource.setId("openlmis_uuid");
        expectedValidSource.setFacilityName("facility_name");
        expectedValidSource.setFacilityTypeUuid("facility_type_uuid");
        expectedValidSource.setOpenlmisUuid("openlmis_uuid");
        expectedValidSource.setProgramUuid("program_uuid");

        validSourcesArr.put(mapper.writeValueAsString(expectedValidSource));
        expectedValidSources.add(expectedValidSource);

        // validSource 2
        expectedValidSource = new ValidSourceMetaData();
        expectedValidSource.setFacilityName("facility_name_1");
        expectedValidSource.setFacilityTypeUuid("facility_type_uuid_1");
        expectedValidSource.setOpenlmisUuid("openlmis_uuid_1");
        expectedValidSource.setId("openlmis_uuid_1");
        expectedValidSource.setProgramUuid("program_uuid_1");

        validSourcesArr.put(mapper.writeValueAsString(expectedValidSource));
        expectedValidSources.add(expectedValidSource);

        // validSource 3
        expectedValidSource = new ValidSourceMetaData();
        expectedValidSource.setFacilityName("facility_name_2");
        expectedValidSource.setFacilityTypeUuid("facility_type_uuid_2");
        expectedValidSource.setOpenlmisUuid("openlmis_uuid_2");
        expectedValidSource.setId("openlmis_uuid_2");
        expectedValidSource.setProgramUuid("program_uuid_2");

        validSourcesArr.put(mapper.writeValueAsString(expectedValidSource));
        expectedValidSources.add(expectedValidSource);

        JSONObject data = new JSONObject();
        data.put(VALID_SOURCES, validSourcesArr);
        String dataString =
                data.toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");
        postRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        List<Object> actualValidSources = new ArrayList<>();
        for (MasterTableEntry entry : repository.getAll()) {
            actualValidSources.add(entry.getJson());
        }

        assertTwoListsAreSameIgnoringOrder(expectedValidSources, actualValidSources, false);
    }

    @Test
    public void testPutShouldUpdateValidSourcesInDb() throws Exception {

        // validSource 1
        ValidSourceMetaData validSource = new ValidSourceMetaData();
        validSource.setId("openlmis_uuid");
        validSource.setFacilityName("facility_name");
        validSource.setFacilityTypeUuid("facility_type_uuid");
        validSource.setOpenlmisUuid("openlmis_uuid");
        validSource.setProgramUuid("program_uuid");

        MasterTableEntry entry = repository.add(validSource);

        // ValidSource 2
        ValidSourceMetaData expectedValidSource = new ValidSourceMetaData();
        expectedValidSource.setFacilityName("facility_name_2");
        expectedValidSource.setFacilityTypeUuid("facility_type_uuid_2");
        expectedValidSource.setOpenlmisUuid("openlmis_uuid_2");
        expectedValidSource.setId("openlmis_uuid");
        expectedValidSource.setProgramUuid("program_uuid_2");

        JSONArray validSourcesArr = new JSONArray();
        validSourcesArr.put(mapper.writeValueAsString(expectedValidSource));

        JSONObject data = new JSONObject();
        data.put(VALID_SOURCES, validSourcesArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");

        putRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        validSource = (ValidSourceMetaData) repository.get(entry.getId()).getJson();
        assertEquals(expectedValidSource.getId(), validSource.getId());
        assertEquals(expectedValidSource.getFacilityName(), validSource.getFacilityName());
        assertEquals(expectedValidSource.getFacilityTypeUuid(), validSource.getFacilityTypeUuid());
        assertEquals(expectedValidSource.getOpenlmisUuid(), validSource.getOpenlmisUuid());
    }
}

