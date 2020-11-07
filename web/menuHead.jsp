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
        
   
		
		  <table align="center" style="width: 1000px">
						<tr>
						<td ><h4><%= pgname%> </h4> <br></td>				
						<td valign="top" style="text-align: right;"><h5>Профиль: <%= profil%><br> <%= nameOnMenuHead%></h5></td>
						</tr>
					
		</table>
		</div>
		
<div style="min-width:1000px;">
        
         
        <ul id="menu-bar">
 <li class="active" ><a href="EnterPage.jsp"> На главную </a></li>
 <li><a href="CurrentPlan.jsp">Просмотр <br>текущего плана</a></li>
 <li><a href="CreatePlan.jsp" >Построить <br>план</a></li>
 <li><a href="Employee.jsp">Пользователи</a></li>
 <li><a href="Order.jsp">Заказы</a></li>
  <li><a href="Client.jsp">Клиенты</a></li>
  <li><a href="Supplier.jsp">Поставщики</a></li>
  <li><a href="Reports1C.jsp"><img alt="Отчёты 1С" title="Отчёты 1С" class="img_1c" src="WebContent/icon1c.png"></a></li>  
  <li class="active"><a href="#" onclick="Go()"> Выход</a>   </li>
  
<form name='myfrm' method='POST' action='Exit'>
<input type='hidden' name='code'>
</form>

  </ul>
  
  </div>
   
  <!-- <div style="position: fixed; bottom: 20px; left: 30px">   
<form method="post" action="Exit">
  <input type="submit" value="Выход из сеанса" style="width:130Px;height:30Px" >
  &nbsp;</form> -->



   <script>
function Go(val) {
   var frm=document.myfrm;
   frm.code.value = val;
   frm.submit();
}
</script>


    </body>
</html>