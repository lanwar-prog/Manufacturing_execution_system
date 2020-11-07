<%-- 
    Document   : Detail
    Created on : 12.05.2016, 13:41:51
    Author     : Ковалев Алексей
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail</title>
        <style>
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
        </style>
    </head>
    <body bgcolor="DCDCDA">
        <%! String detail = "";%>
        <%            if (session.getAttribute("login") == null) {
                response.sendRedirect("Enter.jsp");
            }
            if (request.getParameter("kod") != null) {
                detail = (String) request.getParameter("kod");
            }//если детали не существует то на детали переход
            //
        %>
        <table  align="center">
            <tr>                
                <td><h2>Деталь <%= detail%>!</h2></td>
            </tr>
            <tr>
                <td colspan="2"><%@include file="menuTehn.jsp"%></td>
            </tr>
            <tr valign="" align="center">
                <td>
                    <table border="1">
                        <tr>
                            <td>Номер</td>
                            <td>Время работы</td>
                            <td>Время подготовления</td>
                            <td>Время завершения</td>
                            <td>Тип задачи</td>
                        </tr>
                        <tr>
                            <%
                                try {
                                    DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                                    Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");

                                    Statement s = (Statement) c.createStatement();
                                    Statement sNum = (Statement) c.createStatement();

                                    ResultSet rs = sNum.executeQuery("select * from `mesdb`.`operationdetail` where id_detail=" + detail + "");

                                    ArrayList<Integer> arr = new ArrayList<Integer>();
                                    boolean flag = true;

                                    while (rs.next()) {
                                        int q = Integer.parseInt(rs.getString("id_operation"));
                                        arr.add(q);
                                    }

                                    int size = arr.size();
                                    if (size > 0) {
                                        for (int i = 0; i < arr.size(); i++) {
                                            int res = arr.get(i);
                                            ResultSet rs2 = s.executeQuery("select * from `mesdb`.`operation` where id_operation=" + res + "");
                                            while (rs2.next()) {
                                                flag = false;
                                                out.println("<tr align='center'>");

                                                out.println("<td>" + rs2.getString("id_operation") + "</td>");
                                                out.println("<td>" + rs2.getString("Time") + "</td>");
                                                out.println("<td>" + rs2.getString("TimeSetup") + "</td>");
                                                out.println("<td>" + rs2.getString("TimeCompletion") + "</td>");
                                                out.println("<td>" + rs2.getString("id_tas") + "</td>");

                                                out.println("</tr>");
                                            }
                                        }
                                    }

                                    if (flag == true) {
                                        out.println("<tr>");
                                        out.println("<td colspan='5' align='center'>");
                                        out.println("Операции отсутствуют.");
                                        out.println("</td>");
                                        out.println("</tr>");
                                    }

                                    c.close();

                                } catch (SQLException e) {

                                }
                                //ResultSet rs = s.executeQuery("");
                                //while( rs.next()){
                                //out.println("<tr>");
                                //out.println("<td> " + rs.getString("id_operation") +"</td>");
                                //out.println("<td> " + rs.getString("Time") +"</td>");
                                //out.println("<td> " + rs.getString("TimeSetup") +"</td>");
                                //out.println("<td> " + rs.getString("TimeCompletion") +"</td>");
                                //out.println("<td> " + rs.getString("id_tas") +"</td>");
                                //out.println("</tr>");
                                //}}}
                            %>
                        </tr>
                    </table>
                </td>

                <td>
                    <br>Добавить операцию
                    <form action="AddOper" method="POST">
                        <table>
                            <tr>
                                <td>Код операции</td>                            
                            </tr>
                            <tr>
                                <td>

                                    <select name="kodDetail" style="font-size:16pt;width:270px">

                                        <%
                                            //<input type="text" name="detail" style="font-size:16pt;width:270px"/>
                                            try {

                                                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                                                Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
                                                Statement s = (Statement) c.createStatement();
                                                String query = "select * from mesdb.operation";
                                                ResultSet rs = s.executeQuery(query);

                                                while (rs.next()) {
                                                    int id = rs.getInt("id_operation");
                                                    String name = rs.getString("Name");
                                                    out.println("<option value=\"" + id + "\">" + id + " - " + name + "</option>");// = rs.getInt("TimeCompletion");
                                                }
                                                c.close();
                                            } catch (SQLException e) {
                                                out.println("<option value=\"0\">---</option>");
                                            }
                                            
                                        %>
                                    </select>


                                </td>
                            </tr>
                            <tr>
                                <td align="right">
                                    <input type="submit" value="Добавить">
                                </td>
                            </tr>
                        </table>
                    </form>
                </td>


            </tr>            
        </table>

    </body>
</html>
