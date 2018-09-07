package org.opensrp.stock.openlmis.repository;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.Code;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
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
        tableNames.add("core.master_table");
        tableNames.add("core.master_metadata");
    }

    @Test
    public void testAddShouldAddNewMasterTableEntry() {

        TradeItemMetaData metaData = new TradeItemMetaData(
                "identifier"
        );

        MasterTableEntry masterTableEntry = new MasterTableEntry();
        masterTableEntry.setJson(metaData);
        repository.addOrUpdate(masterTableEntry);

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
    public void testAddShouldUpdateMasterTableEntryIfExists() {

        ProgramMetaData metaData = new ProgramMetaData(
                "identifier"
        );
        metaData.setCode(new Code("code"));
        metaData.setName("program");
        metaData.setDescription("description");
        metaData.setEnableDatePhysicalStockCountCompleted(true);
        metaData.setPeriodsSkippable(false);
        metaData.setShowNonFullSupplyTab(false);
        metaData.setSkipAuthorization(true);
        repository.add(metaData);

        ProgramMetaData newMetaData = new ProgramMetaData(
                "identifier"
        );
        newMetaData.setCode(new Code("code_1"));
        newMetaData.setName("program_1");
        newMetaData.setDescription("description_1");
        newMetaData.setEnableDatePhysicalStockCountCompleted(true);
        newMetaData.setPeriodsSkippable(true);
        newMetaData.setShowNonFullSupplyTab(false);
        newMetaData.setSkipAuthorization(false);
        repository.add(newMetaData);

        // assert all data matches
        metaData = (ProgramMetaData) repository.get("Program", "identifier").getJson();
        assertEquals(newMetaData.getId(), metaData.getId());
        assertEquals(newMetaData.getName(), metaData.getName());
        assertEquals(newMetaData.getDescription(), metaData.getDescription());
        assertEquals(newMetaData.getEnableDatePhysicalStockCountCompleted(), metaData.getEnableDatePhysicalStockCountCompleted());
        assertEquals(newMetaData.getPeriodsSkippable(), metaData.getPeriodsSkippable());
        assertEquals(newMetaData.getShowNonFullSupplyTab(), metaData.getShowNonFullSupplyTab());
        assertEquals(newMetaData.getSkipAuthorization(), metaData.getSkipAuthorization());
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
        metaData.setServerVersion(timeBeforeInsertion + 1);
        repository.add(metaData);

        metaData = new BaseMetaData(
                "identifier_2"
        );
        metaData.setServerVersion(timeBeforeInsertion + 2);
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
        metaData.setServerVersion(timeBeforeInsertion + 1);
        repository.add(metaData);

        metaData = new TradeItemMetaData(
                "identifier_2"
        );
        metaData.setServerVersion(timeBeforeInsertion + 2);
        repository.add(metaData);


        metaData = new ProgramMetaData(
                "identifier_3"
        );
        metaData.setServerVersion(timeBeforeInsertion + 3);
        repository.add(metaData);

        List<MasterTableEntry> masterTableEntries = repository.get("TradeItem", timeBeforeInsertion);
        assertEquals(masterTableEntries.size(), 2);
    }


    @Test
    public void testUpdateShouldUpdateExistingMasterTableEntry() {

        ProgramMetaData metaData = new ProgramMetaData(
                "identifier"
        );
        metaData.setCode(new Code("code"));
        metaData.setName("program");
        metaData.setDescription("description");
        metaData.setEnableDatePhysicalStockCountCompleted(true);
        metaData.setPeriodsSkippable(false);
        metaData.setShowNonFullSupplyTab(false);
        metaData.setSkipAuthorization(true);
        MasterTableEntry entry = repository.add(metaData);

        MasterTableEntry updatedEntry = new MasterTableEntry();
        ProgramMetaData newMetaData = new ProgramMetaData(
                "identifier"
        );
        newMetaData.setCode(new Code("code_1"));
        newMetaData.setName("program_1");
        newMetaData.setDescription("description_1");
        newMetaData.setEnableDatePhysicalStockCountCompleted(true);
        newMetaData.setPeriodsSkippable(true);
        newMetaData.setShowNonFullSupplyTab(false);
        newMetaData.setSkipAuthorization(false);
        updatedEntry.setJson(newMetaData);
        updatedEntry.setServerVersion(getCurrentTime());
        repository.update(updatedEntry);

        // assert all data matches
        metaData = (ProgramMetaData) repository.get(entry.getId()).getJson();
        assertEquals(newMetaData.getId(), metaData.getId());
        assertEquals(newMetaData.getName(), metaData.getName());
        assertEquals(newMetaData.getDescription(), metaData.getDescription());
        assertEquals(newMetaData.getEnableDatePhysicalStockCountCompleted(), metaData.getEnableDatePhysicalStockCountCompleted());
        assertEquals(newMetaData.getPeriodsSkippable(), metaData.getPeriodsSkippable());
        assertEquals(newMetaData.getShowNonFullSupplyTab(), metaData.getShowNonFullSupplyTab());
        assertEquals(newMetaData.getSkipAuthorization(), metaData.getSkipAuthorization());
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
