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
                    <p style="text-align: center; margin-top: 0px;">Список материалов</p>
                </td>
            </tr>
            <tr>
                <td>

                    <table align="center" border="1" class="table_blur">
                        <thead>
                            <tr align="center">
                                <th>Номер</th>
                                <th>Название</th>
                                <th>Кол-во</th>
                                <th>Размерность</th>
                                <th>Поставщик</th>
                                <th>Город поставщика</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${materials}" var="material">
                                <tr class='clickable-row'>
                                    <td align="center" class="id"><c:out value="${material.id_material}" /></td>
                                    <td style="width:150px;"><c:out value="${material.name}" /></td>
                                    <td align="right"><c:out value="${material.number}" /></td>
                                    <td><c:out value="${material.ed_name}" /></td>
                                    <td style="width:150px;"><c:out value="${material.s_name}" /></td>
                                    <td style="width:150px;"><c:out value="${material.s_city}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
