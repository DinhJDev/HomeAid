<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="icon" 
	      type="image/png" 
	      href="/images/HomeAid-Favicon.png" />
		<meta charset="ISO-8859-1">
		<title>HomeAid | Welcome</title>
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.3/css/all.css" integrity="sha384-SZXxX4whJ79/gErwcOYf+zWLeJdY/qpuqC4cAa9rOGUstPomtqpuNWT9wdPEn2fk" crossorigin="anonymous">
		<link
			href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
			rel="stylesheet"
			integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
			crossorigin="anonymous">
			
		<link href="/css/styles.css" rel="stylesheet">
		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
			integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
			crossorigin="anonymous">
		</script>
		<script src="https://code.jquery.com/jquery-3.6.0.slim.js" integrity="sha256-HwWONEZrpuoh951cQD1ov2HUK5zA5DwJ1DNUXaM6FsY=" crossorigin="anonymous"></script>
		<style>@import url('https://fonts.googleapis.com/css2?family=Montserrat:wght@500;600;700&display=swap');</style>
	</head>
	<body>
		<div class="container welcome-card bg-light">
			<div class="row">
				<div class="col" style="padding-right: 0;">
					<div class="form-area">
						<img class="logo" src="/images/HomeAid-Favicon.png">
						<div class="form">
							<form class="login-form" action="/login" method="POST">
								<p class="subtitle">Start tidying up your tasks</p>
								<h1 class="title">Sign In To HomeAid.</h1>
								<div class="register-option">
						      		<p>Don't have an account? <a href="#">Sign Up</a></p>
						      	</div>
								<div class="row mb-4">
									<div class="col">
										<input type="text" id="username" name="username" class="form-control" placeholder="Username" required>
									</div>
								</div>
								<div class="row mb-4">
									<div class="col">
								<input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
									</div>
								</div>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						        <button class="btn btn-primary">Sign In</button>
							</form>
							<form:form class="register-form" action="/registration" method="POST" modelAttribute="member">
								<p class="subtitle">Register now</p>
								<h1 class="title">Sign Up To HomeAid. </h1>
								<div class="login-option">
									<p class="mt-3">Already have an account? <a href="#">Sign In</a></p>
								</div>
								<div class="row mb-4">
									<div class="col">
										<form:input path="fullName" id="name" placeholder="Full Name" class="form-control"/>
									</div>
								</div>
								<div class="row mb-4">
									<div class="col">
										<form:input path="username" id="register-username" placeholder="Username" class="form-control"/>
									</div>
								</div>
								<div class="row mb-4">
									<div class="col">
										<form:password path="password" placeholder="Password" class="form-control"/>
									</div>
								</div>
								<div class="row mb-4">
									<div class="col">
										<form:password path="passwordConfirmation" placeholder="Confirm Password" class="form-control"/>
									</div>
								</div>
								<button class="btn btn-primary">Register</button>
							</form:form>
							</div>
						</div>
					</div>
				<div class="col image-area" style="padding-right:0;">
					<img class="logo-text" src="/images/HomeAid-Logo.png">
					<img class="welcome-image"src="/images/welcome-living-room.jpeg">
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$('.register-form').hide();
		
		$('.register-option').click(function(e) {
			e.preventDefault();
			$('.register-form').show();
			$('.login-form').hide();
			$('#name').focus();
		})
		
		$('.login-option').click(function(e) {
			e.preventDefault();
			$('.login-form').show();
			$('.register-form').hide();
			$('#username').focus();
		})
	</script>
</html>