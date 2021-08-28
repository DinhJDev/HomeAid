<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

            <!DOCTYPE html>
            <html>

            <head>
                <link rel="icon" type="image/png" href="/images/HomeAid-Favicon.png" />
                <meta charset="ISO-8859-1">
                <title>HomeAid | Edit Event </title>
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
                        <h1>Edit Event: ${event.title}</h1>
                        <form:form method="POST" action="/events/edit/${event.id}" modelAttribute="event">
                            <div class="form-group">
                                <form:label path="title"> Title: </form:label>
                                <form:input class="form-control" path="title" />
                            </div>
                            <br>
                            <div class="form-group">
                                <form:label path="note"> Note: </form:label>
                                <form:textarea class="form-control" path="note" rows="3" cols="30" />
                            </div>
                            <br>
                            <div class="form-group">
                                <form:label path="location"> Location: </form:label>
                                <form:input class="form-control" path="location" />
                            </div>
                            <br>
                            
                            <!-- Make usernames unique. They can only make the event public if they're the host. -->
                            <c:choose>
                                <c:when test="${currentUser.username.equals(event.host.username)}">
                                    <div class="form-group">
                                        <label for="privacy">Private?</label>
                                        <input type="checkbox" id="privacy" name="privacy" class="form-check-input">
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group">
                                        <label for="privacy">Private?</label>
                                        <input type="checkbox" id="privacy" name="privacy" class="form-check-input" value="false" disabled>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            
                            <br>
                            <div class="form-group">
                                <!-- <p>Date: <input type="text" id="datepicker"></p> -->
                                <form:label path="start"> Start Date MM-dd-yyyy HH:mm: </form:label>
                                <form:input path="start" id="datepicker" cssClass="form-control" />
                            </div>
                            <br>
                            
                            <div class="form-group">
                                <!-- <p>Date: <input type="text" id="datepicker"></p> -->
                                <form:label path="end"> End Date MM-dd-yyyy HH:mm: </form:label>
                                <form:input path="end" id="datepicker" cssClass="form-control" />
                            </div>
                            <br>


                            <!-- So it doesn't keep creating new ones -->
                            <form:input type="hidden" value="${event.id}" path="id"></form:input>
                            <form:input type="hidden" value="${event.host.id}" path="host"></form:input>

                            <!-- Default is the old value -->
                            <c:choose>
                                <c:when test="${event.attendees.contains(currentUser)}">
                                    <div class="form-group">
                                        <label for="going">Attending?</label>
                                        <input type="checkbox" id="going" name="going" class="form-check-input" checked>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group">
                                        <label for="going">Attending?</label>
                                        <input type="checkbox" id="going" name="going" class="form-check-input">
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <button class="btn btn-primary">Save</button>
                        </form:form>
                        <br>
                        <a href="/events/delete/${event.id}" class="btn btn-danger">Delete</a>
                    </div>
                </div>
            </body>

            </html>