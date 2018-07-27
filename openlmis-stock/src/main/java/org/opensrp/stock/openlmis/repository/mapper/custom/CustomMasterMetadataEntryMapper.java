package org.opensrp.stock.openlmis.repository.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.opensrp.stock.openlmis.domain.MasterMetadataEntry;
import org.opensrp.stock.openlmis.domain.MasterMetadataEntryExample;
import org.opensrp.stock.openlmis.repository.mapper.MasterMetadataEntryMapper;

import java.util.List;

public interface CustomMasterMetadataEntryMapper extends MasterMetadataEntryMapper {

    List<MasterMetadataEntry> select(@Param("example") MasterMetadataEntryExample example, @Param("offset") int offset, @Param("limit") int limit);

    int insertSelectiveAndSetId(MasterMetadataEntry record);
}
