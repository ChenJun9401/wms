package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.StockIncomeBill;
import cn.wolfcode.wms.query.StockIncomeBillQueryObject;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.service.IStockIncomeBillService;
import cn.wolfcode.wms.service.ISupplierService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("stockIncomeBill")
public class StockIncomeBillController {

    @Autowired
    private IStockIncomeBillService stockIncomeBillService;
    @Autowired
    private IDepotService depotService;

    //因为数据要共享到数据模型中,所以这里必须有一个model
    @RequestMapping("list")
    @RequiredPermission("采购入库单列表")
    public String list(@ModelAttribute("qo") StockIncomeBillQueryObject qo, Model model) throws Exception {
        model.addAttribute("depots", depotService.list());
        model.addAttribute("result", stockIncomeBillService.query(qo));
        return "stockIncomeBill/list";
    }

    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        model.addAttribute("depots", depotService.list());
        if (id != null) {
            //因为查出来的数据要共享到stockIncomeBill/input.jsp中,所以要传入一个model
            model.addAttribute("entity", stockIncomeBillService.get(id));
        }
        return "stockIncomeBill/input";//这是一个请求转发
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StockIncomeBill entity) throws Exception {
        stockIncomeBillService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequestMapping("delete")
    @ResponseBody
    //@ResponseBody的作用其实是将java对象转为json格式的数据。
    public Object delete(Long id) throws Exception {
        if (id != null) {
            stockIncomeBillService.delete(id);
        }
        return new JSONResult();
    }

    @RequestMapping("audit")
    @ResponseBody
    public Object audit(Long id) throws Exception {
        if (id != null) {
            stockIncomeBillService.audit(id);
        }
        return new JSONResult();
    }

    @RequestMapping("show")
    public String show(Long id,Model model) throws Exception {
        if (id != null) {
            //因为查出来的数据要共享到stockIncomeBill/input.jsp中,所以要传入一个model
            model.addAttribute("entity", stockIncomeBillService.get(id));
        }
        return "stockIncomeBill/show";
    }

}
