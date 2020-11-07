<%-- 
    Document   : AddPressform
    Created on : 02.04.2016, 13:15:49
    Author     : Ковалев Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%! String pgname = "Добавить прессформу!";%>
        <title><%= pgname%></title>

         <link rel="stylesheet" type="text/css" href="styles/MainStyle.css">

    </head>
    <body bgcolor="DCDCDA">
         <div class="Divbg bg2 " ></div>
    
    
        <%            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
        %>
 <%@include file="HeadSapcon.jsp"%>

        <table align="center">
            <tr>
            </tr>
            <tr>
                <td colspan="2">
                     <%@include file="menuTehn.jsp"%>
                </td>
            </tr>
            <tr valign="top">
                <td align="center">
                
                   <jsp:include page="Pressforms.jsp"/>
                   
                </td>
                <td>
                    <form method="post" action="Addpressform" >
                    <br><br>
                        <table align="right" class="tableinput2">
                            <tr>
                                <td>Название<br>
                                    <input type="text" name="name" style="width:270px"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Дата создания<br>
                                    <input type="date" name="date" style="width:270px"/>
                                </td>
                            </tr>
                            <tr align="right">
                                <td><input type="submit" value="Добавить"/></td>
                            </tr>
                        </table>
                    </form>
                </td>
            </tr>
        </table>
    </body>
</html>
