package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class SaleAccount extends BaseDomain {
    private Date vdate;
    private BigDecimal number;
    private BigDecimal costPrice;
    private BigDecimal costAmount;
    private BigDecimal salePrice;
    private BigDecimal saleAmount;
    //商品
    private Product product;
    //销售人员
    private Employee saleMan;
    //客户
    private Client client;
}
