<%-- 
    Document   : listaProdotti2Json
    Created on : 14-giu-2016, 16.54.38
    Author     : rober
--%>

<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<json:array>
    <c:forEach var="prodotto" items="${listaProdotti}">
        <json:object>
            <json:property name="id" value="${prodotto.id}"/>
            <json:property name="venditoreId" value="${prodotto.venditoreId}"/>
            <json:property name="nome" value="${prodotto.nome}"/>
            <json:property name="urlImmagine" value="${prodotto.urlImmagine}"/>
            <json:property name="descrizione" value="${prodotto.descrizione}"/>
            <json:property name="prezzo" value="${prodotto.prezzo}"/>
        </json:object>
    </c:forEach>
</json:array>