<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<!DOCTYPE html>
<html>

<head>
    <link rel="icon" type="image/png" href="/images/HomeAid-Favicon.png" />
    <meta charset="ISO-8859-1">
    <title>HomeAid | Create Household</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.3/css/all.css"
        integrity="sha384-SZXxX4whJ79/gErwcOYf+zWLeJdY/qpuqC4cAa9rOGUstPomtqpuNWT9wdPEn2fk"
        crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
        crossorigin="anonymous">

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
            <p>Welcome ${currentUser.fullName}!</p>
            <h1>Create a Household</h1>
            <br>
            <p>You will be automatically added to any households you create as a member. </p>
            <form:form method="POST" action="/households/create/new" modelAttribute="household">
                <div class="form-group">
                    <form:label path="name"> Name of Household: </form:label>
                    <form:errors path="name"></form:errors>
                    <form:input class="form-control" path="name"></form:input>
                </div>
                <br>
                <button class="btn btn-primary">Create</button>
            </form:form>
        </div>
    </div>
</body>

</html>