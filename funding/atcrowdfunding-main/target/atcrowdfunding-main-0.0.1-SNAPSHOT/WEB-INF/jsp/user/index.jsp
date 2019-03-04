<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
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

            <%@ include file="/WEB-INF/jsp/common/userinfo.jsp"%>

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
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="inputText" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button onclick="batchDeleteUser()" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class="glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;"
                            onclick="window.location.href='${APP_PATH}/user/add.htm'"><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox" id="checkAll"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <form id="userForm">
                                <tbody>
                                    <%--<c:forEach begin="" end="" items="" step="">
                                        <tr>
                                            <td>1</td>
                                            <td><input type="checkbox"></td>
                                            <td>Lorem</td>
                                            <td>ipsum</td>
                                            <td>dolor</td>
                                            <td>
                                                <button type="button" class="btn btn-success btn-xs"><i
                                                        class=" glyphicon glyphicon-check"></i></button>
                                                <button type="button" class="btn btn-primary btn-xs"><i
                                                        class=" glyphicon glyphicon-pencil"></i></button>
                                                <button type="button" class="btn btn-danger btn-xs"><i
                                                        class=" glyphicon glyphicon-remove"></i></button>
                                            </td>
                                        </tr>
                                </c:forEach>--%>
                                </tbody>
                            </form>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <ul class="pagination">

                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
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
    //点击menu中的用户维护、角色维护等时，触发进行加载
    $(function () {
        $(".list-group-item").click(function () {
            if ($(this).find("ul")) {
                $(this).toggleClass("tree-closed");
                if ($(this).hasClass("tree-closed")) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
        /*//页面加载完成查询1页数据
        queryPage(1);*/

        //根据pageNo参数进行异步查询
        <c:if test="${empty param.pageNo}">
            queryPage(1);
        </c:if>
        <c:if test="${not empty param.pageNo}">
            queryPage(param.pageNo);
        </c:if>
    });


    //复选框全选/全不选
    $("#checkAll").click(function () {
        //获取全选框的选中状态
        var status = this.checked;
        console.log(status);
        //遍历复选框，将状态和全选框状态置为一样
        var box = $("tbody :checkbox");
        $.each(box,function (i, e) {
            e.checked = status;
//          $(this).attr("checked", status);
            console.log(e.id+","+e.checked);
        })
    })

    //批量删除用户
    function batchDeleteUser() {
        //获取选中的复选框
        var checkedBox = $("tbody :checked");
        var length = checkedBox.length;
        console.log(length);

        //拼串  拼接id字符串       id=1&id=2&id=3
        var ids = '';
        $.each(checkedBox, function (i, e) {
            if (i>0){
                ids += "&id=" + e.id;
                return;
            }
            ids += "id=" + e.id;
        })
        console.log(ids);

        if (length == 0){
            layer.msg("请选择要删除的用户！",{icon:5,time:1000});
        }else {
            layer.confirm("确认要删除选中的用户？",{title:"提示",icon:3}, function () {
                $.ajax({
                    type : "post",
                    url : "${APP_PATH}/user/batchDelete.do",
                    data : ids,
                    success : function (result) {
                        var flag = result.success;
                        var message = result.message;
                        if (flag){
                            layer.msg(message, {icon:6,time:1000}, function () {
                                queryPage(1);
                            });
                        }else{
                            layer.msg(message, {icon:5,time:1000});
                        }
                    }
                })
            });
        }
    }

    //单个删除用户
    function deleteUser(id,name) {
        console.log("id="+id);
        console.log($("#delButton").val());
        layer.confirm("确认删除【"+name+"】这个用户吗？", {icon:3,title:"提示"},
            function () {
                $.ajax({
                    type : "post",
                    url : "${APP_PATH}/user/doDelete.do",
                    data : {"id" : id},
                    success : function (result) {
                        var flag = result.success;
                        if (flag){
                            //window.location.href="S{APP_PATH}/user/index.htm";        //同步刷新整个页面
                            //刷新页面前清空输入框的值
                            $("#inputText").val("");
                            $("#input2").val("");
                            queryPage(1);                                               //异步刷新中间表格区域
                        }else {
                            layer.msg("删除用户失败！", {icon:5, time:500});
                            return;
                        }
                    }
                })
            }), function () {
                return;
            }
    }

    //定义page
    var jsonObj = {
        pageNo: 1,
        pageSize: 5
    };

    //条件查询
    $("#queryBtn").click(function(){
        var text = $("#inputText").val();
        jsonObj.text = text;
        queryPage(1);
    })

    function query2() {
        var text2 = $("#input2").val();
        jsonObj.text = text2;
        queryPage(1);
    }

    //分页查询
    function queryPage(pageNo) {
        jsonObj.pageNo = pageNo;
        $.ajax({
            type : "post",
            url  : "${APP_PATH}/user/queryPage.do",
            data : jsonObj,
            dataType : "json",
            beforeSend : function () {
                var loadingIndex = layer.msg("加载数据中...",{time:1000,icon:6});
                layer.close(loadingIndex);
            },
            success : function (res) {
                var page = res.data;
                var list = page.datas;

                //局部刷新
                var content = '';
                $.each(list,function(i,e){ //第一个参数表示元素索引,第二个参数表示迭代元素
                    content+='<tr>';
                    content+='  <td>'+(i+1)+'</td>';
                    content+='  <td><input type="checkbox" id="'+e.id+'"></td>';
                    content+='  <td>'+e.loginacct+'</td>';
                    content+='  <td>'+e.username+'</td>';
                    content+='  <td>'+e.email+'</td>';
                    content+='  <td>';
                    content+='	  <button type="button" class="btn btn-success btn-xs" onclick="window.location.href=\'${APP_PATH}/user/toAssignRole.htm?&userId='+e.id+'\'"><i class=" glyphicon glyphicon-check"></i></button>';
                    content+='	  <button type="button" class="btn btn-primary btn-xs" onclick="window.location.href=\'${APP_PATH}/user/toUpdate.do?&id='+e.id+'\'" ><i class=" glyphicon glyphicon-pencil"></i></button>';
                    content+='	  <button type="button" id="delButton" class="btn btn-danger btn-xs" onclick="deleteUser('+e.id+',\''+e.loginacct+'\')" ><i class=" glyphicon glyphicon-remove"></i></button>';
                    content+='  </td>';
                    content+='</tr>';
                });
                $("tbody").html(content);


                //局部刷新分页条
                var navcontent = '';

                if(page.pageNo==1){
                    navcontent += '<li class="disabled"><a href="#">上一页</a></li>';
                }else{
                    navcontent += '<li ><a onclick="queryPage('+(page.pageNo-1)+')">上一页</a></li>';
                }

                for(var i=1; i<= page.totalNo; i++){
                    navcontent += '<li ';
                    if(page.pageNo==i){
                        navcontent += 'class="active"';
                    }
                    navcontent += ' ><a onclick="queryPage('+i+')">'+i+'</a></li>';
                }

                if(page.pageNo==page.totalNo){
                    navcontent += '<li class="disabled"><a href="#">下一页</a></li>';
                }else{
                    navcontent += '<li ><a onclick="queryPage('+(page.pageNo+1)+')">下一页</a></li>';
                }

                $(".pagination").html(navcontent);

            }
        });
    }

</script>
</body>
</html>
