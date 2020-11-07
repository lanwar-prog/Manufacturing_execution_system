<%-- 
    Document   : ListMaterials
    Created on : 12.05.2016, 16:42:29
    Author     : Ковалев Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table align="center">
            <tr>
                <td>
                    <p style="text-align: center; margin-top: 0px;">Список операций</p>
                </td>
            </tr>
            <tr>
                <td>
                    <table align="center" border="1" class="table_blur">
                        <thead>
                            <tr align="center">
                            <th>Номер</th>
                            <th>Наименование<br>операции</th>
                            <th>Время<br>работы,<br>сек</th>
                            <th>Время<br>подготовки,<br>сек</th>
                            <th>Время<br>завершения,<br>сек</th>
                            <th>Тип операции</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${operations}" var="operation">
                                <tr class='clickable-row'>
                                    <td align="center" class="id"><c:out value="${operation.id_operation}" /></td>
                                    <td style="width:150px;"><c:out value="${operation.name}" /></td>
                                    <td align="right"><c:out value="${operation.time}" /></td>
                                    <td align="right"><c:out value="${operation.timesetup}" /></td>
                                    <td align="right"><c:out value="${operation.timecompletion}" /></td>
                                    <td style="width:200px;"><c:out value="${operation.id_tas} - ${operation.taskname}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
