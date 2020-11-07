/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contr;

import Dao.EdDao;
import Dao.MaterialsDao;
import Dao.SupplierDao;
import Dao.TaskDao;
import Model.Materials;
import Model.Task;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TaskContr extends HttpServlet{
    
    private static final long serialVersionUID = 102831973239L;
    private static String INSERT_OR_EDIT = "/AddTask.jsp";
    private static String LIST = "/ListTask.jsp";
    private TaskDao dao;

    public TaskContr() {
        super();
        dao = new TaskDao();
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
            out.println("<title>Servlet TaskContr</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TaskContr at " + request.getContextPath() + "</h1>");
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
                    Integer taskId = Integer.valueOf(request.getParameter("id"));
                    dao.deleteTask(taskId);
                    forward = LIST;
                    request.setAttribute("tasks", dao.getAllTask());    
                } else if (action.equalsIgnoreCase("edit")){
                    forward = INSERT_OR_EDIT;
                    Integer taskId = Integer.valueOf(request.getParameter("id"));
                    Task task = dao.getTaskById(taskId);
                    request.setAttribute("task", task);
                } else if (action.equalsIgnoreCase("list")){
                    forward = LIST;
                    request.setAttribute("tasks", dao.getAllTask());
                } else {
                    forward = INSERT_OR_EDIT;
                    request.setAttribute("task", dao.clearTask());
                }

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
            Task task = new Task();
            
            if (!(StringUtils.isNullOrEmpty(request.getParameter("name"))))
            {
                try {
                    task.setName(request.getParameter("name"));

                    String id = request.getParameter("id");

                    if(StringUtils.isNullOrEmpty(id))
                    {
                        task = dao.addTask(task);
                    }
                    else
                    {
                        task.setId_task(Integer.valueOf(id));
                        dao.updateTask(task);
                    }
                    //dao.getTaskById(task.getId_task());
                    task = dao.clearTask();

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(400);
                }
            }
            else
                response.setStatus(415);
            
            request.setAttribute("task", task);
            
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
