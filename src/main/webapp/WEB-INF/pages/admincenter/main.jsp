<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自适应学习系统管理后台</title>
<jsp:include page="/WEB-INF/pages/share/bootstrap.jsp"></jsp:include>

<link rel="stylesheet"  href="css/control/admin-all.css">
<link rel="stylesheet"  href="css/control/base.css">
<script type="text/javascript" src="js/control/main.js"></script>
<!-- <script type="text/javascript" src="js/control/chur.min.js"></script> 飘花-->
</head>
<script type="text/javascript">
function exit() {
	if( !confirm("确定退出"))
	{
		return false;
	}else{
		return true;
	}
}
</script>
<body>
	<div class="wrap">
		<!-- 头部开始 -->
		<div class="top_c">
		 <header class="main-header">
		  	 <div class="container">
		  	 	<div class="row page-header">
				     <h1 >自适应学习系统管理后台<small>一切都在变化</small></h1>
		  	 	</div>
		     </div>
		  </header>
			<div class="top-nav">
				<span class="btn btn-info">欢迎您，${admin.account}&nbsp;&nbsp;</span> <a href='<c:url value="/admin/exit.html"/>' class="btn btn-success" onclick="return exit()" ><font color="black">安全退出</font></a>
			</div>
		</div>
		<!-- 头部结束 -->
		<!-- 左边菜单开始-->
		<div class="left_c left">
			<h1>
				<b>系统操作菜单</b>
			</h1>
			<div class="acc">
			<!--<div>
					<a class="one">用户管理</a>
					<ul class="kid">
						<li><b class="tip"></b>
						  <a target="Conframe" href="control/user/list">用户信息</a>
						</li>
					</ul>
				</div>-->
				<div>
					<a class="one">单词管理</a>
					<ul class="kid">
						<li><b class="tip"></b><a target="Conframe" href="<c:url value='admin/control/word/listUI.html'/>" >单词资源</a></li>
						<li><b class="tip"></b><a target="Conframe" href="<c:url value='admin/control/word/statistics.html'/>" >统计信息</a></li>
						<li><b class="tip"></b><a target="Conframe" href="<c:url value='admin/control/wordtheme/list.html'/>" >单词主题</a></li>
					</ul>
				</div>
				<div>
					<a class="one">商店管理</a>
					<ul class="kid">
						<li><b class="tip"></b><a target="Conframe" href="<c:url value='admin/control/store/listtype.html'/>" >商品类型</a></li>
						</ul>
				</div>
				<div>
					<a class="one">问答模块管理</a>
					<ul class="kid">
						<li><b class="tip"></b><a target="Conframe" href="<c:url value='admin/control/qa/listanswermessage.html'/>" >答案信息审核</a></li>
						</ul>
				</div>
				<div id="datepicker"></div>
			</div>
		</div>
		<!-- 左边菜单结束 -->
		<!-- 右边框架开始 -->
		<div class="right_c"> <!-- glyphicon glyphicon-chevron-left aria-hidden="true"-->
			<div class="nav-tip"  onclick="javascript:void(0)">
			<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> 
			</div>
		</div>
		<div class="Conframe">
			<iframe name="Conframe" id="Conframe"></iframe>
		</div>
		<!-- 右边框架结束 -->
		<div class="bottom_c">Copyright &copy;首都师范大学</div>

	</div>

</body>
</html>