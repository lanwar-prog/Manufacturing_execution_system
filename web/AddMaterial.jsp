
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
        
        <%! String pgname = "Материалы";%>
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
                               value="<c:out value="${material.id_material}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Название материала<br>
                        <input type="text" id="name" name="name" style="width:270px" 
                               value="<c:out value="${material.name}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Стандартный размер партии<br>
                        <input type="text" id="number" name="number" style="width:270px"
                               value="<c:out value="${material.number}"/>"
                        />
                    </td>
                </tr>
                <tr>
                    <td>Размерность<br>
                        <select id="ed_id" name="ed_id" style="width:270px">
                            <option value="" ${material.ed_id == null ? 'selected' : ''}></option>
                            <c:forEach items="${eds}" var="ed">
                                <option value="${ed.id_ed}" ${material.ed_id == ed.id_ed ? 'selected' : ''}>${ed.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Поставщик<br>
                        <select id="supplier_id" name="supplier_id" style="width:270px">
                            <option value="" ${material.supplier_id == null ? 'selected' : ''}></option>
                            <c:forEach items="${suppliers}" var="supplier">
                                <option value="${supplier.id_supplier}" ${material.supplier_id == supplier.id_supplier ? 'selected' : ''}>${supplier.name}${supplier.city != '' ? ' г. ' : ''}${supplier.city}</option>
                            </c:forEach>
                        </select>
                </tr>
                <tr align="right">
                    <td>
                        <input type="submit" value="Записать" onclick="saveData('${pageContext.request.contextPath}/MaterialsContr');">
                        <input id="del_btn" type="submit" value="Удалить" onclick="deleteData('${pageContext.request.contextPath}/MaterialsContr', ${material.id_material});">
                        <input id="add_btn" type="submit" value="Очистить" onclick="getData(1,'${pageContext.request.contextPath}/MaterialsContr','clear');">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
