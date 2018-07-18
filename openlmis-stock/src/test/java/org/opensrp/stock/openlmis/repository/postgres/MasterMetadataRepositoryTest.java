package org.opensrp.stock.openlmis.repository.postgres;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.postgres.MasterMetadataEntry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MasterMetadataRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private MasterMetadataRepository repository;

    @BeforeClass
    public static void bootStrap() { tableName = "core.master_metadata"; }

    @Test
    public void testAddShouldAddNewMasterMetadataEntry() {

        MasterMetadataEntry entry = new MasterMetadataEntry();
        entry.setUuid("uuid");
        entry.setType("type");
        entry.setMasterTableEntryId(93412038L);
        entry.setServerVersion(482492049L);

        repository.add(entry);
        List<MasterMetadataEntry> result = repository.get("uuid", "type");
        assertEquals(result.size(), 1);
    }

    @Test
    public void testAddShouldNotAddNewMasterMetadataEntryIfDuplicate() {

        MasterMetadataEntry entry = new MasterMetadataEntry();
        entry.setUuid("uuid");
        entry.setType("type");
        entry.setMasterTableEntryId(93412038L);
        entry.setServerVersion(482492049L);
        repository.add(entry);

        MasterMetadataEntry masterMetadataEntry = new MasterMetadataEntry();
        masterMetadataEntry.setId(entry.getId());
        masterMetadataEntry.setUuid("uuid_2");
        masterMetadataEntry.setType("type_2");
        entry.setMasterTableEntryId(93412038L);
        masterMetadataEntry.setServerVersion(82492049L);
        repository.add(masterMetadataEntry);

        entry = repository.get(entry.getId());
        assertEquals(entry.getUuid(), "uuid");
        assertEquals(entry.getType(), "type");
        assertEquals(entry.getServerVersion().longValue(), 482492049L);
    }

    @Test
    public void testGetShouldRetrieveExistingMasterMetadataEntryById() {

        MasterMetadataEntry entry = new MasterMetadataEntry();
        entry.setUuid("uuid");
        entry.setType("type");
        entry.setMasterTableEntryId(93412038L);
        entry.setServerVersion(482492049L);
        repository.add(entry);

        MasterMetadataEntry result = repository.get(entry.getId());
        assertNotNull(result);
    }

    @Test
    public void testGetShouldRetrieveExistingMasterMetadataEntryByUuidAndType() {

        MasterMetadataEntry entry = new MasterMetadataEntry();
        entry.setUuid("uuid");
        entry.setType("type");
        entry.setMasterTableEntryId(93412038L);
        entry.setServerVersion(482492049L);
        repository.add(entry);

        MasterMetadataEntry masterMetadataEntry = new MasterMetadataEntry();
        masterMetadataEntry.setUuid("uuid_2");
        masterMetadataEntry.setType("type_2");
        entry.setMasterTableEntryId(93412038L);
        masterMetadataEntry.setServerVersion(482492049L);
        repository.add(entry);

        List<MasterMetadataEntry> entries = repository.get("uuid", "type");
        assertEquals(entries.size(), 1);
    }

    @Test
    public void testUpdateShouldUpdateExistingMasterMetadataEntry() {

        MasterMetadataEntry entry = new MasterMetadataEntry();
        entry.setUuid("uuid");
        entry.setType("type");
        entry.setMasterTableEntryId(93412038L);
        entry.setServerVersion(482492049L);
        repository.add(entry);

        MasterMetadataEntry masterMetadataEntry = new MasterMetadataEntry();
        masterMetadataEntry.setId(entry.getId());
        masterMetadataEntry.setUuid("uuid_2");
        masterMetadataEntry.setType("type_2");
        masterMetadataEntry.setMasterTableEntryId(93412038L);
        masterMetadataEntry.setServerVersion(899492049L);
        repository.update(masterMetadataEntry);

        entry = repository.get(entry.getId());
        assertEquals(entry.getUuid(), "uuid_2");
        assertEquals(entry.getType(), "type_2");
        assertEquals(entry.getServerVersion().longValue(), 899492049L);
    }

    @Test
    public void testGetAllShouldRetrieveAllMasterMetadataEntries() {

        MasterMetadataEntry entry = new MasterMetadataEntry();
        entry.setUuid("uuid");
        entry.setType("type");
        entry.setMasterTableEntryId(93412038L);
        entry.setServerVersion(482492049L);
        repository.add(entry);

        MasterMetadataEntry masterMetadataEntry = new MasterMetadataEntry();
        masterMetadataEntry.setUuid("uuid_2");
        masterMetadataEntry.setType("type_2");
        entry.setMasterTableEntryId(93412038L);
        masterMetadataEntry.setServerVersion(482492049L);
        repository.add(masterMetadataEntry);

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
        repository.add(entry);
        assertNull(repository.get(entry.getId()).getDateDeleted());

        // after deleting
        repository.safeRemove(entry);
        assertNotNull(repository.get(entry.getId()).getDateDeleted());
    }
}

