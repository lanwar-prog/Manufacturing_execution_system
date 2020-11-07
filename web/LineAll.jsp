<%-- 
    Document   : Line
    Created on : 07.04.2016, 16:00:33
    Author     : Ковалев Алексей
--%>


<%@page import="ShowPlan.Model"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <%! String pgname = "Просмотр всех линий";%>
        <title><%= pgname%></title>
<link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
    </head>
    <body >  
          <div class="Divbg bg2 " ></div>
    <div class="DivbgInner" >
    
        <%            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
        %>
 <%@include file="HeadSapcon.jsp"%>
 <table valign="top" align="center">
           <tr valign="top">
                <td colspan="2">
                    
                 <%@include file="menuDuty.jsp"%>   
                </td>
            </tr>
            
        </table>
         <table align="center">
            <tr>
                <td align="center">
                    <%
                        Model model = new Model();
                        out.println(model.showLineAll());
                    %>   
                </td>
            </tr>
        </table>
 </div>
    </body>
</html>

