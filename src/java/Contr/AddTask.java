package Contr;

import folderClasses.WorkDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddTask extends HttpServlet {

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
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        if (request.getParameter("name") != null) {
            String name = request.getParameter("name");

            WorkDB database = new WorkDB();
            boolean res = database.addTask(name);

            HttpSession session = request.getSession();
            if (res == true) {
                session.setAttribute("message", "Тип операций добавлен.");
                response.sendRedirect("Tehnolog.jsp");
            } else {
                session.setAttribute("message", "При добавлении типа возникли проблемы. Обратитесь к администратору.");
                response.sendRedirect("Tehnolog.jsp");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
