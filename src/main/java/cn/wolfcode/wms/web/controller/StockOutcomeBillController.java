package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.query.StockOutcomeBillQueryObject;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.service.IDepotService;
import cn.wolfcode.wms.service.IStockOutcomeBillService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import javafx.fxml.LoadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("stockOutcomeBill")
public class StockOutcomeBillController {

    @Autowired
    private IStockOutcomeBillService stockOutcomeBillService;
    @Autowired
    private IDepotService depotService;
    @Autowired
    private IClientService clientService;

    //因为数据要共享到数据模型中,所以这里必须有一个model
    @RequestMapping("list")
    @RequiredPermission("销售出库单列表")
    public String list(@ModelAttribute("qo") StockOutcomeBillQueryObject qo, Model model) throws Exception {
        model.addAttribute("depots", depotService.list());
        model.addAttribute("clients", clientService.list());
        model.addAttribute("result", stockOutcomeBillService.query(qo));
        return "stockOutcomeBill/list";
    }

    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        model.addAttribute("depots", depotService.list());
        model.addAttribute("clients", clientService.list());
        if (id != null) {
            //因为查出来的数据要共享到stockOutcomeBill/input.jsp中,所以要传入一个model
            model.addAttribute("entity", stockOutcomeBillService.get(id));
        }
        return "stockOutcomeBill/input";//这是一个请求转发
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StockOutcomeBill entity) throws Exception {
        stockOutcomeBillService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequestMapping("delete")
    @ResponseBody
    //@ResponseBody的作用其实是将java对象转为json格式的数据。
    public Object delete(Long id) throws Exception {
        if (id != null) {
            stockOutcomeBillService.delete(id);
        }
        return new JSONResult();
    }

    @RequestMapping("audit")
    @ResponseBody
    public Object audit(Long id) throws Exception {
        JSONResult result = new JSONResult();
        try {
            if (id != null) {
                stockOutcomeBillService.audit(id);
            }
        } catch (LogicException e) {
            e.printStackTrace();
            result.mark(e.getMessage());
        }
        return result;
    }

    @RequestMapping("show")
    public String show(Long id, Model model) throws Exception {
        if (id != null) {
            //因为查出来的数据要共享到stockOutcomeBill/input.jsp中,所以要传入一个model
            model.addAttribute("entity", stockOutcomeBillService.get(id));
        }
        return "stockOutcomeBill/show";
    }

}
