package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.domain.StockIncomeBillItem;
import cn.wolfcode.wms.mapper.ProductMapper;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.StockIncomeBillItemMapper;
import cn.wolfcode.wms.mapper.StockIncomeBillMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.service.IStockIncomeBillService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.PrimitiveIterator;

//贴上Service注解
@Service
public class StockIncomeBillServiceImpl implements IStockIncomeBillService {

    //贴上Autowired注解,这个就是DI注入
    //service层要依赖departMapper
    @Autowired
    private StockIncomeBillMapper stockIncomeBillMapper;
    @Autowired
    private StockIncomeBillItemMapper stockIncomeBillItemMapper;
    @Autowired
    private IProductStockService productStockService;

    public void saveOrUpdate(StockIncomeBill entity) {
        //封装采购入库单的信息
        entity.setInputUser(UserContext.getCurrentUser());
        entity.setInputTime(new Date());
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<StockIncomeBillItem> items = entity.getItems();
        for (StockIncomeBillItem item : items) {
            //总数量
            totalNumber = totalNumber.add(item.getNumber());
            //总金额
            totalAmount = totalAmount.add(item.getNumber().multiply(item.getCostPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        entity.setTotalAmount(totalAmount);
        entity.setTotalNumber(totalNumber);
        if (entity.getId() == null) {
            //保存单据信息
            stockIncomeBillMapper.insert(entity);
        } else {
            stockIncomeBillMapper.updateByPrimaryKey(entity);
            //删除之前所有明细
            stockIncomeBillItemMapper.deleteByBillId(entity.getId());
        }
        //保存新的明细
        for (StockIncomeBillItem item : items) {
            item.setAmount(item.getNumber().multiply(item.getCostPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
            item.setBillId(entity.getId());
            stockIncomeBillItemMapper.insert(item);
        }
    }

    public void delete(Long id) {
        stockIncomeBillMapper.deleteByPrimaryKey(id);
        //删除明细
        stockIncomeBillItemMapper.deleteByBillId(id);
    }

    public StockIncomeBill get(Long id) {
        return stockIncomeBillMapper.selectByPrimaryKey(id);
    }

    public List<StockIncomeBill> list() {
        return stockIncomeBillMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        //1.先查询总记录数
        Integer rows = stockIncomeBillMapper.query4Count(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        //2.如果总记录数不为0,则要把所有的数据查询出来
        List<?> data = stockIncomeBillMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, data);
    }

    public void audit(Long id) {
        //查询出审核之前的单据
        StockIncomeBill oldBill = stockIncomeBillMapper.selectByPrimaryKey(id);
        if (oldBill.getStatus() == StockIncomeBill.STATUS_NOMAL) {
            oldBill.setStatus(StockIncomeBill.STATUS_AUDIT);
            oldBill.setAuditor(UserContext.getCurrentUser());
            oldBill.setAuditTime(new Date());
            stockIncomeBillMapper.audit(oldBill);
        }
        //修改库存量
        productStockService.stockIncomeBill(oldBill);
    }
}
