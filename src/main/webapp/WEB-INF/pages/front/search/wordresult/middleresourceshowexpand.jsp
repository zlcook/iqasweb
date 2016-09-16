<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ page isELIgnored="false"%>
<!--Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>Clean Plans and Pricing Tables Responsive Widget Template
	| Home :: w3layouts</title>
<link href="css/middlestyle.css" rel="stylesheet" type="text/css" media="all" />
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
<script>
	function to(value) {
		//    search/sentence.html
		var url = "search/sentence.html?text=" + value;//text.value就是该值
		window.location.href = url;
	}
</script>
<body>
	<div class="header">
		<h1></h1>
		<br /> <br /> <br /> <br />
	</div>
	<div class="main">

		<div class="pricing-table">
			<div class="pricing">
				<div class="price-top">
					<h2>联想</h2>
				</div>
				<div class="price-bottom">
					<!-- <input name="text1" type="text" value="This is my,,,," style="width:320px; height:230px;"> -->
					<div style="width: 324px; height: 230px; margin-left: -17px;">
						<img src="${media.associationpic}" width="296" height="230">
					</div>
					<div
						style="width: 324px; height: 30px; margin-top: 35px; margin-left: -17px;">
						<audio style="width: 296px;" src="${media.associationaud}"
							controls="controls"></audio>
					</div>
					<div
						style="width: 324px; height: 40px; margin-top: 20px; margin-left: -17px;"><%-- ${media.associationtxt} --%>
					<c:set var="associationtxt" value="${media.associationtxt}"
						scope="request"></c:set>
					<% 
						String associationtxt=(String)request.getAttribute("associationtxt");
					    if(associationtxt.contains("/")){
					    String tempassociation [] =new String[5];
						String[] tempassociationtxt = associationtxt.split("/");
						for (int i = 0; i < tempassociationtxt.length; i++) {
							tempassociation[i] = tempassociationtxt[i].substring(0, tempassociationtxt[i].indexOf("("));
							System.out.print(tempassociationtxt[i] + "");
					%>
					<a href="javascript:to('<%=tempassociation[i]%>')"><%=tempassociationtxt[i]%>/</a>
					<%
						}
					    }else{
							%>
							<a href="javascript:to('<%=associationtxt%>')"><%=associationtxt%></a>
							<%}%>
					</div>		
					<!-- <a href="#" class="button">sign up</a> -->
					<br />
				</div>
			</div>
			<div class="pricing">
				<%-- <div class="price-top">
					<h2>同义词</h2>
				</div>
				<div class="price-bottom">
					<!-- <input name="text1" type="text" value="This is my,,,," style="width:320px; height:230px;"> -->
					<div style="width: 324px; height: 230px; margin-left: -17px;">
						<img src="${media.Synonymspic}" width="296" height="230">
					</div>
					<div
						style="width: 324px; height: 30px; margin-top: 35px; margin-left: -17px;">
						<audio style="width: 296px;" src="${media.Synonymsaud}"
							controls="controls"></audio>
					</div>
					<div
						style="width: 324px; height: 40px; margin-top: 20px; margin-left: -17px;">${media.Synonymstxt}
					<c:set var="Synonymstxt" value="${media.Synonymstxt}"
						scope="request"></c:set>
					   <% 
						String Synonymstxt=(String)request.getAttribute("Synonymstxt");
					    if(Synonymstxt.contains("/"))
					    {
					    	String[] tempSynonymstxt = Synonymstxt.split("/");
					    	String tempSynonyms [] =new String[5];
							for (int i = 0; i < tempSynonymstxt.length; i++) {
								tempSynonyms[i] = tempSynonymstxt[i].substring(0, tempSynonymstxt[i].indexOf("("));
								System.out.print(tempSynonymstxt[i] + "");
						%>
					    <a href="javascript:to('<%=tempSynonyms[i]%>')"><%=tempSynonymstxt[i]%>/</a>
					   <%
							}
					    }else{
						%>
						<a href="javascript:to('<%=Synonymstxt%>')"><%=Synonymstxt%></a>
						<%}%>
					</div>
					<!-- <a href="#" class="button">sign up</a> -->
					<br />
				</div> --%>
			</div>
			<div class="pricing">
				<div class="price-top">
					<h2>同义词</h2>
				</div>
				<div class="price-bottom">
					<!-- <input name="text1" type="text" value="This is my,,,," style="width:320px; height:230px;"> -->
					<div style="width: 324px; height: 230px; margin-left: -17px;">
						<img src="${media.Synonymspic}" width="296" height="230">
					</div>
					<div
						style="width: 324px; height: 30px; margin-top: 35px; margin-left: -17px;">
						<audio style="width: 296px;" src="${media.Synonymsaud}"
							controls="controls"></audio>
					</div>
					<div
						style="width: 324px; height: 40px; margin-top: 20px; margin-left: -17px;"><%-- ${media.Synonymstxt} --%>
					<c:set var="Synonymstxt" value="${media.Synonymstxt}"
						scope="request"></c:set>
					   <% 
						String Synonymstxt=(String)request.getAttribute("Synonymstxt");
					    if(Synonymstxt.contains("/"))
					    {
					    	String[] tempSynonymstxt = Synonymstxt.split("/");
					    	String tempSynonyms [] =new String[5];
							for (int i = 0; i < tempSynonymstxt.length; i++) {
								tempSynonyms[i] = tempSynonymstxt[i].substring(0, tempSynonymstxt[i].indexOf("("));
								System.out.print(tempSynonymstxt[i] + "");
						%>
					    <a href="javascript:to('<%=tempSynonyms[i]%>')"><%=tempSynonymstxt[i]%>/</a>
					   <%
							}
					    }else{
						%>
						<a href="javascript:to('<%=Synonymstxt%>')"><%=Synonymstxt%></a>
						<%}%>
					</div>
					<!-- <a href="#" class="button">sign up</a> -->
					<br />
				</div>
			</div>
			<div class="pricing">
				<%-- <div class="price-top top3">
					<h2>拓展</h2>
				</div>
				<div class="price-bottom">
					<!-- <input name="text1" type="text" value="This is my,,,," style="width:320px; height:230px;"> -->
					<div style="width: 324px; height: 230px; margin-left: -17px;">
						<img src="${media.Expandpic}" width="296" height="230">
					</div>
					<div
						style="width: 324px; height: 30px; margin-top: 35px; margin-left: -17px;">
						<audio style="width: 296px;" src="${media.Expandaud}"
							controls="controls"></audio>
					</div>
					<div
						style="width: 324px; height: 40px; margin-top: 20px; margin-left: -17px;">${media.Expandtxt}
					<c:set var="Expandtxt" value="${media.Expandtxt}" scope="request"></c:set>
					<% 
						String Expandtxt=(String)request.getAttribute("Expandtxt");
					if(Expandtxt.contains("/")){
						String[] tempExpandtxt = Expandtxt.split("/");
						String tempExpand [] =new String[5];
						for (int i = 0; i < tempExpandtxt.length; i++) {
							tempExpand[i] = tempExpandtxt[i].substring(0, tempExpandtxt[i].indexOf("("));
							System.out.print(tempExpand[i] + "");
					%>
					<a href="javascript:to('<%=tempExpand[i]%>')"><%=tempExpandtxt[i]%>/</a>
					<%
						}
					}else{
						%>
						<a href="javascript:to('<%=Expandtxt%>')"><%=Expandtxt%></a>
						<%}%>
					</div>
					<!-- <a href="#" class="button">sign up</a> -->
					<br />
				</div> --%>
			</div>

			<div class="pricing" style="margin-left: 30px;">
				<div class="price-top top2">
					<h2>反义词</h2>
				</div>
				<div class="price-bottom">
					<!-- <input name="text1" type="text" value="This is my,,,," style="width:320px; height:230px;"> -->
					<div style="width: 324px; height: 230px; margin-left: -17px;">
						<img src="${media.Antonympic}" width="296" height="230">
					</div>
					<div
						style="width: 324px; height: 30px; margin-top: 35px; margin-left: -17px;">
						<audio style="width: 296px;" src="${media.Antonymaud}"
							controls="controls"></audio>
					</div>
					<div
						style="width: 324px; height: 40px; margin-top: 20px; margin-left: -17px;"><%-- ${media.Antonymtxt} --%>
					<c:set var="Antonymtxt" value="${media.Antonymtxt}" scope="request"></c:set>
					<% 
				    String Antonymtxt=(String)request.getAttribute("Antonymtxt");
					if(Antonymtxt.contains("/")){
						String[] tempAntonymtxt = Antonymtxt.split("/");
						String tempAntonym [] =new String[5];
						for (int i = 0; i < tempAntonymtxt.length; i++) {
							tempAntonym[i] = tempAntonymtxt[i].substring(0, tempAntonymtxt[i].indexOf("("));
							System.out.print(tempAntonymtxt[i] + "");
					%>
					<a href="javascript:to('<%=tempAntonym[i]%>')"><%=tempAntonymtxt[i]%>/</a>
					<%
						}
					}else{
						%>
						<a href="javascript:to('<%=Antonymtxt%>')"><%=Antonymtxt%></a>
						<%}%>
					</div>
					<!-- <a href="#" class="button">sign up</a> -->
					<br />
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div class="footer">
		<p>
			&copy 2016 Capital Normal University . All rights reserved | Design
			by <a href="http://w3layouts.com">Open Learning.</a>
		</p>
	</div>

</body>
</html>