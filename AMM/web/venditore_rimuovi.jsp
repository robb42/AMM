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
            <div id="sidebar1"></div>
            
            <!--  sidebar 2 -->
            <div id="sidebar2">
                <jsp:include page="Resources/sidebar2.jsp"/>
            </div>
            
            <!-- Content -->
            <div id="content">
                <c:choose>
                    <c:when test="${rimosso == true}">
                        <div class="error">
                            <p>
                                Prodotto rimosso
                            </p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="table">
                            <tr class="table-head">
                                <th colspan="2">
                                    Nome
                                </th>
                                <th>
                                    Disponibili
                                </th>
                                <th>
                                    Prezzo
                                </th>
                            </tr>

                            <tr>
                                <th class="table-logo-box">
                                    <div class="table-logo-bg" style="background-image:url('${prodotto.getUrlImmagine()}')"></div>
                                </th>
                                <th>
                                    ${prodotto.getNome()}
                                </th>
                                <th>
                                    ${prodotto.getQuantita()}
                                </th>
                                <th>
                                    ${prodotto.getPrezzo()}
                                </th>
                            </tr>

                        </table>
                        <p>${prodotto.getDescrizione()}</p>
                        <form method="post" action="Venditore?RimuoviGiocoID=${prodotto.getId()}" class="form">
                            <ul>
                                <li>
                                    <input type="submit" name="Rimuovi" value="Rimuovi" class="form-submit form-right">
                                </li>
                            </ul>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
            
            <div style="clear: both; width: 0px; height: 0px;"></div>
            <!--  footer -->
            <div id="footer">
                <jsp:include page="Resources/footer.jsp"/>
            </div>
            
        </div>
        
    </body>
</html>
