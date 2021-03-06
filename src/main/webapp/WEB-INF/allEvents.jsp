<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <!DOCTYPE html>
            <html>

            <head>
                <link rel="icon" type="image/png" href="/images/HomeAid-Favicon.png" />
                <meta charset="ISO-8859-1">
                <title>HomeAid | All Events</title>
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
                    <h1>All Public Events</h1>
                    <div class="col">
                        <ol>
                            <c:forEach items="${allEvents}" var="e">
                                <!-- Can add an option to view later -->
                                <c:if test="${e.host.household.name.equals(currentUser.household.name)}">
                                    <li><a href="/events/edit/${e.id}">${e.title}:  <fmt:formatDate value="${e.start}" pattern="MMMMM dd, yyyy hh:mma" /> - <fmt:formatDate value="${e.end}" pattern="MMMMM dd, yyyy hh:mma" /> </a></li>
                                    <ul>
                                        <c:forEach items="${e.attendees}" var="m">
                                            <li>
                                                ${m.fullName}
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                            </c:forEach>
                        </ol>
                    </div>
                </div>
            </body>

            </html>