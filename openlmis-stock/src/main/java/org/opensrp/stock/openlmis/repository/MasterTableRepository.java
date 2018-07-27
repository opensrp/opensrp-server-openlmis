package org.opensrp.stock.openlmis.repository;

import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
import org.opensrp.stock.openlmis.domain.MasterMetadataEntry;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.MasterTableEntryExample;
import org.opensrp.stock.openlmis.repository.mapper.custom.CustomMasterTableMapper;
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

    public MasterTableEntry add(BaseMetaData baseMetaData) {

        if (baseMetaData == null || baseMetaData.getUuid() == null) {
            return null;
        }

        MasterTableEntry masterTableEntry = convert(baseMetaData, null);
        masterTableEntry.setServerVersion(getCurrentTime());
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

        masterTableEntry.setServerVersion(getCurrentTime());
        int rowsAffected = masterTableMapper.insertSelectiveAndSetId(masterTableEntry);
        if (rowsAffected < 1 || masterTableEntry.getId() == null) {
            return;
        }

        // Add metadata to master metadata table
        MasterMetadataEntry entry =  convert((BaseMetaData) masterTableEntry.getJson());
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

    public List<MasterTableEntry> get(long prevServerVersion) {

        MasterTableEntryExample entryExample = new MasterTableEntryExample();
        entryExample.createCriteria().andServerVersionBetween(prevServerVersion, getCurrentTime());
        return masterTableMapper.selectByExample(entryExample);
    }

    @Override
    public void update(MasterTableEntry masterTableEntry) {

        masterTableEntry.setServerVersion(getCurrentTime());
        masterTableMapper.updateByPrimaryKey(masterTableEntry);

        // Add or update metadata to master metadata table
        MasterMetadataEntry entry = convert((BaseMetaData) masterTableEntry.getJson());
        entry.setMasterTableEntryId(masterTableEntry.getId());
        List<MasterMetadataEntry> result = masterMetadataRepository.get(entry.getUuid(), entry.getClass().getName());

        if (result != null && result.size() > 0) {
            MasterMetadataEntry lastEntry = result.get(result.size() - 1);
            entry.setId(lastEntry.getId());
            entry.setServerVersion(getCurrentTime());
            masterMetadataRepository.update(entry);
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

    private MasterTableEntry convert(BaseMetaData metaData, Long primaryKey) {

        if (metaData == null) {
            return null;
        }

        MasterTableEntry masterTableEntry = new MasterTableEntry();
        masterTableEntry.setId(primaryKey);
        masterTableEntry.setJson(metaData);

        return masterTableEntry;
    }

    private MasterMetadataEntry convert(BaseMetaData metaData) {

        MasterMetadataEntry masterMetadataEntry = new MasterMetadataEntry();
        masterMetadataEntry.setUuid(metaData.getUuid());
        // strip MetaData from class name and set as type
        masterMetadataEntry.setType(metaData.getClass().getSimpleName().split("Meta")[0]);

        return masterMetadataEntry;
    }
}
