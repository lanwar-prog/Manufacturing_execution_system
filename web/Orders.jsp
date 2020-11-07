<%-- 
    Document   : Orders
    Created on : 13.05.2016, 14:13:58
    Author     : Ковалев Алексей
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="folderClasses.Validate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="styles/table.css">
    </head>
    <body bgcolor="DCDCDA">
        <%! int state = 0;%>
        <%
            if (request.getParameter("state") != null) {
                Validate valid = new Validate();
                String value = request.getParameter("state");
                if (valid.isInt(value)) {
                    state = Integer.parseInt(value);
                    if (state < 0 && state > 1) {
                        state = 0;
                    }
                }
            }
        %>
        <table align="center" style="margin: -20px;">
            <tr>
                <td>
                    <h4 align="center">Заказы</h4>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <table  border="1" class="table_blur">
                        <tr align="center">
                            <th> Номер </th>
                            <th> Клиент </th>
                            <th> Дата <br>
                                заказа </th>
                            <th> Состояние </th>
                            <th> Кол-во <br>
                                изделий </th>
                            <th> Деталь </th>
                            <th> Срок <br>
                                выполнения </th>
                        </tr>
                        <%
                            try {
                                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                                Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
                                Statement s = (Statement) c.createStatement();

                                ResultSet rs = s.executeQuery("select * from mesdb.clientorder where State=" + state);
                                boolean flag = false;
                                while (rs.next()) {
                                    flag = true;
                                    out.println("<tr>");
                                    out.println("<td>" + rs.getString("id_order") + "</td>");
                                    out.println("<td>" + rs.getString("id_client") + "</td>");
                                    out.println("<td>" + rs.getString("Date") + "</td>");
                                    out.println("<td>" + rs.getString("State") + "</td>");
                                    out.println("<td>" + rs.getString("NumberDetails") + "</td>");
                                    out.println("<td>" + rs.getString("id_detail") + "</td>");
                                    out.println("<td>" + rs.getString("DateExecution") + "</td>");
                                    out.println("</tr>");
                                }
                                if (flag == false) {
                                    out.println("<tr><td colspan='7' align='center'>Записи отсутствуют.</td></tr>");
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
