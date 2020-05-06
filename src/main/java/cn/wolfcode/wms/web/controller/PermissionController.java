package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IPermissionService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("permission")
public class PermissionController {

    //把servic层的IDpartmentService注入进来
    @Autowired
    private IPermissionService permissionService;

    //因为数据要共享到数据模型中,所以这里必须有一个model
    @RequestMapping("list")
    @RequiredPermission("权限列表")
    public String list(@ModelAttribute("qo") QueryObject qo, Model model) throws Exception {
        model.addAttribute("result", permissionService.query(qo));
        return "permission/list";
    }

    @RequestMapping("delete")
    @ResponseBody
    //@ResponseBody的作用其实是将java对象转为json格式的数据。
    public Object delete(Long id) throws Exception {
        if (id != null) {
            permissionService.delete(id);
        }
        return new JSONResult();
    }

    @RequestMapping("reload")
    @ResponseBody
    public Object reload() throws Exception {
        permissionService.reload(); //加载权限
        return new JSONResult();
    }

}
