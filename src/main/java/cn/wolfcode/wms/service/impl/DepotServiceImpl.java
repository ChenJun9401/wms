package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.mapper.DepotMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//贴上Service注解
@Service
public class DepotServiceImpl implements IDepotService {

    @Autowired
    private DepotMapper depotMapper;

    public void saveOrUpdate(Depot entity) {
        if (entity.getId() == null) {
            depotMapper.insert(entity);
        } else {
            depotMapper.updateByPrimaryKey(entity);
        }
    }

    public void delete(Long id) {
        depotMapper.deleteByPrimaryKey(id);
    }

    public Depot get(Long id) {
        return depotMapper.selectByPrimaryKey(id);
    }

    public List<Depot> list() {
        return depotMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        //1.先查询总记录数
        Integer rows = depotMapper.query4Count(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        //2.如果总记录数不为0,则要把所有的数据查询出来
        List<?> data = depotMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, data);
    }

}
