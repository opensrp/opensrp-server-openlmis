package org.opensrp.stock.openlmis.rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.ProgramOrderable;
import org.opensrp.stock.openlmis.repository.ProgramOrderableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.opensrp.stock.openlmis.util.Utils.PROGRAM_ORDERABLES;
import static org.opensrp.stock.openlmis.util.Utils.SYNC_SERVER_VERSION;
import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class ProgramOrderableResourceTest extends BaseResourceTest {

    private final static String BASE_URL = "/rest/program-orderables/";

    @Autowired
    private ProgramOrderableRepository repository;

    @Before
    public void bootStrap() {
        tableNames.add("core.program_orderable");
        truncateTables();
    }

    @After
    public void tearDown() {
        truncateTables();
    }

    @Test
    public void testGetAllShouldReturnAllProgramOrderables() throws Exception {

        List<Object> expectedProgramOrderables = new ArrayList<>();

        ProgramOrderable expectedProgramOrderable = new ProgramOrderable();
        expectedProgramOrderable.setActive(true);
        expectedProgramOrderable.setDosesPerPatient(5);
        expectedProgramOrderable.setFullSupply(true);
        expectedProgramOrderable.setId("id");
        expectedProgramOrderable.setOrderableId("orderable_id");
        expectedProgramOrderable.setProgramId("program_id");

        repository.add(expectedProgramOrderable);
        expectedProgramOrderables.add(expectedProgramOrderable);

        expectedProgramOrderable = new ProgramOrderable();
        expectedProgramOrderable.setActive(true);
        expectedProgramOrderable.setDosesPerPatient(5);
        expectedProgramOrderable.setFullSupply(true);
        expectedProgramOrderable.setId("id_1");
        expectedProgramOrderable.setOrderableId("orderable_id_1");
        expectedProgramOrderable.setProgramId("program_id_1");

        repository.add(expectedProgramOrderable);
        expectedProgramOrderables.add(expectedProgramOrderable);

        String actualProgramOrderablesString = getResponseAsString(BASE_URL, null, status().isOk());
        List<Object> actualProgramOrderables = new Gson().fromJson(actualProgramOrderablesString, new TypeToken<List<ProgramOrderable>>(){}.getType());

        assertListsAreSameIgnoringOrder(actualProgramOrderables, expectedProgramOrderables);
    }

    @Test
    public void testSyncShouldRetrieveAllProgramOrderablesAfterACertainTime() throws Exception {

        List<Object> expectedProgramOrderables = new ArrayList<>();

        // this trade item should not sync

        // ProgramOrderable 1
        ProgramOrderable expectedProgramOrderable = new ProgramOrderable();
        expectedProgramOrderable.setActive(true);
        expectedProgramOrderable.setDosesPerPatient(5);
        expectedProgramOrderable.setFullSupply(true);
        expectedProgramOrderable.setId("id");
        expectedProgramOrderable.setOrderableId("orderable_id");
        expectedProgramOrderable.setProgramId("program_id");

        repository.add(expectedProgramOrderable);

        // these trade items should sync
        long timeBefore = getCurrentTime();

        // ProgramOrderable 2
        expectedProgramOrderable = new ProgramOrderable();
        expectedProgramOrderable.setActive(true);
        expectedProgramOrderable.setDosesPerPatient(5);
        expectedProgramOrderable.setFullSupply(true);
        expectedProgramOrderable.setId("id_1");
        expectedProgramOrderable.setOrderableId("orderable_id_1");
        expectedProgramOrderable.setProgramId("program_id_1");

        repository.add(expectedProgramOrderable);
        expectedProgramOrderables.add(expectedProgramOrderable);

        // ProgramOrderable 3
        expectedProgramOrderable = new ProgramOrderable();
        expectedProgramOrderable.setActive(true);
        expectedProgramOrderable.setDosesPerPatient(5);
        expectedProgramOrderable.setFullSupply(true);
        expectedProgramOrderable.setId("id_2");
        expectedProgramOrderable.setOrderableId("orderable_id_2");
        expectedProgramOrderable.setProgramId("program_id_2");

        repository.add(expectedProgramOrderable);
        expectedProgramOrderables.add(expectedProgramOrderable);

        String actualProgramOrderablesString = getResponseAsString(BASE_URL + "sync", SYNC_SERVER_VERSION + "=" + timeBefore, status().isOk());
        List<Object> actualProgramOrderables = new Gson().fromJson(actualProgramOrderablesString, new TypeToken<List<ProgramOrderable>>(){}.getType());

        assertListsAreSameIgnoringOrder(actualProgramOrderables, expectedProgramOrderables);
    }

    @Test
    public void testPostShouldCreateNewProgramOrderablesInDb() throws Exception {

        List<Object> expectedProgramOrderables = new ArrayList<>();
        JSONArray programOrderablesArr = new JSONArray();

        // ProgramOrderable 1
        ProgramOrderable expectedProgramOrderable = new ProgramOrderable();
        expectedProgramOrderable.setActive(true);
        expectedProgramOrderable.setDosesPerPatient(5);
        expectedProgramOrderable.setFullSupply(true);
        expectedProgramOrderable.setId("id");
        expectedProgramOrderable.setOrderableId("orderable_id");
        expectedProgramOrderable.setProgramId("program_id");

        expectedProgramOrderables.add(expectedProgramOrderable);
        programOrderablesArr.put(mapper.writeValueAsString(expectedProgramOrderable));

        // ProgramOrderable 2
        expectedProgramOrderable = new ProgramOrderable();
        expectedProgramOrderable.setActive(true);
        expectedProgramOrderable.setDosesPerPatient(5);
        expectedProgramOrderable.setFullSupply(true);
        expectedProgramOrderable.setId("id_1");
        expectedProgramOrderable.setOrderableId("orderable_id_1");
        expectedProgramOrderable.setProgramId("program_id_1");

        expectedProgramOrderables.add(expectedProgramOrderable);
        programOrderablesArr.put(mapper.writeValueAsString(expectedProgramOrderable));

        // ProgramOrderable 3
        expectedProgramOrderable = new ProgramOrderable();
        expectedProgramOrderable.setActive(true);
        expectedProgramOrderable.setDosesPerPatient(5);
        expectedProgramOrderable.setFullSupply(true);
        expectedProgramOrderable.setId("id_2");
        expectedProgramOrderable.setOrderableId("orderable_id_2");
        expectedProgramOrderable.setProgramId("program_id_2");

        expectedProgramOrderables.add(expectedProgramOrderable);
        programOrderablesArr.put(mapper.writeValueAsString(expectedProgramOrderable));

        JSONObject data = new JSONObject();
        data.put(PROGRAM_ORDERABLES, programOrderablesArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");
        postRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        List<Object> actualProgramOrderables = new ArrayList<>();
        for (ProgramOrderable ProgramOrderable : repository.getAll()) {
            actualProgramOrderables.add(ProgramOrderable);
        }

        assertListsAreSameIgnoringOrder(expectedProgramOrderables, actualProgramOrderables);
    }

    @Test
    public void testPutShouldUpdateProgramOrderablesInDb() throws Exception {

        // ProgramOrderable 1
        ProgramOrderable programOrderable = new ProgramOrderable();
        programOrderable.setActive(true);
        programOrderable.setDosesPerPatient(5);
        programOrderable.setFullSupply(true);
        programOrderable.setId("id");
        programOrderable.setOrderableId("orderable_id");
        programOrderable.setProgramId("program_id");

        repository.add(programOrderable);

        // updated ProgramOrderable
        ProgramOrderable expectedProgramOrderable = new ProgramOrderable();
        expectedProgramOrderable = new ProgramOrderable();
        expectedProgramOrderable.setActive(true);
        expectedProgramOrderable.setDosesPerPatient(5);
        expectedProgramOrderable.setFullSupply(false);
        expectedProgramOrderable.setId("id");
        expectedProgramOrderable.setOrderableId("orderable_id_2");
        expectedProgramOrderable.setProgramId("program_id_2");

        JSONArray programOrderablesArr = new JSONArray();
        programOrderablesArr.put(mapper.writeValueAsString(expectedProgramOrderable));

        JSONObject data = new JSONObject();
        data.put(PROGRAM_ORDERABLES, programOrderablesArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");

        putRequestWithJsonContent(BASE_URL, dataString, status().isCreated());

        programOrderable = repository.get(programOrderable.getId());
        assertEquals(expectedProgramOrderable.getId(), programOrderable.getId());
        assertEquals(expectedProgramOrderable.getFullSupply(), programOrderable.getFullSupply());
        assertEquals(expectedProgramOrderable.getDosesPerPatient(), programOrderable.getDosesPerPatient());
        assertEquals(expectedProgramOrderable.getActive(), programOrderable.getActive());
        assertEquals(expectedProgramOrderable.getOrderableId(), programOrderable.getOrderableId());
        assertEquals(expectedProgramOrderable.getProgramId(), programOrderable.getProgramId());
    }

    public void assertListsAreSameIgnoringOrder(List<Object> expectedList, List<Object> actualList) {

        assertEquals(expectedList.size(), actualList.size());

        Set<String> expectedIds = new HashSet<>();
        for (Object expected : expectedList) {
            expectedIds.add(((ProgramOrderable) expected).getId());
        }
        for (Object actual : actualList) {
            assertTrue(expectedIds.contains(((ProgramOrderable) actual).getId()));
        }
    }
}
