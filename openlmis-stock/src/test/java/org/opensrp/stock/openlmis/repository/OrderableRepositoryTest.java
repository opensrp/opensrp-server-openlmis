package org.opensrp.stock.openlmis.repository;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.Orderable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;


public class OrderableRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private OrderableRepository repository;

    @BeforeClass
    public static void bootStrap() {
        tableNames.add("core.orderable");
    }

    @Test
    public void testAddShouldAddNewOrderable() {

        Orderable orderable = new Orderable();
        orderable.setId("id");
        orderable.setFullProductName("full_product_name");
        orderable.setCommodityTypeId("commodity_type_id");
        orderable.setTradeItemId("trade_item_id");
        orderable.setDispensableId("dispensable_id");
        orderable.setFullProductCode("full_product_code");
        orderable.setNetContent(10);
        orderable.setPackRoundingThreshold(2);
        orderable.setRoundToZero(false);
        repository.add(orderable);

        orderable = new Orderable();
        orderable.setId("id_2");
        orderable.setFullProductName("full_product_name");
        orderable.setCommodityTypeId("commodity_type_id");
        orderable.setTradeItemId("trade_item_id");
        orderable.setDispensableId("dispensable_id");
        orderable.setFullProductCode("full_product_code");
        orderable.setNetContent(10);
        orderable.setPackRoundingThreshold(2);
        orderable.setRoundToZero(false);
        repository.add(orderable);

        List<Orderable> orderables = repository.get("id", "trade_item_id", "commodity_type_id");

        assertEquals(orderables.size(), 1);
    }

    @Test
    public void testAddShouldNotAddNewOrderableIfDuplicate() {

        Orderable orderable = new Orderable();
        orderable.setId("id");
        orderable.setFullProductName("full_product_name");
        orderable.setCommodityTypeId("commodity_type_id");
        orderable.setTradeItemId("trade_item_id");
        orderable.setDispensableId("dispensable_id");
        orderable.setFullProductCode("full_product_code");
        orderable.setNetContent(10);
        orderable.setPackRoundingThreshold(2);
        orderable.setRoundToZero(false);
        repository.add(orderable);

        Orderable entry = new Orderable();
        entry.setId(orderable.getId());
        orderable.setFullProductCode("full_product_code");
        entry.setCommodityTypeId("commodity_type_id_2");
        entry.setTradeItemId("trade_item_id_2");
        orderable.setDispensableId("dispensable_id");
        entry.setFullProductName("full_product_code_2");
        entry.setNetContent(20);
        entry.setPackRoundingThreshold(3);
        entry.setRoundToZero(true);
        repository.add(entry);

        entry = repository.get(orderable.getId());
        assertEquals(entry.getFullProductCode(), orderable.getFullProductCode());
        assertEquals(entry.getCommodityTypeId(), orderable.getCommodityTypeId());
        assertEquals(entry.getTradeItemId(), orderable.getTradeItemId());
        assertEquals(entry.getDispensableId(), orderable.getDispensableId());
        assertEquals(entry.getFullProductName(), orderable.getFullProductName());
        assertEquals(entry.getNetContent(), orderable.getNetContent());
        assertEquals(entry.getPackRoundingThreshold(), orderable.getPackRoundingThreshold());
        assertEquals(entry.getRoundToZero(), orderable.getRoundToZero());
    }

    @Test
    public void testGetShouldRetrieveAddedOrderable() {

        Orderable orderable = new Orderable();
        orderable.setId("id_2");
        orderable.setFullProductName("full_product_name");
        orderable.setCommodityTypeId("commodity_type_id_2");
        orderable.setTradeItemId("trade_item_id_2");
        orderable.setDispensableId("dispensable_id");
        orderable.setFullProductCode("full_product_code_2");
        orderable.setNetContent(20);
        orderable.setPackRoundingThreshold(3);
        orderable.setRoundToZero(true);
        repository.add(orderable);

        orderable = new Orderable();
        orderable.setId("id_1");
        orderable.setFullProductCode("full_product_code");
        orderable.setCommodityTypeId("commodity_type_id_2");
        orderable.setTradeItemId("trade_item_id_2");
        orderable.setDispensableId("dispensable_id");
        orderable.setFullProductName("full_product_code_2");
        orderable.setNetContent(20);
        orderable.setPackRoundingThreshold(3);
        orderable.setRoundToZero(true);
        repository.add(orderable);


        List<Orderable> orderables = repository.get("id_2", "trade_item_id_2", "commodity_type_id_2");
        assertEquals(orderables.size(), 1);
    }

    @Test
    public void testGetShouldRetrieveAddedOrderableById() {

        Orderable orderable = new Orderable();
        orderable.setId("id_2");
        orderable.setFullProductName("full_product_name");
        orderable.setCommodityTypeId("commodity_type_id_2");
        orderable.setTradeItemId("trade_item_id_2");
        orderable.setDispensableId("dispensable_id");
        orderable.setFullProductName("full_product_code_2");
        orderable.setNetContent(20);
        orderable.setPackRoundingThreshold(3);
        orderable.setRoundToZero(true);
        repository.add(orderable);

        orderable = new Orderable();
        orderable.setId("id_1");
        orderable.setFullProductCode("full_product_code");
        orderable.setCommodityTypeId("commodity_type_id_2");
        orderable.setTradeItemId("trade_item_id_2");
        orderable.setDispensableId("dispensable_id");
        orderable.setFullProductName("full_product_code_2");
        orderable.setNetContent(20);
        orderable.setPackRoundingThreshold(3);
        orderable.setRoundToZero(true);
        repository.add(orderable);

        Orderable result = repository.get("id_2");
        assertNotNull(result);
    }

    @Test
    public void testGetShouldRetrieveAddedOrderableByServerVersion() {

        long timeBeforeInsertion = getCurrentTime();

        Orderable orderable = new Orderable();
        orderable.setId("id_2");
        orderable.setFullProductName("full_product_name");
        orderable.setCommodityTypeId("commodity_type_id_2");
        orderable.setTradeItemId("trade_item_id_2");
        orderable.setDispensableId("dispensable_id");
        orderable.setFullProductName("full_product_code_2");
        orderable.setNetContent(20);
        orderable.setPackRoundingThreshold(3);
        orderable.setRoundToZero(true);
        repository.add(orderable);

        orderable = new Orderable();
        orderable.setId("id_1");
        orderable.setFullProductCode("full_product_code");
        orderable.setCommodityTypeId("commodity_type_id_2");
        orderable.setTradeItemId("trade_item_id_2");
        orderable.setDispensableId("dispensable_id");
        orderable.setFullProductName("full_product_code_2");
        orderable.setNetContent(20);
        orderable.setPackRoundingThreshold(3);
        orderable.setRoundToZero(true);
        repository.add(orderable);

        List<Orderable> result = repository.get(timeBeforeInsertion);
        assertEquals(result.size(), 2);
    }

    @Test
    public void testUpdateShouldUpdateExistingOrderable() {

        Orderable orderable = new Orderable();
        orderable.setId("id_3");
        orderable.setFullProductName("full_product_name");
        orderable.setCommodityTypeId("commodity_type_id");
        orderable.setTradeItemId("trade_item_id");
        orderable.setDispensableId("dispensable_id");
        orderable.setFullProductCode("full_product_code");
        orderable.setNetContent(10);
        orderable.setPackRoundingThreshold(2);
        orderable.setRoundToZero(false);
        repository.add(orderable);

        orderable = new Orderable();
        orderable.setId("id_3");
        orderable.setFullProductCode("full_product_code");
        orderable.setCommodityTypeId("commodity_type_id_3");
        orderable.setTradeItemId("trade_item_id_3");
        orderable.setDispensableId("dispensable_id");
        orderable.setFullProductName("full_product_name_3");
        orderable.setNetContent(20);
        orderable.setPackRoundingThreshold(3);
        orderable.setRoundToZero(true);
        repository.update(orderable);

        // ensure old values are removed
        List<Orderable> orderables = repository.get("id_3", "trade_item_id", "commodity_type_id");
        assertEquals(orderables.size(), 0);

        // ensure values are updated
        orderables = repository.get("id_3", "trade_item_id_3", "commodity_type_id_3");
        assertEquals(orderables.size(), 1);
    }

    @Test
    public void testGetAllShouldGetAllOrderablesInTable() {

        Orderable orderable = new Orderable();
        orderable.setId("id_3");
        orderable.setFullProductName("full_product_name");
        orderable.setCommodityTypeId("commodity_type_id");
        orderable.setTradeItemId("trade_item_id");
        orderable.setDispensableId("dispensable_id");
       orderable.setFullProductCode("full_product_code");
        orderable.setNetContent(10);
        orderable.setPackRoundingThreshold(2);
        orderable.setRoundToZero(false);
        repository.add(orderable);

        orderable = new Orderable();
        orderable.setId("id_2");
        orderable.setFullProductName("full_product_name");
        orderable.setCommodityTypeId("commodity_type_id_2");
        orderable.setTradeItemId("trade_item_id_2");
        orderable.setDispensableId("dispensable_id");
        orderable.setFullProductName("full_product_code_2");
        orderable.setNetContent(40);
        orderable.setPackRoundingThreshold(4);
        orderable.setRoundToZero(true);
        repository.add(orderable);

        List<Orderable> orderables = repository.getAll();
        assertEquals(orderables.size(), 2);
    }

    @Test
    public void testSafeRemoveShouldAddADeleteDateToOrderable() {

        Orderable orderable = new Orderable();
        orderable.setId("id_3");
        orderable.setFullProductName("full_product_name");
        orderable.setCommodityTypeId("commodity_type_id");
        orderable.setTradeItemId("trade_item_id");
        orderable.setDispensableId("dispensable_id");
       orderable.setFullProductCode("full_product_code");
        orderable.setNetContent(10);
        orderable.setPackRoundingThreshold(2);
        orderable.setRoundToZero(false);
        repository.add(orderable);

        Long timeStamp = repository.safeRemove(orderable);
        Orderable result = repository.get(orderable.getId());
        assertNotNull(result);
        assertEquals(result.getDateDeleted(), timeStamp);
    }
}
