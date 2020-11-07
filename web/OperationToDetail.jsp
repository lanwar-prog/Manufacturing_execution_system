<%-- 
    Document   : Materials
    Created on : 17.03.2017, 9:49:01
    Author     : Ковалев Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="styles/jquery.min.1.4.2.js"></script>
        <script src="styles/ctrl.js"></script>
        <%! String pgname = "Операции для детали";%>
        <title><%= pgname%></title>

         <link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
         <link rel="stylesheet" type="text/css" href="styles/table.css">

    </head>
    <body style="background: rgba(0, 0, 0, 0)">
        <%! String id_detail = "";%>
        <%            
            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
            if (request.getParameter("id") != null) {
                id_detail = (String) request.getParameter("id");
            }//если детали не существует то на детали переход
            //
        %>
            <table align="center" style="width: 1000px">
            <!--<tr>
                <td colspan="2" align="center">
                    <div id="msg-box">
                        <div id="msg">
                        </div>
                    </div>
                </td>
            </tr>-->
            <tr valign="top">
                <td align="center">
                    <div id="listid">
                    </div>
                </td>
                <td>
                    <div id="formid">
                    </div>
                </td>
            </tr>
        </table>
        <script>
            $(document).ready(function() {
                getData(0,"${pageContext.request.contextPath}/OperationToDetailContr","list","","<%=id_detail%>");
                getData(1,"${pageContext.request.contextPath}/OperationToDetailContr","clear","","<%=id_detail%>");
            });
        </script>
    </body>
</html>
