package org.opensrp.stock.openlmis.repository.postgres.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.opensrp.stock.openlmis.domain.postgres.MasterTableEntry;
import org.opensrp.stock.openlmis.domain.postgres.MasterTableEntryExample;
import org.opensrp.stock.openlmis.repository.postgres.mapper.MasterTableEntryMapper;

import java.util.List;

public interface CustomMasterTableMapper extends MasterTableEntryMapper {

    List<MasterTableEntry> select(@Param("example") MasterTableEntryExample example, @Param("offset") int offset, @Param("limit") int limit);

    int insertSelectiveAndSetId(MasterTableEntry record);
}
