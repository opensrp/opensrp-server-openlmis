package org.opensrp.stock.openlmis.repository;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.opensrp.stock.openlmis.domain.metadata.DispensableMetaData;
import org.opensrp.stock.openlmis.domain.metadata.ProgramMetaData;
import org.opensrp.stock.openlmis.domain.metadata.TradeItemMetaData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;

public class MasterTableRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private MasterTableRepository repository;

    @BeforeClass
    public static void bootStrap() {
        tableName = "core.master_table";
    }

    @Test
    public void testAddShouldAddNewMasterTableEntry() {

        TradeItemMetaData metaData = new TradeItemMetaData(
                "identifier"
        );

        MasterTableEntry masterTableEntry = new MasterTableEntry();
        masterTableEntry.setJson(metaData);
        repository.add(masterTableEntry);

        masterTableEntry = repository.get(masterTableEntry.getId());
        assertNotNull(masterTableEntry);
    }

    @Test
    public void testAddShouldAddNewMasterTableEntryFromMetadata() {

        BaseMetaData metaData = new BaseMetaData(
                "identifier"
        );

        MasterTableEntry entry = repository.add(metaData);
        assertNotNull(entry);

        MasterTableEntry masterTableEntryEntry = repository.get(entry.getId());
        assertNotNull(masterTableEntryEntry);
    }

    @Test
    public void testAddShouldNotAddNewMasterTableEntryIfDuplicate() {

        BaseMetaData metaData = new BaseMetaData(
                "identifier"
        );
        MasterTableEntry entry = repository.add(metaData);

        BaseMetaData baseMetaData = new BaseMetaData(
                "identifier_2"
        );
        MasterTableEntry masterTableEntry = new MasterTableEntry();
        masterTableEntry.setId(entry.getId());
        masterTableEntry.setJson(baseMetaData);

        repository.add(masterTableEntry);
        masterTableEntry = repository.get(entry.getId());

        // assert all data matches
        baseMetaData = (BaseMetaData) masterTableEntry.getJson();
        assertEquals(baseMetaData.getId(), metaData.getId());
    }

    @Test
    public void testGetShouldRetrieveExistingMasterTableEntry() {

        BaseMetaData metaData = new BaseMetaData(
                "identifier"
        );

        MasterTableEntry entry = repository.add(metaData);
        entry = repository.get(entry.getId());
        assertNotNull(entry);
    }

    @Test
    public void testGetShouldRetrieveExistingMasterTableEntryByServerVersion() {

        long timeBeforeInsertion = getCurrentTime();
        BaseMetaData metaData = new BaseMetaData(
                "identifier"
        );
        repository.add(metaData);

        metaData = new BaseMetaData(
                "identifier_2"
        );
        repository.add(metaData);

        List<MasterTableEntry> masterTableEntries = repository.get(timeBeforeInsertion);
        assertEquals(masterTableEntries.size(), 2);
    }

    @Test
    public void testGetShouldRetrieveExistingMasterTableEntryByTypeAndId() {

        BaseMetaData metaData = new BaseMetaData(
                "identifier"
        );
        repository.add(metaData);

        metaData = new TradeItemMetaData(
                "identifier_2"
        );
        repository.add(metaData);

        assertNotNull(repository.get("TradeItem", "identifier_2"));
    }

    @Test
    public void testGetShouldRetrieveExistingMasterTableEntryByType() {

        BaseMetaData metaData = new TradeItemMetaData(
                "identifier"
        );
        repository.add(metaData);

        metaData = new TradeItemMetaData(
                "identifier_2"
        );
        repository.add(metaData);


        metaData = new ProgramMetaData(
                "identifier_3"
        );
        repository.add(metaData);

        List<MasterTableEntry> masterTableEntries = repository.get("TradeItem");
        assertEquals(masterTableEntries.size(), 2);
    }

    @Test
    public void testGetShouldRetrieveExistingMasterTableEntryByTypeAndServerVersion() {

        long timeBeforeInsertion = getCurrentTime();
        BaseMetaData metaData = new TradeItemMetaData(
                "identifier"
        );
        repository.add(metaData);

        metaData = new TradeItemMetaData(
                "identifier_2"
        );
        repository.add(metaData);


        metaData = new ProgramMetaData(
                "identifier_3"
        );
        repository.add(metaData);

        List<MasterTableEntry> masterTableEntries = repository.get("TradeItem", timeBeforeInsertion);
        assertEquals(masterTableEntries.size(), 2);
    }


    @Test
    public void testUpdateShouldUpdateExistingMasterTableEntry() {

        ProgramMetaData metaData = new ProgramMetaData(
                "identifier"
        );
        MasterTableEntry entry = repository.add(metaData);

        MasterTableEntry updatedEntry = new MasterTableEntry();
        updatedEntry.setId(entry.getId());

        DispensableMetaData newMetaData = new DispensableMetaData(
                "identifier_2"
        );
        updatedEntry.setJson(newMetaData);
        repository.update(updatedEntry);

        // assert all data matches
        newMetaData = (DispensableMetaData) repository.get(entry.getId()).getJson();
        assertEquals(newMetaData.getId(), "identifier_2");
        assertEquals(newMetaData.getClass().getName(), DispensableMetaData.class.getName());
    }

    @Test
    public void testGetAllShouldRetrieveAllMasterTableEntries() {

        BaseMetaData metaData = new BaseMetaData(
                "identifier"
        );
        repository.add(metaData);

        metaData = new BaseMetaData(
                "identifier_2"
        );
        repository.add(metaData);

       metaData = new BaseMetaData(
                "identifier_3"
        );
        repository.add(metaData);

        List<MasterTableEntry> masterTableEntries = repository.getAll();
        assertEquals(masterTableEntries.size(), 3);
    }

    @Test
    public void testSafeRemoveShouldAddDeleteDateToMasterTableEntry() {

        BaseMetaData metaData = new BaseMetaData(
                "identifier_3"
        );
        MasterTableEntry entry = repository.add(metaData);
        assertNull(entry.getDateDeleted());

        repository.safeRemove(entry);
        entry = repository.get(entry.getId());

        assertNotNull(entry.getDateDeleted());
    }
}
