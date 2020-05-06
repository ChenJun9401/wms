package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockIncomeBillItem;

public interface StockIncomeBillItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockIncomeBillItem entity);

    void deleteByBillId(Long billId);
}