package org.opensrp.stock.openlmis.repository;

import org.opensrp.stock.openlmis.domain.MasterMetadataEntry;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.MasterTableEntryExample;
import org.opensrp.stock.openlmis.domain.metadata.BaseMetaData;
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

        if (baseMetaData == null || baseMetaData.getId() == null) {
            return null;
        }
        MasterTableEntry masterTableEntry = convert(baseMetaData, null);
        masterTableEntry.setServerVersion(baseMetaData.getServerVersion());
        addOrUpdate(masterTableEntry);
        if (masterTableEntry.getId() == null) {
            return null;
        }
        return masterTableEntry;
    }

    
    @Override
    public void addOrUpdate(MasterTableEntry masterTableEntry) {

        if (masterTableEntry == null) {
            return;
        }

        // MasterTableEntry already exists
        String metaDataType = masterTableEntry.getJson().getClass().getSimpleName().split("Meta")[0];
        String metaDataId = ((BaseMetaData) masterTableEntry.getJson()).getId();
        if (get(metaDataType, metaDataId) != null || get(masterTableEntry.getId()) != null) {
            update(masterTableEntry);
            return;
        }

        int rowsAffected = masterTableMapper.insertSelectiveAndSetId(masterTableEntry);
        if (rowsAffected < 1 || masterTableEntry.getId() == null) {
            return;
        }
        // Add metadata to master metadata table
        MasterMetadataEntry entry =  convert((BaseMetaData) masterTableEntry.getJson());
        entry.setMasterTableEntryId(masterTableEntry.getId());
        masterMetadataRepository.addOrUpdate(entry);
    }

    @Override
    public MasterTableEntry get(Object id) {
        return masterTableMapper.selectByPrimaryKey((Long) id);
    }

    public List<MasterTableEntry> get(String type) {
        return masterTableMapper.selectByType(type, 0, DEFAULT_FETCH_LIMIT);
    }

    public List<MasterTableEntry> get(String type, long serverVersion) {
        return masterTableMapper.selectByTypeAndServerVersion(type, serverVersion, 0, DEFAULT_FETCH_LIMIT);
    }

    public MasterTableEntry get(String type, String id) {
        return masterTableMapper.selectByTypeAndId(type, id, 0, DEFAULT_FETCH_LIMIT);
    }

    public List<MasterTableEntry> get(long prevServerVersion) {

        MasterTableEntryExample entryExample = new MasterTableEntryExample();
        entryExample.createCriteria().andServerVersionBetween(prevServerVersion, getCurrentTime());
        return masterTableMapper.selectByExample(entryExample);
    }

    @Override
    public void update(MasterTableEntry masterTableEntry) {

        BaseMetaData metaDataEntry = (BaseMetaData) masterTableEntry.getJson();
        String metaDataType = metaDataEntry.getClass().getSimpleName().split("Meta")[0];
        List<MasterMetadataEntry> result = masterMetadataRepository.get(metaDataEntry.getId(), metaDataType);

        // update master data in master table
        MasterMetadataEntry lastEntry = result.get(result.size() - 1);
        masterTableEntry.setId(lastEntry.getMasterTableEntryId());

        ((BaseMetaData) masterTableEntry.getJson()).setServerVersion(masterTableEntry.getServerVersion());
        masterTableMapper.updateByPrimaryKey(masterTableEntry);

        // update metadata in master metadata table
        metaDataEntry.setId(lastEntry.getUuid());
        masterMetadataRepository.update(convert(metaDataEntry));
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

        // Safe remove metadata from  master metadata table
        MasterMetadataEntry entry = convert((BaseMetaData) masterTableEntry.getJson());
        entry.setMasterTableEntryId(masterTableEntry.getId());
        masterMetadataRepository.safeRemove(entry);

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
        masterMetadataEntry.setUuid(metaData.getId());
        // strip MetaData from class name and set as type
        masterMetadataEntry.setType(metaData.getClass().getSimpleName().split("Meta")[0]);

        return masterMetadataEntry;
    }
}
