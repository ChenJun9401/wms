package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface IStockIncomeBillService {
    void saveOrUpdate(StockIncomeBill entity);

    void delete(Long id);

    StockIncomeBill get(Long id);

    List<StockIncomeBill> list();

    PageResult query(QueryObject qo);

    void audit(Long id);
}

