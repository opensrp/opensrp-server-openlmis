package org.opensrp.stock.openlmis.repository.postgres;

import org.opensrp.stock.openlmis.domain.postgres.Orderable;
import org.opensrp.stock.openlmis.domain.postgres.OrderableExample;
import org.opensrp.stock.openlmis.repository.postgres.mapper.custom.CustomOrderableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public class OrderableRepository implements BaseRepository<Orderable> {

    @Autowired
    private CustomOrderableMapper orderableMapper;

    @Override
    public void add(Orderable orderable) {

        if (orderable == null || orderable.getId() == null) {
            return;
        }

        // Orderable already exists
        if (retrievePrimaryKey(orderable) != null) {
            return;
        }
        orderableMapper.insert(orderable);
    }

    @Override
    public Orderable get(String id) {
        return orderableMapper.selectByPrimaryKey(id);
    }

    public List<Orderable> get(String id, String tradeItemId, String commodityTypeId) {

        OrderableExample orderableExample = new OrderableExample();
        orderableExample.createCriteria().andIdEqualTo(id).andTradeItemIdEqualTo(tradeItemId).andCommodityTypeIdEqualTo(commodityTypeId);
        return orderableMapper.selectByExample(orderableExample);
    }

    @Override
    public void update(Orderable orderable) {
        orderableMapper.updateByPrimaryKey(orderable);
    }

    private String retrievePrimaryKey(Orderable orderable) {

        Object uniqueId = getUniqueField(orderable);
        if (uniqueId == null) {
            return null;
        }

        String id = uniqueId.toString();

        OrderableExample orderableExample = new OrderableExample();
        orderableExample.createCriteria().andIdEqualTo(id);

        List<Orderable> result = orderableMapper.selectByExample(orderableExample);
        if (result.size() == 0) {
            return null;
        }
        return orderable.getId().toString();
    }

    protected Object getUniqueField(Orderable orderable) {

        if (orderable == null) {
            return null;
        }
        return orderable.getId().toString();
    }

    @Override
    public List<Orderable> getAll() {

        OrderableExample orderableExample = new OrderableExample();
        orderableExample.createCriteria();
        return orderableMapper.select(orderableExample, 0, 10);
    }

    @Override
    public Long safeRemove(Orderable orderable) {

        if (orderable == null || orderable.getId() == null) {
            return null;
        }

        if (orderable.getDateDeleted() == null) {
            orderable.setDateDeleted( Calendar.getInstance().getTimeInMillis());
        }

        OrderableExample orderableExample = new OrderableExample();
        orderableExample.createCriteria().andIdEqualTo(orderable.getId()).andDateDeletedIsNull();
        orderableMapper.updateByExample(orderable, orderableExample);

        return orderable.getDateDeleted();
    }
}
