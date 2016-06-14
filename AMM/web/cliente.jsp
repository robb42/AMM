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
        <link href="style.css" rel="stylesheet" type="text/css" media="screen">
        <script type="text/javascript" src="js/jquery-2.2.4.min.js"></script>
        <script type="text/javascript" src="js/filter.js"></script>
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
                    <c:when test="${error == true}">
                        <div class="error">
                            <p>
                                Loggati come cliente per vedere questa pagina
                            </p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <h2 class="description-title">Prodotti disponibili</h2>
                        <div class="form">
                            <ul>
                                <li>
                                    <input type="text" id="ricerca" placeholder="Cerca" class="form-element2 form-right"/>
                                </li>
                            </ul>
                        </div>
                        <table class="table" id="listaProdotti">
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
                                <th class="table-cart">
                                </th>
                            </tr>
                            <c:forEach var="prodotto" items="${listaProdotti}">
                                <tr>
                                    <td class="table-logo-box">
                                        <div class="table-logo-bg" style="background-image:url('${prodotto.getUrlImmagine()}')"></div>
                                    </td>
                                    <td>
                                        ${prodotto.getNome()}
                                    </td>
                                    <td>
                                        ${prodotto.getQuantita()}
                                    </td>
                                    <td>
                                        ${prodotto.getPrezzo()}
                                    </td>
                                    <td>
                                        <a href="Cliente?GiocoID=${prodotto.getId()}"><i class="fa shopping-cart"></i></a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
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
