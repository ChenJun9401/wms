package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface StockIncomeBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockIncomeBill entity);

    StockIncomeBill selectByPrimaryKey(Long id);

    List<StockIncomeBill> selectAll();

    int updateByPrimaryKey(StockIncomeBill entity);

    Integer query4Count(QueryObject qo);

    List<?> query4List(QueryObject qo);

    void audit(StockIncomeBill bill);
}