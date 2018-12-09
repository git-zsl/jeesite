<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <link href="${ctxStatic}/bootstrap/bsie/css/bootstrap-ie6.min.css" rel="stylesheet">
    <script src="${ctxStatic}/echarts/echarts.min.js"></script>

</head>
<style>
    .ShaShiDi{
        width:500px;
        height:400px;
        display:flex;
        align-items:center;
        justify-content:center;
    }

    .ShaShiDi img{
        width:100%;
        height:auto;
    }
</style>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div style="width:1200px;height:100px;" class="ShaShiDi">
   <%-- <div style="width:650px;height:400px;float:left;" class="ShaShiDi">
        <button type="button" class="btn btn-info">info</button>
    </div>--%>
       <ul class="nav nav-pills" role="tablist">
           <li role="presentation" class="active"><a href="${ctx}/cms/article/allList">Messages <span class="badge">42</span></a></li>
           <li role="presentation" class="active"><a href="${ctx}/cms/article/allList">Messages <span class="badge">42</span></a></li>
           <li role="presentation" class="active"><a href="${ctx}/cms/article/allList">Messages <span class="badge">42</span></a></li>
       </ul>
      <%-- <a href="${ctx}/cms/article/allList">
           <img src="${ctxStatic}/img/button.jpg"  class="img-circle">
       </a>--%>


</div>
<div style="width:500px;height:500px;" />
<div style="width:1320px;height:400px;">
<div id="main" style="width: 650px;height:400px;float:left;"></div>
<div id="main2" style="width: 600px;height:400px;float:right;"></div>
</div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart1 = echarts.init(document.getElementById('main'));
    var myChart2 = echarts.init(document.getElementById('main2'));

    // 指定图表的配置项和数据
    var option1 = {
        //图表标题
        title: {
            text: "简单线形图表及其配置展示", //正标题
            link: "正标题链接", //正标题链接 点击可在新窗口中打开
            x: "center", //标题水平方向位置
            subtext: "副标题", //副标题
            sublink: "副标题链接", //副标题链接
//正标题样式
            textStyle: {
                fontSize: 24
            },
//副标题样式
            subtextStyle: {
                fontSize: 12,
                color: "red"
            }
        },
        //数据提示框配置
        tooltip: {
            trigger: 'axis' //触发类型，默认数据触发，见下图，可选为：'item' | 'axis' 其实就是是否共享提示框
        },
        //图例配置
        legend: {
            data: ['减少量', '增加量'], //这里需要与series内的每一组数据的name值保持一致
            y: "bottom"
        },
        //工具箱配置
        toolbox: {
            show: true, //是否显示工具箱
            feature: {
                mark: false, // 辅助线标志，上图icon左数1/2/3，分别是启用，删除上一条，删除全部
                dataView: {readOnly: false}, // 数据视图，上图icon左数8，打开数据视图
                magicType: ['line', 'bar'],      // 图表类型切换，当前仅支持直角系下的折线图、柱状图转换，上图icon左数6/7，分别是切换折线图，切换柱形图
                restore: true, // 还原，复位原始图表，上图icon左数9，还原
                saveAsImage: true  // 保存为图片，上图icon左数10，保存
            }
        },
        calculable: true,
        //轴配置
        xAxis: [
            {
                type: 'category',
                data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
                name: "月份"
            }
        ],
        //Y轴配置
        yAxis: [
            {
                type: 'value',
                splitArea: {show: true},
                name: "数值"
            }
        ],
        //图表Series数据序列配置
        series: [
            {
                name: '减少量',
                type: 'line',
                data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
            },
            {
                name: '增加量',
                type: 'line',
                data: [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
            }
        ]
    };

    var option2 = {
        backgroundColor: '#2c343c',
        visualMap: {
            show: false,
            min: 80,
            max: 600,
            inRange: {
                colorLightness: [0, 1]
            }
        },
        series : [
            {
                name: '访问来源',
                type: 'pie',
                radius: '55%',
                data:[
                    {value:235, name:'视频广告'},
                    {value:274, name:'联盟广告'},
                    {value:310, name:'邮件营销'},
                    {value:335, name:'直接访问'},
                    {value:400, name:'搜索引擎'}
                ],
                roseType: 'angle',
                label: {
                    normal: {
                        textStyle: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        lineStyle: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#c23531',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    }
    // 使用刚指定的配置项和数据显示图表。
    myChart1.setOption(option1);
    myChart2.setOption(option2);
</script>
</body>
</html>