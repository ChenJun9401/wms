package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SystemMenu extends BaseDomain {
    private String name;    //菜单名称
    private String url;     //菜单地址
    private String sn;      //菜单编码
    //多对一关系
    private SystemMenu parent;  //父菜单
}
