<%-- 
    Document   : login
    Created on : 30-apr-2016, 2.23.14
    Author     : rober
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Vapore</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="Roberto Cocco">
        <meta name="keywords" content="Vapore, Giochi, Software">
        <link href="style.css" rel="stylesheet" type="text/css" media="screen" >
    </head>
    <body>
        
        <!-- NavBar -->
        <div id="header">
            <jsp:include page="Resources/navbar.jsp"/>
        </div>
        
        <div id="page">
            <!--  sidebar 1 -->
            <div id="sidebar1"></div>
            
            <!--  sidebar 2 -->
            <div id="sidebar2"></div>
            
            <!-- Content -->
            <div id="content">
                <c:choose>
                    <c:when test="${userFound == 'no'}">
                        <div class="error">
                            <p>
                                Utente non trovato
                            </p>
                        </div>
                    </c:when>
                </c:choose>
                <form method="post" action="Login" class="form">
                    <input type="hidden" name="cmd" value="login">
                    <ul>
                        <li>
                            <input type="text" name="Username" id="username" placeholder="Username" class="form-element form-left">
                            <input type="password" name="Password" id="password" placeholder="Password" class="form-element">
                            <input type="submit" name="Submit" value="Login" class="form-submit form-right">
                        </li>
                    </ul>
                </form>
            </div>
            
            <div style="clear: both; width: 0px; height: 0px;"></div>
            <!--  footer -->
            <div id="footer">
                <jsp:include page="Resources/footer.jsp"/>
            </div>
        
        </div>
        
    </body>
</html>
