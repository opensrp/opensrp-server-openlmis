package org.opensrp.stock.openlmis.repository.postgres;

import org.opensrp.stock.openlmis.domain.postgres.MasterTable;
import org.opensrp.stock.openlmis.domain.postgres.MasterTableExample;
import org.opensrp.stock.openlmis.repository.postgres.mapper.custom.CustomMasterTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.DEFAULT_FETCH_LIMIT;

@Repository
public class MasterTableRepository implements BaseRepository<MasterTable> {

    @Autowired
    private CustomMasterTableMapper masterTableMapper;

    @Override
    public void add(MasterTable masterTableEntry) {

        if (masterTableEntry == null || masterTableEntry.getId() == null) {
            return;
        }
        // MasterTable already exists
        if (retrievePrimaryKey(masterTableEntry) != null) {
            return;
        }
        masterTableMapper.insert(masterTableEntry);
    }

    private String retrievePrimaryKey(MasterTable masterTableEntry) {

        Object uniqueId = getUniqueField(masterTableEntry);
        if (uniqueId == null) {
            return null;
        }

        String id = uniqueId.toString();

        MasterTableExample masterTableEntryExample = new MasterTableExample();
        masterTableEntryExample.createCriteria().andIdEqualTo(id);

        List<MasterTable> result = masterTableMapper.selectByExample(masterTableEntryExample);
        if (result.size() == 0) {
            return null;
        }
        return masterTableEntry.getId().toString();
    }

    private Object getUniqueField(MasterTable masterTableEntry) {

        if (masterTableEntry == null) {
            return null;
        }
        return masterTableEntry.getId().toString();
    }

    @Override
    public MasterTable get(String id) {
        return masterTableMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(MasterTable masterTableEntry) {
        masterTableMapper.updateByPrimaryKey(masterTableEntry);
    }


    @Override
    public List<MasterTable> getAll() {

        MasterTableExample masterTableExample = new MasterTableExample();
        masterTableExample.createCriteria();
        return masterTableMapper.select(masterTableExample, 0, DEFAULT_FETCH_LIMIT);
    }

    @Override
    public Long safeRemove(MasterTable masterTable) {

        if (masterTable == null || masterTable.getId() == null) {
            return null;
        }

        if (masterTable.getDateDeleted() == null) {
            masterTable.setDateDeleted( Calendar.getInstance().getTimeInMillis());
        }

        MasterTableExample masterTableExample = new MasterTableExample();
        masterTableExample.createCriteria().andIdEqualTo(masterTable.getId()).andDateDeletedIsNull();
        masterTableMapper.updateByExample(masterTable, masterTableExample);

        return masterTable.getDateDeleted();
    }
}
