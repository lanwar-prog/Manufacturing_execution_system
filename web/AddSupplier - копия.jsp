<%-- 
    Document   : AddSupplier
    Created on : 31.03.2016, 16:57:15
    Author     : Ковалев Алексей
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                 <%! String pgname = "Поставщики";%>
        <title><%= pgname%></title>

         <link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
           <link rel="stylesheet" type="text/css" href="styles/table.css">
    </head>
    <body bgcolor="DCDCDA">
        <div class="Divbg bg2" ></div>  
           
          <div class="DivbgInner">

        <%            if (session.getAttribute("login") == null) {
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
            <tr valign="top">
                <td align="center" style="width: 63px">
                    
                   <%@include file="Suppliers.jsp"%>&nbsp;
                    </td>
                <td>
                    <form action="AddSuplier" method="post">
                        <table align="right" class="tableinput2">
                            <tr>
                                <td>Название организации<br>
                                    <input type="text" name="name" style="font-size:15px;width:270px"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Сокращенное название<br>
                                    <input type="text" name="minname" style="font-size:15px;width:270px"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Город<br>
                                    <input type="text" name="city" style="font-size:15px;width:270px"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Улица<br>
                                    <input type="text" name="street" style="font-size:15px;width:270px"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Дом<br>
                                    <input type="text" name="house" style="font-size:15px;width:270px"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Телефон<br>
                                    <input type="text" name="phone" style="font-size:15px;width:270px"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Эллектронная почта<br>
                                    <input type="text" name="email" style="font-size:15px;width:270px"/>
                                </td>
                            </tr>
                            <tr align="right">
                                <td><input type="submit" value="Добавить" style="width:100Px;height:30Px"/></td>
                            </tr>
                        </table>
                    </form>                    
                </td>
            </tr>
        </table>  
        </div>      
    </body>
</html>
