import org.junit.Test;
import org.opensrp.stock.openlmis.domain.postgres.Orderable;
import org.opensrp.stock.openlmis.repository.postgres.OrderableRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class OrderableRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private OrderableRepository repository;

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
        orderable.setDateUpdated(4910843012L);
        repository.add(orderable);

        List<Orderable> orderables = repository.get("id", "trade_item_id", "commodity_type_id");

        assertEquals(orderables.size(), 1);
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
        orderable.setDateUpdated(98287323012L);
        repository.add(orderable);

        List<Orderable> orderables = repository.get("id_2", "trade_item_id_2", "commodity_type_id_2");
        assertEquals(orderables.size(), 1);
    }

    @Test
    public void testUpdateShouldUpdateExistingRecord() {

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
        orderable.setDateUpdated(4910843012L);
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
        orderable.setDateUpdated(939900843012L);
        repository.update(orderable);

        // ensure old values are removed
        List<Orderable> orderables = repository.get("id_3", "trade_item_id", "commodity_type_id");
        assertEquals(orderables.size(), 0);

        // ensure values are updated
        orderables = repository.get("id_3", "trade_item_id_3", "commodity_type_id_3");
        assertEquals(orderables.size(), 1);
    }
}
