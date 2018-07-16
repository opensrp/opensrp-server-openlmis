package org.opensrp.stock.openlmis.repository.postgres;

import org.opensrp.stock.openlmis.domain.MasterTableMetaData;
import org.opensrp.stock.openlmis.domain.postgres.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.postgres.MasterTableEntryExample;
import org.opensrp.stock.openlmis.repository.postgres.mapper.custom.CustomMasterTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.DEFAULT_FETCH_LIMIT;

@Repository
public class MasterTableRepository implements BaseRepository<MasterTableEntry> {

    @Autowired
    private CustomMasterTableMapper masterTableMapper;


    public MasterTableEntry add(MasterTableMetaData masterTableMetaData) {

        if (masterTableMetaData == null || masterTableMetaData.getId() == null) {
            return null;
        }

        MasterTableEntry masterTableEntry = convert(masterTableMetaData, null);
        add(masterTableEntry);
        if (masterTableEntry.getId() == null) {
            return null;
        }
        return masterTableEntry;
    }

    @Override
    public void add(MasterTableEntry masterTableEntry) {

        if (masterTableEntry == null) {
            return;
        }

        // MasterTableEntry already exists
        if (retrievePrimaryKey(masterTableEntry) != null) {
            return;
        }

        int rowsAffected = masterTableMapper.insertSelectiveAndSetId(masterTableEntry);
        if (rowsAffected < 1 || masterTableEntry.getId() == null) {
            return;
        }
        // TODO: add metadata to a separate table
    }

    private Long retrievePrimaryKey(MasterTableEntry masterTableEntry) {

        Object uniqueId = getUniqueField(masterTableEntry);
        if (uniqueId == null) {
            return null;
        }
        Long id = (Long) uniqueId;

        MasterTableEntryExample masterTableEntryExample = new MasterTableEntryExample();
        masterTableEntryExample.createCriteria().andIdEqualTo(id);

        List<MasterTableEntry> result = masterTableMapper.selectByExample(masterTableEntryExample);
        if (result.size() == 0) {
            return null;
        }
        return masterTableEntry.getId();
    }

    private Object getUniqueField(MasterTableEntry masterTableEntry) {

        if (masterTableEntry == null) {
            return null;
        }
        return masterTableEntry.getId();
    }

    @Override
    public MasterTableEntry get(Object id) {
        return masterTableMapper.selectByPrimaryKey((Long) id);
    }

    @Override
    public void update(MasterTableEntry masterTableEntry) {
        masterTableMapper.updateByPrimaryKey(masterTableEntry);
    }


    @Override
    public List<MasterTableEntry> getAll() {

        MasterTableEntryExample masterTableExample = new MasterTableEntryExample();
        masterTableExample.createCriteria();
        return masterTableMapper.select(masterTableExample, 0, DEFAULT_FETCH_LIMIT);
    }

    @Override
    public Long safeRemove(MasterTableEntry masterTableEntry) {

        if (masterTableEntry == null || masterTableEntry.getId() == null) {
            return null;
        }

        if (masterTableEntry.getDateDeleted() == null) {
            masterTableEntry.setDateDeleted( Calendar.getInstance().getTimeInMillis());
        }

        MasterTableEntryExample masterTableExample = new MasterTableEntryExample();
        masterTableExample.createCriteria().andIdEqualTo(masterTableEntry.getId()).andDateDeletedIsNull();
        masterTableMapper.updateByExample(masterTableEntry, masterTableExample);

        return masterTableEntry.getDateDeleted();
    }

    private MasterTableEntry convert(MasterTableMetaData metaData, Long primaryKey) {
        if (metaData == null) {
            return null;
        }

        MasterTableEntry masterTableEntry = new MasterTableEntry();
        masterTableEntry.setId(primaryKey);
        masterTableEntry.setJson(metaData);

        return masterTableEntry;
    }
}
