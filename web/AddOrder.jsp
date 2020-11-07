
<%-- 
    Document   : AddEmployee
    Created on : 04.02.2017, 15:55:35
    Author     : Ковалев Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        
        <%! String pgname = "Заказы";%>
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
                               value="<c:out value="${order.id_order}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Город<br>
                        <select id="city" name="city" style="width:270px">
                            <option value="" ${order.clientcity == null ? 'selected' : ''}></option>
                            <c:forEach items="${citys}" var="city">
                                <option value="${city}" ${fn:toUpperCase(order.clientcity) == fn:toUpperCase(city) ? 'selected' : ''}>${city}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Организация<br>
                        <select id="id_client" name="id_client" style="width:270px">
                            <option value="" data-city="" ${order.id_client == null ? 'selected' : ''}></option>
                            <c:forEach items="${clients}" var="client">
                                <option value="${client.id_client}" data-city="${client.city}" ${order.id_client == client.id_client ? 'selected' : ''}>${client.nameorganization}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Дата заказа<br>
                        <fmt:formatDate value="${order.date}" var="formattedDate" type="date" pattern="yyyy-MM-dd" />
                        <input type="date" id="date" class="datepicker" placeholder="1" name="date" style="width:270px" 
                               value="${formattedDate}"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Состояние<br>
                        <select id="state" name="state" style="width:270px">
                            <option value="" ${order.state == null ? 'selected' : ''}></option>
                            <option value="0" ${order.state == 0 ? 'selected' : ''}>В процессе выполнения</option>
                            <option value="1" ${order.state == 1 ? 'selected' : ''}>Выполнен</option>
                            <option value="2" ${order.state == 2 ? 'selected' : ''}>Отложен</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Деталь<br>
                        <select id="id_detail" name="id_detail" style="width:270px">
                            <option value="" ${order.id_detail == null ? 'selected' : ''}></option>
                            <c:forEach items="${details}" var="detail">
                                <option value="${detail.id_detail}" ${order.id_detail == detail.id_detail ? 'selected' : ''}>${detail.id_detail} - ${detail.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Количество изделий<br>
                        <input type="text" id="numberdetails" name="numberdetails" style="width:270px" 
                               value="<c:out value="${order.numberdetails}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Дата выполнения<br>
                        <fmt:formatDate value="${order.dateexecution}" var="formattedDate" type="date" pattern="yyyy-MM-dd" />
                        <input type="date" id="dateexecution" class="datepicker" placeholder="1" name="dateexecution" style="width:270px" 
                               value="${formattedDate}"
                        />
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="checkbox" id="urgency" name="urgency"
                               value="<c:out value="${order.urgency}"/>" />
                        <input type="hidden" name="urgency" value="0" />
                        Срочность
                    </td>
                </tr>
                <tr align="right">
                    <td>
                        <input type="submit" value="Записать" onclick="saveData('${pageContext.request.contextPath}/OrderContr');">
                        <input id="del_btn" type="submit" value="Удалить" onclick="deleteData('${pageContext.request.contextPath}/OrderContr', ${order.id_order});">
                        <input id="add_btn" type="submit" value="Очистить" onclick="getData(1,'${pageContext.request.contextPath}/OrderContr','clear');">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
