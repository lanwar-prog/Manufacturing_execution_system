
<%-- 
    Document   : AddEmployee
    Created on : 04.02.2017, 15:55:35
    Author     : Ковалев Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        
        <%! String pgname = "Операции";%>
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
                               value="<c:out value="${operation.id_operation}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Наименование операции<br>
                        <input type="text" id="name" name="name" style="width:270px" 
                               value="<c:out value="${operation.name}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Время работы, сек<br>
                        <input type="text" id="time" name="time" style="width:270px"
                               value="<c:out value="${operation.time}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Время подготовки, сек<br>
                        <input type="text" id="timesetup" name="timesetup" style="width:270px"
                               value="<c:out value="${operation.timesetup}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Время завершения, сек<br>
                        <input type="text" id="timecompletion" name="timecompletion" style="width:270px"
                               value="<c:out value="${operation.timecompletion}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Тип операции<br>
                        <select id="id_tas" name="id_tas" style="width:270px">
                            <option value="" ${operation.id_tas == null ? 'selected' : ''}></option>
                            <c:forEach items="${tasks}" var="task">
                                <option value="${task.id_task}" ${operation.id_tas == task.id_task ? 'selected' : ''}>${task.id_task} - ${task.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr align="right">
                    <td>
                        <input type="submit" value="Записать" onclick="saveData('${pageContext.request.contextPath}/OperationContr');">
                        <input id="del_btn" type="submit" value="Удалить" onclick="deleteData('${pageContext.request.contextPath}/OperationContr', ${operation.id_operation});">
                        <input id="add_btn" type="submit" value="Очистить" onclick="getData(1,'${pageContext.request.contextPath}/OperationContr','clear');">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
