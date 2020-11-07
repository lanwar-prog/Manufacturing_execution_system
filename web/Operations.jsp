<%-- 
    Document   : Operations
    Created on : 13.05.2016, 21:30:07
    Author     : Ковалев Алексей
--%>

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
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="styles/table.css">
    </head>
    <body bgcolor="DCDCDA">
        <table align="center">
            <tr>
                <td>
                    <p align="center">Список операций</p>
                </td>
            </tr>
            <tr>
                <td>
                    <table align="center" border="1" class="table_blur">
                        <tr align="center">
                            <th>Номер</th>
                            <th>Время работы, сек</th>
                            <th>Время подготовки, сек</th>
                            <th>Время завершения, сек</th>
                            <th>Тип задачи</th>
                        </tr>
                        <%                        try {
                                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                                Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
                                Statement s = (Statement) c.createStatement();
                                ResultSet rs = s.executeQuery("select * from `mesdb`.`operation`");
                                boolean flag = true;
                                while (rs.next()) {
                                    flag = false;

                                    out.println("<tr>");
                                    out.println("<td>" + rs.getString("id_operation") + "</td>");
                                    out.println("<td>" + rs.getString("Time") + "</td>");
                                    out.println("<td>" + rs.getString("TimeSetup") + "</td>");
                                    out.println("<td>" + rs.getString("TimeCompletion") + "</td>");
                                    out.println("<td>" + rs.getString("id_tas") + "</td>");
                                    out.println("</tr>");
                                }

                                if (flag == true) {
                                    //
                                }
                                c.close();
                            } catch (SQLException e) {

                            }
                        %>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
