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
    <form method="post" action="/doUpdateEmployee">

        <div style="margin-bottom: 10px;">
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation"><a href="/employeeManage">员工管理</a></li>
                <li role="presentation" class="active"><a href="/updateEmployee?id=#{id}">修改员工</a></li>
            </ul>
        </div>

        <input type="text" style="display:none" name="id" value=#{id}>

        <div class="form-group">
            <label for="exampleInputEmail1">员工名</label>
            <input type="text" class="form-control" placeholder="请输员工姓名" name="name" id="name">
        </div>

        <div class="form-group">
            <label for="exampleInputPassword1">性别</label>
            <br/>
            <label class="radio-inline">
                <input type="radio" name="gender" id='male' value="男"> 男
            </label>
            <label class="radio-inline">
                <input type="radio" name="gender" id='female' value="女"> 女
            </label>
        </div>

        <div class="form-group">
            <label for="exampleInputEmail1">身份证</label>
            <input type="text" class="form-control" placeholder="请输员工身份证" name="idCard" id="id_card">
        </div>

        <div class="form-group">
            <label for="exampleInputEmail1">员工岗位</label>
            <select class="form-control" name="job">
            </select>
        </div>

        <div class="form-group">
            <label for="exampleInputEmail1">员工工号</label>
            <input type="text" class="form-control" placeholder="请输员工工号" name="jobNumber" id="job_number">

        </div>

        <div class="form-group">
            <label for="exampleInputEmail1">员工图片</label>
        </div>
        <div class="form-group">
            <img id="before" width="100px" height="168px">
        </div>
        <div id="uploader-demo">
            <div id="fileList" class="uploader-list"></div>
            <div id="filePicker">修改员工图片</div>
        </div>
        <input type="text" style="display:none" id="path" name="path">


        <div class="form-group">
            <label for="exampleInputEmail1">员工家庭住址</label>
            <input type="text" class="form-control" placeholder="请输员工家庭住址" name="address" id="address">
        </div>

        <div class="form-group">
            <label for="exampleInputEmail1">评价情况</label>
            <textarea type="text" class="form-control" placeholder="请输入评价情况"
                      name="evaluation" id="evaluation"></textarea>
        </div>

        <div class="form-group">
            <label for="exampleInputEmail1">自我评价</label>
            <textarea type="text" class="form-control" placeholder="请输入自我评价"
                      name="description" id="description"></textarea>
        </div>


        <button type="submit" class="btn btn-default">提交保存</button>
    </form>
</div>
<!-- 全局js -->
<script src="js/jquery.min.js?v=2.1.4"></script>
<script>
    $.get("/getEmployeeById?id=" +${id}, function (data) {
        $("#name").attr("value", data.name);
        $("#id_card").attr("value", data.idCard);
        $("#job_number").attr("value", data.jobNumber);
        $("#address").attr("value", data.address);
        $("#evaluation").text(data.evaluation);
        $("#description").text(data.description);
        $("#before").attr("src", data.path);
        var gender = data.gender;
        if (gender === '男')
            $("#male").attr("checked", true);
        if (gender === '女')
            $("#female").attr("checked", true);

        $.get("/getAllJobs", function (da) {
            $("select").empty();
            $.each(da, function (i, item) {
                op1 = '<option value="' + item.id + '" selected="">' + item.name + '</option>';
                op2 = '<option value="' + item.id + '">' + item.name + '</option>';
                op = data.job === item.id ? op1 : op2;
                $("select").append(op);
            });
        });
    });

</script>
<script src="js/bootstrap.min.js?v=3.3.6"></script>


<!-- 自定义js -->
<script src="js/content.js?v=1.0.0"></script>

<!-- Page-Level Scripts -->


<script type="text/javascript" src="js/webuploader.js"></script>
<script>
    var $list = $("#fileList");
    var thumbnailWidth = 100;   //缩略图高度和宽度 （单位是像素），当宽高度是0~1的时候，是按照百分比计算，具体可以看api文档
    var thumbnailHeight = 100;
    // 初始化Web Uploader
    var uploader = WebUploader.create({

        // 选完文件后，是否自动上传。
        auto: true,

        // swf文件路径
        swf: "Uploader.swf ",

        // 文件接收服务端。
        server: "/fileupload",

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#filePicker',

        // 只允许选择图片文件。
        accept: {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }
    });
    // 当有文件添加进来的时候
    uploader.on('fileQueued', function (file) {
        var $li = $(
            '<div id="' + file.id + '" class="file-item thumbnail">' +
            '<img>' +
            '<div class="info">' + file.name + '</div>' +
            '</div>'
            ),
            $img = $li.find('img');
        $("#before").remove();


        // $list为容器jQuery实例
        $list.append($li);

        // 创建缩略图
        // 如果为非图片文件，可以不用调用此方法。
        // thumbnailWidth x thumbnailHeight 为 100 x 100
        uploader.makeThumb(file, function (error, src) {
            if (error) {
                $img.replaceWith('<span>不能预览</span>');
                return;
            }

            $img.attr('src', src);
        }, thumbnailWidth, thumbnailHeight);
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function (file, percentage) {
        var $li = $('#' + file.id),
            $percent = $li.find('.progress span');

        // 避免重复创建
        if (!$percent.length) {
            $percent = $('<p class="progress"><span></span></p>')
                .appendTo($li)
                .find('span');
        }

        $percent.css('width', percentage * 100 + '%');
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on('uploadSuccess', function (file, response) {
        //alert(response._raw);
        $('#path').val(response._raw);
        $('#' + file.id).addClass('upload-state-done');
    });

    // 文件上传失败，显示上传出错。
    uploader.on('uploadError', function (file) {
        var $li = $('#' + file.id),
            $error = $li.find('div.error');

        // 避免重复创建
        if (!$error.length) {
            $error = $('<div class="error"></div>').appendTo($li);
        }

        $error.text('上传失败');
    });

    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on('uploadComplete', function (file) {
        $('#' + file.id).find('.progress').remove();

    });

</script>


</body>

</html>
