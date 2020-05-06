package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockOutcomeBillItem;

public interface StockOutcomeBillItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockOutcomeBillItem entity);

    void deleteByBillId(Long billId);
}