package cn.wolfcode.wms.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class Product extends BaseDomain {
    private String name;

    private String sn;

    private BigDecimal costPrice;

    private BigDecimal salePrice;

    private String imagePath;

    private String intro;

    private Long brandId;

    private String brandName;

    public String getSmallImagePath() {
        String smallImagePath = "";
        if (StringUtils.hasLength(imagePath)) {
            int index = imagePath.indexOf(".");
            smallImagePath = imagePath.substring(0, index) + "_small" + imagePath.substring(index);
        }
        return smallImagePath;
    }

    //准备页面上需要的商品信息:json格式
    public String getProductInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("id", getId());
        info.put("name", name);
        info.put("costPrice", costPrice);
        info.put("brandName", brandName);
        info.put("salePrice", salePrice);
        return JSON.toJSONString(info);
    }
}
