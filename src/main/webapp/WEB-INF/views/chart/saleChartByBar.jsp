<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="/js/plugins/echarts/echarts-all.js"></script>
</head>
<body>
    <div id="main" style="width: 600px;height: 400px"></div>
    <script type="text/javascript">
        //基于准备好的dom,初始化echarts实例
        var myChart = echarts.init(document.getElementById("main"));

        //指定图表的配置项和数据
        var option = {
            title:{
                text:'销售报表',
                x:'center'
            },
            tooltip:{},
            legend:{
                data:['销量'],
                x:'left'
            },
            xAxis:{
                data:${groupByTypes}
            },
            yAxis:{},
            series:[{
                    name: '销量',
                    type: 'bar',
                    data: ${totalAmounts}
                }]
        };
        //使用刚指定的配置项和数据显示图表.
        myChart.setOption(option);
    </script>
</body>
</html>
