package org.opensrp.stock.openlmis.repository.postgres;

import org.opensrp.stock.openlmis.domain.postgres.ProgramOrderable;
import org.opensrp.stock.openlmis.domain.postgres.ProgramOrderableExample;
import org.opensrp.stock.openlmis.repository.postgres.mapper.custom.CustomProgramOrderableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.DEFAULT_FETCH_LIMIT;

@Repository
public class ProgramOrderableRepository implements BaseRepository<ProgramOrderable> {

    @Autowired
    private CustomProgramOrderableMapper programOrderableMapper;

    @Override
    public void add(ProgramOrderable programOrderable) {
        
        if (programOrderable == null || programOrderable.getId() == null) {
            return;
        }

        // ProgramOrderable already exists
        if (retrievePrimaryKey(programOrderable) != null) {
            return;
        }
        programOrderableMapper.insert(programOrderable);
    }

    @Override
    public ProgramOrderable get(Object id) {
        return programOrderableMapper.selectByPrimaryKey((String) id);
    }

    public List<ProgramOrderable> get(String id, String programId, String orderableId) {

        ProgramOrderableExample programOrderableExample = new ProgramOrderableExample();
        programOrderableExample.createCriteria().andIdEqualTo(id).andProgramIdEqualTo(programId).andOrderableIdEqualTo(orderableId);
        return programOrderableMapper.selectByExample(programOrderableExample);
    }


    @Override
    public void update(ProgramOrderable programOrderable) {
        programOrderableMapper.updateByPrimaryKey(programOrderable);
    }

    @Override
    public List<ProgramOrderable> getAll() {

        ProgramOrderableExample programOrderableExample = new ProgramOrderableExample();
        programOrderableExample.createCriteria();
        return programOrderableMapper.select(programOrderableExample, 0, DEFAULT_FETCH_LIMIT);
    }

    @Override
    public Long safeRemove(ProgramOrderable programOrderable) {

        if (programOrderable == null || programOrderable.getId() == null) {
            return null;
        }

        if (programOrderable.getDateDeleted() == null) {
            programOrderable.setDateDeleted( Calendar.getInstance().getTimeInMillis());
        }

        ProgramOrderableExample programOrderableExample = new ProgramOrderableExample();
        programOrderableExample.createCriteria().andIdEqualTo(programOrderable.getId()).andDateDeletedIsNull();
        programOrderableMapper.updateByExample(programOrderable, programOrderableExample);

        return programOrderable.getDateDeleted();
    }

    private String retrievePrimaryKey(ProgramOrderable programOrderable) {

        Object uniqueId = getUniqueField(programOrderable);
        if (uniqueId == null) {
            return null;
        }

        String id = uniqueId.toString();

        ProgramOrderableExample programOrderableExample = new ProgramOrderableExample();
        programOrderableExample.createCriteria().andIdEqualTo(id);

        List<ProgramOrderable> result = programOrderableMapper.selectByExample(programOrderableExample);
        if (result.size() == 0) {
            return null;
        }
        return programOrderable.getId().toString();
    }

    protected Object getUniqueField(ProgramOrderable programOrderable) {

        if (programOrderable == null) {
            return null;
        }
        return programOrderable.getId().toString();
    }
}
