package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface IStockOutcomeBillService {
    void saveOrUpdate(StockOutcomeBill entity);

    void delete(Long id);

    StockOutcomeBill get(Long id);

    List<StockOutcomeBill> list();

    PageResult query(QueryObject qo);

    void audit(Long id);
}

