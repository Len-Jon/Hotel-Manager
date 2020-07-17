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
    <form method="post" action="/doUpdateAdmin">

        <div style="margin-bottom: 10px;">
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation"><a href="/adminManage">管理员管理</a></li>
                <li role="presentation" class="active"><a href="/updateAdmin?id=${id}">修改管理员</a></li>
            </ul>
        </div>

        <input type="text" style="display:none" name="id" value=${id}>

        <div class="form-group">
            <label for="name">管理员姓名</label>
            <input id="name" type="text" class="form-control" placeholder="请输入管理员姓名" name="name">
        </div>


        <div class="form-group">
            <label for="pass">密码</label>
            <input id="pass" type="text" class="form-control" placeholder="请输入密码" name="pass">
        </div>


        <button type="submit" class="btn btn-default">提交保存</button>
    </form>
</div>
<!-- 全局js -->
<script src="js/jquery.min.js?v=2.1.4"></script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>


<!-- 自定义js -->
<script src="js/content.js?v=1.0.0"></script>
<!--获取数据并显示在框中-->
<script>
    $.get("/selectById?id="+${id}, function (data) {
        $("#name").attr("value", data.name);
        $("#pass").attr("value", data.pass);
    })
</script>
<!-- Page-Level Scripts -->


</body>

</html>
