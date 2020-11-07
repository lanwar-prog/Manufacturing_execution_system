
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
        
        <%! String pgname = "Детали";%>
        <title><%= pgname%></title>
    </head>
    <body>
        <div id="msg-box">
            <div id="msg">
            </div>
        </div>
        <form  id="form-submit" method="post" action="javascript:void(null);">
            <table class="tableinput3" align="right">
                <tr>
                    <td>
                        <input type="text" id="id" class="input-disabled" name="id" readonly="readonly" style="width:270px; display:none;" 
                               value="<c:out value="${operationToDetail.id_operdet}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="text" id="id_detail" name="id_detail" style="display:none;" 
                               value="<c:out value="${operationToDetail.id_detail}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Наименование операции<br>
                        <select id="id_operation" name="id_operation" style="width:270px">
                            <option value="" ${operationToDetail.id_operation == null ? 'selected' : ''}></option>
                            <c:forEach items="${ops}" var="op">
                                <option value="${op.id_operation}" ${operationToDetail.id_operation == op.id_operation ? 'selected' : ''}>${op.id_operation}${op.name != '' ? ' - ' : ''}${op.name}</option>
                            </c:forEach>
                        </select>
                </tr>
                <tr align="right">
                    <td>
                        <input type="submit" value="Записать" onclick="saveData('${pageContext.request.contextPath}/OperationToDetailContr',${operationToDetail.id_detail});">
                        <input id="del_btn" type="submit" value="Удалить" onclick="deleteData('${pageContext.request.contextPath}/OperationToDetailContr', ${operationToDetail.id_operdet}, ${operationToDetail.id_detail});">
                        <input id="add_btn" type="submit" value="Очистить" onclick="getData(1,'${pageContext.request.contextPath}/OperationToDetailContr','clear','',${operationToDetail.id_detail});">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
