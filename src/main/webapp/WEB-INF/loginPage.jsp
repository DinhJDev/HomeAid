<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Login Page</title>
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
		crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<c:if test="${logoutMessage != null}">
	        <c:out value="${logoutMessage}"></c:out>
	    </c:if>
	    <h1>Login</h1>
	    <c:if test="${errorMessage != null}">
	        <c:out value="${errorMessage}" ></c:out>
	    </c:if>
	    <form class="form" method="POST" action="/login">
	        <p>
	            <label for="username" class="form-label">Username</label>
	            <input type="text" id="username" name="username" class="form-control"/>
	        </p>
	        <p>
	            <label for="password" class="form-label">Password</label>
	            <input type="password" id="password" name="password" class="form-control"/>
	        </p>
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <button class="btn btn-primary">Login</button>
	    </form>
	</div>
</body>
</html>