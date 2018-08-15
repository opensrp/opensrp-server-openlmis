package org.opensrp.stock.openlmis.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.Code;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.ProgramMetaData;
import org.opensrp.stock.openlmis.domain.metadata.ReasonMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.opensrp.stock.openlmis.util.Utils.SYNC_SERVER_VERSION;
import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class ReasonResourceTest extends BaseResourceTest {

    private final static String BASE_URL = "/rest/reasons/";

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
    public void testGetAllShouldReturnAllReasons() throws Exception {

        List<Object> expectedReasons = new ArrayList<>();

        ReasonMetaData expectedReason = new ReasonMetaData();
        expectedReason.setAdditive(true);
        expectedReason.setDescription("description");
        expectedReason.setName("name");
        expectedReason.setId("id");
        setProgram(expectedReason);

        repository.add(expectedReason);
        expectedReasons.add(expectedReason);

        expectedReason = new ReasonMetaData();
        expectedReason.setAdditive(true);
        expectedReason.setDescription("description_1");
        expectedReason.setName("name_1");
        expectedReason.setId("id_1");
        setProgram(expectedReason);

        repository.add(expectedReason);
        expectedReasons.add(expectedReason);

        List<Object> actualReasons = getResponseAsList(BASE_URL, null, status().isOk());

        assertTwoListsAreSameIgnoringOrder(actualReasons, expectedReasons, false);
    }

    @Test
    public void testSyncShouldRetrieveAllReasonsAfterACertainTime() throws Exception {

        List<Object> expectedReasons = new ArrayList<>();

        // this commodity type should not sync

        // commodity type 1
        ReasonMetaData expectedReason = new ReasonMetaData();
        expectedReason.setAdditive(true);
        expectedReason.setDescription("description");
        expectedReason.setName("name");
        expectedReason.setId("id");
        setProgram(expectedReason);

        repository.add(expectedReason);

        // these commodity types should sync

        // commodity type 2
        long timeBefore = getCurrentTime();
        expectedReason = new ReasonMetaData();
        expectedReason.setAdditive(true);
        expectedReason.setDescription("description_1");
        expectedReason.setName("name_1");
        expectedReason.setId("id_1");
        setProgram(expectedReason);

        repository.add(expectedReason);
        expectedReasons.add(expectedReason);

        // commodity type 3
        expectedReason = new ReasonMetaData();
        expectedReason.setAdditive(true);
        expectedReason.setDescription("description_2");
        expectedReason.setName("name_2");
        expectedReason.setId("id_2");
        setProgram(expectedReason);

        repository.add(expectedReason);
        expectedReasons.add(expectedReason);


        List<Object> actualReasons = getResponseAsList(BASE_URL + "sync", SYNC_SERVER_VERSION + "=" + timeBefore, status().isOk());

        assertTwoListsAreSameIgnoringOrder(expectedReasons, actualReasons,true);
    }

    @Test
    public void testPostShouldCreateNewReasonsInDb() throws Exception {

        List<Object> expectedReasons = new ArrayList<>();
        JSONArray reasonsArr = new JSONArray();
        // commodity type 1
        ReasonMetaData expectedReason = new ReasonMetaData();
        expectedReason.setAdditive(true);
        expectedReason.setDescription("description");
        expectedReason.setName("name");
        expectedReason.setId("id");
        setProgram(expectedReason);

        expectedReasons.add(expectedReason);
        reasonsArr.put(mapper.writeValueAsString(expectedReason));

        // commodity type 2
        expectedReason = new ReasonMetaData();
        expectedReason.setAdditive(true);
        expectedReason.setDescription("description_1");
        expectedReason.setName("name_1");
        expectedReason.setId("id_1");
        setProgram(expectedReason);

        expectedReasons.add(expectedReason);
        reasonsArr.put(mapper.writeValueAsString(expectedReason));

        // commodity type 3
        expectedReason = new ReasonMetaData();
        expectedReason.setAdditive(true);
        expectedReason.setDescription("description_2");
        expectedReason.setName("name_2");
        expectedReason.setId("id_2");
        setProgram(expectedReason);

        expectedReasons.add(expectedReason);
        reasonsArr.put(mapper.writeValueAsString(expectedReason));

        JSONObject data = new JSONObject();
        data.put("reasons", reasonsArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");
        postRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        List<Object> actualReasons = new ArrayList<>();
        for (MasterTableEntry entry : repository.getAll()) {
            actualReasons.add(entry.getJson());
        }

        assertTwoListsAreSameIgnoringOrder(expectedReasons, actualReasons, false);
    }

    @Test
    public void testPutShouldUpdateReasonsInDb() throws Exception {

        // Reason 1
        ReasonMetaData reason = new ReasonMetaData();
        reason.setAdditive(true);
        reason.setDescription("description");
        reason.setName("name");
        reason.setId("id");
        setProgram(reason);

        MasterTableEntry entry = repository.add(reason);

        // updated Reason
        ReasonMetaData expectedReason = new ReasonMetaData();
        expectedReason.setAdditive(false);
        expectedReason.setDescription("description_1");
        expectedReason.setName("name_1");
        expectedReason.setId("id");
        setProgram(expectedReason);

        JSONArray reasonsArr = new JSONArray();
        reasonsArr.put(mapper.writeValueAsString(expectedReason));

        JSONObject data = new JSONObject();
        data.put("reasons", reasonsArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");

        putRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        reason = (ReasonMetaData) repository.get(entry.getId()).getJson();
        assertEquals(expectedReason.getId(), reason.getId());
        assertEquals(expectedReason.getAdditive(), reason.getAdditive());
        assertEquals(expectedReason.getDescription(), reason.getDescription());
        assertEquals(expectedReason.getName(), reason.getName());
    }

    private void setProgram(ReasonMetaData reasonMetaData) {

        ProgramMetaData program = new ProgramMetaData();
        program.setSkipAuthorization(true);
        program.setShowNonFullSupplyTab(false);
        program.setPeriodsSkippable(false);
        program.setDescription("description");
        program.setName("name");
        program.setEnableDatePhysicalStockCountCompleted(false);
        program.setCode(new Code("code"));
        program.setId("id");

        reasonMetaData.setProgram(program);
    }
}
