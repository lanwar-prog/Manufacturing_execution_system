<%@page import="folderClasses.WorkDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
		<!--link rel="stylesheet" type="text/css" href="styles/MainStyle.css"-->
     
        
        <link rel="stylesheet" type="text/css" href="styles/menubar.css">
    </head>
    <body>
<div >
		
        <%! String nameOnMenuHead;%>
        <%! WorkDB database = new WorkDB();%>
        <%! int rol= 0;%>
        <%! String profil;%>
         <!--%            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
        %-->

        
        <%
            String login = (String) session.getAttribute("login");
            nameOnMenuHead = database.getFIO(login);
            String log = (String) session.getAttribute("login");
             rol = database.getRole(log);
             if (rol == 0) {
                 profil = "Не определен";
            	}else if (rol == 1) {
                    profil = "Начальник производства";
                } else if (rol == 2) {
                    profil = "Технолог цеха";
                    } else if (rol == 3) {
                    profil = "Диспетчер цеха";
                     
                    
                        }
                    
        %>
        
   
		
		  <table align="center" style="width: 1065px;">
						<tr>
						<td ><h4><%= pgname%> </h4> <br></td>				
						<td valign="top" style="text-align: right;"><h5>Профиль: <%= profil%><br> <%= nameOnMenuHead%></h5></td>
						</tr>
					
		</table>
		</div>
		
<table align="center" style="text-align: center; width: 1065px;">
    <tr>
        <td>
            <ul id="menu-bar">
                <li class="active" ><a href="EnterPage.jsp"> На главную </a></li>
                <li><a href="Materials.jsp">Материалы</a></li>
                <li><a href="Detail.jsp" >Детали</a></li>
                <li><a href="Operation.jsp">Операции</a></li>
                <li><a href="AddPressform.jsp">Пресс-формы</a></li>
                <li><a href="Workplace.jsp">Рабочие<br>центры</a></li>
                <li><a href="Taskline.jsp">Задачи<br>на линиях</a></li>
                <li><a href="Task.jsp">Типы<br>операций</a></li>
                <li class="active"><a href="#" onclick="Go()"> Выход</a></li>

                <form name='myfrm' method='POST' action='Exit'>
                <input type='hidden' name='code'>
                </form>
            </ul>
        </td>
    </tr>
</table>
<script>
function Go(val) {
   var frm=document.myfrm;
   frm.code.value = val;
   frm.submit();
}
</script>


    </body>
</html>