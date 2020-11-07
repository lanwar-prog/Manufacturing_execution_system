<%-- 
    Document   : AddEmployee
    Created on : 04.04.2016, 15:55:35
    Author     : Ковалев Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    
        <meta http-equiv="content-type" content="text/html; charset=utf-8" />
        <script src="styles/jquery.min.1.4.2.js"></script>
        <script src="styles/ctrl.js"></script>
                 <%! String pgname = "Пользователи";%>
        <title><%= pgname%></title>

        <link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
        <link rel="stylesheet" type="text/css" href="styles/table.css">
    
    </head>
    <body bgcolor="DCDCDA">
   
	<div class="Divbg bg2"></div>  

        <%            
            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
        %>
        
        <%@include file="HeadSapcon.jsp"%>

        <table align="center" >
            
            <tr valign="top">
                <td colspan="2" >
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
                getData(0,"${pageContext.request.contextPath}/EmployeeContr","list");
                getData(1,"${pageContext.request.contextPath}/EmployeeContr","clear");
            });
        </script>
    </body>
</html>
