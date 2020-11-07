package Contr;

import folderClasses.Validate;
import folderClasses.WorkDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddOrder extends HttpServlet {

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
        if (request.getParameter("detail") != null) {
            String kodDetail = request.getParameter("detail");
            int idDetail = Integer.parseInt(kodDetail);

            String numberDetails = request.getParameter("number");
            int numberDetail = Integer.parseInt(numberDetails);

            String[] citys = request.getParameterValues("city");
            String city = "";
            if (citys != null) {
                city = citys[0];
            }

            String organizations[] = request.getParameterValues("organization");
            String idOrg = "";
            if (organizations != null) {
                idOrg = organizations[0];
            }
            int organizationId = Integer.parseInt(idOrg);

            String date = request.getParameter("date");
            String dateExecution = request.getParameter("dateExecution");

            String urgencyS = request.getParameter("urgency");
            int urgency = 0;
            if (urgencyS != null) {
                if (urgencyS.equals("on")) {
                    urgency = 1;
                }
            }

            WorkDB database = new WorkDB();
            boolean res;
            res = database.addOrder(idDetail, numberDetail, city, organizationId, date, urgency, dateExecution);

            HttpSession session = request.getSession();
            if (res == true) {
                session.setAttribute("message", "Заказ добавлен.");
                response.sendRedirect("PageOfHead.jsp");
            } else {
                session.setAttribute("message", "Ошибка добавления заказа.");
                response.sendRedirect("PageOfHead.jsp");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
