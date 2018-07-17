package org.opensrp.stock.openlmis.repository.postgres.mapper.custom;

import org.apache.ibatis.annotations.Param;
import org.opensrp.stock.openlmis.domain.postgres.Orderable;
import org.opensrp.stock.openlmis.domain.postgres.OrderableExample;
import org.opensrp.stock.openlmis.repository.postgres.mapper.OrderableMapper;

import java.util.List;

public interface CustomOrderableMapper extends OrderableMapper {

    List<Orderable> select(@Param("example") OrderableExample example, @Param("offset") int offset, @Param("limit") int limit);
}
