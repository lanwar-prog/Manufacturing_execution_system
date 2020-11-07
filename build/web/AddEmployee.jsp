
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
        
        <%! String pgname = "Пользователи";%>
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
                               value="<c:out value="${employee.id_employee}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Имя<br>
                        <input type="text" id="name" name="name" style="width:270px" 
                               value="<c:out value="${employee.name}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Фамилия<br>
                        <input type="text" id="surname" name="surname" style="width:270px" 
                               value="<c:out value="${employee.surname}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Логин<br>
                        <input type="text" id="login" name="login" style="width:270px" 
                               value="<c:out value="${employee.login}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Отдел<br>
                        <select id="role" name="role" style="width:270px">
                            <option value="" ${employee.role == null ? 'selected' : ''}></option>
                            <option value="1" ${employee.role == 1 ? 'selected' : ''}>1 - Начальник производства</option>
                            <option value="2" ${employee.role == 2 ? 'selected' : ''}>2 - Технолог</option>
                            <option value="3" ${employee.role == 3 ? 'selected' : ''}>3 - Диспетчер</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Пароль<br>
                        <input type="text" id="password" name="password" style="width:270px" 
                               value="<c:out value="${employee.password}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Телефон<br>
                        <input type="text" id="phone" name="phone" style="width:270px" 
                               value="<c:out value="${employee.phone}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Дата оформления<br>
                        <fmt:formatDate value="${employee.datehider}" var="formattedDate" type="date" pattern="yyyy-MM-dd" />
                        <input type="date" id="datehider" class="datepicker" placeholder="1" name="datehider" style="width:270px" 
                               value="${formattedDate}"
                        />
                    </td>
                </tr>
                <tr align="right">
                    <td>
                        <input type="submit" value="Записать" onclick="saveData('${pageContext.request.contextPath}/EmployeeContr');">
                        <input id="del_btn" type="submit" value="Удалить" onclick="deleteData('${pageContext.request.contextPath}/EmployeeContr', ${employee.id_employee});">
                        <input id="add_btn" type="submit" value="Очистить" onclick="getData(1,'${pageContext.request.contextPath}/EmployeeContr','clear');">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
