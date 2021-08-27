<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

            <!DOCTYPE html>
            <html>

            <head>
                <link rel="icon" type="image/png" href="/images/HomeAid-Favicon.png" />
                <meta charset="ISO-8859-1">
                <title>HomeAid | Plan Meal</title>
                <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
                <link rel="stylesheet" href="/resources/demos/style.css">
                <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
                <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
                <script>
                    $(function () {
                        $("#datepicker").datepicker();
                    });
                </script>
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
                        <h1>Plan a Meal</h1>
                        <form:form method="POST" action="/events/create/new" modelAttribute="event">
                            <div class="form-group">
                                <form:label path="title"> Meal Title: </form:label>
                                <form:input class="form-control" path="title" />
                            </div>
                            <br>

                            <div class="form-group">
                                <form:label path="location"> Location: </form:label>
                                <form:input class="form-control" path="location" />
                            </div>
                            <br>

                            <div class="form-group">
                                <form:label path="note"> Meal prep info: </form:label>
                                <form:textarea class="form-control" path="note" rows="3" cols="30" />
                            </div>
                            <br>

                            <div class="form-group">
                                <form:label path="start"> Start Date: </form:label>
                                <form:input class="form-control" type="datetime-local" path="start" />
                            </div>
                            <br>
                            <div class="form-group">
                                <form:label path="end"> End Date: </form:label>
                                <form:input class="form-control" type="datetime-local" path="end" />
                            </div>
                            <br>

                            <button class="btn btn-primary">Plan</button>
                        </form:form>
                    </div>
                </div>
            </body>

            </html>