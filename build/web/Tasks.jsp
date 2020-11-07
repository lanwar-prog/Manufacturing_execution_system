<%-- 
    Document   : Tasks
    Created on : 13.05.2016, 15:38:05
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
        <table align="right">
            <tr>
                <td>
                    <p align="center">Список типов операций</p>
                </td>
            </tr>
            <tr>
              </tr>
            <tr>
                <td>
                    <table border="1" align="center" class="table_blur">
                        <tr align="center">
                            <th width="50pt">Код</th>
                            <th width="150pt">Название</th>
                        </tr>
                        <%                        try {
                                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                                Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
                                Statement s = (Statement) c.createStatement();
                                ResultSet rs = s.executeQuery("SELECT * FROM `mesdb`.`task`");
                                while (rs.next()) {
                                    out.println("<tr>");
                                    out.println("<td>" + rs.getString("id_task") + "</td>");
                                    out.println("<td>" + rs.getString("Name") + "</td>");
                                    out.println("</tr>");
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
