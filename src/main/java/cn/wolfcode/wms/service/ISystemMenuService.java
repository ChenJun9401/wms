package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;
import java.util.Map;

public interface ISystemMenuService {
    void saveOrUpdate(SystemMenu entity);

    void delete(Long id);

    SystemMenu get(Long id);

    List<SystemMenu> list(SystemMenuQueryObject qo);

    //根据当前菜单获取祖先菜单
    List<SystemMenu> getParentMenus(SystemMenu menu);

    //查询所有子菜单
    List<SystemMenu> getChildMenus();

    //根据父菜单的编码查询子菜单
    List<Map<String, Object>> selectByParentSn(String parentSn);
}

