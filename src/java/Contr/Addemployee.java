package Contr;

import folderClasses.WorkDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Addemployee", urlPatterns = {"/Addemployee"})
public class Addemployee extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {           
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Addemployee</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Addemployee at " + request.getContextPath() + "</h1>");
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
        //считать параметры и добавить в бд 
        request.setCharacterEncoding("utf-8");
        if (request.getParameter("name") != null) {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String[] roles = request.getParameterValues("role");
            int role = 0;
            if (roles != null) {
                role = Integer.parseInt(roles[0]);
            }
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String telephone = request.getParameter("telephone");
            String date = new java.util.Date().toString();

            WorkDB database = new WorkDB();
            boolean res;
            res = database.addEmployee(name, surname, role, login, password, telephone, date);

            HttpSession session = request.getSession();
            if (res == true) {
                session.setAttribute("message", "Пользователь был добавлен.");
                response.sendRedirect("PageOfHead.jsp");
            } else {
                session.setAttribute("message", "Ошибка добавления. Обратитель к администратору.");
                response.sendRedirect("PageOfHead.jsp");
            }
        } else {
            response.sendRedirect("post.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
