<%-- 
    Document   : ListOperationToDetail
    Created on : 12.02.2017, 13:41:51
    Author     : Ковалев Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail</title>
      
      <link rel="stylesheet" type="text/css" href="styles/table.css">
 	<link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
    </head>
    <body>
        <table>
            <tr>
                <td><p style="text-align: center; margin-top: 0px;">Список операций для детали "${detail.id_detail} - ${detail.name}"</p></td>
            </tr>
            <tr>
                <td>
                    <table border="1" class="table_blur" style="width: 950px; ">
                        <thead>
                            <tr align="center">
                                <th style="display:none">Id</th>
                                <th>Наименование<br>операции</th>
                                <th style="width: 20px">Время<br>работы, сек</th>
                                <th style="width: 20px">Время<br>подготовки, сек</th>
                                <th style="width: 20px">Время<br>завершения, сек</th>
                                <th >Тип операции</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty operationToDetails}">
                                    <tr>
                                        <td colspan='5' align='center'>
                                            Операции отсутствуют
                                        </td>
                                    </tr>
                                </c:when>    
                                <c:otherwise>
                                    <c:forEach items="${operationToDetails}" var="operationToDetail">
                                        <tr class='clickable-row'>
                                            <td class="id" style="display:none"><c:out value="${operationToDetail.id_operdet}" /></td>
                                            <td style="width:200px;"><c:out value="${operationToDetail.id_operation} - ${operationToDetail.name}" /></td>
                                            <td align="right"><c:out value="${operationToDetail.time}" /></td>
                                            <td align="right"><c:out value="${operationToDetail.timesetup}" /></td>
                                            <td align="right"><c:out value="${operationToDetail.timecompletion}" /></td>
                                            <td style="width:200px;"><c:out value="${operationToDetail.id_tas} - ${operationToDetail.taskname}" /></td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>