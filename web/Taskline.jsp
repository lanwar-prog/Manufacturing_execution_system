<%-- 
    Document   : Taskline
    Created on : 02.04.2017, 12:44:52
    Author     : Ковалев Алексей
--%>
<%@page import="TaskLine.Workplace"%>
<%@page import="TaskLine.Task"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
<%! String pgname = "Задачи на линиях!";%>
        <title><%= pgname%></title>

         <link rel="stylesheet" type="text/css" href="styles/MainStyle.css">
          <link rel="stylesheet" type="text/css" href="styles/table.css">

        <style>   
            .classTd{                
                text-align: center;                
            }
        </style>
        <script lang="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
        <script lang="text/javascript">
            $(document).ready(function() {
                $("td").click(function() {

                    var el = window.event.srcElement;
                    
                    if (el.innerText === "") {
                        el.innerText = "V";
                    } else if (el.innerText === "V") {
                        el.innerText = "";
                    }
                   //  if (el.innerText === "") {
                   //     el.innerText = "+";
                   // } else if (el.innerText === "+") {
                   //     el.innerText = "";
                   // }

                    var col = $(this).parent().children().index($(this));
                    var row = $(this).parent().parent().children().index($(this).parent());
                    //alert('Row: ' + row + ', Column: ' + col);
                    //отправить на сервер об изменении таски

                    // x = $(this).rows[row].cells;
                    //x[col].innerHTML = "Теперь";

                    //////////////////////////////////////////
                    var xmlhttp;
                    if (window.XMLHttpRequest)
                    {// код для IE7+, Firefox, Chrome, Opera, Safari
                        xmlhttp = new XMLHttpRequest();
                    }
                    else
                    {// код для IE6, IE5
                        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    xmlhttp.open("POST", "Taskline", true);
                    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                    //xmlhttp.onreadystatechange = callback;
                    xmlhttp.send("idW=" + row + "&idT=" + col + "");
                    //window.location = "http://www.google.com";
                });
            });
        </script>
    </head>
    <body bgcolor="DCDCDA">
        <div class="Divbg bg2 " ></div>
    
    
        <%            if (session.getAttribute("login") == null) {
                response.sendRedirect("EnterPage.jsp");
            }
        %>
 <%@include file="HeadSapcon.jsp"%>
        <table align="center">
            <tr>
             
            </tr>
            <tr>
                <td>
                    <%@include file="menuTehn.jsp"%>
                </td>
            </tr>
        </table>
<br><br>
        <table style="width: 1100px" border="1" align="center" cursor="pointer" class="classTd tableinput2" >
            <%
                ArrayList<Workplace> workplaces = new ArrayList<Workplace>();
                ArrayList<TaskLine.Task> tasks = new ArrayList<Task>();

                try {
                    DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                    Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
                    Statement s = (Statement) c.createStatement();
                    ResultSet rs = s.executeQuery("select * from `mesdb`.`workplace`");

                    Workplace w;
                    while (rs.next()) {
                        w = new Workplace();
                        w.Name = rs.getString("Name");
                        w.id = rs.getInt("id_workplace");
                        workplaces.add(w);
                    }

                    rs = s.executeQuery("select * from `mesdb`.`task`");
                    Task t;
                    while (rs.next()) {
                        t = new Task();
                        t.Name = rs.getString("Name");
                        t.id = rs.getInt("id_task");
                        tasks.add(t);
                    }

                    if (workplaces.size() != 0 && tasks.size() != 0) {
                        out.println("<tr>");
                        out.println("<th>Линия №</th>");
                        for (int i = 0; i < tasks.size(); i++) {
                            out.println("<th> " + tasks.get(i).Name + " </th>");
                        }
                        out.println("</tr>");

                        for (int i = 0; i < workplaces.size(); i++) {
                            out.println("<tr>");
                            out.println("<td align='left'>" + workplaces.get(i).Name + "</td>");
                            for (int j = 0; j < tasks.size(); j++) {
                                out.println("<td style='color: #006600; font-size: 18px; font-weight: bold; font-style: oblique'>");
                                String query = "select * from `mesdb`.`workplacetask` where id_task=" + tasks.get(j).id + " and id_workplace=" + workplaces.get(i).id + "";

                                rs = s.executeQuery(query);
                                if (rs.next()) {
                                    out.println("V");
                                } else {
                                    out.println("");
                                }
                                out.println("</td>");
                            }
                            out.println("</tr>");
                        }
                    }
                    c.close();
                } catch (SQLException e) {

                }
                /* 
                 <table border="1" align="center" cursor="pointer" class="classTd">
                 <tr>
                 <td>Р.место</td><td title="код&#13;название&#13;Информация о операции">Резьба</td><td>Шлефовка</td><td>Покраска</td><td>Ламинирование</td><td>Очистка</td>
                 </tr>
                 <tr>
                 <td>Workplace 1</td><td>+</td><td></td><td>+</td><td></td><td></td>
                 </tr>            
                 <tr>
                 <td>Workplace 2</td><td></td><td>+</td><td></td><td></td><td>+</td>
                 </tr> 
                 <tr>
                 <td>Workplace 3</td><td></td><td></td><td>+</td><td></td><td>+</td>
                 </tr> 
                 <tr>
                 <td>Workplace 4</td><td>+</td><td></td><td></td><td>+</td><td></td>
                 </tr> 
                 <tr>
                 <td>Workplace 5</td><td></td><td>+</td><td></td><td>+</td><td></td>
                 </tr>
                 </table>*/
            %>
        </table>


    </body>
</html>
