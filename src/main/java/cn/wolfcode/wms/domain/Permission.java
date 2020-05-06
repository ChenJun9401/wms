package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Permission extends BaseDomain {
    private String name;    //权限的名称
    private String expression;  //权限的表达式
}
