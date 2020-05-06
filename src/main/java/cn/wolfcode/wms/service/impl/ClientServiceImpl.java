package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Client;
import cn.wolfcode.wms.mapper.ClientMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IClientService;
import cn.wolfcode.wms.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//贴上Service注解
@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientMapper clientMapper;

    public void saveOrUpdate(Client entity) {
        if (entity.getId() == null) {
            clientMapper.insert(entity);
        } else {
            clientMapper.updateByPrimaryKey(entity);
        }
    }

    public void delete(Long id) {
        clientMapper.deleteByPrimaryKey(id);
    }

    public Client get(Long id) {
        return clientMapper.selectByPrimaryKey(id);
    }

    public List<Client> list() {
        return clientMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        //1.先查询总记录数
        Integer rows = clientMapper.query4Count(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        //2.如果总记录数不为0,则要把所有的数据查询出来
        List<?> data = clientMapper.query4List(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, data);
    }

}
