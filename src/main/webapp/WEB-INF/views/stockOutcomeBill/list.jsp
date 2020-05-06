<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>WMS-销售出库单管理</title>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body>
<form id="searchForm" action="/stockOutcomeBill/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        业务时间
                        <fmt:formatDate value="${qo.beginDate}" pattern="yyyy-MM-dd" var="beginDate"/>
                        <fmt:formatDate value="${qo.endDate}" pattern="yyyy-MM-dd" var="endDate"/>
                        <input type="text" class="ui_input_txt02 Wdate" readonly="readonly" name="beginDate"
                               value="${beginDate}" onclick="WdatePicker();"/> ~
                        <input type="text" class="ui_input_txt02 Wdate" readonly="readonly" name="endDate"
                               value="${endDate}" onclick="WdatePicker();"/>
                        仓库
                        <select class="ui_select01" name="depotId">
                            <option value="-1">--请选择--</option>
                            <c:forEach items="${depots}" var="depot">
                                <option value=${depot.id} ${qo.depotId==depot.id ? "selected='selected'":""}>${depot.name}</option>
                            </c:forEach>
                        </select>
                        客户
                        <select class="ui_select01" name="depotId">
                            <option value="-1">--请选择--</option>
                            <c:forEach items="${clients}" var="client">
                                <option value=${client.id} ${qo.depotId==client.id ? "selected='selected'":""}>${client.name}</option>
                            </c:forEach>
                        </select>
                        状态
                        <select id="status" class="ui_select01" name="status">
                            <option value="-1">全部</option>
                            <option value="0">待审核</option>
                            <option value="1">已审核</option>
                        </select>
                        <script>
                            $("#status option[value='${qo.status}']").prop("selected", true);
                        </script>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page"/>
                        <input type="button" value="新增" class="ui_input_btn01 btn_input"
                               data-url="/stockOutcomeBill/input.do"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all"/></th>
                        <th>入库单号</th>
                        <th>业务时间</th>
                        <th>仓库</th>
                        <th>客户</th>
                        <th>总数量</th>
                        <th>总金额</th>
                        <th>录入人</th>
                        <th>审核人</th>
                        <th>状态</th>
                        <th></th>
                    </tr>
                    <%--varStatus:用来显示当前页面是第几次做迭代--%>
                    <c:forEach var="entity" items="${result.data}" varStatus="num">
                        <tr>
                            <td><input type="checkbox" class="acb"/></td>
                            <td>${entity.sn}</td>
                            <td><fmt:formatDate value="${entity.vdate}" pattern="yyyy-MM-dd"/></td>
                            <td>${entity.depot.name}</td>
                            <td>${entity.client.name}</td>
                            <td>${entity.totalNumber}</td>
                            <td>${entity.totalAmount}</td>
                            <td>${entity.inputUser.name}</td>
                            <td>${entity.auditor.name}</td>
                            <td>
                                <c:if test="${entity.status==0}">
                                    <span style="color:red">未审核</span>
                                </c:if>
                                <c:if test="${entity.status==1}">
                                    <span style="color:green;">已审核</span>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${entity.status==0}">
                                    <a href="javascript:" data-url="/stockOutcomeBill/audit.do?id=${entity.id}"
                                       data-obj="stockOutcomeBill" class="btn_audit">审核</a>
                                    <a href="/stockOutcomeBill/input.do?id=${entity.id}">编辑</a>
                                    <a href="javascript:" class="btn_delete"
                                       data-url="/stockOutcomeBill/delete.do?id=${entity.id}"
                                       data-obj="stockOutcomeBill">删除</a>
                                </c:if>
                                <c:if test="${entity.status==1}">
                                    <a href="/stockOutcomeBill/show.do?id=${entity.id}">查看</a>
                                </c:if>
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
