<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">   
<title>标题</title> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<form action="admin/control/import/resource.html" method="post" enctype="multipart/form-data">
		<input type="file" name="resource">
		<input type="submit" value="提交">
	</form>
	<form action="admin/control/import/video.html" method="post" enctype="multipart/form-data">
		<input type="file" name="video">
		<input type="submit" value="提交">
	</form>
</body>
</html>