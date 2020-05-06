package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.domain.OrderBillItem;
import cn.wolfcode.wms.mapper.OrderBillItemMapper;
import cn.wolfcode.wms.mapper.OrderBillMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IOrderBillService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//贴上Service注解
@Service
public class OrderBillServiceImpl implements IOrderBillService {

    //贴上Autowired注解,这个就是DI注入
    //service层要依赖departMapper
    @Autowired
    private OrderBillMapper orderBillMapper;
    @Autowired
    private OrderBillItemMapper orderBillItemMapper;

    public void saveOrUpdate(OrderBill entity) {
        //封装订单的信息
        entity.setInputUser(UserContext.getCurrentUser());
        entity.setInputTime(new Date());
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderBillItem> items = entity.getItems();
        for (OrderBillItem item : items) {
            //总数量
            totalNumber = totalNumber.add(item.getNumber());
            //总金额
            totalAmount = totalAmount.add(item.getNumber().multiply(item.getCostPrice()));
        }
        entity.setTotalAmount(totalAmount);
        entity.setTotalNumber(totalNumber);
        if (entity.getId() == null) {
            //保存单据信息
            orderBillMapper.insert(entity);
        } else {
            orderBillMapper.updateByPrimaryKey(entity);
            //删除之前所有明细
            orderBillItemMapper.deleteByBillId(entity.getId());
        }
        //保存新的明细
        for (OrderBillItem item : items) {
            item.setAmount(item.getNumber().multiply(item.getCostPrice()));
            item.setBillId(entity.getId());
            orderBillItemMapper.insert(item);
        }
    }

    public void delete(Long id) {
        orderBillMapper.deleteByPrimaryKey(id);
        //删除明细
        orderBillItemMapper.deleteByBillId(id);
    }

    public OrderBill get(Long id) {
        return orderBillMapper.selectByPrimaryKey(id);
    }

    public List<OrderBill> list() {
        return orderBillMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        //1.先查询总记录数
        Integer rows = orderBillMapper.query4Count(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        //2.如果总记录数不为0,则要把所有的数据查询出来
        List<?> data = orderBillMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, data);
    }

    public void audit(Long id) {
        //查询出审核之前的单据
        OrderBill oldBill = orderBillMapper.selectByPrimaryKey(id);
        if (oldBill.getStatus() == OrderBill.STATUS_NOMAL) {
            oldBill.setStatus(OrderBill.STATUS_AUDIT);
            oldBill.setAuditor(UserContext.getCurrentUser());
            oldBill.setAuditTime(new Date());
            orderBillMapper.audit(oldBill);
        }
    }

}
