package org.opensrp.stock.openlmis.repository;

import org.opensrp.stock.openlmis.domain.Lot;
import org.opensrp.stock.openlmis.domain.LotExample;
import org.opensrp.stock.openlmis.repository.mapper.LotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;

@Repository
public class LotRepository implements BaseRepository<Lot> {

    @Autowired
    private LotMapper lotMapper;

    @Override
    public void addOrUpdate(Lot lot) {

        if (lot == null || lot.getId() == null) {
            return;
        }
        lot.setServerVersion(getCurrentTime());
        // Lot already exists
        if (retrievePrimaryKey(lot) != null) {
            update(lot);
            return;
        }
        lotMapper.insert(lot);
    }

    @Override
    public Lot get(Object id) {
        return lotMapper.selectByPrimaryKey((String) id);
    }

    public List<Lot> get(String id, String tradeItemId, String lotCode) {

        LotExample lotExample = new LotExample();
        lotExample.createCriteria().andIdEqualTo(id).andTradeItemIdEqualTo(tradeItemId).andLotCodeEqualTo(lotCode);
        return lotMapper.selectByExample(lotExample);
    }

    public List<Lot> get(long prevServerVersion) {

        LotExample lotExample = new LotExample();
        lotExample.createCriteria().andServerVersionBetween(prevServerVersion, getCurrentTime());
        return lotMapper.selectByExample(lotExample);
    }

    @Override
    public void update(Lot lot) {
        lot.setServerVersion(getCurrentTime());
        lotMapper.updateByPrimaryKey(lot);
    }

    @Override
    public List<Lot> getAll() {

        LotExample lotExample = new LotExample();
        lotExample.createCriteria();
        return lotMapper.selectByExample(lotExample);
    }

    @Override
    public Long safeRemove(Lot lot) {

        if (lot == null || lot.getId() == null) {
            return null;
        }

        if (lot.getDateDeleted() == null) {
            lot.setDateDeleted(Calendar.getInstance().getTimeInMillis());
        }

        LotExample lotExample = new LotExample();
        lotExample.createCriteria().andIdEqualTo(lot.getId()).andDateDeletedIsNull();
        lotMapper.updateByExample(lot, lotExample);

        return lot.getDateDeleted();
    }

    private String retrievePrimaryKey(Lot lot) {

        Object uniqueId = getUniqueField(lot);
        if (uniqueId == null) {
            return null;
        }

        String id = uniqueId.toString();

        LotExample lotExample = new LotExample();
        lotExample.createCriteria().andIdEqualTo(id);

        List<Lot> result = lotMapper.selectByExample(lotExample);
        if (result.size() == 0) {
            return null;
        }
        return lot.getId().toString();
    }

    private Object getUniqueField(Lot lot) {

        if (lot == null) {
            return null;
        }
        return lot.getId().toString();
    }
}
