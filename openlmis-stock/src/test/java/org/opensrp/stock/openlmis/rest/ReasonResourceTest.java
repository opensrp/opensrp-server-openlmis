package org.opensrp.stock.openlmis.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.StockCardLineItemReason;
import org.opensrp.stock.openlmis.domain.metadata.ReasonMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.opensrp.stock.openlmis.util.Utils.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class ReasonResourceTest extends BaseResourceTest {

    private final static String BASE_URL = "/rest/reasons/";

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
    public void testGetAllShouldReturnAllReasons() throws Exception {

        List<Object> expectedReasons = new ArrayList<>();

        ReasonMetaData expectedReason = new ReasonMetaData();
        expectedReason.setId("id");
        expectedReason.setProgramId("program_id");
        expectedReason.setFacilityType("facility_type");
        setStockCardLineItemReason(expectedReason);

        repository.add(expectedReason);
        expectedReasons.add(expectedReason);

        expectedReason = new ReasonMetaData();
        expectedReason.setId("id_1");
        expectedReason.setProgramId("program_id_1");
        expectedReason.setFacilityType("facility_type_1");
        setStockCardLineItemReason(expectedReason);

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
        expectedReason.setId("id");
        expectedReason.setProgramId("program_id");
        expectedReason.setFacilityType("facility_type");
        setStockCardLineItemReason(expectedReason);
        repository.add(expectedReason);

        // these commodity types should sync

        // commodity type 2
        long timeBefore = getCurrentTime();
        expectedReason = new ReasonMetaData();
        expectedReason.setId("id_1");
        expectedReason.setProgramId("program_id_1");
        expectedReason.setFacilityType("facility_type_1");
        setStockCardLineItemReason(expectedReason);

        repository.add(expectedReason);
        expectedReasons.add(expectedReason);

        // commodity type 3
        expectedReason = new ReasonMetaData();
        expectedReason.setId("id_2");
        expectedReason.setProgramId("program_id_2");
        expectedReason.setFacilityType("facility_type_2");
        setStockCardLineItemReason(expectedReason);

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
        expectedReason.setId("id");
        expectedReason.setProgramId("program_id");
        expectedReason.setFacilityType("facility_type");
        setStockCardLineItemReason(expectedReason);

        expectedReasons.add(expectedReason);
        reasonsArr.put(mapper.writeValueAsString(expectedReason));

        // commodity type 2
        expectedReason = new ReasonMetaData();
        expectedReason.setId("id_1");
        expectedReason.setProgramId("program_id_1");
        expectedReason.setFacilityType("facility_type_1");
        setStockCardLineItemReason(expectedReason);

        expectedReasons.add(expectedReason);
        reasonsArr.put(mapper.writeValueAsString(expectedReason));

        // commodity type 3
        expectedReason = new ReasonMetaData();
        expectedReason.setId("id_2");
        expectedReason.setProgramId("program_id_2");
        expectedReason.setFacilityType("facility_type_2");
        setStockCardLineItemReason(expectedReason);

        expectedReasons.add(expectedReason);
        reasonsArr.put(mapper.writeValueAsString(expectedReason));

        JSONObject data = new JSONObject();
        data.put(REASONS, reasonsArr);
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
        reason.setId("id");
        reason.setProgramId("program_id");
        reason.setFacilityType("facility_type");

        MasterTableEntry entry = repository.add(reason);

        // updated Reason
        ReasonMetaData expectedReason = new ReasonMetaData();
        expectedReason.setId("id");
        expectedReason.setProgramId("program_id_1");
        expectedReason.setFacilityType("facility_type_1");
        setStockCardLineItemReason(expectedReason);

        JSONArray reasonsArr = new JSONArray();
        reasonsArr.put(mapper.writeValueAsString(expectedReason));

        JSONObject data = new JSONObject();
        data.put(REASONS, reasonsArr);
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
    }

    private void setStockCardLineItemReason(ReasonMetaData reason) {

        StockCardLineItemReason stockCardLineItemReason = new StockCardLineItemReason();
        stockCardLineItemReason.setReasonCategory("reason_category");
        stockCardLineItemReason.setReasonType("reason_type");
        stockCardLineItemReason.setDescription("description");
        stockCardLineItemReason.setFreeTextAllowed(true);
        stockCardLineItemReason.setName("name");

        reason.setStockCardLineItemReason(stockCardLineItemReason);
    }
}
