package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.*;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.SaleAccountMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//贴上Service注解
@Service
public class ProductStockServiceImpl implements IProductStockService {
    
    @Autowired
    private ProductStockMapper productStockMapper;
    @Autowired
    private SaleAccountMapper saleAccountMapper;

    public PageResult query(QueryObject qo) {
        //1.先查询总记录数
        Integer rows = productStockMapper.query4Count(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        //2.如果总记录数不为0,则要把所有的数据查询出来
        List<?> data = productStockMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, data);
    }

    public void stockIncomeBill(StockIncomeBill oldBill) {
        //修改库存量
        //判断当前商品是否存在:商品的编号和仓库的编号
        List<StockIncomeBillItem> items = oldBill.getItems();
        for (StockIncomeBillItem item : items) {
            ProductStock ps = productStockMapper.selectByProductIdAndDepotId(item.getProduct().getId(), oldBill.getDepot().getId());
            if(ps==null){
                //不存在:直接插入一条新的数据
                ps = new ProductStock();
                ps.setPrice(item.getCostPrice());
                ps.setStoreNumber(item.getNumber());
                ps.setAmount(item.getAmount());
                ps.setDepot(oldBill.getDepot());
                ps.setProduct(item.getProduct());
                productStockMapper.insert(ps);
            }else {
                //存在:修改库存量:数量/价格/总额
                ps.setStoreNumber(ps.getStoreNumber().add(item.getNumber()));
                //BigDecimal的除法小数位数的保留,用下面的方式,不要使用setScale方法
                ps.setPrice(item.getAmount().add(ps.getAmount()).divide(ps.getStoreNumber(),2, BigDecimal.ROUND_HALF_UP));
                ps.setAmount(ps.getStoreNumber().multiply(ps.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP));
                productStockMapper.updateByPrimaryKey(ps);
            }
        }
    }

    public void stockOutcomeBill(StockOutcomeBill oldBill) {
        List<StockOutcomeBillItem> items = oldBill.getItems();
        for (StockOutcomeBillItem item : items) {
            ProductStock ps = productStockMapper.selectByProductIdAndDepotId(item.getProduct().getId(), oldBill.getDepot().getId());
            if (ps == null) {
                //不存在
                throw new LogicException("商品[" + item.getProduct().getName() + "]在仓库[" + oldBill.getDepot().getName() + "]中不存在");
            }
            //BigDecimal的大小比较,使用compareTo方法
            //a.compareTo(b)
            //>0 a>b
            //=0 a=b
            //<0 a<b
            if (item.getNumber().compareTo(ps.getStoreNumber()) > 0) {
                throw new LogicException("商品[" + item.getProduct().getName() + "]在仓库[" + oldBill.getDepot().getName() +
                        "]中的数量[" + ps.getStoreNumber() + "]不足[" + item.getNumber() + "]");
            }

            //数量足够:
            ps.setStoreNumber(ps.getStoreNumber().subtract(item.getNumber()));
            ps.setAmount(ps.getStoreNumber().multiply(ps.getPrice()));
            productStockMapper.updateByPrimaryKey(ps);

            //生成销售帐
            SaleAccount saleAccount = new SaleAccount();
            saleAccount.setClient(oldBill.getClient());
            saleAccount.setCostPrice(ps.getPrice());
            saleAccount.setNumber(item.getNumber());
            saleAccount.setCostAmount(saleAccount.getCostPrice().multiply(saleAccount.getNumber()).setScale(BigDecimal.ROUND_HALF_UP));
            saleAccount.setSalePrice(item.getSalePrice());
            saleAccount.setSaleAmount(saleAccount.getSalePrice().multiply(saleAccount.getNumber()).setScale(BigDecimal.ROUND_HALF_UP));
            saleAccount.setProduct(item.getProduct());
            saleAccount.setSaleMan(oldBill.getInputUser());
            saleAccount.setVdate(new Date());
            saleAccountMapper.insert(saleAccount);
        }
    }

}
