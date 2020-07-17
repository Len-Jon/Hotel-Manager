<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> - CHarts</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>折线图</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-wrench"></i>
                        </a>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="echarts" id="echarts-line-chart"></div>
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>柱状图</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-wrench"></i>
                        </a>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">

                    <div class="echarts" id="echarts-bar-chart"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 全局js -->
<script src="js/jquery.min.js?v=2.1.4"></script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>


<!-- ECharts -->
<script src="js/plugins/echarts/echarts-all.js"></script>

<!-- 自定义js -->
<script src="js/content.js?v=1.0.0"></script>


<!-- ECharts demo data -->
<!--script src="js/demo/echarts-demo.js"></script-->
<script>
    $(function () {
        var myDate = new Date(); //获取今天日期
        myDate.setDate(myDate.getDate() - 6);
        var dateArray = [];
        var dateTemp;
        for (var i = 0; i < 7; i++) {
            dateTemp = (myDate.getMonth() + 1) + "-" + myDate.getDate();
            dateArray.push(dateTemp);
            myDate.setDate(myDate.getDate() + 1);
        }

        var lineChart = echarts.init(document.getElementById("echarts-line-chart"));
        var lineoption = {
            title: {
                text: '过去一周入住统计'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['入住数']
            },
            grid: {
                x: 40,
                x2: 40,
                y2: 24
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: [dateArray[0], dateArray[1], dateArray[2], dateArray[3], dateArray[4], dateArray[5], dateArray[6]]
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} 人'
                    }
                }
            ],
            series: [
                {
                    name: '入住数',
                    type: 'line',
                    data: [${day6}, ${day5}, ${day4}, ${day3}, ${day2}, ${day1}, ${day0}],
                    markPoint: {
                        data: [
                            {type: 'max', name: '最大值'},
                            {type: 'min', name: '最小值'}
                        ]
                    },
                    markLine: {
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    }
                }
            ]
        };
        lineChart.setOption(lineoption);
        $(window).resize(lineChart.resize);
        var d = new Date();
        var result = [];
        d.setMonth(d.getMonth() + 1);
        for (var i = 0; i < 12; i++) {
            d.setMonth(d.getMonth() - 1);
            var m = d.getMonth() + 1;
            m = m < 10 ? "0" + m : m;
            //在这里可以自定义输出的日期格式
            result.push(d.getFullYear() + "-" + m);
        }

        var barChart = echarts.init(document.getElementById("echarts-bar-chart"));
        var baroption = {
            title: {
                text: '每月入住统计'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['入住数']
            },
            grid: {
                x: 30,
                x2: 40,
                y2: 24
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    data: [result[11], result[10], result[9], result[8], result[7], result[6], result[5], result[4], result[3], result[2], result[1], result[0]]
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '入住数',
                    type: 'bar',
                    data: [${month11}, ${month10}, ${month9}, ${month8}, ${month7}, ${month6}, ${month5}, ${month4}, ${month3}, ${month2}, ${month1}, ${month0}],
                    markPoint: {
                        data: [
                            {type: 'max', name: '最大值'},
                            {type: 'min', name: '最小值'}
                        ]
                    },
                    markLine: {
                        data: [
                            {type: 'average', name: '平均值'}
                        ]
                    }
                }
            ]
        };
        barChart.setOption(baroption);

        window.onresize = barChart.resize;


    });

</script>


</body>

</html>
