<%-- 
    Document   : Line
    Created on : 07.04.2016, 16:00:33
    Author     : Ковалев Алексей
--%>

<%@page import="ShowPlan.Block"%>
<%@page import="java.util.ArrayList"%>
<%@page import="folderClasses.WorkDB"%>
<%@page import="folderClasses.Validate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% synchronized(this) { %>
<%!
    int line = 0;
    Validate valid = new Validate();
%>
<%    //
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
        <link rel="stylesheet" type="text/css" href="styles/table.css">
 
    </head>
    <body style="background: none;">  
        <%! int role = 0;%>
          <%  WorkDB database = new WorkDB();%>
        <%            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
            if (session.getAttribute("login") != null) {

                String log = (String) session.getAttribute("login");
                role = database.getRole(log);
                
            }
            if (request.getParameter("line") != null) {
                String value = request.getParameter("line");
                if (valid.isInt(value)) {
                    line = Integer.parseInt(value);
                }
            }
        %>

        <table align="center">
            <tr>
                <td>
                    <p align="center" >План линии №<%= line%></p>
                </td>
            </tr>
            <tr>
                <td>
                    <div style="max-height:386px; overflow-y: auto; width:856px;">
                        <table align ="center" border="1" class="table_blur" width="850">
                            <tr align ="center">
                                <th>
                                    Номер
                                </th>
                                <th>
                                    Время начала                    
                                </th>
                                <th>
                                    Продолжительность
                                </th>
                                <th>
                                    Деталь
                                </th>
                                <th>
                                    Кол-во деталей
                                </th>
                                <th>
                                    Операция
                                </th>                
                            </tr>

                            <%
                                if (database.lineUsed(line) == false) {
                                    out.println("<tr>");
                                    out.println("<td colspan=\"6\" align=\"center\">");
                                    out.println("Рабочее место не используется");
                                    out.println("</td>");
                                    out.println("</tr>");
                                }
                                if (database.lineUsed(line) == true) {
                                    ArrayList<Block> blocks = database.getTaskLine(line);

                                    for (int i = 0; i < blocks.size(); i++) {
                                        out.println("<tr>");
                                        out.println("<td align='center' style='width:30px;'>");
                                        out.println(i + 1);
                                        out.println("</td>");

                                        out.println("<td style='width:100px;'>");
                                        out.println(blocks.get(i).getTimeStart());
                                        out.println("</td>");

                                        out.println("<td align='center' style='width:100px;'>");
                                        int temp = 0;

                                        if (blocks.get(i).getNumberDetails() <= 0) {
                                            /*String s = blocks.get(i).getColor();
                                            if (s.equals("00FFFF")) {
                                                temp = database.getWidthOperationStart(blocks.get(i).getIdOperation());
                                                out.println(temp);
                                            } else {
                                                temp = database.getWidthOperationEnd(blocks.get(i).getIdOperation());
                                                out.println(temp);
                                            }*/

                                            temp = blocks.get(i).getTimeLenght();
                                        } else {
                                            temp = blocks.get(i).getTimeLenght() * blocks.get(i).getNumberDetails();
                                        }
                                        out.println(temp);
                                        out.println("</td>");

                                        out.println("<td style='width:200px;'>");
                                        if (blocks.get(i).getNumberDetails() > 0) {
                                            out.println(blocks.get(i).getIdDetail() + " - " + blocks.get(i).getDetailName());
                                        }
                                        out.println("</td>");

                                        out.println("<td align='center' style='width:100px;'>");
                                        if (blocks.get(i).getNumberDetails() > 0) {
                                            out.println(blocks.get(i).getNumberDetails());
                                        }
                                        out.println("</td>");

                                        out.println("<td style='width:220px;'>");
                                        if (blocks.get(i).getNumberDetails() <= 0) {
                                            if (blocks.get(i).getIdOperation() == -1) {
                                                out.println("Подготовка оборудования");
                                            } else if (blocks.get(i).getIdOperation() == -2) {
                                                out.println("Очистка оборудования");
                                            } else if (blocks.get(i).getIdOperation() == -3) {
                                                out.println("Простой оборудования");
                                            }
                                        } else {
                                            out.println(blocks.get(i).getIdOperation() + " - " + blocks.get(i).getOperName());
                                        }
                                        out.println("</td>");
                                        out.println("</tr>");
                                    }
                                }
                            %>

                        </table>
                    </div>
                </td>
            </tr>
        </table>
    </body>
</html>
<% } %>