package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class OrderBill extends BaseBillDomain {

    //供应商 多对一
    private Supplier supplier;

    //单据明细 一对多
    private List<OrderBillItem> items = new ArrayList<>();
}
