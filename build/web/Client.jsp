<%-- 
    Document   : AddClient
    Created on : 04.04.2016, 16:10:01
    Author     : Ковалев Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="styles/jquery.min.1.4.2.js"></script>
        <script src="styles/ctrl.js"></script>
        
                 <%! String pgname = "Клиенты";%>
        <title><%= pgname%></title>
 
        <link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
        <link rel="stylesheet" type="text/css" href="styles/table.css">
    </head>
    <body bgcolor="DCDCDA">
        <div class="Divbg bg2" ></div>  

        <%            
            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
        %>
        
        <%@include file="HeadSapcon.jsp"%>
        <table align="center">
           
            <tr>
                <td valign="top" colspan="2">
                    <table align="center">
                        <tr>
                            <td colspan="2"> <%@include file="menuHead.jsp"%>&nbsp;</td>
                        </tr>
                    </table>
                </td>
            </tr>
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
                getData(0,"${pageContext.request.contextPath}/ClientContr","list");
                getData(1,"${pageContext.request.contextPath}/ClientContr","clear");
            });
        </script>
    </body>
</html>