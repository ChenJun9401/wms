<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <link href="/js/plugins/fancybox/jquery.fancybox.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/plugins/iframeTools.js"></script>
    <script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
    <script type="text/javascript" src="/js/plugins/fancybox/jquery.fancybox.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript">
        $(function () {
            $(".btn_selectProduct").click(function () {
                //将父窗口中所需要的数据共享回去
                $.artDialog.data("productInfo",$(this).data("productinfo"));
                //关闭当前子窗口
                $.artDialog.close();
            });
        })
    </script>
    <title>WMS-商品管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/product/selectProductList.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        商品名称/编码
                        <input type="text" class="ui_input_txt02" name="keyword" value="${qo.keyword}"/>
                        商品品牌
                        <select name="brandId" class="ui_select03">
                                <option value="-1">--请选择---</option>
                            <c:forEach items="${brands}" var="brand">
                                <option value=${brand.id} ${qo.brandId==brand.id ? "selected='selected'":""}>${brand.name}</option>
                            </c:forEach>
                        </select>
                        <script>
                            $("#brandId option[value='']").prop("selected",true);
                        </script>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>编号</th>
                        <th>商品图片</th>
                        <th>商品名称</th>
                        <th>商品编码</th>
                        <th>商品品牌</th>
                        <th>成本价</th>
                        <th>零售价</th>
                        <th></th>
                    </tr>
                    <%--varStatus:用来显示当前页面是第几次做迭代--%>
                    <c:forEach var="entity" items="${result.data}" varStatus="num">
                        <tr>
                            <td><input type="checkbox" class="acb"/></td>
                            <td>${num.count}</td>
                            <td>
                                <a class="fancybox" href="${entity.smallImagePath}" data-fancybox-group="gallery"
                                   title="${entity.name}">
                                    <img src="${entity.smallImagePath}" width="80px">
                                </a>
                            </td>
                            <td>${entity.name}</td>
                            <td>${entity.sn}</td>
                            <td>${entity.brandName}</td>
                            <td>${entity.costPrice}</td>
                            <td>${entity.salePrice}</td>
                            <td>
                               <input type="button" value="选中" data-productinfo='${entity.productInfo}' class="left2right btn_selectProduct"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <%--引入分页内容--%>
            <%@include file="/WEB-INF/views/common/page.jsp" %>
        </div>
    </div>
</form>
</body>
</html>
