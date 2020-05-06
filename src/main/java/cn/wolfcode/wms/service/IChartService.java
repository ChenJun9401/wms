package cn.wolfcode.wms.service;

import cn.wolfcode.wms.query.QueryObject;

import java.util.List;
import java.util.Map;

public interface IChartService {
    List<Map<String,Object>> selectOrderChart(QueryObject qo);
    List<Map<String,Object>> selectSaleChart(QueryObject qo);
}
