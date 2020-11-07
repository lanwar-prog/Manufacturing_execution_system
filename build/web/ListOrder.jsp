<%-- 
    Document   : ListMaterials
    Created on : 12.05.2016, 16:42:29
    Author     : Ковалев Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
                </td>
            </tr>
            <tr>
                <td>
                    <table align="center" border="1" class="table_blur">
                        <thead>
                            <tr align="center">
                            <th>Номер</th>
                            <th>Клиент</th>
                            <th>Дата<br>заказа</th>
                            <th>Состояние</th>
                            <th>Деталь</th>
                            <th>Кол-во<br>изделий</th>
                            <th>Срок<br>выполнения</th>
                            <th>Срочность</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${orders}" var="order">
                                <tr class='clickable-row'>
                                    <td align="center" style="width:30px;" class="id"><c:out value="${order.id_order}" /></td>
                                    <td style="width:120px;"><c:out value="${order.clientname}" /></td>
                                    <td align="center" style="width:90px;"><fmt:formatDate pattern="dd.MM.yyyy г." value="${order.date}"/></td>
                                    <td style="width:80px;"><c:out value="${order.state == 0 ? 'В процессе выполнения' : order.state == 1 ? 'Выполнен' : order.state == 2 ? 'Отложен' : ''}" /></td>
                                    <td style="width:120px;"><c:out value="${order.id_detail} - ${order.detailname}" /></td>
                                    <td align="right" style="width:60px;"><c:out value="${order.numberdetails}" /></td>
                                    <td align="center" style="width:90px;"><fmt:formatDate pattern="dd.MM.yyyy г." value="${order.dateexecution}"/></td>
                                    <td style="width:30px;"><c:out value="${order.urgency == 0 ? 'Нет' : order.urgency == 1 ? 'Да' : ''}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
