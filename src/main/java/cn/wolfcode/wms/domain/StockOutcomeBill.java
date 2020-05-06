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
public class StockOutcomeBill extends BaseBillDomain {
    //仓库 多对一
    private Depot depot;
    //客户:多对一
    private Client client;
    //单据明细 一对多
    private List<StockOutcomeBillItem> items = new ArrayList<>();
}
