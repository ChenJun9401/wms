package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Employee extends BaseDomain {
    private String name;
    private String password;
    private String email;
    private Integer age;
    private boolean admin;  //这里的boolean使用基本数据类型

    //多对一关联关系
    private Department dept;
    //多对多关联关系
    private List<Role> roles = new ArrayList<>();
}

