<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
   contextPath : ${contextPath }
<!DOCTYPE html>
<html>
<head>

<meta 좀비봍charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/daumPost.js"></script>


	<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
	
	default - header.jsp
	<h1>CARE LAB</h1>
	<a href="${contextPath }/index">HOME</a>
	<c:if test="${login != null }">
		<a href="${contextPath }/member/logout">LOGOUT</a>
	</c:if>
	<c:if test="${login == null }">
		<a href="${contextPath }/member/login">LOGIN</a>
	</c:if>
	<a href="${contextPath }/member/list">MEMBERS</a>
</body>
</html>