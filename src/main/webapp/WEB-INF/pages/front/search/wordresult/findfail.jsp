<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!--Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>学生添加资源信息</title>
<link href="css/addstyle.css" rel="stylesheet" type="text/css" media="all" />
<!-- Custom Theme files -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="Clean Plans and Pricing Tables  Responsive, Login form web template, Sign up Web Templates, Flat Web Templates, Login signup Responsive web template, Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<!--web-fonts-->
<link
	href='//fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic'
	rel='stylesheet' type='text/css'>
<!-- jQuery -->
<link
	href='//fonts.googleapis.com/css?family=Ubuntu:400,300,300italic,400italic,500,500italic,700,700italic'
	rel='stylesheet' type='text/css'>
</head>
<body>
	<div align="center" class="header" style="margin-top:8cm;">
		<form action="search/addResource.html" method=post>
         <p style="font-family:verdana;font-size:50px;color:F0CBD0"> 
            <img src="image/cry.png" width="150" height="150">    
                                      抱歉，您输入的资源不存在！                
         </p> 
         <p>
             <input name="text" type="button" value="重新输入内容" style="margin-left:240px; margin-top:100px; width:100px; height:50px;" onclick="window.location.href='search/input.html'" /> 
		     <input name="Search" type="button" value="友情添加资源" style="margin-left:100px;margin-top:100px; height:50px;" onclick="window.location.href='add/addResourceMessage.html'" /> 
         </p>                                      
		</form>
		<br/> <br/>
		<br />
		<br />
	</div>
</body>
</html>