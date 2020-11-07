<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="folderClasses.Work1C"%>
<%@page import="folderClasses.Connect1C"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                 <%! String pgname = "Обновление материалов в эксплуатации";%>
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
        <table align="center" >
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
                <td align="center" colspan="2">
                    <table align="center" style="margin: -20px;" class="table_1c">                 
                        <tr>
                            <th>Обновление материалов в эксплуатации</th>
                        </tr>
                        <tr>
                            <td>
                                <table id="table_refresh">
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>                                    
                                <input style="float: right" type="button" value="Возврат" onClick="location.href='Rep1CArticleExp.jsp';">                          
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>  
        </div>      
        <script>
            <%
                Connect1C C1C=Connect1C.getInstance("file=\"X:\\db\";usr=\"admin\";pwd=\"1\"");
                Work1C w1c=new Work1C();
                %>
                window.document.getElementById('table_refresh').innerHTML=window.document.getElementById('table_refresh').innerHTML+
                        '<tr><td>Подключение к 1С</td><td><img class="img_small" id="img_1" src="WebContent\\load.gif"></td></tr>';
                <%
                C1C.Open();
                %>
                window.document.getElementById("img_1").src="WebContent\\ok.png";
                window.document.getElementById('table_refresh').innerHTML=window.document.getElementById('table_refresh').innerHTML+
                        '<tr><td>Выполнение запросов</td><td><img class="img_small" id="img_2" src="WebContent\\load.gif"></td></tr>';
                <%         
                try{    
                    w1c.ReloadArticleExpFrom1C();
                    %>
                    window.document.getElementById("img_2").src="WebContent\\ok.png";
                    window.document.getElementById('table_refresh').innerHTML=window.document.getElementById('table_refresh').innerHTML+
                            '<tr><td>Данные обновлены</td><td><img class="img_small" id="img_3" src="WebContent\\ok.png"></td></tr>';
                    <%         
                }finally{
                    C1C.Close();    //закрываем соединение с 1с
                }               
            %>            
        </script>
    </body>
</html>