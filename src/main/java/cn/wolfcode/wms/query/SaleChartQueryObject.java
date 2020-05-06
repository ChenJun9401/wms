package cn.wolfcode.wms.query;

import cn.wolfcode.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Setter
@Getter
public class SaleChartQueryObject extends QueryObject {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String keyword;
    private Long clientId = -1L;
    private Long brandId = -1L;
    private String groupByType = "saleman.name";

    //使用一个Map集合存放分组的集合
    public static Map<String, String> groupByTypeMap = new LinkedHashMap<>();

    static {
        groupByTypeMap.put("saleman.name", "销售员");
        groupByTypeMap.put("p.name", "商品名称");
        groupByTypeMap.put("p.brandName", "商品品牌");
        groupByTypeMap.put("c.name", "客户");
        groupByTypeMap.put("DATE_FORMAT(sa.vdate,'%Y-%m-%d')", "日期(天)");
        groupByTypeMap.put("DATE_FORMAT(sa.vdate,'%Y-%m')", "日期(月)");
    }

    public String getKeyword() {
        return empty2Null(keyword);
    }

    public String getGroupByType() {
        return empty2Null(groupByType);
    }

    public Date getEndDate() {
        return endDate != null ? DateUtil.getEndDate(endDate) : null;
    }
}
