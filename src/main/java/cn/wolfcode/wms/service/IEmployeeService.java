package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface IEmployeeService {
    void saveOrUpdate(Employee entity, Long[] ids);

    void delete(Long id);

    Employee get(Long id);

    List<Employee> list();

    PageResult query(QueryObject qo);

    //员工登陆的方法
    void login(String username, String password);

    //批量删除
    void batchDelete(Long[] ids);
}
