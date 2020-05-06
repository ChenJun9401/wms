package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface IPermissionService {
    void delete(Long id);

    List<Permission> list();

    PageResult query(QueryObject qo);

    /*
     *   加载系统中的权限
     * */
    void reload();
}
