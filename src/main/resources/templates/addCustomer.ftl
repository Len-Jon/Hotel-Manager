<!DOCTYPE html>
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
    <form method="post" action="/saveCustomer">
        <div style="margin-bottom: 10px;">
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation"><a href="/customerManage">顾客管理</a></li>
                <li role="presentation" class="active"><a href="/addCustomer">新增顾客</a></li>
            </ul>
        </div>

        <div class="form-group">
            <label for="exampleInputEmail1">顾客名</label>
            <input type="text" class="form-control" placeholder="请输顾客姓名" name="name">
        </div>


        <div class="form-group">
            <label for="exampleInputEmail1">手机号码</label>
            <input type="text" class="form-control" placeholder="请输手机号码" name="phoneNumber">
        </div>

        <div class="form-group">
            <label for="exampleInputEmail1">优惠券</label>
            <select class="form-control" name="coupon">
            </select>
        </div>


        <button type="submit" class="btn btn-default">提交保存</button>
    </form>
</div>
<!-- 全局js -->
<script src="js/jquery.min.js?v=2.1.4"></script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>
<script>
    $.get("/getAllCoupon", function (da) {
        $("select").empty();
        $.each(da, function (i, item) {
            op = '<option value="' + item.id + '">' + item.name + '</option>';
            $("select").append(op);
        });
    })
</script>

<!-- 自定义js -->
<script src="js/content.js?v=1.0.0"></script>

<!-- Page-Level Scripts -->


</body>

</html>
