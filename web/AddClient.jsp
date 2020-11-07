
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
        
        <%! String pgname = "Клиенты";%>
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
                               value="<c:out value="${client.id_client}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Название организации<br>
                        <input type="text" id="nameorganization" name="nameorganization" style="width:270px" 
                               value="<c:out value="${client.nameorganization}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Сокращенное название<br>
                        <input type="text" id="minname" name="minname" style="width:270px" 
                               value="<c:out value="${client.minname}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Город<br>
                        <input type="text" id="city" name="city" style="width:270px" 
                               value="<c:out value="${client.city}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Улица<br>
                        <input type="text" id="street" name="street" style="width:270px" 
                               value="<c:out value="${client.street}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Дом<br>
                        <input type="text" id="numberhouse" name="numberhouse" style="width:270px" 
                               value="<c:out value="${client.numberhouse}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Телефон<br>
                        <input type="text" id="telephone" name="telephone" style="width:270px" 
                               value="<c:out value="${client.telephone}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Электронная почта<br>
                        <input type="text" id="email" name="email" style="width:270px" 
                               value="<c:out value="${client.email}"/>"
                        />
                    </td>
                </tr>
                <tr align="right">
                    <td>
                        <input type="submit" value="Записать" onclick="saveData('${pageContext.request.contextPath}/ClientContr');">
                        <input id="del_btn" type="submit" value="Удалить" onclick="deleteData('${pageContext.request.contextPath}/ClientContr', ${client.id_client});">
                        <input id="add_btn" type="submit" value="Очистить" onclick="getData(1,'${pageContext.request.contextPath}/ClientContr','clear');">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
