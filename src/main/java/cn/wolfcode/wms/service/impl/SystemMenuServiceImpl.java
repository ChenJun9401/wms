package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.mapper.SystemMenuMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
import cn.wolfcode.wms.service.ISystemMenuService;
import cn.wolfcode.wms.util.PageResult;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

//贴上Service注解
@Service
public class SystemMenuServiceImpl implements ISystemMenuService {

    //贴上Autowired注解,这个就是DI注入
    //service层要依赖departMapper
    @Autowired
    private SystemMenuMapper systemMenuMapper;

    public List<SystemMenu> getChildMenus() {
        return systemMenuMapper.selectChildMenus();
    }


    public List<Map<String, Object>> selectByParentSn(String parentSn) {
        //获取到当前登陆的用户
        Employee currentUser = UserContext.getCurrentUser();
        //判断当前用户是否是超级管理员
        if (!currentUser.isAdmin()){
            return systemMenuMapper.selectByParentSnAndEmpId(parentSn,currentUser.getId());
        }else {
            return systemMenuMapper.selectByParentSn(parentSn);
        }
    }

    public List<SystemMenu> getParentMenus(SystemMenu menu) {
        List<SystemMenu> parents = new ArrayList<>();
        parents.add(menu);
        while (menu.getParent() != null) {
            menu = menu.getParent();
            //添加父菜单
            parents.add(menu);
        }
        Collections.reverse(parents);   //逆序集合
        return parents;
    }

    public void saveOrUpdate(SystemMenu entity) {
        if (entity.getId() == null) {
            systemMenuMapper.insert(entity);
        } else {
            systemMenuMapper.updateByPrimaryKey(entity);
        }
    }

    public void delete(Long id) {
        systemMenuMapper.deleteByPrimaryKey(id);
    }

    public SystemMenu get(Long id) {
        return systemMenuMapper.selectByPrimaryKey(id);
    }

    public List<SystemMenu> list(SystemMenuQueryObject qo) {
        return systemMenuMapper.selectAll(qo);
    }

}
