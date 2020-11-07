<%@page import="folderClasses.Auth"%>
<%@page import="folderClasses.WorkDB"%>
<%@page import="java.util.List" %>  
<%--
<%@page import="java.io.IOException" %>
<%@page import="java.util.Properties" %>

<%@page import="org.jinterop.dcom.common.JIException" %>

<%@page import="com.ipc.oce.ApplicationDriver" %>
<%@page import="com.ipc.oce.ConfigurationConstants.*" %>
<%@page import="com.ipc.oce.OCApp" %>
<%@page import="com.ipc.oce.PropertiesReader" %>
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% //@page errorPage="Error.jsp" %>
<!DOCTYPE html>
<html> 
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Управление технической подготовкой производства - Вход.</title>
        <link rel="shortcut icon" href="" type="image/x-icon">
        <!--link rel="stylesheet" type="text/css" href="styles/animate.css-3.5.1/animate.min.css"-->
       	<link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
       	<link rel="stylesheet" type="text/css" href="styles/animate.css-3.5.1/animate.min.css">
    </head>
    <body  >
        
        <%@ page language="java"%>
        <%!
            public WorkDB database = new WorkDB();
            


        %>
            
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
        
                <div class="Divbg" ></div>  
           
          <div class="DivbgInner">
          
            <%@include file="HeadSapcon.jsp"%> 
            <div style="height: 91px"> </div>
        
        <form method="post" action="Enter">
            <table align="center" style="width: 307px; height: 262px;" class="tableinput" >
                <tr align="left">
                    <td  style="width: 394px"> <b><br>
					Вход в систему</b> </td>
                </tr>
                <tr>
                    <td style="width: 394px"> <br>Имя пользователя</td>
                </tr>
                <tr>
                    <td style="width: 394px"> 
					<input type="text" name="name" style="font-size:10pt;width:270px; height: 31px; padding-left: 5px;"/> </td>
                </tr>
                <tr>
                    <td style="width: 394px; height: 25px"> Пароль</td>
                </tr>
                <tr>
                    <td style="width: 394px"> 
					<input type="password" name="password" style="font-size:10pt;width:270px; height: 31px; padding-left: 5px;"/> </td>
                </tr>
                <tr>
                    <td align="center" style="width: 394px"> <input type="submit" value="Подтвердить" style="width:100Px;height:30Px"/> </td>
                </tr>
            </table>
        </form> 
         
    	
		
		
		</div>
         <div style="font-family: Arial, Helvetica, sans-serif; font-size: x-small; font-style: italic; font: normal normal bold xx-small Arial, Helvetica, sans-serif; color: #FFFFFF; position: absolute; z-index: auto; right: 20px; bottom: 10px; text-decoration: underline;">
    	        <p align="right"><%= database.TestConnectDB(request, response) %></p> 
		</div>
    </body>
        
</html>
