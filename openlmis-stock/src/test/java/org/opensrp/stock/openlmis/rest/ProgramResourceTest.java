package org.opensrp.stock.openlmis.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.Code;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.ProgramMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.SYNC_SERVER_VERSION;
import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class ProgramResourceTest extends BaseResourceTest {

    private final static String BASE_URL = "/rest/programs/";

    @Autowired
    private MasterTableRepository repository;

    @Autowired
    private ProgramResource programResource;

    @Before
    public void bootStrap() {
        truncateTable("core.master_table");
    }

    @After
    public void tearDown() {
        truncateTable("core.master_table");
    }

    @Test
    public void testGetAllShouldReturnAllPrograms() throws Exception {

        List<Object> expectedPrograms = new ArrayList<>();

        ProgramMetaData expectedProgram = new ProgramMetaData(
                "identifier"
        );
        expectedProgram.setActive(true);
        expectedProgram.setCode(new Code("code"));
        expectedProgram.setName("program_name");
        expectedProgram.setDescription("program_description");
        expectedProgram.setEnableDatePhysicalStockCountCompleted(true);
        expectedProgram.setPeriodsSkippable(false);
        expectedProgram.setShowNonFullSupplyTab(false);
        expectedProgram.setSkipAuthorization(true);

        repository.add(expectedProgram);
        expectedPrograms.add(expectedProgram);

        expectedProgram = new ProgramMetaData(
                "identifier_1"
        );
        expectedProgram.setActive(true);
        expectedProgram.setCode(new Code("code_1"));
        expectedProgram.setName("program_name_1");
        expectedProgram.setDescription("program_description_1");
        expectedProgram.setEnableDatePhysicalStockCountCompleted(true);
        expectedProgram.setPeriodsSkippable(false);
        expectedProgram.setShowNonFullSupplyTab(true);
        expectedProgram.setSkipAuthorization(true);

        repository.add(expectedProgram);
        expectedPrograms.add(expectedProgram);

        List<Object> actualPrograms = getResponseAsList(BASE_URL, null, status().isOk());

        assertTwoListsAreSameIgnoringOrder(actualPrograms, expectedPrograms);
    }

    @Test
    public void testSyncShouldRetrieveAllProgramsAfterACertainTime() throws Exception {

        List<Object> expectedPrograms = new ArrayList<>();

        // this program should not sync
        ProgramMetaData expectedProgram = new ProgramMetaData(
                "identifier"
        );
        expectedProgram.setActive(true);
        expectedProgram.setCode(new Code("code"));
        expectedProgram.setName("program_name");
        expectedProgram.setDescription("program_description");
        expectedProgram.setEnableDatePhysicalStockCountCompleted(true);
        expectedProgram.setPeriodsSkippable(false);
        expectedProgram.setShowNonFullSupplyTab(false);
        expectedProgram.setSkipAuthorization(true);

        repository.add(expectedProgram);

        // these programs should sync
        long timeBefore = getCurrentTime();
        expectedProgram = new ProgramMetaData(
                "identifier_1"
        );
        expectedProgram.setActive(true);
        expectedProgram.setCode(new Code("code_1"));
        expectedProgram.setName("program_name_1");
        expectedProgram.setDescription("program_description_1");
        expectedProgram.setEnableDatePhysicalStockCountCompleted(true);
        expectedProgram.setPeriodsSkippable(false);
        expectedProgram.setShowNonFullSupplyTab(true);
        expectedProgram.setSkipAuthorization(true);

        repository.add(expectedProgram);
        expectedPrograms.add(expectedProgram);

        expectedProgram = new ProgramMetaData(
                "identifier_2"
        );
        expectedProgram.setActive(true);
        expectedProgram.setCode(new Code("code_2"));
        expectedProgram.setName("program_name_2");
        expectedProgram.setDescription("program_description_2");
        expectedProgram.setEnableDatePhysicalStockCountCompleted(false);
        expectedProgram.setPeriodsSkippable(false);
        expectedProgram.setShowNonFullSupplyTab(false);
        expectedProgram.setSkipAuthorization(true);

        repository.add(expectedProgram);
        expectedPrograms.add(expectedProgram);

        List<Object> actualPrograms = getResponseAsList(BASE_URL + "sync", SYNC_SERVER_VERSION + "=" + timeBefore, status().isOk());

        assertTwoListsAreSameIgnoringOrder(actualPrograms, expectedPrograms);
    }

    @Test
    public void testPostShouldCreateNewProgramsInDb() throws Exception {

        List<Object> expectedPrograms = new ArrayList<>();
        JSONArray programsArr = new JSONArray();

        // program 1
        ProgramMetaData expectedProgram = new ProgramMetaData(
                "identifier"
        );
        expectedProgram.setActive(true);
        expectedProgram.setCode(new Code("code"));
        expectedProgram.setName("program_name");
        expectedProgram.setDescription("program_description");
        expectedProgram.setEnableDatePhysicalStockCountCompleted(true);
        expectedProgram.setPeriodsSkippable(false);
        expectedProgram.setShowNonFullSupplyTab(false);
        expectedProgram.setSkipAuthorization(true);

        programsArr.put(mapper.writeValueAsString(expectedProgram));
        expectedPrograms.add(expectedProgram);

        // program 2
        expectedProgram = new ProgramMetaData(
                "identifier_1"
        );
        expectedProgram.setActive(true);
        expectedProgram.setCode(new Code("code_1"));
        expectedProgram.setName("program_name_1");
        expectedProgram.setDescription("program_description_1");
        expectedProgram.setEnableDatePhysicalStockCountCompleted(true);
        expectedProgram.setPeriodsSkippable(false);
        expectedProgram.setShowNonFullSupplyTab(true);
        expectedProgram.setSkipAuthorization(true);

        programsArr.put(mapper.writeValueAsString(expectedProgram));
        expectedPrograms.add(expectedProgram);

        // program 3
        expectedProgram = new ProgramMetaData(
                "identifier_2"
        );
        expectedProgram.setActive(true);
        expectedProgram.setCode(new Code("code_2"));
        expectedProgram.setName("program_name_2");
        expectedProgram.setDescription("program_description_2");
        expectedProgram.setEnableDatePhysicalStockCountCompleted(false);
        expectedProgram.setPeriodsSkippable(false);
        expectedProgram.setShowNonFullSupplyTab(false);
        expectedProgram.setSkipAuthorization(true);

        programsArr.put(mapper.writeValueAsString(expectedProgram));
        expectedPrograms.add(expectedProgram);

        JSONObject data = new JSONObject();
        data.put("programs", programsArr);
        String dataString =
                        data.toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");
        postRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        List<Object> actualPrograms = new ArrayList<>();
        for (MasterTableEntry entry : repository.getAll()) {
            actualPrograms.add(entry.getJson());
        }

        assertTwoListsAreSameIgnoringOrder(expectedPrograms, actualPrograms);
    }
}
