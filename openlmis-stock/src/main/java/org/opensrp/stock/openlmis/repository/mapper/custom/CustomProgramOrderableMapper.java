package org.opensrp.stock.openlmis.repository.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.opensrp.stock.openlmis.domain.ProgramOrderable;
import org.opensrp.stock.openlmis.domain.ProgramOrderableExample;
import org.opensrp.stock.openlmis.repository.mapper.ProgramOrderableMapper;

import java.util.List;

public interface CustomProgramOrderableMapper extends ProgramOrderableMapper {

    List<ProgramOrderable> select(@Param("example") ProgramOrderableExample example, @Param("offset") int offset, @Param("limit") int limit);
}
