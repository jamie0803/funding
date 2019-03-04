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
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i>表单数据</h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <div class="form-group">
                        <label for="roleName">角色名称</label>
                        <input type="text" class="form-control" id="roleName" placeholder="请输入角色名称">
                    </div>
                    <div class="form-group">
                        <label for="roleCode">角色代码</label>
                        <input type="text" class="form-control" id="roleCode" placeholder="请输入角色代码">
                    </div>
                    <div class="form-group">
                        <label for="mark">备注</label>
                        <textarea type="mark" class="form-control" id="mark" placeholder="请输入备注"></textarea>
                    </div>
                        <button onclick="submit()" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <button onclick="reset()" type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>aa
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script src="${APP_PATH}/jquery/layer/layer.js"></script>
<script type="text/javascript">
    function submit() {
        $.ajax({
            type : "post",
            url : "${APP_PATH}/role/addRole.do",
            data : {
                "name" : $("#roleName").val(),
                "code" : $("#roleCode").val(),
                "mark" : $("#mark").val()
            },
            success : function (res) {
                var flag = res.success;
                var message = res.message;
                if (flag){
                    <%--window.location.href = "${APP_PATH}/role/index.htm";--%>
                    layer.msg(message, {icon:5,time:2000}, function () {
                        queryPage(1);
                    })
                }else {
                    layer.msg(message, {icon:6,time:1000});
                }
            }
        })
    }

    //重置按钮
    function reset() {

    }

</script>
</body>
</html>
