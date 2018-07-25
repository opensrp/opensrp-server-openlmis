package org.opensrp.stock.openlmis.repository.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.opensrp.stock.openlmis.domain.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.MasterTableEntryExample;
import org.opensrp.stock.openlmis.repository.mapper.MasterTableEntryMapper;

import java.util.List;

public interface CustomMasterTableMapper extends MasterTableEntryMapper {

    List<MasterTableEntry> select(@Param("example") MasterTableEntryExample example, @Param("offset") int offset, @Param("limit") int limit);

    int insertSelectiveAndSetId(MasterTableEntry record);
}
