<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

            <!DOCTYPE html>
            <html>

            <head>
                <link rel="icon" type="image/png" href="/images/HomeAid-Favicon.png" />
                <meta charset="ISO-8859-1">
                <title>HomeAid | Edit Task </title>
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
                        <h1>Edit Task: ${task.title}</h1>
                        <form:form method="POST" action="/tasks/edit/${task.id}" modelAttribute="task">
                            <div class="form-group">
                                <form:label path="note"> Title: </form:label>
                                <form:input class="form-control" path="title" />
                            </div>
                            <br>

                            <div class="form-group">
                                <form:label path="priority"> Priority: </form:label>
                                <form:select class="form-select" path="priority">
                                    <form:option value="1">Low</form:option>
                                    <form:option value="2">Medium</form:option>
                                    <form:option value="3">High</form:option>
                                </form:select>
                            </div>
                            <br>
                            <form:label path="difficulty"> Difficulty: </form:label>
                            <form:select class="form-select" path="difficulty">
                                <form:option value="1">Easy</form:option>
                                <form:option value="2">Medium</form:option>
                                <form:option value="3">Hard</form:option>
                            </form:select>
                            <br>
                            <div class="form-group">
                                <form:label path="note"> Note: </form:label>
                                <form:textarea class="form-control" path="note" rows="3" cols="30" />
                            </div>
                            <br>

                            <div class="form-group">
                                <form:label path="assignees"> Assignees (choose 1 or more): </form:label>
                                <br>
                                <form:select class="form-select" multiple="true" path="assignees"
                                    items="${houseMembers}" >

                                    <!-- <form:options items="${booth.categories}" itemValue="categoryName" itemLabel="categoryName"></form:options> -->
                                </form:select>
                            </div>
                            <br>

                            <!-- So it doesn't keep creating new ones -->
                            <form:input type="hidden" value="${task.id}" path="id"></form:input>

                            <!-- Should be clicked when it's true and not when it's false -->
                            <div class="form-group">
                                <form:label path="completed"> Completed? </form:label>
                                
                                <form:checkbox class="form-check-input" path="completed"  />
                            </div>
                            <button class="btn btn-primary">Save</button>
                        </form:form>
                    </div>
                </div>
            </body>

            </html>