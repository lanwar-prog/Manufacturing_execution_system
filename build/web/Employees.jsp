<%-- 
    Document   : Employees
    Created on : 12.05.2016, 14:49:34
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
    	<style type="text/css">
.style1 {
	text-align: center;
}
</style>
    </head>


    <body bgcolor="DCDCDA">
        <!--%            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
        %-->

        <table align="center" style="margin: -20px;">
            <tr>
                <td >
                    <h4 style="text-align: center">Список пользователей</h4>
                </td>
            </tr>
            <tr align="center">
            
            </tr>
            <tr>
                <td align="center">
                    <table aling="center" border="1" class="table_blur ">
                        <tr>
                            <th>№</th>
                            <th>Имя</th>
                            <th>Фамилия</th>
                            <th>Логин</th>
                            <th>Отдел</th>
                            <th>Телефон</th>
                            <!-- <td>Дата начала работы</td> -->
                        </tr >

                        <%
                            try {
                                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                                Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
                                Statement s = (Statement) c.createStatement();
                                ResultSet rs = s.executeQuery("SELECT * FROM `mesdb`.`Employee`");
                                while (rs.next()) {
                                    out.println("<tr>");
                                    out.println("<td>" + rs.getString("id_employee") + "</td>");
                                    out.println("<td>" + rs.getString("Name") + "</td>");
                                    out.println("<td>" + rs.getString("Surname") + "</td>");
                                    out.println("<td>" + rs.getString("login") + "</td>");
                                    out.println("<td>" + rs.getString("Role") + "</td>");
                                    out.println("<td>" + rs.getString("Phone") + "</td>");
                                    //out.println("<td>" + rs.getString("DateHider") + "</td>");                                    
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
