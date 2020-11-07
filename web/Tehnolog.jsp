<%-- 
    Document   : PageOfTechnologist
    Created on : 02.04.2016, 13:09:59
    Author     : Ковалев Алексей
--%>

<%@page import="folderClasses.WorkDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <%! String pgname = "Главная";%>
        <title><%= pgname%></title>

<link rel="stylesheet" type="text/css" href="styles/MainStyle.css">

      
    </head>
    <body>
    <div class="Divbg bg2" ></div>  
    
        <%            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
        %>
        <%@include file="HeadSapcon.jsp"%>
     <table align="center">
            
                <td> 
                <table align="center">
						<tr>
										<td colspan="2"> <%@include file="menuTehn.jsp"%>&nbsp;</td>
						</tr>
					
		</table>
		</td>
            
            <tr>
                <td align="center">
                    <%! String message = "";%>
                    <%
                        if (session.getAttribute("message") != null) {
                            message = (String) session.getAttribute("message");
                            session.removeAttribute("message");
                        } else {
                            message = "<br><br><br>Для продолжения работы выберите действие.";
                        }
                    %>
                    <h4 > <%= message%>  </h4>&nbsp;

                </td>
            </tr>
        </table>
    </body>
</html>
