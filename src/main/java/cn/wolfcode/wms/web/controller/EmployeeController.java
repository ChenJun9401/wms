package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepartmentService;
import cn.wolfcode.wms.service.IEmployeeService;
import cn.wolfcode.wms.service.IRoleService;
import cn.wolfcode.wms.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("employee")
public class EmployeeController {

    //把servic层的IDpartmentService注入进来
    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IRoleService roleService;

    //因为数据要共享到数据模型中,所以这里必须有一个model
    @RequestMapping("list")
    public String list(@ModelAttribute("qo") EmployeeQueryObject qo, Model model) throws Exception {
        model.addAttribute("result", employeeService.query(qo));
        model.addAttribute("depts", departmentService.list());
        return "employee/list";
    }

    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        if (id != null) {
            //因为查出来的数据要共享到employee/input.jsp中,所以要传入一个model
            model.addAttribute("entity", employeeService.get(id));
        }
        model.addAttribute("depts", departmentService.list());
        model.addAttribute("roles", roleService.list());
        return "employee/input";//这是一个请求转发
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Employee entity, Long[] ids) throws Exception {
        employeeService.saveOrUpdate(entity, ids);
        return new JSONResult();
    }

    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) throws Exception {
        if (id != null) {
            employeeService.delete(id);
        }
        return new JSONResult();
    }

    @RequestMapping("batchDelete")
    @ResponseBody
    public Object batchDelete(Long[] ids) throws Exception {
        if (ids != null) {
            employeeService.batchDelete(ids);
        }
        return new JSONResult();
    }
}
