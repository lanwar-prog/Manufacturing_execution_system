<%-- 
    Document   : Workplaces
    Created on : 13.05.2016, 21:46:59
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

        <%            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
        %>

        <table align="center">
            <tr>
                <td>
                    <p align="center">Список рабочих центров</p>
                </td>
            </tr>
            <tr>
   
            </tr>
            <tr>
                <td>

                    <table align="center" border="1" class="table_blur">
                        <tr align="center">
                            <th>Номер</th>
                            <th>Название</th>
                            <th>Дата создания</th>                            
                        </tr>

                        <%                            
                            try {
                                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                                Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
                                Statement s = (Statement) c.createStatement();
                                ResultSet rs = s.executeQuery("select * from `mesdb`.`workplace`");
                                boolean flag = false;
                                while (rs.next()) {
                                    flag = true;
                                    out.println("<tr>");
                                    out.println("<td align='center'>" + rs.getString("id_workplace") + "</td>");
                                    out.println("<td>" + rs.getString("Name") + "</td>");
                                    out.println("<td>" + rs.getString("Created") + "</td>");
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
