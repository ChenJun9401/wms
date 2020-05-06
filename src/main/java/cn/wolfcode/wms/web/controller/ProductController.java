package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.domain.Product;
import cn.wolfcode.wms.query.ProductQueryObject;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IBrandService;
import cn.wolfcode.wms.service.IProductService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import cn.wolfcode.wms.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;

@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private ServletContext context;

    //因为数据要共享到数据模型中,所以这里必须有一个model
    @RequestMapping("list")
    @RequiredPermission("商品列表")
    public String list(@ModelAttribute("qo") ProductQueryObject qo, Model model) throws Exception {
        //查询出所有的商品品牌信息
        model.addAttribute("brands", brandService.list());
        model.addAttribute("result", productService.query(qo));
        return "product/list";
    }

    @RequestMapping("selectProductList")
    @RequiredPermission("商品列表")
    public String selectProductList(@ModelAttribute("qo") ProductQueryObject qo, Model model) throws Exception {
        //查询出所有的商品品牌信息
        model.addAttribute("brands", brandService.list());
        model.addAttribute("result", productService.query(qo));
        return "product/selectProductList";
    }

    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        //查询出所有的商品品牌信息
        model.addAttribute("brands", brandService.list());
        if (id != null) {
            //因为查出来的数据要共享到product/input.jsp中,所以要传入一个model
            model.addAttribute("entity", productService.get(id));
        }
        return "product/input";//这是一个请求转发
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Product entity, MultipartFile pic) throws Exception {
        JSONResult result = new JSONResult();
        try {
            //删除商品的图片:1.用户传递了图片 2.当前商品只求是有图片的
            if (pic != null && !StringUtils.isEmpty(entity.getImagePath())) {
                UploadUtil.deleteFile(context, entity.getImagePath());
            }
            //保存上传文件
            if (pic != null) {
                String imagePath = UploadUtil.upload(pic, context.getRealPath("/upload"));
                entity.setImagePath(imagePath);
            }
            productService.saveOrUpdate(entity);
        } catch (Exception e) {
            e.printStackTrace();
            result.mark("保存失败");
        }
        return result;
    }

    @RequestMapping("delete")
    @ResponseBody
    //@ResponseBody的作用其实是将java对象转为json格式的数据。
    public Object delete(Long id, String imagePath) throws Exception {
        if (id != null) {
            productService.delete(id);
            //删除商品的图片
            UploadUtil.deleteFile(context, imagePath);
        }
        return new JSONResult();
    }
}
