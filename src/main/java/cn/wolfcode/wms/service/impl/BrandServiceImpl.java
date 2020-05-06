package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.mapper.BrandMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//贴上Service注解
@Service
public class BrandServiceImpl implements IBrandService {

    //贴上Autowired注解,这个就是DI注入
    //service层要依赖departMapper
    @Autowired
    private BrandMapper brandMapper;

    public void saveOrUpdate(Brand entity) {
        if (entity.getId() == null) {
            brandMapper.insert(entity);
        } else {
            brandMapper.updateByPrimaryKey(entity);
        }
    }

    public void delete(Long id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    public Brand get(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    public List<Brand> list() {
        return brandMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        //1.先查询总记录数
        Integer rows = brandMapper.query4Count(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        //2.如果总记录数不为0,则要把所有的数据查询出来
        List<?> data = brandMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, data);
    }

}
