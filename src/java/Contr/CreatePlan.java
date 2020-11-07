package Contr;

import createPlan.StartGenerate;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreatePlan extends HttpServlet {

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
        if (request.getParameter("dateStart") != null) {
            String dateStart = request.getParameter("dateStart");
            String timeStart = request.getParameter("timeStart");

            StartGenerate sg = new StartGenerate(dateStart, timeStart);
            sg.generatePlan();
            response.sendRedirect("CurrentPlan.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
