package org.opensrp.stock.openlmis.repository.postgres;

import org.opensrp.stock.openlmis.domain.MasterTableMetaData;
import org.opensrp.stock.openlmis.domain.postgres.MasterMetadataEntry;
import org.opensrp.stock.openlmis.domain.postgres.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.postgres.MasterTableEntryExample;
import org.opensrp.stock.openlmis.repository.postgres.mapper.custom.CustomMasterTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.DEFAULT_FETCH_LIMIT;
import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;

@Repository
public class MasterTableRepository implements BaseRepository<MasterTableEntry> {

    @Autowired
    private CustomMasterTableMapper masterTableMapper;

    @Autowired
    private MasterMetadataRepository masterMetadataRepository;

    public MasterTableEntry add(MasterTableMetaData masterTableMetaData) {

        if (masterTableMetaData == null || masterTableMetaData.getUuid() == null) {
            return null;
        }

        MasterTableEntry masterTableEntry = convert(masterTableMetaData, null);
        masterTableEntry.setDateUpdated(getCurrentTime());
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

        masterTableEntry.setDateUpdated(getCurrentTime());
        int rowsAffected = masterTableMapper.insertSelectiveAndSetId(masterTableEntry);
        if (rowsAffected < 1 || masterTableEntry.getId() == null) {
            return;
        }

        // Add metadata to master metadata table
        MasterMetadataEntry entry =  convert((MasterTableMetaData) masterTableEntry.getJson());
        entry.setMasterTableEntryId(masterTableEntry.getId());
        masterMetadataRepository.add(entry);
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

        masterTableEntry.setDateUpdated(getCurrentTime());
        masterTableMapper.updateByPrimaryKey(masterTableEntry);

        // Add or update metadata to master metadata table
        MasterMetadataEntry entry = convert((MasterTableMetaData) masterTableEntry.getJson());
        entry.setMasterTableEntryId(masterTableEntry.getId());
        List<MasterMetadataEntry> result = masterMetadataRepository.get(entry.getUuid(), entry.getType());
        MasterMetadataEntry lastEntry = result.get(result.size() - 1);
        if (result != null) {
            entry.setId(lastEntry.getId());
            masterMetadataRepository.update(entry, entry.getUuid(), entry.getType());
        } else {
            masterMetadataRepository.add(entry);
        }
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

    private MasterMetadataEntry convert(MasterTableMetaData masterTableMetaData) {

        MasterMetadataEntry masterMetadataEntry = new MasterMetadataEntry();
        masterMetadataEntry.setMasterTableEntryId(masterTableMetaData.getMasterTableEntryId());
        masterMetadataEntry.setType(masterTableMetaData.getType());
        masterMetadataEntry.setUuid(masterTableMetaData.getUuid());

        return masterMetadataEntry;
    }
}
