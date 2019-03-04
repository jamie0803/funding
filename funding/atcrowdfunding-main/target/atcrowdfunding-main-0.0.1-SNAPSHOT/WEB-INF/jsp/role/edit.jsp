<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">

    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }

        table tbody tr:nth-child(odd) {
            background: #F4F4F4;
        }

        table tbody td:nth-child(even) {
            color: #C00;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">平台 - 用户维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">

            <%@ include file="/WEB-INF/jsp/common/userinfo.jsp" %>

        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <jsp:include page="/WEB-INF/jsp/common/menu.jsp"></jsp:include>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">修改</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i>表单数据</h3>
                </div>
                <div class="panel-body">
                    <div class="panel-body">
                        <div class="table-responsive">
                            <div class="form-group">
                                <label for="roleName">角色名称</label>
                                <input type="text" class="form-control" id="roleName" value="${role.name}" placeholder="请输入角色名称">
                            </div>
                            <div class="form-group">
                                <label for="roleCode">角色代码</label>
                                <input type="text" class="form-control" id="roleCode" value="${role.code}" placeholder="请输入角色代码">
                            </div>
                            <div class="form-group">
                                <label for="mark">备注</label>
                                <textarea type="mark" class="form-control" id="mark" placeholder="${role.mark}"></textarea>
                            </div>
                            <button onclick="submit()" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 修改</button>
                            <button onclick="reset()" id="clear" type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script src="${APP_PATH}/jquery/layer/layer.js"></script>
<script type="text/javascript">

    function submit() {
        var paramObj = {
            'code' : $("#roleCode").val(),
            'name' : $("#roleName").val(),
            'mark' : $("#mark").val(),
            'id' : ${role.id}
        };
        $.ajax({
            type : "post",
            url : "${APP_PATH}/role/doUpdate.do",
            dataType : "json",
            data : paramObj,
            success : function (result) {
                var message = result.message;
                console.log(flag);
                if (result.success){
                    console.log("准备跳转上个页面");
                    window.location.href="${APP_PATH}/role/index.htm";
                }else{
                    layer.msg("修改失败！"+message,{time:1000,icon:5});
                    return;
                }
            },
        });
    }

    //重置按钮
    function reset() {
        $("#clear").reset();
    }

</script>
</body>
</html>
