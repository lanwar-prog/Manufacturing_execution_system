/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contr;

import Dao.EmployeeDao;
import Model.Employee;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeeContr extends HttpServlet {
    
    private static final long serialVersionUID = 102831973239L;
    private static String INSERT_OR_EDIT = "/AddEmployee.jsp";
    private static String LIST = "/ListEmployee.jsp";
    private EmployeeDao dao;
    
    public EmployeeContr() {
        super();
        dao = new EmployeeDao();
    }
    
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
        
        String forward="";
        String action = request.getParameter("action");
        
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        if (request.getSession().getAttribute("login") != null) {
            try {
                if (action.equalsIgnoreCase("delete")){
                    Integer employeeId = Integer.valueOf(request.getParameter("id"));
                    dao.deleteEmployee(employeeId);
                    forward = LIST;
                    request.setAttribute("employees", dao.getAllEmployee());    
                } else if (action.equalsIgnoreCase("edit")){
                    forward = INSERT_OR_EDIT;
                    Integer employeeId = Integer.valueOf(request.getParameter("id"));
                    Employee employee = dao.getEmployeeById(employeeId);
                    request.setAttribute("employee", employee);
                } else if (action.equalsIgnoreCase("list")){
                    forward = LIST;
                    request.setAttribute("employees", dao.getAllEmployee());
                } else {
                    forward = INSERT_OR_EDIT;
                    request.setAttribute("employee", dao.clearEmployee());
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
            Employee employee = new Employee();
            
            if (!(StringUtils.isNullOrEmpty(request.getParameter("name")) ||
                StringUtils.isNullOrEmpty(request.getParameter("surname")) ||
                StringUtils.isNullOrEmpty(request.getParameter("login")) ||
                StringUtils.isNullOrEmpty(request.getParameter("role")) ||
                StringUtils.isNullOrEmpty(request.getParameter("password")) ||
                StringUtils.isNullOrEmpty(request.getParameter("phone")) ||
                StringUtils.isNullOrEmpty(request.getParameter("datehider"))))
            {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    employee.setName(request.getParameter("name"));
                    employee.setSurname(request.getParameter("surname"));
                    employee.setLogin(request.getParameter("login"));
                    employee.setRole(Integer.valueOf(request.getParameter("role")));
                    employee.setPassword(request.getParameter("password"));
                    employee.setPhone(request.getParameter("phone"));
                    
                    Date dt = df.parse(request.getParameter("datehider")); 
                    employee.setDatehider(dt);

                    String id = request.getParameter("id");

                    if(StringUtils.isNullOrEmpty(id))
                    {
                        employee = dao.addEmployee(employee);
                    }
                    else
                    {
                        employee.setId_employee(Integer.valueOf(id));
                        dao.updateEmployee(employee);
                    }
                    //dao.getEmployeeById(employee.getId_employee());
                    employee = dao.clearEmployee();

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(400);
                }
            }
            else
                response.setStatus(415);
            
            request.setAttribute("employee", employee);
            
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
    }
}
