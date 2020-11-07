<!-- 
    Document   : Detail
    Created on : 12.02.2017, 13:41:51
    Author     : Ковалев Алексей
-->

<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% synchronized(this) { %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail</title>
      
      <link rel="stylesheet" type="text/css" href="styles/table.css">
 	<link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
    </head>
    <body style="background: rgba(0, 0, 0, 0)" >
        
        <%! String detail = "";%>
        <% 
            if (request.getParameter("kod") != null) {
                detail = (String) request.getParameter("kod");
            }//если детали не существует то на детали переход
            //
        %>
        
    <table align="center" style="width: 1000px">
		<tr>
			<td align="center"><p>Список операций для детали <%= detail%>!</p></td>
			<td></td>
		</tr>
		<tr>
			<td>
			<table border="1" class="table_blur">
                        <tr>
                            <th>Номер</th>
                            <th>Время работы</th>
                            <th>Время подготовки</th>
                            <th>Время завершения</th>
                            <th>Тип задачи</th>
                        </tr>
                        <tr>
                            <%
                                try {
                                    DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                                    Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "password");

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
                            %>
                        </tr>
                    </table>
                    </td>
			<td>
			</td>
		</tr>
</table>

    </body>
</html>
<% } %>