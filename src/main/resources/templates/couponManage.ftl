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


</head>

<body class="gray-bg">
<div class="ibox-content">
    <div class="row row-lg">


        <div class="col-sm-12">
            <!-- Example Pagination -->
            <div class="example-wrap">
                <h4 class="example-title">优惠券管理</h4>
                <button type="button" class="btn btn-success" style="margin-bottom: 10px;" id="add">新增</button>
                <div class="example">
                    <table data-toggle="table">
                        <thead>
                        <tr>
                            <th>优惠券名</th>
                            <th>修改</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- End Example Pagination -->
        </div>


    </div>

    <!-- End Panel Other -->
</div>

<!-- 全局js -->
<script src="js/jquery.min.js?v=2.1.4"></script>
<script>
    $.get("/getAllCoupon", function (data) {
        $.each(data, function (i, item) {
            var id = item.id;
            var name = item.name;
            $("tbody").append(
                '<tr>\n' +
                '  <td>' + name + '</td>\n' +
                '  <td><a href="/updateCoupon?id=' + id + '">修改</a></td>\n' +
                '</tr>\n'
            );
        });
        $(".no-records-found").remove();
    });
</script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>


<!-- 自定义js -->
<script src="js/content.js?v=1.0.0"></script>

<!-- Page-Level Scripts -->

<!-- Bootstrap table -->
<script src="js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

<!-- Peity -->
<script src="js/demo/bootstrap-table-demo.js"></script>
<script>
    $("#add").click(function () {
        //alert("ok");
        window.location = "/addCoupon";
    });

</script>


</body>

</html>
