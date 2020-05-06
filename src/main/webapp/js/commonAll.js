//禁用数组参数名后面加[]
$.ajaxSettings.traditional = true;

//弹出对话框
function showDialog(msg, ok, cancel) {
    $.dialog({
        title: "温馨提示",
        icon: "face-smile",
        content: msg,
        lock:true,
        ok: ok || true, //ok传进来的是函数就执行函数,如果什么都没有传进来就执行true;
        cancel: cancel
    });
}

$(function () {
    //点击新增按钮跳转到指定界面
    $(".btn_input").click(function () {
        //获取到当前按钮自定义的url属性值,再跳转
        var url = $(this).data("url");
        location.href = url;
    });

    //翻页操作
    $(".btn_page").click(function () {
        var pageNo = $(this).data("page") || $(":text[name='currentPage']").val();
        //设置当前页的值
        $(":text[name='currentPage']").val(pageNo);
        //提交表单
        $("#searchForm").submit();
    });
    //设置页面容量
    $("#pageSize").change(function () {
        //修改当前页为1,再提交表单
        $(":text[name='currentPage']").val(1);
        //提交表单
        $("#searchForm").submit();
    });

    //获取所有的删除超链接,然后给它绑定点击事件
    $(".btn_delete").click(function () {
        var url = $(this).data("url");
        var obj = $(this).data("obj");
        //弹出提示框
        showDialog("确定要删除吗?", function () {
            //点击确定按钮发送ajax请求来删除数据
            $.get(url, function (data) {
                if (data.success) {
                    //提示用户操作成功
                    showDialog("操作成功", function () {
                        //跳转到当前对象的list界面
                        location.href = "/" + obj + "/list.do";
                    });
                } else {
                    //提示用户操作失败
                    showDialog("操作失败:" + data.msg);
                }
            }, "json");
        }, true);
    });

    $(".btn_audit").click(function () {
        var url = $(this).data("url");
        var obj = $(this).data("obj");
        //弹出提示框
        showDialog("确定要审核吗?", function () {
            //点击确定按钮发送ajax请求来删除数据
            $.get(url, function (data) {
                if (data.success) {
                    //提示用户操作成功
                    showDialog("操作成功", function () {
                        //跳转到当前对象的list界面
                        location.href = "/" + obj + "/list.do";
                    });
                } else {
                    //提示用户操作失败
                    showDialog("操作失败:" + data.msg);
                }
            }, "json");
        }, true);
    });

    //把editForm表单变成ajax提交
    $("#editForm").ajaxForm(function (data) {
        var obj = $("#editForm").data("obj");   //获取自定义属性obj的值;
        if (data.success) {   //判断是否能获取到JSONResult对象;
            showDialog("操作成功", function () {
                location.href = "/" + obj + "/list.do";
            });
        } else {
            showDialog("操作失败:" + data.msg);
        }
    });

    /*======================================================*/
    /** table鼠标悬停换色* */
    // 如果鼠标移到行上时，执行函数
    $(".table tr").mouseover(function () {
        $(this).css({
            background: "#CDDAEB"
        });
        $(this).children('td').each(function (index, ele) {
            $(ele).css({
                color: "#1D1E21"
            });
        });
    }).mouseout(function () {
        $(this).css({
            background: "#FFF"
        });
        $(this).children('td').each(function (index, ele) {
            $(ele).css({
                color: "#909090"
            });
        });
    });
});
