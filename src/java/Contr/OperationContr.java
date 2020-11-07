package Contr;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.OperationDao;
import Dao.TaskDao;
import Model.Operation;
import com.mysql.jdbc.StringUtils;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;

public class OperationContr extends HttpServlet {

    private static final long serialVersionUID = 102831973239L;
    private static String INSERT_OR_EDIT = "/AddOperation.jsp";
    private static String LIST = "/ListOperation.jsp";
    private OperationDao dao;
    private TaskDao task_dao;

    public OperationContr() {
        super();
        dao = new OperationDao();
        task_dao = new TaskDao();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet OperationContr</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OperationContr at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String forward="";
        String action = request.getParameter("action");
        
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        if (request.getSession().getAttribute("login") != null) {
            try {
                if (action.equalsIgnoreCase("delete")){
                    Integer operationId = Integer.valueOf(request.getParameter("id"));
                    dao.deleteOperation(operationId);
                    forward = LIST;
                    request.setAttribute("operations", dao.getAllOperation());    
                } else if (action.equalsIgnoreCase("edit")){
                    forward = INSERT_OR_EDIT;
                    Integer operationId = Integer.valueOf(request.getParameter("id"));
                    Operation operation = dao.getOperationById(operationId);
                    request.setAttribute("operation", operation);
                } else if (action.equalsIgnoreCase("list")){
                    forward = LIST;
                    request.setAttribute("operations", dao.getAllOperation());
                } else {
                    forward = INSERT_OR_EDIT;
                    request.setAttribute("operation", dao.clearOperation());
                }

                request.setAttribute("tasks", task_dao.getAllTask());
                RequestDispatcher view = request.getRequestDispatcher(forward);
                view.forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(501);
            }
        }
        else {
            response.setStatus(301);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        
        if (request.getSession().getAttribute("login") != null) {
            Operation operation = new Operation();
            
            if (!(StringUtils.isNullOrEmpty(request.getParameter("name")) ||
                StringUtils.isNullOrEmpty(request.getParameter("time")) || 
                StringUtils.isNullOrEmpty(request.getParameter("timesetup")) ||
                StringUtils.isNullOrEmpty(request.getParameter("timecompletion")) ||
                StringUtils.isNullOrEmpty(request.getParameter("id_tas"))))
            {
                try {
                    operation.setName(request.getParameter("name"));
                    operation.setTime(Integer.valueOf(request.getParameter("time")));
                    operation.setTimesetup(Integer.valueOf(request.getParameter("timesetup")));
                    operation.setTimecompletion(Integer.valueOf(request.getParameter("timecompletion")));
                    operation.setId_tas(Integer.valueOf(request.getParameter("id_tas")));

                    String id = request.getParameter("id");

                    if(StringUtils.isNullOrEmpty(id))
                    {
                        operation = dao.addOperation(operation);
                    }
                    else
                    {
                        operation.setId_operation(Integer.valueOf(id));
                        dao.updateOperation(operation);
                    }
                    //dao.getOperationById(operation.getId_operation());
                    operation = dao.clearOperation();

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(400);
                }
            }
            else
                response.setStatus(415);
            
            request.setAttribute("operation", operation);
            request.setAttribute("tasks", task_dao.getAllTask());
            
            RequestDispatcher view = request.getRequestDispatcher(INSERT_OR_EDIT);
            view.forward(request, response);
        }
        else {
            response.setStatus(301);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
