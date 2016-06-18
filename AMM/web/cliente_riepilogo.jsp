<%-- 
    Document   : cliente
    Created on : 30-apr-2016, 2.20.46
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
            <div id="sidebar1">
                <jsp:include page="Resources/sidebar_account.jsp"/>
            </div>
            
            <!--  sidebar 2 -->
            <div id="sidebar2"></div>
            
            <!-- Content -->
            <div id="content">
                <c:choose>
                    <c:when test="${acquisto == true}">
                        <div class="error">
                            <p>
                                Acquisto effettuato con successo
                            </p>
                        </div>
                    </c:when>
                    <c:when test="${error == true}">
                        <div class="error">
                            <p>
                                Acquisto fallito, controlla di avere un saldo sufficiente
                            </p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <ul class="summary">
                            <li class="summary-bgbox">
                                <img src="${prodotto.getUrlImmagine()}" class="summary-bg"/>
                            </li>
                            <li class="summary-title">
                                <h2 class="titlebox">${prodotto.getNome()}</h2>
                            </li>
                            <li>
                                <div class="summary-description">${prodotto.getDescrizione()}</div>
                            </li>
                            <li class="summary-form">
                            <form method="post" action="Cliente?GiocoID=${prodotto.getId()}" class="form">
                                <div class="form-generic form-left">Disponibili: ${prodotto.getQuantita()}</div>
                                <input type="submit" name="Submit" value="Procedi - ${prodotto.getPrezzo()}€" class="summary-submit form-right">
                            </form>
                          </li>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
            
        </div>
            
        <div style="clear: both; width: 0px; height: 0px;"></div>
        <!--  footer -->
        <div id="footer">
            <jsp:include page="Resources/footer.jsp"/>
        </div>
        
    </body>
</html>
