package Contr;

import folderClasses.WorkDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddSuplier extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddSuplier</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddSuplier at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        //Считать данные и записать в базу
        
        request.setCharacterEncoding("utf-8");        
        if (request.getParameter("name") != null) {
            String name = request.getParameter("name");
            String minname = request.getParameter("minname");
            String city = request.getParameter("city");
            String street = request.getParameter("street");
            String house = request.getParameter("house");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            boolean res;
            WorkDB database = new WorkDB();
            res = database.addSuplier(name, minname, city, street, house, phone, email);

            HttpSession session = request.getSession();
            if (res == true) {
                session.setAttribute("message", "Поставщик был добавлен.");
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
    }
}
