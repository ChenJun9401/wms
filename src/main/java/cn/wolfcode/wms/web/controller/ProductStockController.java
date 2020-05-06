package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.query.ProductStockQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.service.IProductStockService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("productStock")
public class ProductStockController {

    @Autowired
    private IProductStockService ProductStockService;
    @Autowired
    private IDepotService depotService;
    @Autowired
    private IBrandService brandService;

    //因为数据要共享到数据模型中,所以这里必须有一个model
    @RequestMapping("list")
    @RequiredPermission("即时库存报表")
    public String list(@ModelAttribute("qo") ProductStockQueryObject qo, Model model) throws Exception {
        model.addAttribute("depots",depotService.list());
        model.addAttribute("brands",brandService.list());
        model.addAttribute("result", ProductStockService.query(qo));
        return "productStock/list";
    }
    
}
