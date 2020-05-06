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
public class StockIncomeBill extends BaseBillDomain {
    //仓库 多对一
    private Depot depot;

    //单据明细 一对多
    private List<StockIncomeBillItem> items = new ArrayList<>();
}
