package cn.wolfcode.wms.util;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class PageResult {
    //特殊情况,没有查询到结果的时候
    public static final PageResult EMPTY_PAGE = new PageResult(1, 1, 0, Collections.EMPTY_LIST);

    private int currentPage;
    private int pageSize;

    private Integer rows;
    private List<?> data;

    private int endPage;
    private int prevPage;
    private int nextPage;

    public PageResult(int currentPage, int pageSize, Integer rows, List<?> data) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.rows = rows;
        this.data = data;

        if (rows <= pageSize) {
            endPage = 1;
            prevPage = 1;
            nextPage = 1;
            return;
        }

        endPage = rows % pageSize != 0 ? rows / pageSize + 1 : rows / pageSize;
        prevPage = currentPage > 1 ? currentPage - 1 : currentPage;
        nextPage = currentPage < endPage ? currentPage + 1 : endPage;
    }
}
