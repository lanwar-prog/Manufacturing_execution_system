<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                 <%! String pgname = "Остатки товаров на складах";%>
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
                            <th>Остатки товаров на складах</th>
                        </tr>
                        <td>     
                            <table class="table_blur" id="table_article_rest">
                                <tr>
                                    <th>Склад</th>
                                    <th></th>
                                    <th colspan="3">Количество</th>
                                </tr>
                                <tr>
                                    <th>Номенклатура</th>
                                    <th>Базовая единица измерения</th>
                                    <th>Приход</th>
                                    <th>Расход</th>
                                    <th>Конечный остаток</th>
                                </tr>
                                <%
                                    WorkDB wdb=new WorkDB();
                                    ArrayList<Object[]> Data=wdb.GetArticleRest();
                                    String OldWorkHouse="";
                                    Object[] Row;
                                    if(Data.size()>0){
                                        for(int i=0;i<Data.size();i++){
                                            Row=Data.get(i);
                                            if(!OldWorkHouse.equals((String)Row[1])){
                                                out.println("<tr><td class=\"dt_level_0\">"+(String)Row[3]+"</td><td colspan=\"4\"></td></tr>");
                                                OldWorkHouse =(String)Row[1];
                                            }
                                            out.println("<tr><td class=\"dt_level_1\">"+(String)Row[2]+"</td><td>"+(String)Row[7]+"</td><td>"+WorkDB.FloatFormat((Float)Row[4])+"</td><td>"+WorkDB.FloatFormat((Float)Row[5])+"</td><td>"+WorkDB.FloatFormat((Float)Row[6])+"</td></tr>");                
                                        }
                                    }else{
                                        out.println("<tr><td colspan=\"5\">Нет остатков товаров на складах</td></tr>");
                                    }
                                %>                
                            </table>
                            <input class="input_button" style="float: right" type="button" value="Обновить из 1С" onClick="location.href='Refresh1CArticleRest.jsp';">                          
                            <input class="input_button" style="float: right" type="button" value="Возврат" onClick="location.href='Reports1C.jsp';">                          
                        </td>
                    </table>
                </td>
            </tr>
        </table>  
        </div>      
    </body>
</html>