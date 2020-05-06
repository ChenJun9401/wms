package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
import cn.wolfcode.wms.service.ISystemMenuService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("systemMenu")
public class SystemMenuController {

    //把servic层的IDpartmentService注入进来
    @Autowired
    private ISystemMenuService systemMenuService;

    //因为数据要共享到数据模型中,所以这里必须有一个model
    @RequestMapping("list")
    public String list(@ModelAttribute("qo") SystemMenuQueryObject qo, Model model) throws Exception {
        if (qo.getParentId() != null) {//非根菜单,应该查询当前菜单的父菜单集合
            SystemMenu menu = systemMenuService.get(qo.getParentId());
            model.addAttribute("parents", systemMenuService.getParentMenus(menu));
        }
        model.addAttribute("list", systemMenuService.list(qo));
        return "systemMenu/list";
    }

    @RequestMapping("input")
    public String input(Long id, Long parentId, Model model) throws Exception {
        if (parentId != null) {
            model.addAttribute("parent", systemMenuService.get(parentId));
        }
        if (id != null) {
            //因为查出来的数据要共享到systemMenu/input.jsp中,所以要传入一个model
            model.addAttribute("entity", systemMenuService.get(id));
        }
        return "systemMenu/input";//这是一个请求转发
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(SystemMenu entity) throws Exception {
        systemMenuService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequestMapping("delete")
    @ResponseBody
    //@ResponseBody的作用其实是将java对象转为json格式的数据。
    public Object delete(Long id) throws Exception {
        if (id != null) {
            systemMenuService.delete(id);
        }
        return new JSONResult();
    }

    @RequestMapping("loadMenu")
    @ResponseBody
    //@ResponseBody的作用其实是将java对象转为json格式的数据。
    public List<Map<String,Object>> loadMenu(String parentSn) throws Exception {
        List<Map<String,Object>> menus =  systemMenuService.selectByParentSn(parentSn);
        return menus;
    }

}
