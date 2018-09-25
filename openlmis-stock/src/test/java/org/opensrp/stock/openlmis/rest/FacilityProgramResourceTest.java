package org.opensrp.stock.openlmis.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.FacilityProgramMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.opensrp.stock.openlmis.util.Utils.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class FacilityProgramResourceTest extends BaseResourceTest {

        private final static String BASE_URL = "/rest/facility-programs/";

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
        public void testGetAllShouldReturnAllFacilityPrograms() throws Exception {

            List<Object> expectedFacilityPrograms = new ArrayList<>();

            FacilityProgramMetaData expectedFacilityProgram = new FacilityProgramMetaData();
            expectedFacilityProgram.setId("openlmis_uuid");
            expectedFacilityProgram.setFacilityName("facility_name");
            expectedFacilityProgram.setFacilityTypeUuid("facility_type_uuid");
            expectedFacilityProgram.setOpenlmisUuid("openlmis_uuid");

            repository.add(expectedFacilityProgram);
            expectedFacilityPrograms.add(expectedFacilityProgram);

            expectedFacilityProgram = new FacilityProgramMetaData();
            expectedFacilityProgram.setFacilityName("facility_name_1");
            expectedFacilityProgram.setFacilityTypeUuid("facility_type_uuid_1");
            expectedFacilityProgram.setOpenlmisUuid("openlmis_uuid_1");
            expectedFacilityProgram.setId("openlmis_uuid_1");

            repository.add(expectedFacilityProgram);
            expectedFacilityPrograms.add(expectedFacilityProgram);

            List<Object> actualFacilityPrograms = getResponseAsList(BASE_URL, null, status().isOk());

            assertTwoListsAreSameIgnoringOrder(actualFacilityPrograms, expectedFacilityPrograms, false);
        }

        @Test
        public void testSyncShouldRetrieveAllFacilityProgramsAfterACertainTime() throws Exception {

            List<Object> expectedFacilityPrograms = new ArrayList<>();

            // this facilityProgram should not sync
            FacilityProgramMetaData expectedFacilityProgram = new FacilityProgramMetaData();
            expectedFacilityProgram.setId("openlmis_uuid");
            expectedFacilityProgram.setFacilityName("facility_name");
            expectedFacilityProgram.setFacilityTypeUuid("facility_type_uuid");
            expectedFacilityProgram.setOpenlmisUuid("openlmis_uuid");

            repository.add(expectedFacilityProgram);

            // these facilityPrograms should sync
            long timeBefore = getCurrentTime();
            expectedFacilityProgram = new FacilityProgramMetaData();
            expectedFacilityProgram.setFacilityName("facility_name_1");
            expectedFacilityProgram.setFacilityTypeUuid("facility_type_uuid_1");
            expectedFacilityProgram.setOpenlmisUuid("openlmis_uuid_1");
            expectedFacilityProgram.setId("openlmis_uuid_1");
            expectedFacilityProgram.setServerVersion(timeBefore + 1);

            repository.add(expectedFacilityProgram);
            expectedFacilityPrograms.add(expectedFacilityProgram);

            expectedFacilityProgram = new FacilityProgramMetaData();
            expectedFacilityProgram.setFacilityName("facility_name_2");
            expectedFacilityProgram.setFacilityTypeUuid("facility_type_uuid_2");
            expectedFacilityProgram.setOpenlmisUuid("openlmis_uuid_2");
            expectedFacilityProgram.setId("openlmis_uuid_2");
            expectedFacilityProgram.setServerVersion(timeBefore + 2);

            repository.add(expectedFacilityProgram);
            expectedFacilityPrograms.add(expectedFacilityProgram);

            List<Object> actualFacilityPrograms = getResponseAsList(BASE_URL + "sync", SYNC_SERVER_VERSION + "=" + timeBefore, status().isOk());

            assertTwoListsAreSameIgnoringOrder(expectedFacilityPrograms, actualFacilityPrograms, true);
        }

        @Test
        public void testPostShouldCreateNewFacilityProgramsInDb() throws Exception {

            List<Object> expectedFacilityPrograms = new ArrayList<>();
            JSONArray facilityProgramsArr = new JSONArray();

            // facilityProgram 1
            FacilityProgramMetaData expectedFacilityProgram = new FacilityProgramMetaData();
            expectedFacilityProgram.setId("openlmis_uuid");
            expectedFacilityProgram.setFacilityName("facility_name");
            expectedFacilityProgram.setFacilityTypeUuid("facility_type_uuid");
            expectedFacilityProgram.setOpenlmisUuid("openlmis_uuid");

            facilityProgramsArr.put(mapper.writeValueAsString(expectedFacilityProgram));
            expectedFacilityPrograms.add(expectedFacilityProgram);

            // facilityProgram 2
            expectedFacilityProgram = new FacilityProgramMetaData();
            expectedFacilityProgram.setFacilityName("facility_name_1");
            expectedFacilityProgram.setFacilityTypeUuid("facility_type_uuid_1");
            expectedFacilityProgram.setOpenlmisUuid("openlmis_uuid_1");
            expectedFacilityProgram.setId("openlmis_uuid_1");

            facilityProgramsArr.put(mapper.writeValueAsString(expectedFacilityProgram));
            expectedFacilityPrograms.add(expectedFacilityProgram);

            // facilityProgram 3
            expectedFacilityProgram = new FacilityProgramMetaData();
            expectedFacilityProgram.setFacilityName("facility_name_2");
            expectedFacilityProgram.setFacilityTypeUuid("facility_type_uuid_2");
            expectedFacilityProgram.setOpenlmisUuid("openlmis_uuid_2");
            expectedFacilityProgram.setId("openlmis_uuid_2");

            facilityProgramsArr.put(mapper.writeValueAsString(expectedFacilityProgram));
            expectedFacilityPrograms.add(expectedFacilityProgram);

            JSONObject data = new JSONObject();
            data.put(FACILITY_PROGRAMS, facilityProgramsArr);
            String dataString =
                    data.toString()
                            .replace("\"{", "{")
                            .replace("}\"", "}")
                            .replace("\\", "")
                            .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");
            postRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

            List<Object> actualFacilityPrograms = new ArrayList<>();
            for (MasterTableEntry entry : repository.getAll()) {
                actualFacilityPrograms.add(entry.getJson());
            }

            assertTwoListsAreSameIgnoringOrder(expectedFacilityPrograms, actualFacilityPrograms, false);
        }

        @Test
        public void testPutShouldUpdateFacilityProgramsInDb() throws Exception {

            // facilityProgram 1
            FacilityProgramMetaData facilityProgram = new FacilityProgramMetaData();
            facilityProgram.setId("openlmis_uuid");
            facilityProgram.setFacilityName("facility_name");
            facilityProgram.setFacilityTypeUuid("facility_type_uuid");
            facilityProgram.setOpenlmisUuid("openlmis_uuid");
            
            MasterTableEntry entry = repository.add(facilityProgram);

            // FacilityProgram 2
            FacilityProgramMetaData expectedFacilityProgram = new FacilityProgramMetaData();
            expectedFacilityProgram.setFacilityName("facility_name_2");
            expectedFacilityProgram.setFacilityTypeUuid("facility_type_uuid_2");
            expectedFacilityProgram.setOpenlmisUuid("openlmis_uuid_2");
            expectedFacilityProgram.setId("openlmis_uuid");

            JSONArray facilityProgramsArr = new JSONArray();
            facilityProgramsArr.put(mapper.writeValueAsString(expectedFacilityProgram));

            JSONObject data = new JSONObject();
            data.put(FACILITY_PROGRAMS, facilityProgramsArr);
            String dataString =
                    data
                            .toString()
                            .replace("\"{", "{")
                            .replace("}\"", "}")
                            .replace("\\", "")
                            .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");

            putRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

            facilityProgram = (FacilityProgramMetaData) repository.get(entry.getId()).getJson();
            assertEquals(expectedFacilityProgram.getId(), facilityProgram.getId());
            assertEquals(expectedFacilityProgram.getFacilityName(), facilityProgram.getFacilityName());
            assertEquals(expectedFacilityProgram.getFacilityTypeUuid(), facilityProgram.getFacilityTypeUuid());
            assertEquals(expectedFacilityProgram.getOpenlmisUuid(), facilityProgram.getOpenlmisUuid());
        }
}

