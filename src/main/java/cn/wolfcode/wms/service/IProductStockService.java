package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface IProductStockService {
    PageResult query(QueryObject qo);

    void stockIncomeBill(StockIncomeBill oldBill);

    void stockOutcomeBill(StockOutcomeBill oldBill);
}

