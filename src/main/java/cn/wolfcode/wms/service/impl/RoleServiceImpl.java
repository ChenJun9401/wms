package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.mapper.RoleMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IRoleService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//贴上Service注解
@Service
public class RoleServiceImpl implements IRoleService {

    //贴上Autowired注解,这个就是DI注入
    //service层要依赖departMapper
    @Autowired
    private RoleMapper roleMapper;

    public void saveOrUpdate(Role entity, Long[] ids, Long[] menuIds) {
        if (entity.getId() == null) {
            roleMapper.insert(entity);
        } else {
            //先删除角色和权限的关系
            roleMapper.deleteRelation(entity.getId());
            roleMapper.deleteMenuRelation(entity.getId());
            roleMapper.updateByPrimaryKey(entity);
        }
        //处理关联关系
        if (ids != null) {
            for (Long permissionId : ids) {
                roleMapper.insertRelation(entity.getId(), permissionId);
            }
        }
        if (menuIds != null) {
            for (Long menuId : menuIds) {
                roleMapper.insertMenuRelation(entity.getId(), menuId);
            }
        }
    }

    public void delete(Long id) {
        //先删除角色和权限的关系(打破关系)
        roleMapper.deleteRelation(id);
        roleMapper.deleteMenuRelation(id);
        //再删除角色
        roleMapper.deleteByPrimaryKey(id);
    }

    public Role get(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public List<Role> list() {
        return roleMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        //1.先查询总记录数
        Integer rows = roleMapper.query4Count(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        //2.如果总记录数不为0,则要把所有的数据查询出来
        List<?> data = roleMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, data);
    }

}
