package org.opensrp.stock.openlmis.repository.postgres;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.MasterTableMetaData;
import org.opensrp.stock.openlmis.domain.postgres.MasterTableEntry;
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

        MasterTableMetaData metaData = new MasterTableMetaData(
                "type",
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

        MasterTableMetaData metaData = new MasterTableMetaData(
                "type",
                "identifier"
        );

        MasterTableEntry entry = repository.add(metaData);
        assertNotNull(entry);

        MasterTableEntry masterTableEntryEntry = repository.get(entry.getId());
        assertNotNull(masterTableEntryEntry);
    }

    @Test
    public void testAddShouldNotAddNewMasterTableEntryIfDuplicate() {

        MasterTableMetaData metaData = new MasterTableMetaData(
                "type",
                "identifier"
        );
        MasterTableEntry entry = repository.add(metaData);

        MasterTableMetaData  masterTableMetaData= new MasterTableMetaData(
                "type_2",
                "identifier_2"
        );
        MasterTableEntry masterTableEntry = new MasterTableEntry();
        masterTableEntry.setId(entry.getId());
        masterTableEntry.setJson(masterTableMetaData);

        repository.add(masterTableEntry);
        masterTableEntry = repository.get(entry.getId());

        // assert all data matches
        masterTableMetaData = (MasterTableMetaData) masterTableEntry.getJson();
        assertEquals(masterTableMetaData.getType(), metaData.getType());
        assertEquals(masterTableMetaData.getUuid(), metaData.getUuid());
    }

    @Test
    public void testGetShouldRetrieveExistingMasterTableEntry() {

        MasterTableMetaData metaData = new MasterTableMetaData(
                "type",
                "identifier"
        );

        MasterTableEntry entry = repository.add(metaData);
        entry = repository.get(entry.getId());
        assertNotNull(entry);
    }

    @Test
    public void testGetShouldRetrieveExistingMasterTableEntryByServerVersion() {

        long timeBeforeInsertion = getCurrentTime();
        MasterTableMetaData metaData = new MasterTableMetaData(
                "type",
                "identifier"
        );
        repository.add(metaData);

        metaData = new MasterTableMetaData(
                "type_2",
                "identifier_2"
        );
        repository.add(metaData);

        List<MasterTableEntry> masterTableEntries = repository.get(timeBeforeInsertion);
        assertEquals(masterTableEntries.size(), 2);
    }

    @Test
    public void testUpdateShouldUpdateExistingMasterTableEntry() {

        MasterTableMetaData metaData = new MasterTableMetaData(
                "type",
                "identifier"
        );
        MasterTableEntry entry = repository.add(metaData);

        MasterTableEntry updatedEntry = new MasterTableEntry();
        updatedEntry.setId(entry.getId());

        MasterTableMetaData newMetaData = new MasterTableMetaData(
                "type_2",
                "identifier_2"
        );
        updatedEntry.setJson(newMetaData);
        repository.update(updatedEntry);

        // assert all data matches
        metaData = (MasterTableMetaData) repository.get(entry.getId()).getJson();
        assertEquals(metaData.getType(), "type_2");
        assertEquals(metaData.getUuid(), "identifier_2");
    }

    @Test
    public void testGetAllShouldRetrieveAllMasterTableEntries() {

        MasterTableMetaData metaData = new MasterTableMetaData(
                "type",
                "identifier"
        );
        repository.add(metaData);

        metaData = new MasterTableMetaData(
                "type_2",
                "identifier_2"
        );
        repository.add(metaData);

       metaData = new MasterTableMetaData(
                "type_3",
                "identifier_3"
        );
        repository.add(metaData);

        List<MasterTableEntry> masterTableEntries = repository.getAll();
        assertEquals(masterTableEntries.size(), 3);
    }

    @Test
    public void testSafeRemoveShouldAddDeleteDateToMasterTableEntry() {

        MasterTableMetaData metaData = new MasterTableMetaData(
                "type_3",
                "identifier_3"
        );
        MasterTableEntry entry = repository.add(metaData);
        assertNull(entry.getDateDeleted());

        repository.safeRemove(entry);
        entry = repository.get(entry.getId());

        assertNotNull(entry.getDateDeleted());
    }
}
