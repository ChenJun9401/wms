package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface BrandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Brand entity);

    Brand selectByPrimaryKey(Long id);

    List<Brand> selectAll();

    int updateByPrimaryKey(Brand entity);

    Integer query4Count(QueryObject qo);

    List<?> query4List(QueryObject qo);
}