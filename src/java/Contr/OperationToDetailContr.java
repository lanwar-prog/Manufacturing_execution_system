/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contr;

import Dao.DetailDao;
import Dao.OperationDao;
import Dao.OperationToDetailDao;
import Model.OperationToDetail;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OperationToDetailContr extends HttpServlet {
    
    private static final long serialVersionUID = 102831973239L;
    private static String INSERT_OR_EDIT = "/AddOperationToDetail.jsp";
    private static String LIST = "/ListOperationToDetail.jsp";
    private OperationToDetailDao dao;
    private DetailDao detail_dao;
    private OperationDao op_dao;

    public OperationToDetailContr() {
        super();
        dao = new OperationToDetailDao();
        detail_dao = new DetailDao();
        op_dao = new OperationDao();
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
            out.println("<title>Servlet MaterialsContr</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OperationToDetailContr at " + request.getContextPath() + "</h1>");
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
        
        Integer detail_id = null;
         if (!(StringUtils.isNullOrEmpty(request.getParameter("parent_id"))))
                 detail_id = Integer.valueOf(request.getParameter("parent_id"));

        if (request.getSession().getAttribute("login") != null) {
            try {
                if (action.equalsIgnoreCase("delete")){
                    Integer operationToDetailId = Integer.valueOf(request.getParameter("id"));
                    dao.deleteOperationToDetail(operationToDetailId);
                    forward = LIST;
                    request.setAttribute("operationToDetails", dao.getAllOperationToDetails(detail_id));
                    request.setAttribute("detail", detail_dao.getDetailById(detail_id));
                } else if (action.equalsIgnoreCase("edit")){
                    forward = INSERT_OR_EDIT;
                    Integer operationToDetailId = Integer.valueOf(request.getParameter("id"));
                    OperationToDetail operationToDetail = dao.getOperationToDetailById(operationToDetailId);
                    request.setAttribute("operationToDetail", operationToDetail);
                } else if (action.equalsIgnoreCase("list")){
                    forward = LIST;
                    request.setAttribute("operationToDetails", dao.getAllOperationToDetails(detail_id));
                    request.setAttribute("detail", detail_dao.getDetailById(detail_id));
                } else {
                    forward = INSERT_OR_EDIT;
                    request.setAttribute("operationToDetail", dao.clearOperationToDetail(detail_id));
                }

                request.setAttribute("ops", op_dao.getAllOperation());
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
            OperationToDetail operationToDetail = new OperationToDetail();
            
            if (!(StringUtils.isNullOrEmpty(request.getParameter("id_operation"))))
            {
                try {
                    operationToDetail.setId_detail(Integer.valueOf(request.getParameter("id_detail")));
                    operationToDetail.setId_operation(Integer.valueOf(request.getParameter("id_operation")));
                    operationToDetail.setPriority(1);

                    String id = request.getParameter("id");

                    if(StringUtils.isNullOrEmpty(id))
                    {
                        operationToDetail = dao.addOperationToDetail(operationToDetail);
                    }
                    else
                    {
                        operationToDetail.setId_operdet(Integer.valueOf(id));
                        dao.updateOperationToDetail(operationToDetail);
                    }
                    //dao.getOperationToDetailById(operationToDetail.getId_operdet());
                    operationToDetail = dao.clearOperationToDetail(operationToDetail.getId_detail());

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(400);
                }
            }
            else
                response.setStatus(415);
            
            request.setAttribute("operationToDetail", operationToDetail);
            request.setAttribute("ops", op_dao.getAllOperation());
            
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
