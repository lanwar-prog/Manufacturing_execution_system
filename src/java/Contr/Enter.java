package Contr;

import folderClasses.Auth;
import folderClasses.WorkDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Enter extends HttpServlet {

//    @Override
//    public void init() throws ServletException {
//        super.init(); //To change body of generated methods, choose Tools | Templates.
//    }
    
   
            

         
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Enter</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Enter at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        request.setCharacterEncoding("UTF-8");
        Auth auth = new Auth();

        if (request.getParameter("name") != null) {

            HttpSession session = request.getSession();
            String login = request.getParameter("name");
            String password = request.getParameter("password");

            if (!login.equals("") && !password.equals("")) {

                //если существует логин
                if (auth.checkLogin(login) == true) {
                    //если логин пароль совпадают

                    if (auth.equalsData(login, password)) {
                        session.setAttribute("login", login);
                        int role = auth.getRole(login);
                        if (role == 1) {
                            response.sendRedirect("PageOfHead.jsp");
                        }
                        if (role == 2) {
                            response.sendRedirect("Tehnolog.jsp");
                        }
                        if (role == 3) {
                            response.sendRedirect("PageOfDuty.jsp");
                        } else if (role == 4) {
                            response.sendRedirect("Error.jsp");
                        }
                    } else {
                        sendError(session, response);
                    }
                } else {
                    sendError(session, response);
                }
            } else {
                sendError(session, response);
            }
        } else {
            response.sendRedirect("EnterPage.jsp");
        }
    }

    private void sendError(HttpSession session, HttpServletResponse response) throws IOException {
        session.setAttribute("stringState", "Неверные данные логин/пароль. вернитесь на страницу входа и повторите ввод."
                + "<a href=\"EnterPage.jsp\">Страница входа</a>");
        response.sendRedirect("Error.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
