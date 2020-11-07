
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
        <form  id="form-submit" method="post" action="javascript:void(null);" enctype="multipart/form-data">
            <table class="tableinput2" align="right">
                <tr>
                    <td>Номер<br>
                        <input type="text" id="id" class="input-disabled" name="id" readonly="readonly" style="width:270px" 
                               value="<c:out value="${detail.id_detail}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Название<br>
                        <input type="text" id="name" name="name" style="width:270px" 
                               value="<c:out value="${detail.name}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Описание<br>
                        <textarea id="description" name="description" cols="20" rows="6" style="width:270px">${detail.description}</textarea>
                    </td>
                </tr>
                <tr>
                    <td>Дата создания<br>
                        <fmt:formatDate value="${detail.created}" var="formattedDate" type="date" pattern="yyyy-MM-dd" />
                        <input type="date" id="created" class="datepicker" placeholder="1" name="created" style="width:270px" 
                               value="${formattedDate}"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Изображение<br>
                        <input type="file" accept="image/*" id="image" name="image" style="width:270px"/><br>
                        <c:if test="${detail.image != null}">
                            <button id="del_img" name="del_img" style="margin-top: 5px" onclick="deleteImage('${pageContext.request.contextPath}/DetailContr', ${detail.id_detail});">Удалить изображение</button>
                        </c:if>
                    </td>
                </tr>
                <tr align="right">
                    <td>
                        <input type="submit" value="Записать" onclick="saveData('${pageContext.request.contextPath}/DetailContr');">
                        <input id="del_btn" type="submit" value="Удалить" onclick="deleteData('${pageContext.request.contextPath}/DetailContr', ${detail.id_detail});">
                        <input id="add_btn" type="submit" value="Очистить" onclick="getData(1,'${pageContext.request.contextPath}/DetailContr','clear');">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
