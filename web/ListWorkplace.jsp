<%-- 
    Document   : Details
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
                <td><p style="text-align: center; margin-top: 0px;">Список рабочих центров</p></td>
            </tr>
  
            <tr>
                <td>
                     <table align="center" border="1" class="table_blur">
                         <thead>
                            <tr align="center">
                                <th>Номер</th>
                                <th>Название</th>
                                <th>Дата создания</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${workplaces}" var="workplace">
                                <tr class='clickable-row'>
                                    <td align="center" class="id"><c:out value="${workplace.id_workplace}" /></td>
                                    <td style="width:250px;"><c:out value="${workplace.name}" /></td>
                                    <td align="center" style="width:120px;"><fmt:formatDate pattern="dd.MM.yyyy г." value="${workplace.created}"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>		
        </table>
    </body>
</html>
