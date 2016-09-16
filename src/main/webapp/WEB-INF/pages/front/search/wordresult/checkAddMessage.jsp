<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.cnu.iqas.bean.Recommend.Answer" %>
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
<title>教师审核信息</title>
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
		<form action="check/updateMessage.html" method=post enctype="multipart/form-data"> 
		   
            <%-- <c:forEach items="${listcheckMessage}" var="item" >
              ${item.content}
                                           内容: <select name="content" style="width: 360px; height: 35px;">
	         <option value="${item.content}"></option>
			   </select>                
           </c:forEach> --%>
            <% 
           	ArrayList<Answer> listcheck=(ArrayList<Answer>)request.getAttribute("listcheckMessage");  	
             %>
           <select name="content" style="width: 360px; height: 35px;">
          <!--  <select name="attributes" style="width: 360px; height: 35px;"> -->
           <% 
           for(Answer temp:listcheck){
           %>
           <option value=""><%=temp.getContent() %></option>
		  <%--  <option value=""><%=temp.getAttributes() %></option>    --%>
           
           <%   
           } 
           %>
           
           </select> 
           
           
           
           
           
           <input name="text1" type="submit" value="提交"
		   style="margin-left: 266px; margin-top: 90px;width: 85px; height: 35px;">		 
		    		
		</form>		
	</div>
</body>
</html>