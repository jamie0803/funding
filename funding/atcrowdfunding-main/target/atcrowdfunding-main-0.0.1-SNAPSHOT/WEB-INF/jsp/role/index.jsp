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
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 角色维护</a></div>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="searchText" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button onclick="searchByText()" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button  type="button" class="btn btn-danger" style="float:right;margin-left:10px;"
                             onclick="batchdeleteRole()"><i class="glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;"
                            onclick="addRole()"><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox" id="checkAll"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            <!-- -->

                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <%--<li class="disabled"><a href="#">上一页</a></li>
                                        <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                                        <li><a href="#">2</a></li>
                                        <li><a href="#">3</a></li>
                                        <li><a href="#">下一页</a></li>--%>
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
        queryPage(1);
    });
    
    var paramObj = {
        pageNo: 1,
        pageSize: 5,
    };

    //分页查询
    function queryPage(pageNo) {
        paramObj.pageNo = pageNo;
        $.ajax({
                type: "POST",
                url: "${APP_PATH}/role/queryPage.do",
                data: paramObj,
                success: function (result) {
                    var page = result.data;
                    var list = page.datas;
                    var success = result.success;
                    var message = result.message;
                    console.log("success=" + success + ",message=" + message);
                    if (list == null) {
                        $("tbody").html(message);
                    }

                    if (success) {
                        //局部刷新，拼接数据进tbody中
                        var content = '';
                        $.each(list, function (i, e) { //第一个参数表示元素索引,第二个参数表示迭代元素
                            content += '<tr>';
                            content += '  <td>' + (i + 1) + '</td>';
                            content += '  <td><input type="checkbox" id="' + e.id + '"></td>';
                            content += '  <td>' + e.name + '</td>';
                            content += '  <td>';
                            content += '	  <button type="button" class="btn btn-success btn-xs" onclick="window.location.href=\'${APP_PATH}/role/toAssignPermission.do?&roleid=' + e.id + '\'" ><i class=" glyphicon glyphicon-check"></i></button>';
                            content += '	  <button type="button" class="btn btn-primary btn-xs" onclick="window.location.href=\'${APP_PATH}/role/toUpdate.do?&id=' + e.id + '\'" ><i class=" glyphicon glyphicon-pencil"></i></button>';
                            content += '	  <button type="button" class="btn btn-danger btn-xs" onclick="deleteRole('+ e.id +',\''+e.name+'\')" ><i class=" glyphicon glyphicon-remove"></i></button>';
                            content += '  </td>';
                            content += '</tr>';
                        });
                        $("tbody").html(content);
                    } else {
                        layer.msg(message, {icon: 5, time: 1000});
                    }

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
            })
    }

    //条件查询
    function searchByText() {
        paramObj.text = $("#searchText").val();
        queryPage(1);

    }

    function query2() {
        paramObj.text = $("#input2").val();
        queryPage(1);
    }

    //复选框全选/全不选
    $("#checkAll").click(function () {
        //获取全选框状态
        var status = this.checked;
        //遍历复选择框,将属性和全选框状态置为一样
        var box = $("tbody :checkbox");
        console.log(box);
        $.each(box, function (i, e) {
          e.checked = status;
          console.log(e.checked);
        })
    })

    //批量删除
    function batchdeleteRole() {
        //获取选中的复选框
        var checkedbox = $("tbody :checked");
        var length = checkedbox.length;
        console.log(length);
        var ids = '';
        $.each(checkedbox, function (i, e) {
            if (i > 0){
                ids += "&id" + e.id;
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
                    url : "${APP_PATH}/role/batchDeleteRole.do",
                    data : ids,         //此处是ids  但后端以数组形式接收时是id
                    success : function (res) {
                        if (res.success){
                            layer.msg("删除成功!", {icon:6,time:1000},function () {
                                queryPage(1);
                            })
                        }else {
                            layer.msg("删除失败!", {icon:6,time:1000},function () {
                                return;
                            });
                        }
                    }
                })
            });
        }
    }

    //单个删除
    function deleteRole(id,name) {
        console.log(id,name);
        layer.confirm("确认删除"+name+"这个角色吗?",{icon:3,title:"提示"});
        $.ajax({
            type:"post",
            url:"${APP_PATH}/role/deleteRole.do",
            data : { id : "id" },
            success : function (res) {
                var flag = res.success;
                var message = res.message;
                if (flag){
                    layer.msg(message, {icon:5, time:1000}, function () {
                        queryPage(1);
                    })
                }else{
                    layer.msg(message, {icon:6, time:1000});
                }
            }
        })
    }


    function addRole() {
        window.location.href = "${APP_PATH}/role/toAdd.htm"
    }


    $("tbody .btn-success").click(function () {
        window.location.href = "assignPermission.html";
    });


</script>
</body>
</html>

