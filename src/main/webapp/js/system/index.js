$(function () {
    // 加载日期
    loadDate();
    // ======================================
    // 显示隐藏侧边栏
    $("#show_hide_btn").click(function () {
        switchSysBar();
    });
    // ======================================
    //面板切换
    $("#TabPage2 li").click(function () {
        //把所有li先复原
        $.each($("#TabPage2 li"), function (index, val) {
            $(val).removeClass("selected");
            $(val).children("img").prop("src", "/images/common/" + (index + 1) + ".jpg");
        });
        //当前面板被选中
        $(this).addClass("selected");
        $(this).children("img").prop("src", "/images/common/" + ($(this).index() + 1) + "_hover.jpg");
        $("#nav_module img").prop("src", "/images/common/module_" + ($(this).index() + 1) + ".png");
        //切换菜单
        loadMenus($(this).data("rootmenu"));
    });

    //初始化菜单树
    loadMenus("business");
});
//=======================zTree插件=========================================
//zTree的个性配置
var setting = {
    data: {
        //配置启用简单JSON格式
        simpleData: {
            enable: true
        }
    },
    //设置节点的回调函数
    callback: {
        onClick: function (event, treeId, node) {
            //点击子菜单显示对应页面
            if (node.action) {
                $("#rightMain").prop("src", node.action + ".do");
                //修改菜单对应的标题 当前位置:系统模块&nbsp;>&nbsp;员工管理
                $("#here_area").html("当前位置:" + node.getParentNode().name + "&nbsp;>&nbsp;" + node.name);
            }
        }
    },
    async: {
        enable: true,
        url: "/systemMenu/loadMenu.do",
        //将当前节点中的属性传递过去
        autoParam: ["sn=parentSn"]
    }
};

//菜单的树节点:重点掌握简单JSON格式
var treeNodes = {
    business: [
        {id: 1, pId: 0, sn: "business", name: "业务模块", isParent: true}
    ],
    systemManage: [
        {id: 1, pId: 0, sn: "system", name: "系统模块", isParent: true}
    ],
    charts: [
        {id: 1, pId: 0, sn: "chart", name: "报表模块", isParent: true}
    ]
};

//加载菜单
function loadMenus(menu) {
    $.fn.zTree.init($("#dleft_tab1"), setting, treeNodes[menu]);
}

//加载当前日期
function loadDate() {
    var time = new Date();
    var myYear = time.getFullYear();
    var myMonth = time.getMonth() + 1;
    var myDay = time.getDate();
    if (myMonth < 10) {
        myMonth = "0" + myMonth;
    }
    document.getElementById("day_day").innerHTML = myYear + "." + myMonth + "."
        + myDay;
}

/**
 * 隐藏或者显示侧边栏
 *
 */
function switchSysBar(flag) {
    var side = $('#side');
    var left_menu_cnt = $('#left_menu_cnt');
    if (flag == true) { // flag==true
        left_menu_cnt.show(500, 'linear');
        side.css({
            width: '280px'
        });
        $('#top_nav').css({
            width: '77%',
            left: '304px'
        });
        $('#main').css({
            left: '280px'
        });
    } else {
        if (left_menu_cnt.is(":visible")) {
            left_menu_cnt.hide(10, 'linear');
            side.css({
                width: '60px'
            });
            $('#top_nav').css({
                width: '100%',
                left: '60px',
                'padding-left': '28px'
            });
            $('#main').css({
                left: '60px'
            });
            $("#show_hide_btn").find('img').attr('src',
                '/images/common/nav_show.png');
        } else {
            left_menu_cnt.show(500, 'linear');
            side.css({
                width: '280px'
            });
            $('#top_nav').css({
                width: '77%',
                left: '304px',
                'padding-left': '0px'
            });
            $('#main').css({
                left: '280px'
            });
            $("#show_hide_btn").find('img').attr('src',
                '/images/common/nav_hide.png');
        }
    }
}
