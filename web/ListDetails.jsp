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
                <td><p style="text-align: center; margin-top: 0px;">Список деталей</p></td>
            </tr>
  
            <tr>
                <td>
                     <table align="center" border="1" class="table_blur">
                         <thead>
                            <tr align="center">
                                <th>Номер</th>
                                <th>Название</th>
                                <th>Описание</th>
                                <th>Дата создания</th>
                                <th>Изображение</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${details}" var="detail">
                                <tr class='clickable-row'>
                                    <td align="center" class="id"><a href="javascript:showModalWin('${pageContext.request.contextPath}/OperationToDetail.jsp', ${detail.id_detail});">${detail.id_detail}</a></td>
                                    <td style="width:150px;"><c:out value="${detail.name}" /></td>
                                    <td style="width:250px;"><c:out value="${detail.description}" /></td>
                                    <td align="center" style="width:120px;"><fmt:formatDate pattern="dd.MM.yyyy г." value="${detail.created}"/></td>
                                    <c:choose>
                                        <c:when test="${detail.image == null}">
                                            <td></td>
                                        </c:when>    
                                        <c:otherwise>
                                        <td align="center" style="padding: 6px"><a href="javascript:showModalWin('${pageContext.request.contextPath}/ViewImg', ${detail.id_detail}, true);"><img valign="middle" src="WebContent/img_icon.png" alt=""></a></td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>		
        </table>
    </body>
    
</html>
