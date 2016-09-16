<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/pages/share/taglib.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div style="">
		<form action="<c:url value='/admin/control/qa/updatemessage.html'/>"
			method="post">

			<p>
				添加内容:<input name="content" type="text" value="${answer.content}"
					style="margin-left: 10px; width: 450px; height: 30px;">
				添加属性: <select name="attributes" style="width: 120px; height: 35px;">
					<option value="${answer.attributes}" selected="selected">${answer.attributes}</option>
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
			</p>
			<br>
			<p>
				难度值:<select name="difficulty" style="width: 60px; height: 35px;">
					<option value="${answer.difficulty}" selected="selected">${answer.difficulty}</option>
					<option value="1">低</option>
					<option value="2">中</option>
					<option value="3">高</option>
				</select> 媒体类型:<select name="mediaType" style="width: 60px; height: 35px;">
					<option value="${answer.mediaType}" selected="selected">${answer.mediaType}</option>
					<option value="文本">文本</option>
					<option value="图片">图片</option>
					<option value="绘本">绘本</option>
					<option value="音频">音频</option>
				</select>
			</p>
			<br>
			<p>
				是否通过:<select name="checked" style="width: 90px; height: 35px;">
					<option value="0">未通过</option>
					<option value="1">通过</option>
				</select>
			</p>
			<input name="text1" type="submit" value="修改"
				style="margin-left: 266px; margin-top: 90px; width: 85px; height: 35px;">
			<input name="text2" type="reset" value="重置"
				style="margin-left: 176px; width: 85px; height: 35px;">
		</form>
	</div>
</body>
</html>