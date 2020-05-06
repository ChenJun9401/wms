package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Client;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.util.PageResult;

import java.util.List;

public interface IClientService {
    void saveOrUpdate(Client entity);

    void delete(Long id);

    Client get(Long id);

    List<Client> list();

    PageResult query(QueryObject qo);
}

