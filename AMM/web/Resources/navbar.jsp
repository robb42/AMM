<%-- 
    Document   : Navbar
    Created on : 30-apr-2016, 15.03.43
    Author     : rober
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav>
    <div class="navbar">
        <ul>
            <li id="descrizione" class="title"><a href="descrizione.jsp">Descrizione</a></li>
            <li id="cliente" class="title"><a href="Cliente">Cliente</a></li>
            <li id="venditore" class="title"><a href="Venditore">Venditore</a></li>
            <c:choose>
                <c:when test="${loggedIn == true}">
                    <li id="login" class="title login"><a href="Login">Logout</a></li>
                </c:when>
                <c:otherwise>
                    <li id="login" class="title login"><a href="Login">Login</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>
