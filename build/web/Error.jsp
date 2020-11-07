<% //@  page isErrorPage="true"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ошибка</title>
         <link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
    </head>
    <body bgcolor="#DCDCDA">
            <div class="Divbg bg2" ></div>  
           
          <div class="DivbgInner centered "   >
          
    
        <table align="center" class="tableinput2" style="width:auto;" >
            <tr>
                <td rowspan="2">                    
                     <img src="WebContent/error.png" width="100" height="100">&nbsp; &nbsp;
					</td>
                <td>                    
                    <h2>В системе возникла ошибка.</h2>
                </td>
            </tr>
            <tr>
                <td>
                    <%@page language="java" %>
                    <%
                        String s = (String) session.getAttribute("stringState");
                    %>
                    Ошибка: <%= s%> <br> <br>
					Если вы не можете решить проблему самостоятельно, обратитесь к
					<span lang="ru">системному</span> администратору.
                    
                </td>
            </tr>
        </table>
                    </div> 
    </body>
</html>
