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
		<form action="search/addResource.html" method=post enctype="multipart/form-data"> 		  
         <%
          HttpSession s = request.getSession(); 
         %>
			<p>
				输入内容: <%=s.getAttribute("text")%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
			   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 	
				添加类型: <select name="type" style="width: 60px; height: 35px;">
					     <option value="1">单词</option>
					     <option value="2">句子</option>
				       </select>
			</p>
			<br>
			<p>
			           添加内容:<input name="content" type="text" value=""
					style="margin-left: 10px; width: 450px; height: 30px;">
				添加属性: <select name="attributes" style="width: 120px; height: 35px;">
					     <option value="课文原句">课文原句</option>
					     <option value="情境段落">情境段落</option>
					     <option value="延伸例句">延伸例句</option>
					     <option value="百科">百科</option>
					     <option value="用法">用法</option>					
					     <option value="联想">联想</option>
					     <option value="同义词">同义词</option>
					     <option value="反义词">反义词</option>
					     <option value="拓展">拓展</option>
					     <option value="常用">常用</option>
					  </select>		
			       难度值:<select name="difficulty" style="width: 60px; height: 35px;">
					     <option value="1">低</option>
					     <option value="2">中</option>
					      <option value="3">高</option>
				       </select>
			      媒体类型:<select name="mediaType" style="width: 60px; height: 35px;">
					     <option value="文本">文本</option>
					     <option value="图片">图片</option>
					     <option value="绘本">绘本</option>
					     <option value="音频">音频</option>		     
				       </select>			
			</p>
			<br>
			<br>
			<p>			 
			   上传文件 <input type="text" name="name" />
			       <input type="file" name="file" style="margin-left: 6px; width: 150px; height: 35px;">			   
			</p>
			<br>
			   <input name="text1" type="submit" value="提交"
					style="margin-left: 266px; margin-top: 90px;width: 85px; height: 35px;">
			   <input name="text2" type="reset" value="重置"
					style="margin-left: 176px; width: 85px; height: 35px;">
			<p>
			</p>
		</form>
		<br /> <br />
		<br />
		<br />
	</div>
</body>
</html>