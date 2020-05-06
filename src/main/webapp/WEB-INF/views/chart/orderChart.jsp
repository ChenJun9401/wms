<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>WMS-订货报表管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/chart/order.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        业务时间
                        <fmt:formatDate value="${qo.beginDate}" var="beginDate" pattern="yyyy-MM-dd"/>
                        <fmt:formatDate value="${qo.endDate}" var="endDate" pattern="yyyy-MM-dd"/>
                        <input type="text" onclick="WdatePicker();" class="ui_input_txt01" name="beginDate"
                               value="${beginDate}"/> ~
                        <input type="text" onclick="WdatePicker();" class="ui_input_txt01" name="endDate"
                               value="${endDate}"/>
                        货品名称/编码
                        <input type="text" class="ui_input_txt01" name="keyword" value="${qo.keyword}"/>
                        供应商
                        <select class="ui_select03" name="supplierId">
                            <option value="-1">--请选择--</option>
                            <c:forEach items="${suppliers}" var="supplier">
                                <option value=${supplier.id} ${qo.supplierId==supplier.id ? "selected='selected'":""}>${supplier.name}</option>
                            </c:forEach>
                        </select>
                        品牌
                        <select id="brandId" class="ui_select01" name="brandId">
                            <option value="-1">全部品牌</option>
                            <c:forEach var="brand" items="${brands}">
                                <option value="${brand.id}" ${brand.id==qo.brandId?"selected='selected'":""}>${brand.name}</option>
                            </c:forEach>
                        </select>
                        类型
                        <select id="groupType" class="ui_select01" name="groupByType">
                            <c:forEach items="${groupByTypeMap}" var="groupByType">
                                <option value="${groupByType.key}"} ${qo.groupByType==groupByType.key?"selected='selected'":""}>${groupByType.value}</option>
                            </c:forEach>
                        </select>
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
                        <th>分组类型</th>
                        <th>订货总数</th>
                        <th>订货总额</th>
                        <th></th>
                    </tr>
                    <%--varStatus:用来显示当前页面是第几次做迭代--%>
                    <c:forEach var="orderChart" items="${orderCharts}" varStatus="num">
                        <tr>
                            <td><input type="checkbox" class="acb"/></td>
                            <td>${orderChart.groupByType}</td>
                            <td>${orderChart.totalNumber}</td>
                            <td>${orderChart.totalAmount}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</form>
</body>
</html>
