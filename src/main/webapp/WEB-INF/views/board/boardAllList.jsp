<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@include file="../default/header.jsp" %>
	
	게시판
	<table border="1">
		<tr>
			<th>번호</th> <th>id</th> <th>제목</th>
			<th>날짜</th> <th>조회수</th> <th>image_file_name</th>
		</tr>
		
		<c:if test="${list.size() == 0 }">
			<tr><th colspan="6">등록된 글이 없습니다.</th></tr>
		</c:if>
		
		<c:forEach var="dto" items="${list }">
			<tr>
				 <td>${dto.writeNo }</td> <td>${dto.id }</td> 
				 <td><a href="contentView?writeNo=${dto.writeNo }">${dto.title }</a>
				 </td><td>${dto.saveDate }</td> 
				 <td>${dto.hit }</td> <td>${dto.imageFileName }</td>
			 </tr>
		</c:forEach>
		<tr><td colspan="6">
			<c:forEach var="n" begin="1" end="${repeat }">
				<a href="boardAllList?num=${n }">${n }</a>
			</c:forEach>
			
			
			<a href="writeForm">글작성</a>
		</td></tr>
	</table>
</body>
</html>