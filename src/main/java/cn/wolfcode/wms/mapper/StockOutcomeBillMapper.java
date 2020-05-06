package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface StockOutcomeBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockOutcomeBill entity);

    StockOutcomeBill selectByPrimaryKey(Long id);

    List<StockOutcomeBill> selectAll();

    int updateByPrimaryKey(StockOutcomeBill entity);

    Integer query4Count(QueryObject qo);

    List<?> query4List(QueryObject qo);

    void audit(StockOutcomeBill bill);
}