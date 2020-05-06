package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.domain.Product;
import cn.wolfcode.wms.mapper.BrandMapper;
import cn.wolfcode.wms.mapper.ProductMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IProductService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//贴上Service注解
@Service
public class ProductServiceImpl implements IProductService {

    //贴上Autowired注解,这个就是DI注入
    //service层要依赖departMapper
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private BrandMapper brandMapper;

    public void saveOrUpdate(Product entity) {
        //查询出商品的品牌名称
        Brand brand = brandMapper.selectByPrimaryKey(entity.getBrandId());
        entity.setBrandName(brand.getName());
        if (entity.getId() == null) {
            productMapper.insert(entity);
        } else {
            productMapper.updateByPrimaryKey(entity);
        }
    }

    public void delete(Long id) {
        productMapper.deleteByPrimaryKey(id);
    }

    public Product get(Long id) {
        return productMapper.selectByPrimaryKey(id);
    }

    public List<Product> list() {
        return productMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        //1.先查询总记录数
        Integer rows = productMapper.query4Count(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        //2.如果总记录数不为0,则要把所有的数据查询出来
        List<?> data = productMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, data);
    }

}
