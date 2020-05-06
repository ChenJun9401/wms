package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.SaleAccount;
import cn.wolfcode.wms.query.OrderChartQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SaleChartQueryObject;
import cn.wolfcode.wms.service.*;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("chart")
public class ChartController {

    @Autowired
    private IChartService chartService;
    @Autowired
    private ISupplierService supplierService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private IClientService clientService;

    //因为数据要共享到数据模型中,所以这里必须有一个model
    @RequestMapping("order")
    @RequiredPermission("订货报表")
    public String orderChart(@ModelAttribute("qo") OrderChartQueryObject qo, Model model) throws Exception {
        model.addAttribute("suppliers", supplierService.list());
        model.addAttribute("brands", brandService.list());
        model.addAttribute("orderCharts", chartService.selectOrderChart(qo));
        model.addAttribute("groupByTypeMap", OrderChartQueryObject.groupByTypeMap);
        return "chart/orderChart";
    }

    @RequestMapping("sale")
    @RequiredPermission("销售报表")
    public String saleChart(@ModelAttribute("qo") SaleChartQueryObject qo, Model model) throws Exception {
        model.addAttribute("clients", clientService.list());
        model.addAttribute("brands", brandService.list());
        model.addAttribute("saleCharts", chartService.selectSaleChart(qo));
        model.addAttribute("groupByTypeMap", SaleChartQueryObject.groupByTypeMap);
        return "chart/saleChart";
    }

    @RequestMapping("saleChartByBar")
    public String saleChartByBar(Model model, SaleChartQueryObject qo) {
        List<Map<String, Object>> charts = chartService.selectSaleChart(qo);
        //存放分组的类型
        List<String> groupByTypes = new ArrayList<>();
        //存放销售总额
        List<Object> totalAmounts = new ArrayList<>();
        //分组类型
        for (Map<String, Object> chart : charts) {
            groupByTypes.add(chart.get("groupByType").toString());
            totalAmounts.add(chart.get("totalAmount").toString());
        }
        //将分组类型转换成JSON格式的字符串,共享回去
        model.addAttribute("groupByTypes", JSON.toJSONString(groupByTypes));
        model.addAttribute("totalAmounts", JSON.toJSONString(totalAmounts));
        //每组的数据
        return "chart/saleChartByBar";
    }

    @RequestMapping("saleChartByPie")
    public String saleChartByPie(Model model, SaleChartQueryObject qo) {
        List<Map<String, Object>> charts = chartService.selectSaleChart(qo);
        //存放分组的类型
        List<String> groupByTypes = new ArrayList<>();
        //存放报表的数据
        List<Map<String, Object>> datas = new ArrayList<>();
        //分组类型
        for (Map<String, Object> chart : charts) {
            groupByTypes.add(chart.get("groupByType").toString());
            Map<String, Object> data = new HashMap<>();
            data.put("value", chart.get("totalAmount"));
            data.put("name", chart.get("groupByType"));
            datas.add(data);
        }
        //将分组类型转换成JSON格式的字符串,共享回去
        model.addAttribute("groupByTypes", JSON.toJSONString(groupByTypes));
        model.addAttribute("datas", JSON.toJSONString(datas));
        model.addAttribute("groupByType",SaleChartQueryObject.groupByTypeMap.get(qo.getGroupByType()));
        return "chart/saleChartByPie";
    }
}
