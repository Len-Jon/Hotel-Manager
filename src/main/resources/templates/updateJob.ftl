<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title> - jqGird</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css?v=4.1.0" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="css/webuploader.css">


</head>

<body class="gray-bg">
<div class="ibox-content">
    <form method="post" action="/doUpdateJob">

        <div style="margin-bottom: 10px;">
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation"><a href="/jobManage">岗位管理</a></li>
                <li role="presentation" class="active"><a href="/updateJob?id=${id}">修改岗位</a></li>
            </ul>
        </div>

        <input type="text" style="display:none" name="id" value=${id}>

        <div class="form-group">
            <label for="exampleInputEmail1">岗位姓名</label>
            <input type="text" class="form-control" placeholder="请输入岗位名" name="name">
        </div>

        <button type="submit" class="btn btn-default">提交保存</button>
    </form>
</div>
<!-- 全局js -->
<script src="js/jquery.min.js?v=2.1.4"></script>
<script>
    $.post("/selectById", {"id":${id}}, function (data) {
        $("#name").attr("value", data.name);
    })
</script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>


<!-- 自定义js -->
<script src="js/content.js?v=1.0.0"></script>
<div class="gohome"><a class="animated bounceInUp" href="index.html?v=4.0" title="返回首页"><i class="fa fa-home"></i></a>
</div>

<!-- Page-Level Scripts -->


</body>
</html>