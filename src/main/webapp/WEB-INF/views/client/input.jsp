<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>WMS-客户管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/validate/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
</head>
<body>
<form id="editForm" action="/client/saveOrUpdate.do" method="post" data-obj="client">
    <%--把值从作用域中取出,进行回显;--%>
    <input type="hidden" name="id" value="${entity.id}">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">客户编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">客户名称</td>
                    <td class="ui_text_lt">
                        <input name="name" class="ui_input_txt02" value="${entity.name}"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">客户编号</td>
                    <td class="ui_text_lt">
                        <input name="sn" class="ui_input_txt02" value="${entity.sn}"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">联系方式</td>
                    <td class="ui_text_lt">
                        <input name="phone" class="ui_input_txt02" value="${entity.phone}"/>
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
