package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Supplier;
import cn.wolfcode.wms.mapper.SupplierMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.ISupplierService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//贴上Service注解
@Service
public class SupplierServiceImpl implements ISupplierService {

    //贴上Autowired注解,这个就是DI注入
    //service层要依赖departMapper
    @Autowired
    private SupplierMapper supplierMapper;

    public void saveOrUpdate(Supplier entity) {
        if (entity.getId() == null) {
            supplierMapper.insert(entity);
        } else {
            supplierMapper.updateByPrimaryKey(entity);
        }
    }

    public void delete(Long id) {
        supplierMapper.deleteByPrimaryKey(id);
    }

    public Supplier get(Long id) {
        return supplierMapper.selectByPrimaryKey(id);
    }

    public List<Supplier> list() {
        return supplierMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        //1.先查询总记录数
        Integer rows = supplierMapper.query4Count(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        //2.如果总记录数不为0,则要把所有的数据查询出来
        List<?> data = supplierMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, data);
    }

}
