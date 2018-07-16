package org.opensrp.stock.openlmis.repository.postgres.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.opensrp.stock.openlmis.domain.postgres.MasterTable;
import org.opensrp.stock.openlmis.domain.postgres.MasterTableExample;
import org.opensrp.stock.openlmis.repository.postgres.mapper.MasterTableMapper;

import java.util.List;

public interface CustomMasterTableMapper extends MasterTableMapper {

    List<MasterTable> select(@Param("example") MasterTableExample example, @Param("offset") int offset, @Param("limit") int limit);
}
