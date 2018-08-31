package org.opensrp.stock.openlmis.repository;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.Lot;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;

public class LotRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private LotRepository repository;

    @BeforeClass
    public static void bootStrap() {
        tableNames.add("core.lot");
    }

    @Test
    public void testAddShouldAddNewLot() {

        Lot lot = new Lot();
        lot.setActive(true);
        lot.setExpirationDate(getCurrentTime());
        lot.setId("id");
        lot.setLotCode("lot_code");
        lot.setManufactureDate(getCurrentTime() - 20000L);
        lot.setTradeItemId("trade_item_id");
        repository.addOrUpdate(lot);

        lot = new Lot();
        lot.setActive(true);
        lot.setExpirationDate(getCurrentTime());
        lot.setId("id_1");
        lot.setLotCode("lot_code_2");
        lot.setManufactureDate(getCurrentTime() - 20000L);
        lot.setTradeItemId("trade_item_id_2");
        repository.addOrUpdate(lot);

        List<Lot> lots = repository.getAll();

        assertEquals(lots.size(), 2);
    }

    @Test
    public void testAddShouldUpdateLotIfExists() {

        Lot lot = new Lot();
        lot.setActive(true);
        lot.setExpirationDate(getCurrentTime());
        lot.setId("id");
        lot.setLotCode("lot_code");
        lot.setManufactureDate(getCurrentTime() - 20000L);
        lot.setTradeItemId("trade_item_id");
        repository.addOrUpdate(lot);

        Lot entry = new Lot();
        entry.setId(lot.getId());
        entry.setActive(true);
        entry.setExpirationDate(getCurrentTime());
        entry.setLotCode("lot_code_1");
        entry.setManufactureDate(getCurrentTime() - 20000L);
        entry.setTradeItemId("trade_item_id_1");
        repository.addOrUpdate(entry);

        lot = repository.get(lot.getId());
        assertEquals(entry.getId(), lot.getId());
        assertEquals(entry.getLotCode(), lot.getLotCode());
        assertEquals(entry.getTradeItemId(), lot.getTradeItemId());
        assertEquals(entry.getExpirationDate(), lot.getExpirationDate());
        assertEquals(entry.getActive(), lot.getActive());
    }

    @Test
    public void testGetShouldRetrieveAddedLot() {

        Lot lot = new Lot();
        lot.setActive(true);
        lot.setExpirationDate(getCurrentTime());
        lot.setId("id");
        lot.setLotCode("lot_code");
        lot.setManufactureDate(getCurrentTime() - 20000L);
        lot.setTradeItemId("trade_item_id");
        repository.addOrUpdate(lot);

        lot = new Lot();
        lot.setActive(true);
        lot.setExpirationDate(getCurrentTime());
        lot.setId("id_1");
        lot.setLotCode("lot_code_2");
        lot.setManufactureDate(getCurrentTime() - 20000L);
        lot.setTradeItemId("trade_item_id_2");
        repository.addOrUpdate(lot);


        List<Lot> lots = repository.get("id_1", "trade_item_id_2", "lot_code_2");
        assertEquals(lots.size(), 1);
    }

    @Test
    public void testGetShouldRetrieveAddedLotById() {

        Lot lot = new Lot();
        lot.setActive(true);
        lot.setExpirationDate(getCurrentTime());
        lot.setId("id");
        lot.setLotCode("lot_code");
        lot.setManufactureDate(getCurrentTime() - 20000L);
        lot.setTradeItemId("trade_item_id");
        repository.addOrUpdate(lot);

        lot = new Lot();
        lot.setActive(true);
        lot.setExpirationDate(getCurrentTime());
        lot.setId("id_1");
        lot.setLotCode("lot_code_2");
        lot.setManufactureDate(getCurrentTime() - 20000L);
        lot.setTradeItemId("trade_item_id_2");
        repository.addOrUpdate(lot);

        assertNotNull(repository.get("id_1"));
    }

    @Test
    public void testGetShouldRetrieveAddedLotByServerVersion() {

        long timeBeforeInsertion = getCurrentTime();

        Lot lot = new Lot();
        lot.setActive(true);
        lot.setExpirationDate(getCurrentTime());
        lot.setId("id");
        lot.setLotCode("lot_code");
        lot.setManufactureDate(getCurrentTime() - 20000L);
        lot.setTradeItemId("trade_item_id");
        repository.addOrUpdate(lot);

        lot = new Lot();
        lot.setActive(true);
        lot.setExpirationDate(getCurrentTime());
        lot.setId("id_1");
        lot.setLotCode("lot_code_2");
        lot.setManufactureDate(getCurrentTime() - 20000L);
        lot.setTradeItemId("trade_item_id_2");
        repository.addOrUpdate(lot);

        List<Lot> result = repository.get(timeBeforeInsertion);
        assertEquals(result.size(), 2);
    }

    @Test
    public void testUpdateShouldUpdateExistingLot() {

        Lot lot = new Lot();
        lot.setActive(true);
        lot.setExpirationDate(getCurrentTime());
        lot.setId("id");
        lot.setLotCode("lot_code");
        lot.setManufactureDate(getCurrentTime() - 20000L);
        lot.setTradeItemId("trade_item_id");
        repository.addOrUpdate(lot);

        lot = new Lot();
        lot.setActive(true);
        lot.setExpirationDate(getCurrentTime());
        lot.setId("id");
        lot.setLotCode("lot_code_2");
        lot.setManufactureDate(getCurrentTime() - 20000L);
        lot.setTradeItemId("trade_item_id_2");
        repository.update(lot);

        // ensure old values are removed
        List<Lot> lots = repository.get("id_3", "trade_item_id", "lot_code");
        assertEquals(lots.size(), 0);

        // ensure values are updated
        lots = repository.get("id", "trade_item_id_2", "lot_code_2");
        assertEquals(lots.size(), 1);
    }

    @Test
    public void testGetAllShouldGetAllLotsInTable() {

        Lot lot = new Lot();
        lot.setActive(true);
        lot.setExpirationDate(getCurrentTime());
        lot.setId("id");
        lot.setLotCode("lot_code");
        lot.setManufactureDate(getCurrentTime() - 20000L);
        lot.setTradeItemId("trade_item_id");
        repository.addOrUpdate(lot);

        lot = new Lot();
        lot.setActive(true);
        lot.setExpirationDate(getCurrentTime());
        lot.setId("id_1");
        lot.setLotCode("lot_code_2");
        lot.setManufactureDate(getCurrentTime() - 20000L);
        lot.setTradeItemId("trade_item_id_2");
        repository.addOrUpdate(lot);

        List<Lot> lots = repository.getAll();

        assertEquals(lots.size(), 2);
    }

    @Test
    public void testSafeRemoveShouldAddADeleteDateToLot() {

        Lot lot = new Lot();
        lot.setActive(true);
        lot.setExpirationDate(getCurrentTime());
        lot.setId("id");
        lot.setLotCode("lot_code");
        lot.setManufactureDate(getCurrentTime() - 20000L);
        lot.setTradeItemId("trade_item_id");
        repository.addOrUpdate(lot);

        Long timeStamp = repository.safeRemove(lot);
        Lot result = repository.get(lot.getId());
        assertNotNull(result);
        assertEquals(result.getDateDeleted(), timeStamp);
    }
}
