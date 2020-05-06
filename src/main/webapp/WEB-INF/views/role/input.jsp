<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>WMS-角色管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript">
        //给左移和右移按钮绑定点击事件
        $(function () {
            //全部右移
            $("#selectAll").click(function () {
                $(".all_permissions option").appendTo(".selected_permissions");
            });
            //选择右移
            $("#select").click(function () {
                $(".all_permissions option:selected").appendTo(".selected_permissions");
            });
            //全部左移
            $("#deselectAll").click(function () {
                $(".selected_permissions option").appendTo(".all_permissions");
            });
            //选择左移
            $("#deselect").click(function () {
                $(".selected_permissions option:selected").appendTo(".all_permissions");
            });
            //-------------------------------------------------------------------------
            //全部右移
            $("#selectAllMenu").click(function () {
                $(".all_menus option").appendTo(".selected_menus");
            });
            //选择右移
            $("#selectMenu").click(function () {
                $(".all_menus option:selected").appendTo(".selected_menus");
            });
            //全部左移
            $("#deselectAllMenu").click(function () {
                $(".selected_menus option").appendTo(".all_menus");
            });
            //选择左移
            $("#deselectMenu").click(function () {
                $(".selected_menus option:selected").appendTo(".all_menus");
            });
            //--------------------------------------------------------------
            //给提交按钮绑定点击事件
            $("#btn_submit").click(function () {
                //选中右边所有的option,再提交表单
                $(".selected_permissions option").prop("selected", true);
                $(".selected_menus option").prop("selected", true);
                $("#editForm").submit();
            });

            //消除已经分配的权限
            var pIds = $.map($(".selected_permissions option"), function (ele) {
                return ele.value;
            });
            $.each($(".all_permissions option"), function (index, val) {
                if ($.inArray(val.value, pIds) != -1) {//当前option有重复
                    $(val).remove();
                }
            });
            //------------------------------------------------------------
            //消除已经分配的权限
            var mIds = $.map($(".selected_menus option"), function (ele) {
                return ele.value;
            });
            $.each($(".all_menus option"), function (index, val) {
                if ($.inArray(val.value, mIds) != -1) {//当前option有重复
                    $(val).remove();
                }
            });
        });
    </script>
</head>
<body>
<form id="editForm" action="/role/saveOrUpdate.do" method="post" data-obj="role">
    <%--把值从作用域中取出,进行回显;--%>
    <input type="hidden" name="id" value="${entity.id}">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">角色编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">角色名称</td>
                    <td class="ui_text_lt">
                        <input name="name" class="ui_input_txt02" value="${entity.name}"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">角色编号</td>
                    <td class="ui_text_lt">
                        <input name="sn" class="ui_input_txt02" value="${entity.sn}"/>
                    </td>
                </tr>

                <tr>
                    <td class="ui_text_rt" width="140">分配权限</td>
                    <td class="ui_text_lt">
                        <table>
                            <tr>
                                <td>
                                    <%--系统中所有的权限--%>
                                    <select multiple="true" class="ui_multiselect01 all_permissions">
                                        <c:forEach var="obj" items="${permissions}">
                                            <option value="${obj.id}">${obj.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td align="center">
                                    <input type="button" id="select" value="-->" class="left2right"/><br/>
                                    <input type="button" id="selectAll" value="==>" class="left2right"/><br/>
                                    <input type="button" id="deselect" value="<--" class="left2right"/><br/>
                                    <input type="button" id="deselectAll" value="<==" class="left2right"/>
                                </td>
                                <td>
                                    <%--显示当前对象已经拥有的权限--%>
                                    <select name="ids" multiple="true"
                                            class="ui_multiselect01 selected_permissions">
                                        <c:forEach var="obj" items="${entity.permissions}">
                                            <option value="${obj.id}">${obj.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tr>
                    <td class="ui_text_rt" width="140">分配菜单</td>
                    <td class="ui_text_lt">
                        <table>
                            <tr>
                                <td>
                                    <%--系统中所有的子菜单--%>
                                    <select multiple="true" class="ui_multiselect01 all_menus">
                                        <c:forEach var="obj" items="${menus}">
                                            <option value="${obj.id}">${obj.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td align="center">
                                    <input type="button" id="selectMenu" value="-->" class="left2right"/><br/>
                                    <input type="button" id="selectAllMenu" value="==>" class="left2right"/><br/>
                                    <input type="button" id="deselectMenu" value="<--" class="left2right"/><br/>
                                    <input type="button" id="deselectAllMenu" value="<==" class="left2right"/>
                                </td>
                                <td>
                                    <%--显示当前对象已经拥有的子菜单--%>
                                    <select name="menuIds" multiple="true"
                                            class="ui_multiselect01 selected_menus">
                                        <c:forEach var="obj" items="${entity.menus}">
                                            <option value="${obj.id}">${obj.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<input id="btn_submit" type="button" value="确定保存" class="ui_input_btn01"/>
                        &nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
                    </td>
                </tr>

            </table>
        </div>
    </div>
</form>
</body>
</html>
