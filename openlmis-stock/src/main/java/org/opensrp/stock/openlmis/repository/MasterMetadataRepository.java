package org.opensrp.stock.openlmis.repository;

import org.opensrp.stock.openlmis.domain.MasterMetadataEntry;
import org.opensrp.stock.openlmis.domain.MasterMetadataEntryExample;
import org.opensrp.stock.openlmis.repository.mapper.custom.CustomMasterMetadataEntryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.DEFAULT_FETCH_LIMIT;
import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;

@Repository
public class MasterMetadataRepository implements BaseRepository<MasterMetadataEntry> {

    @Autowired
    private CustomMasterMetadataEntryMapper metadataEntryMapper;

    @Override
    public void addOrUpdate(MasterMetadataEntry metadataEntry) {

        if (metadataEntry == null || metadataEntry.getMasterTableEntryId() == null) {
            return;
        }
        // MasterMetadataEntry already exists
        if (isDuplicateEntry(metadataEntry)) {
            update(metadataEntry);
            return;
        }
        metadataEntryMapper.insertSelectiveAndSetId(metadataEntry);
    }

    public List<MasterMetadataEntry> get(String uuid, String type) {

        MasterMetadataEntryExample metadataEntryExample = new MasterMetadataEntryExample();
        metadataEntryExample.createCriteria().andUuidEqualTo(uuid).andTypeEqualTo(type);
        return metadataEntryMapper.selectByExample(metadataEntryExample);
    }

    public List<MasterMetadataEntry> get(long prevServerVersion) {

        MasterMetadataEntryExample metadataEntryExample = new MasterMetadataEntryExample();
        metadataEntryExample.createCriteria().andServerVersionBetween(prevServerVersion, getCurrentTime());
        return metadataEntryMapper.selectByExample(metadataEntryExample);
    }

    @Override
    public MasterMetadataEntry get(Object id) {
        return metadataEntryMapper.selectByPrimaryKey((Long) id);
    }

    @Override
    public void update(MasterMetadataEntry masterMetadataEntry) {
        metadataEntryMapper.updateByPrimaryKey(masterMetadataEntry);
    }

    public void update(MasterMetadataEntry entry, String uuid, String type) {

        MasterMetadataEntryExample metadataEntryExample = new MasterMetadataEntryExample();
        metadataEntryExample.createCriteria().andUuidEqualTo(uuid).andTypeEqualTo(type);
        metadataEntryMapper.updateByExample(entry, metadataEntryExample);
    }

    @Override
    public List<MasterMetadataEntry> getAll() {

        MasterMetadataEntryExample masterMetadataEntryExample = new MasterMetadataEntryExample();
        masterMetadataEntryExample.createCriteria();
        return metadataEntryMapper.select(masterMetadataEntryExample, 0, DEFAULT_FETCH_LIMIT);
    }


    @Override
    public Long safeRemove(MasterMetadataEntry entry) {

        if (entry == null || entry.getId() == null) {
            return null;
        }

        if (entry.getDateDeleted() == null) {
            entry.setDateDeleted( Calendar.getInstance().getTimeInMillis());
        }

        MasterMetadataEntryExample masterMetadataEntryExample = new MasterMetadataEntryExample();
        masterMetadataEntryExample.createCriteria().andIdEqualTo(entry.getId()).andDateDeletedIsNull();
        metadataEntryMapper.updateByExample(entry, masterMetadataEntryExample);

        return entry.getDateDeleted();
    }

    private boolean isDuplicateEntry(MasterMetadataEntry metadataEntry) {

        String uuid = metadataEntry.getUuid();
        String type = metadataEntry.getType();
        MasterMetadataEntryExample metadataEntryExample = new MasterMetadataEntryExample();
        metadataEntryExample.createCriteria().andUuidEqualTo(uuid).andTypeEqualTo(type);

        List<MasterMetadataEntry> result = metadataEntryMapper.selectByExample(metadataEntryExample);
        return (result.size() != 0 ? true : false);
    }
}
