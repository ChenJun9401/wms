package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface IBrandService {
    void saveOrUpdate(Brand entity);

    void delete(Long id);

    Brand get(Long id);

    List<Brand> list();

    PageResult query(QueryObject qo);
}

