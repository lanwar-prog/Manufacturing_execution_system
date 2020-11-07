package Contr;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import folderClasses.WorkDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TaskLine extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /////////////////
        String s1 = request.getParameter("idW");
        String s2 = request.getParameter("idT");

        //select * from `mesdb`.`workplacetask` where id_task=1 and id_workplace=1
        try {

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();
            ResultSet rs = s.executeQuery("select * from `mesdb`.`workplacetask` where id_task=" + s2 + " and id_workplace=" + s1 + "");

            if (rs.next()) {
                Statement sDelete = (Statement) c.createStatement();
                int del = rs.getInt("id_workplacetask");
                String query = "delete from `mesdb`.`workplacetask` where id_workplacetask=" + del + "";
                sDelete.executeUpdate(query);
                c.close();

            } else {
                Properties properties = new Properties();
                properties.setProperty("user", "root");
                properties.setProperty("password", "123456");
                properties.setProperty("useUnicode", "true");
                properties.setProperty("characterEncoding", "utf-8");

                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                //Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", properties);
                Statement sInsert = (Statement) c.createStatement();

                WorkDB database = new WorkDB();

                int id = database.getMaxId("`mesdb`.`workplacetask`", "id_workplacetask") + 1;

                String query = "insert into `mesdb`.`workplacetask` values(" + id + ", " + s2 + ", " + s1 + ")";
                sInsert.executeUpdate(query);
                c.close();

            }
            c.close();

        } catch (SQLException e) {

        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
