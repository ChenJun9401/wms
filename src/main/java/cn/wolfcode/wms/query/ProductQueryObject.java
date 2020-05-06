package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductQueryObject extends QueryObject {
    private String keyword;
    private Long brandId = -1L;  //-1表示默认查询所有部门

    public String getKeyword() {
        return empty2Null(keyword);
    }
}

