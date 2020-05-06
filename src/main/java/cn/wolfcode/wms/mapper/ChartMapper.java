package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.query.QueryObject;

import java.util.List;
import java.util.Map;

public interface ChartMapper {
    //查询订货报表
    List<Map<String, Object>> selectOrderChart(QueryObject qo);
    //查询销售报表
    List<Map<String, Object>> selectSaleChart(QueryObject qo);
}
