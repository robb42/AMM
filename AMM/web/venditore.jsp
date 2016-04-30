<%-- 
    Document   : venditore
    Created on : 30-apr-2016, 2.23.23
    Author     : rober
--%>

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
                <form method="post" class="form">
                    <ul>
                        <li>
                            <input type="text" name="NomeProdotto" id="nome" placeholder="Nome Prodotto" class="form-element2 form-left">
                            <input type="text" name="URLImmagine" id="url" placeholder="URL immagine" class="form-element2 form-right">
                        </li>
                        <li>
                            <textarea rows="3" cols="25" name="Descrizione" id="description" placeholder="Descrizione" class="form-textarea"></textarea>
                        </li>
                        <li>
                            <input type="number" name="Quantita" id="quantita" placeholder="Quantità" class="form-element form-left">
                            <input type="number" step="0.01" name="Prezzo" id="prezzo" placeholder="Prezzo" class="form-element">
                            <input type="submit" class="form-submit form-right">
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
