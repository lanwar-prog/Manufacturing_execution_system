/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contr;

import Dao.WorkplaceDao;
import Model.Detail;
import Model.Workplace;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WorkplaceContr extends HttpServlet {
    
    private static final long serialVersionUID = 102831973239L;
    private static String INSERT_OR_EDIT = "/AddWorkplace.jsp";
    private static String LIST = "/ListWorkplace.jsp";
    private WorkplaceDao dao;
    
    public WorkplaceContr() {
        super();
        dao = new WorkplaceDao();
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
                    Integer workplaceId = Integer.valueOf(request.getParameter("id"));
                    dao.deleteWorkplace(workplaceId);
                    forward = LIST;
                    request.setAttribute("workplaces", dao.getAllWorkplace());    
                } else if (action.equalsIgnoreCase("edit")){
                    forward = INSERT_OR_EDIT;
                    Integer workplaceId = Integer.valueOf(request.getParameter("id"));
                    Workplace workplace = dao.getWorkplaceById(workplaceId);
                    request.setAttribute("workplace", workplace);
                } else if (action.equalsIgnoreCase("list")){
                    forward = LIST;
                    request.setAttribute("workplaces", dao.getAllWorkplace());
                } else {
                    forward = INSERT_OR_EDIT;
                    request.setAttribute("workplace", dao.clearWorkplace());
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
            Workplace workplace = new Workplace();
            
            if (!(StringUtils.isNullOrEmpty(request.getParameter("name")) ||
                StringUtils.isNullOrEmpty(request.getParameter("created"))))
            {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    workplace.setName(request.getParameter("name"));
                    
                    Date dt = df.parse(request.getParameter("created")); 
                    workplace.setCreated(dt);

                    String id = request.getParameter("id");

                    if(StringUtils.isNullOrEmpty(id))
                    {
                        workplace = dao.addWorkplace(workplace);
                    }
                    else
                    {
                        workplace.setId_workplace(Integer.valueOf(id));
                        dao.updateWorkplace(workplace);
                    }
                    //dao.getWorkplaceById(workplace.getId_workplace());
                    workplace = dao.clearWorkplace();

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(400);
                }
            }
            else
                response.setStatus(415);
            
            request.setAttribute("workplace", workplace);
            
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
