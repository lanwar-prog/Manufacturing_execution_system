package Contr;

import folderClasses.WorkDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Addmaterial extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Addmaterial</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Addmaterial at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //считать параметры и записать в базу
        request.setCharacterEncoding("utf-8");
        if (request.getParameter("name") != null) {
            String name = request.getParameter("name");
            String number = request.getParameter("number");

            String options[] = request.getParameterValues("deminsion");
            String deminsion = "";
            if (options != null) {
                deminsion = options[0];
            }
            
            String idSupliers[] = request.getParameterValues("supplier");
            String idSuplier = "";
            if (idSupliers != null) {
                idSuplier = idSupliers[0];
            }

            WorkDB database = new WorkDB();
            boolean res = database.addMeterial(name, number, deminsion, idSuplier);

            HttpSession session = request.getSession();
            if (res == true) {
                session.setAttribute("message", "Материал добавлен.");
                response.sendRedirect("Tehnolog.jsp");
            } else {
                session.setAttribute("message", "Ошибка добавления материала. Обратитесь к администратору");
                response.sendRedirect("Tehnolog.jsp");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
