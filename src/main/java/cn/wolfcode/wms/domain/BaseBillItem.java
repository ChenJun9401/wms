package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class BaseBillItem extends BaseDomain {
    private BigDecimal number;
    private BigDecimal amount;
    private String remark;
    private Product product;
    private Long billId;
}
