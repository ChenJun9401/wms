package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.mapper.EmployeeMapper;
import cn.wolfcode.wms.mapper.PermissionMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IEmployeeService;
import cn.wolfcode.wms.util.MD5;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

//贴上Service注解
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    //贴上Autowired注解,这个就是DI注入
    //service层要依赖departMapper
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    public void batchDelete(Long[] ids) {
        employeeMapper.batchDelete(ids);
    }

    public void login(String username, String password) {
        Employee emp = employeeMapper.selectEmployeeByInfo(username, MD5.encoder((password)));
        if (emp == null) {    //登录失败
            throw new RuntimeException("账号和密码不匹配");
        }
        //当前登录成功的用户存入session
        UserContext.setCurrentUser(emp);
        //如果不是超级管理员,查询登录用户的权限表达式存入session
        List<String> exps = permissionMapper.selectByEmployeeId(emp.getId());
        UserContext.setExpressions(exps);
    }

    public void saveOrUpdate(Employee entity, Long[] ids) {
        if (entity.getId() == null) {
            //先对原始密码加密,再保存用户
            entity.setPassword(MD5.encoder(entity.getPassword()));
            employeeMapper.insert(entity);
        } else {
            employeeMapper.deleteRelation(entity.getId());
            employeeMapper.updateByPrimaryKey(entity);
        }
        //处理关联关系
        if (ids != null) {
            for (Long roleId : ids) {
                employeeMapper.insertRelation(entity.getId(), roleId);
            }
        }
    }

    public void delete(Long id) {
        employeeMapper.deleteRelation(id);
        employeeMapper.deleteByPrimaryKey(id);
    }

    public Employee get(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    public List<Employee> list() {
        return employeeMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        //1.先查询总记录数
        Integer rows = employeeMapper.query4Count(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        //2.如果总记录数不为0,则要把所有的数据查询出来
        List<?> data = employeeMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, data);
    }
}
