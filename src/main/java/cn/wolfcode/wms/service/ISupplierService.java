package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Supplier;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface ISupplierService {
    void saveOrUpdate(Supplier entity);

    void delete(Long id);

    Supplier get(Long id);

    List<Supplier> list();

    PageResult query(QueryObject qo);
}

