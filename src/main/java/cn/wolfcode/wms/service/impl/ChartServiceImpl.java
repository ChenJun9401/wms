package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.mapper.ChartMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChartServiceImpl implements IChartService {
    @Autowired
    private ChartMapper chartMapper;

    public List<Map<String, Object>> selectOrderChart(QueryObject qo) {
        return chartMapper.selectOrderChart(qo);
    }
    public List<Map<String, Object>> selectSaleChart(QueryObject qo) {
        return chartMapper.selectSaleChart(qo);
    }
}
