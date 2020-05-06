package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class QueryObject {
    private int currentPage = 1;//默认是第一页
    private int pageSize = 3;//页面容量

    //这是一个属性,属性名是start
    public Integer getStart() {
        return (currentPage - 1) * pageSize;
    }

    //判断传进来的字符串是否有长度，如果有长度则返回这个字符串本身，否则返回null
    protected String empty2Null(String s) {
        //导包是导入org.springframework.util.StringUtils这个包
        return StringUtils.hasLength(s) ? s : null;
    }
}
