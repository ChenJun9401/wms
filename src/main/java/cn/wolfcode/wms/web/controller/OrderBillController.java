package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.query.OrderBillQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IOrderBillService;
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
@RequestMapping("orderBill")
public class OrderBillController {

    @Autowired
    private IOrderBillService orderBillService;
    @Autowired
    private ISupplierService supplierService;

    //因为数据要共享到数据模型中,所以这里必须有一个model
    @RequestMapping("list")
    @RequiredPermission("采购订单列表")
    public String list(@ModelAttribute("qo") OrderBillQueryObject qo, Model model) throws Exception {
        model.addAttribute("suppliers", supplierService.list());
        model.addAttribute("result", orderBillService.query(qo));
        return "orderBill/list";
    }

    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        model.addAttribute("suppliers", supplierService.list());
        if (id != null) {
            //因为查出来的数据要共享到orderBill/input.jsp中,所以要传入一个model
            model.addAttribute("entity", orderBillService.get(id));
        }
        return "orderBill/input";//这是一个请求转发
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(OrderBill entity) throws Exception {
        orderBillService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequestMapping("delete")
    @ResponseBody
    //@ResponseBody的作用其实是将java对象转为json格式的数据。
    public Object delete(Long id) throws Exception {
        if (id != null) {
            orderBillService.delete(id);
        }
        return new JSONResult();
    }

    @RequestMapping("audit")
    @ResponseBody
    public Object audit(Long id) throws Exception {
        if (id != null) {
            orderBillService.audit(id);
        }
        return new JSONResult();
    }

    @RequestMapping("show")
    public String show(Long id,Model model) throws Exception {
        if (id != null) {
            //因为查出来的数据要共享到orderBill/input.jsp中,所以要传入一个model
            model.addAttribute("entity", orderBillService.get(id));
        }
        return "orderBill/show";
    }

}
