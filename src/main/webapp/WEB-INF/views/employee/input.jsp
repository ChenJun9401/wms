<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>WMS-账户管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/plugins/validate/messages_cn.js"></script>
    <script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script type="text/javascript">
        var roleTr = null;  //用来存储角色的删除的
        $(function () {
            //把表单做成jQuery对象,再调用插件中的方法
            //格式就是json中套json
            $("#editForm").validate({
                rules: {
                    //参数名称:规则
                    name: {
                        required: true,
                        rangelength: [2, 6]
                    },
                    password: {
                        required: true,
                        rangelength: [2, 6]
                    },
                    repassword: {
                        equalTo: "#password"
                    },
                    email: {
                        required: true,
                        email: true
                    },
                    age: {
                        digits: true,
                        range: [16, 60]
                    }
                },
                messages: {
                    //参数名:提示信息
                    name: {
                        required: "账号必填",
                        rangelength: "长度必须是2-6位"
                    }
                }
            });
            //全部右移
            $("#selectAll").click(function () {
                $(".all_roles option").appendTo(".selected_roles");
            });
            //选择右移
            $("#select").click(function () {
                $(".all_roles option:selected").appendTo(".selected_roles");
            });
            //全部左移
            $("#deselectAll").click(function () {
                $(".selected_roles option").appendTo(".all_roles");
            });
            //选择左移
            $("#deselect").click(function () {
                $(".selected_roles option:selected").appendTo(".all_roles");
            });
            //消除已经分配的权限
            var pIds = $.map($(".selected_roles option"), function (ele) {
                return ele.value;
            });
            $.each($(".all_roles option"), function (index, val) {
                if ($.inArray(val.value, pIds) != -1) {//当前option有重复
                    $(val).remove();
                }
            });
            //如果是超级管理员,没有角色选择
            if ($("#admin").prop("checked")) {
                roleTr = $("#roleTr").detach();
            }
            //取消超级管理员对勾时,切换角色行(roleTr)的出现
            $("#admin").click(function () {
                if (this.checked) {   //删除
                    roleTr = $("#roleTr").detach();
                } else {
                    $(this).closest("tr").after(roleTr);
                }
            });
            //点击提交按钮选择右边所有的option
            $(".btn_submit").click(function () {
                $(".selected_roles option").prop("selected", true);
                $("#editForm").submit();
            });
        });
    </script>
</head>
<body>
<form id="editForm" action="/employee/saveOrUpdate.do" method="post" data-obj="employee">
    <input type="hidden" name="id" value="${entity.id}">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">用户编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">用户名</td>
                    <td class="ui_text_lt">
                        <input name="name" value="${entity.name}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <%--判断是否是新增,是新增才显示这个密码输入框,否则就是修改,修改的话一般是不用回显密码的--%>
                <c:if test="${empty entity.id}">
                    <tr>
                        <td class="ui_text_rt" width="140">密码</td>
                        <td class="ui_text_lt">
                            <input type="password" name="password" id="password" class="ui_input_txt02"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="ui_text_rt" width="140">验证密码</td>
                        <td class="ui_text_lt">
                            <input name="repassword" type="password" class="ui_input_txt02"/>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td class="ui_text_rt" width="140">Email</td>
                    <td class="ui_text_lt">
                        <input name="email" value="${entity.email}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">年龄</td>
                    <td class="ui_text_lt">
                        <input name="age" value="${entity.age}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">所属部门</td>
                    <td class="ui_text_lt">
                        <select id="dept" name="dept.id" class="ui_select03">
                            <c:forEach var="entity" items="${depts}">
                                <option value="${entity.id}">${entity.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">超级管理员</td>
                    <td class="ui_text_lt">
                        <input id="admin" type="checkbox" name="admin" class="ui_checkbox01"/>
                    </td>
                </tr>
                <tr id="roleTr">
                    <td class="ui_text_rt" width="140">角色</td>
                    <td class="ui_text_lt">
                        <table>
                            <tr>
                                <td>
                                    <select multiple="true" class="ui_multiselect01 all_roles">
                                        <c:forEach var="obj" items="${roles}">
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
                                    <select name="ids" multiple="true" class="ui_multiselect01 selected_roles">
                                        <c:forEach var="obj" items="${entity.roles}">
                                            <option value="${obj.id}">${obj.name}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <%--JS写在这里表示对以上的数据做操作,里面要用到EL表达式,必须写到JSP文件中--%>
                <script type="text/javascript">
                    $("#dept option[value='${entity.dept.id}']").prop("selected", true);
                    $("#admin").prop("checked", ${empty entity ? false : entity.admin});
                </script>
                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<input type="button" value="确定保存" class="ui_input_btn01 btn_submit"/>
                        &nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>