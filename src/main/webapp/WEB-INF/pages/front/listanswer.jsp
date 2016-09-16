<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/pages/share/taglib.jsp"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>问题答案信息审核界面</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<script type="text/javascript">

	//查询
	function topage(page)
	{
		var form = document.forms[0];
		form.page.value= page;
		form.submit();
	}
	//删除、恢复商品类型
	function makeVisible(method,id) {
		
		var form = document.forms[0];
		form.action='admin/control/store/'+method+".html";
		form.id.value=id;
		form.submit();
	}
</script>

</head>
<body>
<div class="panel panel-default">
  <%-- <div class="panel-heading">
    <h3 class="panel-title">当前位置 &gt;热点话题管理 &gt;<a href='control/group/list'><font color="blue">热点话题信息</font></a>&gt;<font style="font-family:'楷体';font-weight: bold; ">${pageView.records[0].groupChat.name}</font></h3>
  </div> --%>
  <div class="panel-body">
  
<form  action="http://localhost:8080/iqasweb/admin/control/qa/listanswermessage.html" method="post">
	
   <!-- 查询参数 -->
    <input type="hidden" name="page">
	<table class="table table-bordered table-striped">
		<thead>
			<tr>
				<td align="center" width="10%">答案ID</td>
				<td align="center" width="10%">内容属性</td>
				<td align="center" width="30%">内容</td>
				<td align="center" width="5%">难度</td>
				<td align="center" width="10%">媒体类型</td>
				<td align="center" width="15%">创建时间</td>
				<td align="center" width="10%">是否通过</td>
				<td align="center" width="10%">操作</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${pageView.records}" var="entity">
			<tr>
				<td align="center">
				${entity.answerId}
				</td> 
				<td align="center">
				${entity.attributes}
				 </td>
				<td align="center">
				<%-- <input type="text" name="content" style="width: 560px;"value="${entity.content}">	 --%>	
				${entity.content}		 
				 </td>
				<td align="center">
				<%-- <input type="text" name="difficulty" value="${entity.difficulty}"> --%>
				${entity.difficulty}
				</td>
				<td align="center">
				${entity.mediaType}
				</td>
				<td align="center">
				${entity.createDate} 
				</td>
				<td align="center">
				<%-- <input type="text" name="checked" value="${entity.checked}"> --%>
				${entity.checked}
				</td>
				 <td align="center">
				　<%-- <c:if test="${entity.visible==true}">
					<input type="button" value="审核" class="btn btn-info btn-xs" onclick="javascript:makeVisible('disableType','${entity.answerId }')">
				 </c:if>
				<%--  <c:if test="${entity.visible==true}"> --%>							
				 <!-- onclick="window.location.href='admin/control/qa/updatemessage.html'" -->
				<%--</c:if>--%>
				 <input type="button" value="审核" onclick="window.location.href='admin/control/qa/loadMessage.html?id=${entity.answerId}'" class="btn btn-info btn-xs" /> 
			</tr>
		 </c:forEach>
		</tbody>
	</table>
  </form> 
  </div>
   <div class="panel-footer">
     <%@ include file="/WEB-INF/pages/share/fenye.jsp"%>
   </div>
</div>
</body>
</html>