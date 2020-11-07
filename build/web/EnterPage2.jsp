<%@page import="folderClasses.Auth"%>
<%@page import="folderClasses.WorkDB"%>
  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% //@page errorPage="Error.jsp" %>
<!DOCTYPE html>
<html> 
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Управление планирование производства - Вход.</title>
        <link rel="shortcut icon" href="" type="image/x-icon">
         
		<!--link rel="stylesheet" type="text/css" href="styles/animate.css-3.5.1/animate.min.css"-->
       	<link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
       	
       	<link rel="stylesheet" type="text/css" href="styles/animate.css-3.5.1/animate.min.css">
       	
       <!-- <style>
             input[type=submit] {
                border-radius: 5px;
                border: 0;
                height:25px;
                font-family: Tahoma;
                background: #f4f4f4;
                background: -moz-linear-gradient(top, #f4f4f4 1%, #ededed 100%);
                background: -webkit-gradient(linear, left top, left bottom, color-stop(1%, #f4f4f4), color-stop(100%, #ededed));
                background: -webkit-linear-gradient(top, #f4f4f4 1%, #ededed 100%);
                background: -o-linear-gradient(top, #f4f4f4 1%, #ededed 100%);
                background: -ms-linear-gradient(top, #f4f4f4 1%, #ededed 100%);
                background: linear-gradient(to bottom, #f4f4f4 1%, #ededed 100%);
                filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#f4f4f4', endColorstr='#ededed', GradientType=0);                
            }
        </style> -->
    </head>
    <body  >
        <%@page language="java" %>
        <%!
            public WorkDB database = new WorkDB();
            
        %>

        
               
        <table style="width: 100%; height: 134px;" id="tableHead" >
			<tr>
				<td>
				<img alt="" src="WebContent/sapkon1.gif" width="320" height="120"></td>
				<td class="animated zoomIn" style="padding: 20px; ">
				Программа по оперативному планированию производства <br>
				АО &quot;НЕФТЕМАШ&quot;-САПКОН&nbsp;</td>
				<td>
				<img alt="" src="WebContent/cert.gif" width="183" height="91" style="float: right">&nbsp;</td>
			</tr>
		</table>
       <div style="height: 91px">
      
       </div>
        
            
        <%
            
            if (session.getAttribute("login") != null) {
                String login = (String) session.getAttribute("login");
                int role = database.getRole(login);
    
                if (role == 1) {
                    response.sendRedirect("PageOfHead.jsp");
                }
                if (role == 2) {
                    response.sendRedirect("Tehnolog.jsp");
                }
                if (role == 3) {
                    response.sendRedirect("PageOfDuty.jsp");
                }
            }
        %>
        <form method="post" action="Enter">
            <table align="center" style="width: 307px; height: 262px;" >
                <tr align="left">
                    <td  style="width: 394px"> <b><br>
					Вход в систему</b> </td>
                </tr>
                <tr>
                    <td style="width: 394px"> <br>Логин </td>
                </tr>
                <tr>
                    <td style="width: 394px"> <input type="text" name="name" style="font-size:18pt;width:270px"/> </td>
                </tr>
                <tr>
                    <td style="width: 394px; height: 25px"> Пароль</td>
                </tr>
                <tr>
                    <td style="width: 394px"> <input type="password" name="password" style="font-size:18pt;width:270px"/> </td>
                </tr>
                <tr>
                    <td align="center" style="width: 394px"> <input type="submit" value="Подтвердить" style="width:100Px;height:30Px"/> </td>
                </tr>
            </table>
        </form> 
         
    	<div style="font-family: Arial, Helvetica, sans-serif; font-size: xx-small; font-style: italic; color: #009999; position: absolute; z-index: auto; right: 20px; bottom: 10px;  ">
    	        <p align="right"><%= database.TestConnectDB(request, response) %></p> 
		</div>
         
    </body>
        
</html>
