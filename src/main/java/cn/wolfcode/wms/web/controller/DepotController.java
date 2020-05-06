package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("depot")
public class DepotController {
    
    @Autowired
    private IDepotService depotService;

    //因为数据要共享到数据模型中,所以这里必须有一个model
    @RequestMapping("list")
    @RequiredPermission("仓库列表")
    public String list(@ModelAttribute("qo") QueryObject qo, Model model) throws Exception {
        model.addAttribute("result", depotService.query(qo));
        return "depot/list";
    }

    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        if (id != null) {
            //因为查出来的数据要共享到depot/input.jsp中,所以要传入一个model
            model.addAttribute("entity", depotService.get(id));
        }
        return "depot/input";//这是一个请求转发
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Depot entity) throws Exception {
        depotService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequestMapping("delete")
    @ResponseBody
    //@ResponseBody的作用其实是将java对象转为json格式的数据。
    public Object delete(Long id) throws Exception {
        if (id != null) {
            depotService.delete(id);
        }
        return new JSONResult();
    }
}
