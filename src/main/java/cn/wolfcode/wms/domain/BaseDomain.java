package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

//贴上Setter和Getter标签
@Setter
@Getter
public class BaseDomain {
    //因为BaseDomain中的id属性子类也需要用到,所以用protected修饰;
    protected Long id;
}