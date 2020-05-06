<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>WMS-采购入库单管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/plugins/iframeTools.js"></script>
    <script type="text/javascript" src="/js/plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#edit_table_body").on("click", ".searchproduct", function () {
                var currentTr = $(this).closest("tr");
                $.artDialog.open("/product/selectProductList.do", {
                    id: "selectProduct",
                    title: "选择商品",
                    width: 1000,
                    height: 500,
                    lock: true,
                    opacity: 0.1,    //透明度
                    resize: false,
                    close: function () {
                        var productInfo = $.artDialog.data("productInfo");
                        if (productInfo) {
                            currentTr.find("[tag=name]").val(productInfo.name);
                            currentTr.find("[tag=pid]").val(productInfo.id);
                            currentTr.find("[tag=brand]").text(productInfo.brandName);
                            currentTr.find("[tag=costPrice]").val(productInfo.costPrice);
                        }
                        $.artDialog.removeData("productInfo");
                    }
                });
            }).on("blur", "[tag=costPrice],[tag=number]", function () {
                var currentTr = $(this).closest("tr");
                var costPrice = parseFloat(currentTr.find("[tag=costPrice]").val()) || 0;
                var number = parseFloat(currentTr.find("[tag=number]").val()) || 0;
                currentTr.find("[tag=amount]").text((costPrice * number).toFixed(2));
            }).on("click", ".removeItem", function () {
                var currentTr = $(this).closest("tr");
                if ($("#edit_table_body tr").size() > 1) {
                    currentTr.remove();
                } else {
                    //清空行数据
                    currentTr.find("[tag=name]").val("");
                    currentTr.find("[tag=pid]").val("");
                    currentTr.find("[tag=brand]").text("");
                    currentTr.find("[tag=costPrice]").val("");
                    currentTr.find("[tag=number]").val("");
                    currentTr.find("[tag=amount]").text("");
                    currentTr.find("[tag=remark]").val("");
                }
            });
            //添加明细
            $(".appendRow").click(function () {
                var newTr = $("#edit_table_body tr:first").clone();
                //清空行数据
                newTr.find("[tag=name]").val("");
                newTr.find("[tag=pid]").val("");
                newTr.find("[tag=brand]").text("");
                newTr.find("[tag=costPrice]").val("");
                newTr.find("[tag=number]").val("");
                newTr.find("[tag=amount]").text("");
                newTr.find("[tag=remark]").val("");
                $("#edit_table_body").append(newTr);
            });

            $("#editForm").submit(function () {
                $("#edit_table_body tr").each(function (index, item) {
                    $(item).find("[tag=pid]").prop("name", "items[" + index + "].product.id");
                    $(item).find("[tag=costPrice]").prop("name", "items[" + index + "].costPrice");
                    $(item).find("[tag=number]").prop("name", "items[" + index + "].number");
                    $(item).find("[tag=remark]").prop("name", "items[" + index + "].remark");
                });
            });

            //把editForm表单变成ajax提交
            $("#editForm").ajaxForm(function (data) {
                if (data.success) {   //判断是否能获取到JSONResult对象;
                    showDialog("操作成功", function () {
                        location.href = "/stockIncomeBill/list.do";
                    });
                } else {
                    showDialog("操作失败:" + data.msg);
                }
            });
        });
    </script>
</head>
<body>
<form id="editForm" action="/stockIncomeBill/saveOrUpdate.do" method="post" data-obj="stockIncomeBill">
    <%--把值从作用域中取出,进行回显;--%>
    <input type="hidden" name="id" value="${entity.id}">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">采购入库单编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">采购入库单编码</td>
                    <td class="ui_text_lt">
                        <input name="sn" class="ui_input_txt02" value="${entity.sn}"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">仓库</td>
                    <td class="ui_text_lt">
                        <select name="depot.id" class="ui_select03">
                            <c:forEach items="${depots}" var="depot">
                                <option value=${depot.id} ${entity.depot.id==depot.id ? "selected='selected'":""}>${depot.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">业务时间</td>
                    <td class="ui_text_lt">
                        <fmt:formatDate value="${entity.vdate}" pattern="yyyy-MM-dd" var="vdate"/>
                        <input name="vdate" class="ui_input_txt02 Wdate" value="${vdate}" onclick="WdatePicker();"
                               readonly="readonly"/>
                    </td>
                </tr>

                <tr>
                    <td class="ui_text_rt" width="140">单据明细</td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="button" value="添加明细" class="ui_input_btn01 appendRow"/>
                        <table class="edit_table" cellspacing="0" cellpadding="0" border="0" style="width: auto">
                            <thead>
                            <tr>
                                <th width="10"></th>
                                <th width="200">货品</th>
                                <th width="120">品牌</th>
                                <th width="80">价格</th>
                                <th width="80">数量</th>
                                <th width="80">金额小计</th>
                                <th width="150">备注</th>
                                <th width="60"></th>
                            </tr>
                            </thead>
                            <tbody id="edit_table_body">
                            <c:if test="${entity.id==null}">
                                <tr>
                                    <td></td>
                                    <td>
                                        <input disabled="true" readonly="true"
                                               class="ui_input_txt02" tag="name"/>
                                        <img src="/images/common/search.png" class="searchproduct"/>
                                        <input type="hidden" tag="pid"/>
                                    </td>
                                    <td><span tag="brand"></span></td>
                                    <td><input tag="costPrice"
                                               class="ui_input_txt02"/></td>
                                    <td><input tag="number"
                                               class="ui_input_txt02"/></td>
                                    <td><span tag="amount"></span></td>
                                    <td><input tag="remark"
                                               class="ui_input_txt02"/></td>
                                    <td>
                                        <a href="javascript:;" class="removeItem">删除明细</a>
                                    </td>
                                </tr>
                            </c:if>
                            <c:forEach var="item" items="${entity.items}">
                                <tr>
                                    <td></td>
                                    <td>
                                        <input disabled="true" name="items[0].product.name" readonly="true"
                                               value="${item.product.name}"
                                               class="ui_input_txt02" tag="name"/>
                                        <img src="/images/common/search.png" class="searchproduct"/>
                                        <input type="hidden" name="items[0].product.id" tag="pid"
                                               value="${item.product.id}"/>
                                    </td>
                                    <td><span tag="brand">${item.product.brandName}</span></td>
                                    <td><input tag="costPrice" name="items[0].costPrice" value="${item.costPrice}"
                                               class="ui_input_txt02"/></td>
                                    <td><input tag="number" name="items[0].number" value="${item.number}"
                                               class="ui_input_txt02"/></td>
                                    <td><span tag="amount">${item.amount}</span></td>
                                    <td><input tag="remark" name="items[0].remark" value="${item.remark}"
                                               class="ui_input_txt02"/></td>
                                    <td>
                                        <a href="javascript:;" class="removeItem">删除明细</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<input type="submit" value="确定保存" class="ui_input_btn01"/>
                        &nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>
