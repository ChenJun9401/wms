package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Permission;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission entity);

    List<Permission> selectAll();

    Integer query4Count(QueryObject qo);

    List<?> query4List(QueryObject qo);

    List<String> selectAllExpressions();

    List<String> selectByEmployeeId(Long id);
}