<%-- 
    Document   : CreatePlan
    Created on : 31.03.2016, 16:57:51
    Author     : Ковалев Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                 <%! String pgname = "Построить план";%>
        <title><%= pgname%></title>

                

        <link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
        <link rel="stylesheet" type="text/css" href="styles/table.css">
    </head>
    <body bgcolor="#DCDCDA">
        <div class="Divbg bg2" ></div>  
           
          <div class="DivbgInner">

        <%            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
        %>
        
        <%@include file="HeadSapcon.jsp"%>


        <table align="center">
            
                <td> 
                <table align="center">
						<tr>
										<td colspan="2"> <%@include file="menuHead.jsp"%>&nbsp;</td>
						</tr>
					
		</table>
		</td>
            
            <tr>
                <td>
                    <form method="post" action="CreatePlan">
                        <table align="center" class="tableinput2">
                            <tr>
                                <td>
                                    Дата начала<br>
                                    <input type="date" name="dateStart" style="font-size:15px;width:270px"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Время начала<br>
                                    <input type="time" name="timeStart" style="font-size:15px;width:270px"/>
                                </td>
                            </tr>
                            <tr align="right">
                                <td>
                                    <input type="submit" value="Построить план" style="width:120x;height:30Px">                                    
                                </td>
                            </tr>
                        </table>
                    </form>
                </td>
            </tr>
        </table>
        </div> 
    </body>
</html>
