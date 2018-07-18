package org.opensrp.stock.openlmis.repository.postgres;

import org.junit.BeforeClass;
import org.junit.Test;
import org.opensrp.stock.openlmis.domain.postgres.ProgramOrderable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class ProgramOrderableRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private ProgramOrderableRepository repository;

    @BeforeClass
    public static void bootStrap() {
        tableName = "core.program_orderable";
    }

    @Test
    public void testAddShouldAddNewProgramOrderable() {

        ProgramOrderable programOrderable = new ProgramOrderable();
        programOrderable.setId("id");
        programOrderable.setProgramId("program_id");
        programOrderable.setOrderableId("orderable_id");
        programOrderable.setDosesPerPatient(2);
        programOrderable.setActive(true);
        programOrderable.setFullSupply(false);
        programOrderable.setDateUpdated(93932430132L);

        repository.add(programOrderable);

        List<ProgramOrderable> programOrderables = repository.get("id", "program_id", "orderable_id");
        assertEquals(programOrderables.size(), 1);
    }

    @Test
    public void testAddShouldNotAddNewProgramOrderableIfDuplicate() {

        ProgramOrderable programOrderable = new ProgramOrderable();
        programOrderable.setId("id");
        programOrderable.setProgramId("program_id");
        programOrderable.setOrderableId("orderable_id");
        programOrderable.setDosesPerPatient(2);
        programOrderable.setActive(true);
        programOrderable.setFullSupply(false);
        programOrderable.setDateUpdated(93932430132L);
        repository.add(programOrderable);

        ProgramOrderable entry = new ProgramOrderable();
        programOrderable.setId(programOrderable.getId());
        programOrderable.setProgramId("program_id");
        programOrderable.setOrderableId("orderable_id");
        programOrderable.setDosesPerPatient(2);
        programOrderable.setActive(true);
        programOrderable.setFullSupply(false);
        programOrderable.setDateUpdated(93932430132L);
        repository.add(entry);

        entry = repository.get(programOrderable.getId());
        assertEquals(entry.getProgramId(), programOrderable.getProgramId());
        assertEquals(entry.getOrderableId(), programOrderable.getOrderableId());
        assertEquals(entry.getDosesPerPatient(), programOrderable.getDosesPerPatient());
        assertEquals(entry.getActive(), programOrderable.getActive());
        assertEquals(entry.getFullSupply(), programOrderable.getFullSupply());
    }

    @Test
    public void testGetShouldRetrieveAddedProgramOrderable() {

        ProgramOrderable programOrderable = new ProgramOrderable();
        programOrderable.setId("id_2");
        programOrderable.setProgramId("program_id_2");
        programOrderable.setOrderableId("orderable_id_2");
        programOrderable.setDosesPerPatient(3);
        programOrderable.setActive(true);
        programOrderable.setFullSupply(true);
        programOrderable.setDateUpdated(93932430132L);
        repository.add(programOrderable);

        List<ProgramOrderable> programOrderables = repository.get("id_2", "program_id_2", "orderable_id_2");
        assertEquals(programOrderables.size(), 1);
    }

    @Test
    public void testGetShouldRetrieveAddedProgramOrderableById() {

        ProgramOrderable programOrderable = new ProgramOrderable();
        programOrderable.setId("id_3");
        programOrderable.setProgramId("program_id_3");
        programOrderable.setOrderableId("orderable_id_3");
        programOrderable.setDosesPerPatient(3);
        programOrderable.setActive(true);
        programOrderable.setFullSupply(true);
        programOrderable.setDateUpdated(93932430132L);
        repository.add(programOrderable);

        ProgramOrderable result = repository.get("id_3");
        assertNotNull(result);
    }

    @Test
    public void testUpdateShouldUpdateExistingProgramOrderable() {

        ProgramOrderable programOrderable = new ProgramOrderable();
        programOrderable.setId("id_4");
        programOrderable.setProgramId("program_id_3");
        programOrderable.setOrderableId("orderable_id_3");
        programOrderable.setDosesPerPatient(3);
        programOrderable.setActive(true);
        programOrderable.setFullSupply(true);
        programOrderable.setDateUpdated(93932430132L);
        repository.add(programOrderable);

        programOrderable = new ProgramOrderable();
        programOrderable.setId("id_4");
        programOrderable.setProgramId("program_id_4");
        programOrderable.setOrderableId("orderable_id_4");
        programOrderable.setDosesPerPatient(3);
        programOrderable.setActive(true);
        programOrderable.setFullSupply(true);
        programOrderable.setDateUpdated(93932430132L);
        repository.update(programOrderable);

        // ensure old values are removed
        List<ProgramOrderable> programOrderables = repository.get("id_4", "program_id_3", "orderable_id_3");
        assertEquals(programOrderables.size(), 0);

        // ensure values are updated
        programOrderables = repository.get("id_4", "program_id_4", "orderable_id_4");;
        assertEquals(programOrderables.size(), 1);
    }

    @Test
    public void testGetAllShouldGetAllProgramOrderablesInTable() {


        ProgramOrderable programOrderable = new ProgramOrderable();
        programOrderable.setId("id_5");
        programOrderable.setProgramId("program_id_5");
        programOrderable.setOrderableId("orderable_id_5");
        programOrderable.setDosesPerPatient(3);
        programOrderable.setActive(true);
        programOrderable.setFullSupply(true);
        programOrderable.setDateUpdated(93932430132L);
        repository.add(programOrderable);

        programOrderable = new ProgramOrderable();
        programOrderable.setId("id_6");
        programOrderable.setProgramId("program_id_6");
        programOrderable.setOrderableId("orderable_id_6");
        programOrderable.setDosesPerPatient(3);
        programOrderable.setActive(true);
        programOrderable.setFullSupply(true);
        programOrderable.setDateUpdated(93932430132L);
        repository.add(programOrderable);

        List<ProgramOrderable> programOrderables = repository.getAll();
        assertEquals(programOrderables.size(), 2);
    }

    @Test
    public void testSafeRemoveShouldAddADeleteDateToProgramOrderable() {

        ProgramOrderable programOrderable = new ProgramOrderable();
        programOrderable.setId("id_7");
        programOrderable.setProgramId("program_id_7");
        programOrderable.setOrderableId("orderable_id_7");
        programOrderable.setDosesPerPatient(3);
        programOrderable.setActive(true);
        programOrderable.setFullSupply(true);
        programOrderable.setDateUpdated(93932430132L);
        repository.add(programOrderable);

        Long timeStamp = repository.safeRemove(programOrderable);
        ProgramOrderable result = repository.get(programOrderable.getId());
        assertNotNull(result);
        assertEquals(result.getDateDeleted(), timeStamp);
    }
}
