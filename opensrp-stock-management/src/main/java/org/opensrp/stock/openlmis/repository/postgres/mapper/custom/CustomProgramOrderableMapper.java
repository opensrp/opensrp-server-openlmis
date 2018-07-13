package org.opensrp.stock.openlmis.repository.postgres.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.opensrp.stock.openlmis.domain.postgres.ProgramOrderable;
import org.opensrp.stock.openlmis.domain.postgres.ProgramOrderableExample;
import org.opensrp.stock.openlmis.repository.postgres.mapper.ProgramOrderableMapper;

import java.util.List;

public interface CustomProgramOrderableMapper extends ProgramOrderableMapper {

    List<ProgramOrderable> select(@Param("example") ProgramOrderableExample example, @Param("offset") int offset, @Param("limit") int limit);
}
