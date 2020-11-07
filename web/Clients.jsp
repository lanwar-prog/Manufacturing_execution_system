<%-- 
    Document   : Clients
    Created on : 12.05.2016, 15:54:48
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
        <!--%            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
        %-->
        <table align="center" style="margin: -20px;">
            <tr>
                <td>
                    <h4 align="center">Список клиентов</h4>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <table border="1" class="table_blur" >
                        <tr>
                            <th>№</th>
                            <th>Название</th>
                            <th>Телефон</th>
                            <th>Город</th>
                            <th>Адрес</th>
                        </tr>
                        <%                            try {
                                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                                Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
                                Statement s = (Statement) c.createStatement();
                                ResultSet rs = s.executeQuery("SELECT * FROM `mesdb`.`client`");
                                while (rs.next()) {
                                    out.println("<tr>");
                                    out.println("<td>" + rs.getString("id_client") + "</td>");
                                    out.println("<td>" + rs.getString("NameOrganization") + "</td>");
                                    out.println("<td>" + rs.getString("Telephone") + "</td>");
                                    out.println("<td>" + rs.getString("City") + "</td>");
                                    out.println("<td>" + rs.getString("Address") + "</td>");
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
