
<%-- 
    Document   : AddEmployee
    Created on : 04.02.2017, 15:55:35
    Author     : Ковалев Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        
        <%! String pgname = "Рабочие центры";%>
        <title><%= pgname%></title>
    </head>
    <body>
        <div id="msg-box">
            <div id="msg">
            </div>
        </div>
        <form  id="form-submit" method="post" action="javascript:void(null);">
            <table class="tableinput2" align="right">
                <tr>
                    <td>Номер<br>
                        <input type="text" id="id" class="input-disabled" name="id" readonly="readonly" style="width:270px" 
                               value="<c:out value="${workplace.id_workplace}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Название<br>
                        <input type="text" id="name" name="name" style="width:270px" 
                               value="<c:out value="${workplace.name}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Дата создания<br>
                        <fmt:formatDate value="${workplace.created}" var="formattedDate" type="date" pattern="yyyy-MM-dd" />
                        <input type="date" id="created" class="datepicker" placeholder="1" name="created" style="width:270px" 
                               value="${formattedDate}"
                        />
                    </td>
                </tr>
                <tr align="right">
                    <td>
                        <input type="submit" value="Записать" onclick="saveData('${pageContext.request.contextPath}/WorkplaceContr');">
                        <input id="del_btn" type="submit" value="Удалить" onclick="deleteData('${pageContext.request.contextPath}/WorkplaceContr', ${workplace.id_workplace});">
                        <input id="add_btn" type="submit" value="Очистить" onclick="getData(1,'${pageContext.request.contextPath}/WorkplaceContr','clear');">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
