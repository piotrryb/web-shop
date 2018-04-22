<%@ page import="hibernate.shop.UserSessionHelper" %>
<%@ page import="hibernate.shop.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: piotr
  Date: 14.04.2018
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User userFromCookie = UserSessionHelper.getUserFromCookie(request.getCookies());
    if (userFromCookie != null) {
        pageContext.setAttribute("user", userFromCookie);
    }

    Object i = session.getAttribute("i");
    if (i == null) {
        session.setAttribute("i", 1);
    } else {
        session.setAttribute("i", (int) i + 1);
    }

    pageContext.setAttribute("i", session.getAttribute("i"));
%>
<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container">
        <a class="navbar-brand" href="/index.jsp">Super Jeff shop</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive"
                aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#">Counter: ${i}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>
                <c:if test="${user == null}">
                    <li class="nav-item">
                        <a class="nav-link" href="/login.jsp">Login</a>
                    </li>
                </c:if>
                <c:if test="${user != null}">
                    <li class="nav-item">
                        <a class="nav-link" href="/cart.jsp">Cart</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/orderHistory.jsp">Orders</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link">${user.firstName} ${user.lastName}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">Logout</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
