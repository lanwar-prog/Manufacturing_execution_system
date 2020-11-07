<%-- 
    Document   : AddDetail
    Created on : 02.04.2016, 13:15:00
    Author     : Ковалев Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
       
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <script src="styles/jquery-1.7.2.js"></script>
        <script src="styles/ctrl.js"></script>
        
        <%! String pgname = "Детали";%>
        <title><%= pgname%></title>

         <link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
         <link rel="stylesheet" type="text/css" href="styles/table.css">
        
    </head>
    <body >
    <div class="Divbg bg2 " ></div>
        <%            
            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
        %>
 <%@include file="HeadSapcon.jsp"%>
        <table align="center">
            <tr>
               
            </tr>
            <tr valign="top">
                <td colspan="2">
                    <%@include file="menuTehn.jsp"%>
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
                    <div id="formid" >
                    </div>
                </td>
            </tr>
        </table>
        <script>
            $(document).ready(function() {
                getData(0,"${pageContext.request.contextPath}/DetailContr","list");
                getData(1,"${pageContext.request.contextPath}/DetailContr","clear");
            });
        </script>
    </body>
</html>
