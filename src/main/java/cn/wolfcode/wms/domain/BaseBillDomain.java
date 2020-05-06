package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class BaseBillDomain extends BaseDomain {
    //待审核
    public static final int STATUS_NOMAL = 0;
    //已审核
    public static final int STATUS_AUDIT = 1;
    //订单编码
    private String sn;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vdate;
    //审核状态
    private int status = STATUS_NOMAL;
    //总金额
    private BigDecimal totalAmount;
    //总数量
    private BigDecimal totalNumber;
    //审核时间
    private Date auditTime;
    //录入时间
    private Date inputTime;
    //录入员 多对一
    private Employee inputUser;
    //审核员 多对一
    private Employee auditor;
}
