package Contr;

import folderClasses.WorkDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddOper extends HttpServlet {

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
        if (request.getParameter("kod") != null) {
            String kodOperation = request.getParameter("kod");
            String kodDetail = request.getParameter("kodDetail");

            WorkDB database = new WorkDB();
            boolean flag = database.addOper(kodDetail, kodOperation);
            HttpSession session = request.getSession();

            if (flag == true) {
                session.setAttribute("message", "Операция добавлена");
                response.sendRedirect("Tehnolog.jsp");
            } else {
                session.setAttribute("message", "Ошибка добавления. Обратитесь к администратору.");
                response.sendRedirect("Tehnolog.jsp");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}