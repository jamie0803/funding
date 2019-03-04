<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
	<link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
	<link rel="stylesheet" href="${APP_PATH }/css/login.css">
	<style>

	</style>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="javascript:void(0)" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
      </div>
    </nav>

    <div class="container">

      <form class="form-signin" id="loginForm" >
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
        <c:if test="${not empty requestScope.exception }">
        	<div class="form-group has-success has-feedback">
				${requestScope.exception.message }
		    </div>
		</c:if>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" id="loginacct" name="loginacct" value="jamie"  placeholder="请输入登录账号" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<input type="password" class="form-control" id="userpswd" name="userpswd" value="1234" placeholder="请输入登录密码" style="margin-top:10px;">
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<select id="usertype" class="form-control" name="usertype">
                <option value="member">会员</option>
                <option value="user" selected>管理</option>
            </select>
		  </div>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> 记住我
          </label>
          <br>
          <label>
            忘记密码
          </label>
          <label style="float:right">
            <a href="reg.html">我要注册</a>
          </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
      </form>
    </div>
    <script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
    <script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
    <script src="${APP_PATH}/jquery/layer/layer.js"></script>
    <script>
    function dologin() {

    	var loginacct = $("#loginacct").val();
    	if($.trim(loginacct) == ""){ //表单域什么都不输入,值为空串,不是null
//    		alert("用户名称不能为空!");
            layer.msg("用户名称不能为空",{time:2000,icon:5,shift:6});
    		return false;
    	}
    	
    	//异步请求    	
    	$.ajax({
    		type : "post",
    		url : "${APP_PATH}/doLogin.do",
    		data : {
    			loginacct : loginacct,
    			userpswd : $("#userpswd").val(),
    			usertype : $("#usertype").val()
    		},
    		beforeSend : function(){    			
    			return true ;
    		},
    		success : function(result){ //{"success":true}     {"success":false,"message":"用户名称不存在"}
    			if(result.success){
    				window.location.href="${APP_PATH}/main.htm";
    			}else{
    				alert(result.message);
    			}
    		}
    	});

    }
    </script>
  </body>
</html>