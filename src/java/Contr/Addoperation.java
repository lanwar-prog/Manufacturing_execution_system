package Contr;

import folderClasses.WorkDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Addoperation extends HttpServlet {

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

            String tasks[] = request.getParameterValues("task");
            String task = "";
            if (tasks != null) {
                task = tasks[0];
            }

            String time = request.getParameter("time");
            String timeStart = request.getParameter("timeStart");
            String timeEnd = request.getParameter("timeEnd");
            
            WorkDB database = new WorkDB();
            boolean res = database.addOperation(name, time, timeStart, timeEnd, task);
            
            HttpSession session = request.getSession();
            if (res == true) {
                session.setAttribute("message", "Операция добавлена.");
                response.sendRedirect("Tehnolog.jsp");
            } else {
                session.setAttribute("message", "Ошибка добавления операции. Обратитесь к администратору");
                response.sendRedirect("Tehnolog.jsp");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
