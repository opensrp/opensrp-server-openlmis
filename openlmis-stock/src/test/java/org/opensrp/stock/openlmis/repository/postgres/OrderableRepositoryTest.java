package org.opensrp.stock.openlmis.repository.postgres;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.postgres.Orderable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;


public class OrderableRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private OrderableRepository repository;

    @BeforeClass
    public static void bootStrap() {
        tableName = "core.orderable";
    }

    @Test
    public void testAddShouldAddNewOrderable() {

        Orderable orderable = new Orderable();
        orderable.setId("id");
        orderable.setCode("code");
        orderable.setCommodityTypeId("commodity_type_id");
        orderable.setTradeItemId("trade_item_id");
        orderable.setDispensable(1);
        orderable.setFullProductCode("full_product_code");
        orderable.setNetContent(10);
        orderable.setPackRoundingThreshold(2);
        orderable.setRoundToZero(false);
        repository.add(orderable);

        orderable = new Orderable();
        orderable.setId("id_2");
        orderable.setCode("code");
        orderable.setCommodityTypeId("commodity_type_id");
        orderable.setTradeItemId("trade_item_id");
        orderable.setDispensable(1);
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
        orderable.setCode("code");
        orderable.setCommodityTypeId("commodity_type_id");
        orderable.setTradeItemId("trade_item_id");
        orderable.setDispensable(1);
        orderable.setFullProductCode("full_product_code");
        orderable.setNetContent(10);
        orderable.setPackRoundingThreshold(2);
        orderable.setRoundToZero(false);
        repository.add(orderable);

        Orderable entry = new Orderable();
        entry.setId(orderable.getId());
        entry.setCode("code_2");
        entry.setCommodityTypeId("commodity_type_id_2");
        entry.setTradeItemId("trade_item_id_2");
        entry.setDispensable(3);
        entry.setFullProductCode("full_product_code_2");
        entry.setNetContent(20);
        entry.setPackRoundingThreshold(3);
        entry.setRoundToZero(true);
        repository.add(entry);

        entry = repository.get(orderable.getId());
        assertEquals(entry.getCode(), orderable.getCode());
        assertEquals(entry.getCommodityTypeId(), orderable.getCommodityTypeId());
        assertEquals(entry.getTradeItemId(), orderable.getTradeItemId());
        assertEquals(entry.getDispensable(), orderable.getDispensable());
        assertEquals(entry.getFullProductCode(), orderable.getFullProductCode());
        assertEquals(entry.getNetContent(), orderable.getNetContent());
        assertEquals(entry.getPackRoundingThreshold(), orderable.getPackRoundingThreshold());
        assertEquals(entry.getRoundToZero(), orderable.getRoundToZero());
    }

    @Test
    public void testGetShouldRetrieveAddedOrderable() {

        Orderable orderable = new Orderable();
        orderable.setId("id_2");
        orderable.setCode("code_2");
        orderable.setCommodityTypeId("commodity_type_id_2");
        orderable.setTradeItemId("trade_item_id_2");
        orderable.setDispensable(3);
        orderable.setFullProductCode("full_product_code_2");
        orderable.setNetContent(20);
        orderable.setPackRoundingThreshold(3);
        orderable.setRoundToZero(true);
        repository.add(orderable);

        orderable = new Orderable();
        orderable.setId("id_1");
        orderable.setCode("code_1");
        orderable.setCommodityTypeId("commodity_type_id_2");
        orderable.setTradeItemId("trade_item_id_2");
        orderable.setDispensable(3);
        orderable.setFullProductCode("full_product_code_2");
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
        orderable.setCode("code_2");
        orderable.setCommodityTypeId("commodity_type_id_2");
        orderable.setTradeItemId("trade_item_id_2");
        orderable.setDispensable(3);
        orderable.setFullProductCode("full_product_code_2");
        orderable.setNetContent(20);
        orderable.setPackRoundingThreshold(3);
        orderable.setRoundToZero(true);
        repository.add(orderable);

        orderable = new Orderable();
        orderable.setId("id_1");
        orderable.setCode("code_1");
        orderable.setCommodityTypeId("commodity_type_id_2");
        orderable.setTradeItemId("trade_item_id_2");
        orderable.setDispensable(3);
        orderable.setFullProductCode("full_product_code_2");
        orderable.setNetContent(20);
        orderable.setPackRoundingThreshold(3);
        orderable.setRoundToZero(true);
        repository.add(orderable);

        Orderable result = repository.get("id_2");
        assertNotNull(result);
    }

    @Test
    public void testUpdateShouldUpdateExistingOrderable() {

        Orderable orderable = new Orderable();
        orderable.setId("id_3");
        orderable.setCode("code");
        orderable.setCommodityTypeId("commodity_type_id");
        orderable.setTradeItemId("trade_item_id");
        orderable.setDispensable(1);
        orderable.setFullProductCode("full_product_code");
        orderable.setNetContent(10);
        orderable.setPackRoundingThreshold(2);
        orderable.setRoundToZero(false);
        repository.add(orderable);

        orderable = new Orderable();
        orderable.setId("id_3");
        orderable.setCode("code_3");
        orderable.setCommodityTypeId("commodity_type_id_3");
        orderable.setTradeItemId("trade_item_id_3");
        orderable.setDispensable(2);
        orderable.setFullProductCode("full_product_code_3");
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
        orderable.setCode("code");
        orderable.setCommodityTypeId("commodity_type_id");
        orderable.setTradeItemId("trade_item_id");
        orderable.setDispensable(1);
        orderable.setFullProductCode("full_product_code");
        orderable.setNetContent(10);
        orderable.setPackRoundingThreshold(2);
        orderable.setRoundToZero(false);
        repository.add(orderable);

        orderable = new Orderable();
        orderable.setId("id_2");
        orderable.setCode("code_2");
        orderable.setCommodityTypeId("commodity_type_id_2");
        orderable.setTradeItemId("trade_item_id_2");
        orderable.setDispensable(4);
        orderable.setFullProductCode("full_product_code_2");
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
        orderable.setCode("code");
        orderable.setCommodityTypeId("commodity_type_id");
        orderable.setTradeItemId("trade_item_id");
        orderable.setDispensable(1);
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
