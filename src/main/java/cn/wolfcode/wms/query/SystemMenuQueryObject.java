package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SystemMenuQueryObject extends QueryObject {
    private Long parentId;  //父菜单的ID,该值为null说明是查询根菜单
}

