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
                    <p style="text-align: center; margin-top: 0px;">Список клиентов</p>
                </td>
            </tr>
            <tr>
                <td>
                    <table align="center" border="1" class="table_blur">
                        <thead>
                            <tr align="center">
                            <th>Номер</th>
                            <th>Название<br>организации</th>
                            <th>Город</th>
                            <th>Улица</th>
                            <th>Дом</th>
                            <th>Телефон</th>
                            <th>Электронная<br>почта</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${clients}" var="client">
                                <tr class='clickable-row'>
                                    <td style="width:30px;" align="center" class="id"><c:out value="${client.id_client}" /></td>
                                    <td style="width:150px;"><c:out value="${client.nameorganization}" /></td>
                                    <td style="width:100px;"><c:out value="${client.city}" /></td>
                                    <td style="width:100px;"><c:out value="${client.street}" /></td>
                                    <td style="width:30px;"><c:out value="${client.numberhouse}" /></td>
                                    <td style="width:100px;"><c:out value="${client.telephone}" /></td>
                                    <td style="width:100px;"><c:out value="${client.email}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
