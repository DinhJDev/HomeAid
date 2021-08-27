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
	<title>HomeAid | Dashboard</title>
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
	<nav class="navbar">
		<div class="container-fluid">
			<a class="navbar-brand" href="/dashboard">
				<img src="/images/HomeAid-Favicon.png" class="logo">
			</a>
			<span class="navbar-text text-white ms-auto">Welcome <c:out value="${currentUser.fullName}"/>,</span>
			<img class="icon" src="currentUser.profilePic">
			<form id="logoutForm" method="POST" action="/logout">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="submit" class="btn btn-primary" value="logout" />
			</form>
		</div>
	</nav>
	<div class="dashboard-card bg-light row">
		<div class="col">
			<p>${successMessage}</p>
			<p>${failureMessage}</p>
			<h1 class="title">Dashboard</h1>
			<c:choose>
				<c:when test="${currentUser.household != null}">
					<p>Household: ${currentUser.household.name}</p>
					<p>
						${currentUser.household.name} family members: 
							<ul>
								<c:forEach items="${currentUser.household.members}" var="m"> 
									<li>${m.fullName}</li>
								</c:forEach>
							</ul>
					</p>
				</c:when>
				<c:otherwise>
					<a href="/households/create" class="btn btn-primary">Create New Household</a>
					<a href="/households/join" class="btn btn-primary">Join Existing Household</a>
				</c:otherwise>
			</c:choose>
			<hr>
			<p class="title">Events Overview</p>
				<div class="row mb-3">
					<div class="col">
						<div class="dashboard-btn dashboard-task-count" onclick="location.href='/tasks/create'"
							style="cursor: pointer;">
							<p>Upcoming Events</p>
						</div>
					</div>
					<div class="col-3">
						<div class="dashboard-btn dashboard-task-count" onclick="location.href='/events/new'" style="cursor: pointer;">
							<!-- <p class="title">${currentUser.tasks.size()}</p> -->
							<p>New Event</p>
						</div>
					</div>
					<div class="col-3">
						<div class="dashboard-btn dashboard-task-count" onclick="location.href='/tasks/all'" style="cursor: pointer;">
							<!-- <p class="title">${currentUser.tasks.size()}</p> -->
							<p>All Events</p>
						</div>
					</div>
					
				</div>
			<hr>
			<p class="title">Tasks Overview</p>
			<div class="row mb-3">
				<div class="col-3">
					<div class="dashboard-btn dashboard-task-count" onclick="location.href='/tasks/create'" style="cursor: pointer;">
						<p>Create New Task</p>
					</div>
				</div>
				<div class="col-3">
					<div class="dashboard-btn dashboard-task-count" onclick="location.href='/tasks/all'" style="cursor: pointer;">
						<!-- <p class="title">${currentUser.tasks.size()}</p> -->
						<p>All Tasks</p>
					</div>
				</div>
				<div class="col-3">
					<div class="dashboard-btn dashboard-task-count" onclick="location.href='/tasks/mytasks'" style="cursor: pointer;">
						<p class="title">${currentUser.tasks.size()}</p>
						<p>Your Tasks</p>
					</div>
				</div>
			</div>
			<div class="row mb-3">
				<div class="col">
					<div class="dashboard-btn-red">
						<p class="title">Highest Priority Task</p>
						<c:choose>
							<c:when test="${highestPriorityTask.title.equals('')}">
								<p>Title: ${highestPriorityTask.title}</p>
								<p>Note: ${highestPriorityTask.note}</p>
								<p>Assigned to: ${highestPriorityTask.assignees}</p>
								<!-- <input type="checkbox" id="priorityCheckBox"> -->
								<form:form method="POST" action="/tasks/edit/${highestPriorityTask.id}" modelAttribute="task">
									<form:input type="hidden" value="${highestPriorityTask.id}" path="id"></form:input>
									<form:input type="hidden" path="completed" value="false" />
									<button class="btn btn-primary">Complete</button>
								</form:form>
							</c:when>
							<c:otherwise>
								<p>All done!</p>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="col">
					<div class="dashboard-btn-green">
						<p class="title">Easiest Task</p>
						<c:choose>
							<c:when test="${easiestTask.title.equals('')}">
								<p>Title: ${easiestTask.title}</p>
								<p>Note: ${easiestTask.note}</p>
								<p>Assigned to: ${easiestTask.assignees}</p>
								<!-- <input type="checkbox" id="priorityCheckBox"> -->
								<form:form method="POST" action="/tasks/edit/${easiestTask.id}" modelAttribute="task">
									<form:input type="hidden" value="${easiestTask.id}" path="id"></form:input>
									<form:input type="hidden" path="completed" value="false" />
									<button class="btn btn-primary">Complete</button>
								</form:form>
							</c:when>
							<c:otherwise>
								<p>All done!</p>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<hr>
			<p class="title">Meal Planning</p>
			<div class="row mb-3">
				<div class="col-3">
					<div class="dashboard-btn">
						<p>View recipes</p>
					</div>
				</div>
				<div class="col-3">
					<div class="dashboard-btn">
						<p>Ingredients</p>
					</div>
				</div>
				<div class="col-3">
					<div class="dashboard-btn">
						<p>Plan a meal</p>
					</div>
				</div>
			</div>
			<div class="row mb-3">
				<div class="col-6">
					<div class="dashboard-btn-red">
						<p>Expiring soon</p>
						<div class="table-responsive">
							<table class="table">
								<tr>
									<th>Item</th>
									<th>Expiration Date</th>
								</tr>
								<tr>
									<td>item.name</td>
									<td>item.expirationDate</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div class="col">
					<div class=dashboard-btn>
						<p>Recommended Meal</p>
						<p>$recommendedRecipe.name</p>
						<p>$recommendedRecipe.ingredients</p>
					</div>
				</div>
			</div>
		</div>
		<!-- <div class="col-2">
			<ul>

			</ul>
				<p class="title">Events</p>
				<p>currentUser.events</p>
			</div> -->
	</div>
</body>
</html>