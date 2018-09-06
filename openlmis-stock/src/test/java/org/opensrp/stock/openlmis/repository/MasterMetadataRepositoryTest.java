package org.opensrp.stock.openlmis.repository;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.MasterMetadataEntry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;

public class MasterMetadataRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private MasterMetadataRepository repository;

    @BeforeClass
    public static void bootStrap() {
        tableNames.add("core.master_table");
        tableNames.add("core.master_metadata");
    }

    @Test
    public void testAddShouldAddNewMasterMetadataEntry() {

        MasterMetadataEntry entry = new MasterMetadataEntry();
        entry.setUuid("uuid");
        entry.setType("type");
        entry.setMasterTableEntryId(93412038L);
        entry.setServerVersion(482492049L);
        repository.addOrUpdate(entry);


        entry = new MasterMetadataEntry();
        entry.setUuid("uuid_2");
        entry.setType("type_2");
        entry.setMasterTableEntryId(93412038L);
        entry.setServerVersion(482492049L);
        repository.addOrUpdate(entry);

        List<MasterMetadataEntry> result = repository.get("uuid", "type");
        assertEquals(result.size(), 1);
    }

    @Test
    public void testAddShouldUpdateMasterMetadataEntryIfExists() {

        MasterMetadataEntry entry = new MasterMetadataEntry();
        entry.setUuid("uuid");
        entry.setType("type");
        entry.setMasterTableEntryId(7l);
        entry.setMasterTableEntryId(93412038L);
        entry.setServerVersion(482492049L);
        repository.addOrUpdate(entry);

        MasterMetadataEntry masterMetadataEntry = new MasterMetadataEntry();
        masterMetadataEntry.setUuid("uuid");
        masterMetadataEntry.setType("type");
        masterMetadataEntry.setMasterTableEntryId(8l);
        masterMetadataEntry.setMasterTableEntryId(93412038L);
        masterMetadataEntry.setServerVersion(82492049L);
        repository.addOrUpdate(masterMetadataEntry);

        // ensure values updated
        entry = repository.get(entry.getId());
        assertEquals(masterMetadataEntry.getMasterTableEntryId(), entry.getMasterTableEntryId());
    }

    @Test
    public void testGetShouldRetrieveExistingMasterMetadataEntryById() {

        MasterMetadataEntry entry = new MasterMetadataEntry();
        entry.setUuid("uuid");
        entry.setType("type");
        entry.setMasterTableEntryId(93412038L);
        entry.setServerVersion(482492049L);
        repository.addOrUpdate(entry);

        MasterMetadataEntry result = repository.get(entry.getId());
        assertNotNull(result);
    }

    @Test
    public void testGetShouldRetrieveExistingMasterMetadataEntryByUuidAndType() {

        MasterMetadataEntry entry = new MasterMetadataEntry();
        entry.setUuid("uuid");
        entry.setType("type");
        entry.setMasterTableEntryId(93412038L);
        repository.addOrUpdate(entry);

        MasterMetadataEntry masterMetadataEntry = new MasterMetadataEntry();
        masterMetadataEntry.setUuid("uuid_2");
        masterMetadataEntry.setType("type_2");
        masterMetadataEntry.setMasterTableEntryId(93412038L);
        repository.addOrUpdate(masterMetadataEntry);

        List<MasterMetadataEntry> entries = repository.get("uuid", "type");
        assertEquals(entries.size(), 1);
    }

    @Test
    public void testGetShouldRetrieveExistingMasterMetadataEntryByServerVersion() {

        long timeBeforeInsertion = getCurrentTime();

        MasterMetadataEntry entry = new MasterMetadataEntry();
        entry.setUuid("uuid");
        entry.setType("type");
        entry.setMasterTableEntryId(93412038L);
        entry.setServerVersion(timeBeforeInsertion + 1);
        repository.addOrUpdate(entry);

        MasterMetadataEntry masterMetadataEntry = new MasterMetadataEntry();
        masterMetadataEntry.setUuid("uuid_2");
        masterMetadataEntry.setType("type_2");
        masterMetadataEntry.setMasterTableEntryId(93412038L);
        masterMetadataEntry.setServerVersion(timeBeforeInsertion + 2);
        repository.addOrUpdate(masterMetadataEntry);

        List<MasterMetadataEntry> entries = repository.get(timeBeforeInsertion);
        assertEquals(entries.size(), 2);
    }

    @Test
    public void testUpdateShouldUpdateExistingMasterMetadataEntry() {

        MasterMetadataEntry entry = new MasterMetadataEntry();
        entry.setUuid("uuid");
        entry.setType("type");
        entry.setMasterTableEntryId(93412038L);
        repository.addOrUpdate(entry);

        MasterMetadataEntry masterMetadataEntry = new MasterMetadataEntry();
        masterMetadataEntry.setId(entry.getId());
        masterMetadataEntry.setUuid("uuid_2");
        masterMetadataEntry.setType("type_2");
        masterMetadataEntry.setMasterTableEntryId(878038L);
        repository.update(masterMetadataEntry);

        // ensure values were updated
        entry = repository.get(entry.getId());
        assertEquals(masterMetadataEntry.getMasterTableEntryId(), entry.getMasterTableEntryId());
    }

    @Test
    public void testGetAllShouldRetrieveAllMasterMetadataEntries() {

        MasterMetadataEntry entry = new MasterMetadataEntry();
        entry.setUuid("uuid");
        entry.setType("type");
        entry.setMasterTableEntryId(93412038L);
        entry.setServerVersion(482492049L);
        repository.addOrUpdate(entry);

        MasterMetadataEntry masterMetadataEntry = new MasterMetadataEntry();
        masterMetadataEntry.setUuid("uuid_2");
        masterMetadataEntry.setType("type_2");
        masterMetadataEntry.setMasterTableEntryId(93412038L);
        masterMetadataEntry.setServerVersion(482492049L);
        repository.addOrUpdate(masterMetadataEntry);

        List<MasterMetadataEntry> entries = repository.getAll();
        assertEquals(entries.size(), 2);
    }

    @Test
    public void testSafeRemoveShouldAddDeleteDateToMasterMetadateEntry() {

        MasterMetadataEntry entry = new MasterMetadataEntry();
        entry.setUuid("uuid");
        entry.setType("type");
        entry.setMasterTableEntryId(93412038L);
        entry.setServerVersion(482492049L);

        // before deleting
        repository.addOrUpdate(entry);
        assertNull(repository.get(entry.getId()).getDateDeleted());

        // after deleting
        repository.safeRemove(entry);
        assertNotNull(repository.get(entry.getId()).getDateDeleted());
    }
}

