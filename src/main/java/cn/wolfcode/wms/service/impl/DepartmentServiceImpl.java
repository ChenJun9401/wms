package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.mapper.DepartmentMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepartmentService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//贴上Service注解
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    //贴上Autowired注解,这个就是DI注入
    //service层要依赖departMapper
    @Autowired
    private DepartmentMapper departmentMapper;

    public void saveOrUpdate(Department entity) {
        if (entity.getId() == null) {
            departmentMapper.insert(entity);
        } else {
            departmentMapper.updateByPrimaryKey(entity);
        }
    }

    public void delete(Long id) {
        departmentMapper.deleteByPrimaryKey(id);
    }

    public Department get(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    public List<Department> list() {
        return departmentMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        //1.先查询总记录数
        Integer rows = departmentMapper.query4Count(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        //2.如果总记录数不为0,则要把所有的数据查询出来
        List<?> data = departmentMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, data);
    }

}
