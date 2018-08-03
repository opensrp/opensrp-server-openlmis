package org.opensrp.stock.openlmis.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.CommodityTypeMetaData;
import org.opensrp.stock.openlmis.domain.metadata.TradeItemMetaData;
import org.opensrp.stock.openlmis.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

public class CommodityTypeResourceTest extends BaseResourceTest {

    private final static String BASE_URL = "/rest/commodity-types/";

    @Autowired
    private MasterTableRepository repository;

    @Autowired
    private CommodityTypeResource commodityTypeResource;

    @Before
    public void bootStrap() {
        truncateTable("core.master_table");
    }

    @After
    public void tearDown() {
        truncateTable("core.master_table");
    }

    @Test
    public void testGetAllShouldReturnAllCommodityTypes() throws Exception {

        List<Object> expectedCommodityTypes = new ArrayList<>();

        CommodityTypeMetaData expectedCommodityType = new CommodityTypeMetaData(
                "identifier"
        );
        expectedCommodityType.setClassificationId("classification_id");
        expectedCommodityType.setClassificationSystem("classification_system");
        expectedCommodityType.setName("commodity_name");
        expectedCommodityType.setParentId("parent_id");
        setParentAndChildrenAndTradeItems(expectedCommodityType);

        repository.add(expectedCommodityType);
        expectedCommodityTypes.add(expectedCommodityType);

        expectedCommodityType = new CommodityTypeMetaData(
                "identifier_1"
        );
        expectedCommodityType.setClassificationId("classification_id_1");
        expectedCommodityType.setClassificationSystem("classification_system_1");
        expectedCommodityType.setName("commodity_name_1");
        expectedCommodityType.setParentId("parent_id_1");
        setParentAndChildrenAndTradeItems(expectedCommodityType);

        repository.add(expectedCommodityType);
        expectedCommodityTypes.add(expectedCommodityType);

        List<Object> actualCommodityTypes = getResponseAsList(BASE_URL, null, status().isOk());

        assertTwoListsAreSameIgnoringOrder(actualCommodityTypes, expectedCommodityTypes);
    }

    @Test
    public void testSyncShouldRetrieveAllCommodityTypesAfterACertainTime() throws Exception {

        List<Object> expectedCommodityTypes = new ArrayList<>();

        // this commodity type should not sync

        // commodity type 1
        CommodityTypeMetaData expectedCommodityType = new CommodityTypeMetaData(
                "identifier"
        );
        expectedCommodityType.setClassificationId("classification_id");
        expectedCommodityType.setClassificationSystem("classification_system");
        expectedCommodityType.setName("commodity_name");
        expectedCommodityType.setParentId("parent_id");
        setParentAndChildrenAndTradeItems(expectedCommodityType);

        repository.add(expectedCommodityType);

        // these commodity types should sync

        // commodity type 2
        long timeBefore = getCurrentTime();
        expectedCommodityType = new CommodityTypeMetaData(
                "identifier_1"
        );
        expectedCommodityType.setClassificationId("classification_id_1");
        expectedCommodityType.setClassificationSystem("classification_system_1");
        expectedCommodityType.setName("commodity_name_1");
        expectedCommodityType.setParentId("parent_id_1");
        setParentAndChildrenAndTradeItems(expectedCommodityType);

        repository.add(expectedCommodityType);
        expectedCommodityTypes.add(expectedCommodityType);

        // commodity type 3
        expectedCommodityType = new CommodityTypeMetaData(
                "identifier_2"
        );
        expectedCommodityType.setClassificationId("classification_id_2");
        expectedCommodityType.setClassificationSystem("classification_system_2");
        expectedCommodityType.setName("commodity_name_2");
        expectedCommodityType.setParentId("parent_id_2");
        setParentAndChildrenAndTradeItems(expectedCommodityType);

        repository.add(expectedCommodityType);
        expectedCommodityTypes.add(expectedCommodityType);

        List<Object> actualCommodityTypes = getResponseAsList(BASE_URL + "sync", "last_server_version=" + timeBefore, status().isOk());

        assertTwoListsAreSameIgnoringOrder(actualCommodityTypes, expectedCommodityTypes);
    }

    @Test
    public void testPostShouldCreateNewCommodityTypesInDb() throws Exception {


        List<Object> expectedCommodityTypes = new ArrayList<>();
        JSONArray commodityTypesArr = new JSONArray();
        // commodity type 1
        CommodityTypeMetaData expectedCommodityType = new CommodityTypeMetaData(
                "identifier"
        );
        expectedCommodityType.setClassificationId("classification_id");
        expectedCommodityType.setClassificationSystem("classification_system");
        expectedCommodityType.setName("commodity_name");
        expectedCommodityType.setParentId("parent_id");
        setParentAndChildrenAndTradeItems(expectedCommodityType);

        expectedCommodityTypes.add(expectedCommodityType);
        commodityTypesArr.put(mapper.writeValueAsString(expectedCommodityType));

        // commodity type 2
        expectedCommodityType = new CommodityTypeMetaData(
                "identifier_1"
        );
        expectedCommodityType.setClassificationId("classification_id_1");
        expectedCommodityType.setClassificationSystem("classification_system_1");
        expectedCommodityType.setName("commodity_name_1");
        expectedCommodityType.setParentId("parent_id_1");
        setParentAndChildrenAndTradeItems(expectedCommodityType);

        expectedCommodityTypes.add(expectedCommodityType);
        commodityTypesArr.put(mapper.writeValueAsString(expectedCommodityType));

        // commodity type 3
        expectedCommodityType = new CommodityTypeMetaData(
                "identifier_2"
        );
        expectedCommodityType.setClassificationId("classification_id_2");
        expectedCommodityType.setClassificationSystem("classification_system_2");
        expectedCommodityType.setName("commodity_name_2");
        expectedCommodityType.setParentId("parent_id_2");
        setParentAndChildrenAndTradeItems(expectedCommodityType);

        expectedCommodityTypes.add(expectedCommodityType);
        commodityTypesArr.put(mapper.writeValueAsString(expectedCommodityType));

        JSONObject data = new JSONObject();
        data.put("commodity_types", commodityTypesArr);
        String dataString =
                data
                        .toString()
                        .replace("\"{", "{")
                        .replace("}\"", "}")
                        .replace("\\", "")
                        .replace("[\"java.util.ArrayList\",", "").replace("]]", "]");
        postRequestWithJsonContent(BASE_URL + "add", dataString, status().isCreated());

        List<Object> actualCommodityTypes = new ArrayList<>();
        for (MasterTableEntry entry : repository.getAll()) {
            actualCommodityTypes.add(entry.getJson());
        }

        assertTwoListsAreSameIgnoringOrder(expectedCommodityTypes, actualCommodityTypes);
    }

    private void setParentAndChildrenAndTradeItems(CommodityTypeMetaData commodityTypeMetaData) throws Exception {

        CommodityTypeMetaData parent = new CommodityTypeMetaData("parent_id");
        List<TradeItemMetaData> tradeItems = new ArrayList<>();
        List<CommodityTypeMetaData> children = new ArrayList<>();

        TradeItemMetaData tradeItem = new TradeItemMetaData("trade_item_1");
        tradeItems.add(tradeItem);
        tradeItem = new TradeItemMetaData("trade_item_2");
        tradeItems.add(tradeItem);

        CommodityTypeMetaData childCommodityType = new CommodityTypeMetaData("child_1");
        children.add(childCommodityType);
        childCommodityType = new CommodityTypeMetaData("child_2");
        children.add(childCommodityType);

        commodityTypeMetaData.setParent(parent);
        commodityTypeMetaData.setTradeItems(tradeItems);
        commodityTypeMetaData.setChildren(children);
    }
}
