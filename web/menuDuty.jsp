<%-- 
    Document   : menuDuty
    Created on : 23.04.2016, 10:57:47
    Author     : Ковалев Алексей
--%>

<%@page import="folderClasses.WorkDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <link rel="stylesheet" type="text/css" href="styles/menubar.css">
   
    </head>
    <body>
<div >

        <%! String nameOnMenuHead2;%>
        <%! WorkDB database2 = new WorkDB();%>
        <%! int rol2= 0;%>
        <%! String profil2;%>
       
        <!--%
            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
    	%--> 
        
        <%
            String login = (String) session.getAttribute("login");
            nameOnMenuHead2 = database2.getFIO(login);
            String log = (String) session.getAttribute("login");
             rol2 = database2.getRole(log);
             if (rol2 == 0) {
                 profil2 = "Не определен";
            	}else if (rol2 == 1) {
                    profil2 = "Начальник производства";
                } else if (rol2 == 2) {
                    profil2 = "Технолог цеха";
                    } else if (rol2 == 3) {
                    profil2 = "Диспетчер цеха";
                     
                    
                        }
                    
        %>
 <table align="center" style="width: 600px">
						<tr>
						<td><h4><%= pgname%> </h4> <br></td>				
						<td valign="top" style="text-align: right;"><h5>Профиль: <%= profil2%><br> <%= nameOnMenuHead2%></h5></td>
						</tr>
					
		</table>
		</div>
<div style="min-width:590px;">
        
         
        <ul id="menu-bar" style="width:590px;">
 <li class="active" ><a href="EnterPage.jsp"> На главную </a></li>
 <li style="left: 22px; top: 0px"><a href="CurrentPlan.jsp">Текуший план</a></li>
 <li style="left: 52px; top: 1px"><a href="LineAll.jsp" >План линий</a></li>
   <li   class="active" style="left: 83px; top: 0px"><a  href="#" onclick="Go()"> Выход</a>   </li>
  
<form name='myfrm' method='POST' action='Exit'>
<input type='hidden' name='code'>
</form>

  </ul>
  
  </div>
   <script>
function Go(val) {
   var frm=document.myfrm;
   frm.code.value = val;
   frm.submit();
}
</script>

    </body>
</html>
