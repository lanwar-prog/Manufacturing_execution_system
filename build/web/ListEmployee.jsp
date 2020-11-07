<%-- 
    Document   : ListEmployee
    Created on : 12.02.2017, 12:36:52
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
    <body >  
        <table align="center">
            <tr>
                <td><p style="text-align: center; margin-top: 0px;">Список пользователей</p></td>
            </tr>
  
            <tr>
                <td>
                     <table align="center" border="1" class="table_blur">
                         <thead>
                            <tr align="center">
                            <th>Номер</th>
                            <th>Имя</th>
                            <th>Фамилия</th>
                            <th>Логин</th>
                            <th>Отдел</th>
                            <th>Телефон</th>
                            <th>Дата начала работы</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${employees}" var="employee">
                                <tr class='clickable-row'>
                                    <td style="width:20px;" align="center" class="id"><c:out value="${employee.id_employee}" /></td>
                                    <td style="width:80px;"><c:out value="${employee.name}" /></td>
                                    <td style="width:80px;"><c:out value="${employee.surname}" /></td>
                                    <td style="width:80px;"><c:out value="${employee.login}" /></td>
                                    <td style="width:150px;"><c:out value="${employee.role}${employee.role == 1 ? ' - Начальник производства' : employee.role == 2 ? ' - Технолог' : ' - Диспетчер'}" /></td>
                                    <td style="width:120px;"><c:out value="${employee.phone}" /></td>
                                    <td align="center" style="width:100px;"><fmt:formatDate pattern="dd.MM.yyyy г." value="${employee.datehider}"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>		
        </table>
    </body>
</html>
