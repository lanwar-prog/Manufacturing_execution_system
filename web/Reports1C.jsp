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
                            <th>
                                <h4>Отчёты 1С</h4>
                            </th>
                        </tr>
                        <tr>
                            <td>
                                <a href="Rep1CArticleRest.jsp">Остатки товаров на складах</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <a href="Rep1CArticleExp.jsp">Материалы в эксплуатации</a>                
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>  
        </div>      
    </body>
</html>