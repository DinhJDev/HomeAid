<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/png" href="/images/HomeAid-Favicon.png" />
    <meta charset="ISO-8859-1">
    <title>HomeAid | Dashboard</title>
	<script src="js/jquery.js"> </script>
	<script src="js/bootstrap.bundle.min.js"> </script>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.3/css/all.css"
        integrity="sha384-SZXxX4whJ79/gErwcOYf+zWLeJdY/qpuqC4cAa9rOGUstPomtqpuNWT9wdPEn2fk"
        crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
        crossorigin="anonymous">
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>

    <link href="/css/styles.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous">
        </script>
    <script src="https://code.jquery.com/jquery-3.6.0.slim.js"
        integrity="sha256-HwWONEZrpuoh951cQD1ov2HUK5zA5DwJ1DNUXaM6FsY=" crossorigin="anonymous"></script>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@500;600;700&display=swap');
    </style>
</head>
<body>
	<nav class="navbar">
	    <div class="container-fluid">
	        <a class="navbar-brand" href="/dashboard">
	            <img src="/images/HomeAid-Favicon.png" class="logo">
	        </a>
	        <span class="navbar-text text-white ms-auto">Welcome
	            <c:out value="${currentUser.fullName}" />,
	        </span>
	        <img class="icon" src="currentUser.profilePic">
	    </div>
	</nav>
	<div class="dashboard-card bg-light row">
		<div class="col">
			<h1>Add an Ingredient</h1>
			<p>${addSuccess}</p>
			<form:form method="POST" action="/meals/ingredients/add" modelAttribute="item">
				<div class="form-group">
					<form:label path="name"> Name: </form:label>
					<form:input class="form-control" path="name" />
				</div>
				<br>
				<form:input type="hidden" path="ingredient" value="true" />
				<div class="input-group mb-3">
					<form:label path="quantity"> Quantity: </form:label>
					<form:input path="quantity" class="form-control" />
					<form:label path="quantity"> Measurement: </form:label>
					<form:select class="form-select" path="measurement">
						<form:option value="oz" label="Ounce(oz)" />
						<form:option value="ml" label="Milliliter(mL)" />
						<form:option value="li" label="Liter(L)" />
						<form:option value="gr" label="Gram(g)" />
						<form:option value="lb" label="Pound(lb)" />
						<form:option value="ct" label="Count" />
					</form:select>
				</div>
				<form:label path="expirationDate"> Expiration Date: </form:label>
				<form:input class="form-control" type="date" path="expirationDate"/>
				<button class="btn btn-primary">Create</button>
			</form:form>
		</div>
	</div>
</body>
</html>