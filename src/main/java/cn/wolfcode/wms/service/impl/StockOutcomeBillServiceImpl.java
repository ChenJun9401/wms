package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.domain.SaleAccount;
import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.domain.StockOutcomeBillItem;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.SaleAccountMapper;
import cn.wolfcode.wms.mapper.StockOutcomeBillItemMapper;
import cn.wolfcode.wms.mapper.StockOutcomeBillMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.service.IStockOutcomeBillService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//贴上Service注解
@Service
public class StockOutcomeBillServiceImpl implements IStockOutcomeBillService {

    //贴上Autowired注解,这个就是DI注入
    //service层要依赖departMapper
    @Autowired
    private StockOutcomeBillMapper stockOutcomeBillMapper;
    @Autowired
    private StockOutcomeBillItemMapper stockOutcomeBillItemMapper;
    @Autowired
    private IProductStockService productStockService;

    public void saveOrUpdate(StockOutcomeBill entity) {
        //封装采购出库单的信息
        entity.setInputUser(UserContext.getCurrentUser());
        entity.setInputTime(new Date());
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<StockOutcomeBillItem> items = entity.getItems();
        for (StockOutcomeBillItem item : items) {
            //总数量
            totalNumber = totalNumber.add(item.getNumber());
            //总金额
            totalAmount = totalAmount.add(item.getNumber().multiply(item.getSalePrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        entity.setTotalAmount(totalAmount);
        entity.setTotalNumber(totalNumber);
        if (entity.getId() == null) {
            //保存单据信息
            stockOutcomeBillMapper.insert(entity);
        } else {
            stockOutcomeBillMapper.updateByPrimaryKey(entity);
            //删除之前所有明细
            stockOutcomeBillItemMapper.deleteByBillId(entity.getId());
        }
        //保存新的明细
        for (StockOutcomeBillItem item : items) {
            item.setAmount(item.getNumber().multiply(item.getSalePrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
            item.setBillId(entity.getId());
            stockOutcomeBillItemMapper.insert(item);
        }
    }

    public void delete(Long id) {
        stockOutcomeBillMapper.deleteByPrimaryKey(id);
        //删除明细
        stockOutcomeBillItemMapper.deleteByBillId(id);
    }

    public StockOutcomeBill get(Long id) {
        return stockOutcomeBillMapper.selectByPrimaryKey(id);
    }

    public List<StockOutcomeBill> list() {
        return stockOutcomeBillMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        //1.先查询总记录数
        Integer rows = stockOutcomeBillMapper.query4Count(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        //2.如果总记录数不为0,则要把所有的数据查询出来
        List<?> data = stockOutcomeBillMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, data);
    }

    public void audit(Long id) {
        //查询出审核之前的单据
        StockOutcomeBill oldBill = stockOutcomeBillMapper.selectByPrimaryKey(id);
        if (oldBill.getStatus() == StockOutcomeBill.STATUS_NOMAL) {
            oldBill.setStatus(StockOutcomeBill.STATUS_AUDIT);
            oldBill.setAuditor(UserContext.getCurrentUser());
            oldBill.setAuditTime(new Date());
            stockOutcomeBillMapper.audit(oldBill);
        }

        //修改库存量
        productStockService.stockOutcomeBill(oldBill);
    }
}
