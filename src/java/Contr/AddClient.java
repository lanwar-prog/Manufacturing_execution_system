package Contr;

import folderClasses.WorkDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AddClient extends HttpServlet {

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
        //считать параметры из пост и добавить в бд
        WorkDB database = new WorkDB();
        request.setCharacterEncoding("utf-8");
        if (request.getParameter("organization") != null) {
            String organization = request.getParameter("organization");
            String minnameorgan = request.getParameter("minorgan");
            String telephone = request.getParameter("telephone");
            String Address = request.getParameter("address");
            String city = request.getParameter("city");

            boolean res = database.addClient(organization, minnameorgan, telephone, Address, city);
            HttpSession session = request.getSession();
            if (res == true) {
                session.setAttribute("message", "Заказчик был добавлен.");
                response.sendRedirect("PageOfHead.jsp");
            } else {
                session.setAttribute("message", "Ошибка добавления. Обратитель к администратору.");
                response.sendRedirect("PageOfHead.jsp");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
