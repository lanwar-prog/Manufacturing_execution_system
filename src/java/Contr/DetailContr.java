package Contr;

import Dao.DetailDao;
import Model.Detail;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class DetailContr extends HttpServlet {

    private static final long serialVersionUID = 102831973239L;
    private static String INSERT_OR_EDIT = "/AddDetail.jsp";
    private static String LIST = "/ListDetails.jsp";
    private DetailDao dao;
    
    public DetailContr() {
        super();
        dao = new DetailDao();
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
                    Integer detailId = Integer.valueOf(request.getParameter("id"));
                    dao.deleteDetail(detailId);
                    forward = LIST;
                    request.setAttribute("details", dao.getAllDetails());
                } else if (action.equalsIgnoreCase("delete_img")){
                    Integer detailId = Integer.valueOf(request.getParameter("id"));
                    dao.deleteImage(detailId);
                    forward = LIST;
                    request.setAttribute("details", dao.getAllDetails());
                } else if (action.equalsIgnoreCase("delete_img_form")){
                    Integer detailId = Integer.valueOf(request.getParameter("id"));
                    forward = INSERT_OR_EDIT;
                    request.setAttribute("detail", dao.getDetailById(detailId));
                } else if (action.equalsIgnoreCase("edit")){
                    forward = INSERT_OR_EDIT;
                    Integer detailId = Integer.valueOf(request.getParameter("id"));
                    Detail detail = dao.getDetailById(detailId);
                    request.setAttribute("detail", detail);
                } else if (action.equalsIgnoreCase("list")){
                    forward = LIST;
                    request.setAttribute("details", dao.getAllDetails());
                } else {
                    forward = INSERT_OR_EDIT;
                    request.setAttribute("detail", dao.clearDetail());
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
            Detail detail = new Detail();
            
            if (!(StringUtils.isNullOrEmpty(request.getParameter("name")) ||
                StringUtils.isNullOrEmpty(request.getParameter("description")) || 
                StringUtils.isNullOrEmpty(request.getParameter("created"))))
            {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    detail.setName(request.getParameter("name"));
                    detail.setDescription(request.getParameter("description"));
                    
                    Date dt = df.parse(request.getParameter("created")); 
                    detail.setCreated(dt);
                    
                    Part filePart = request.getPart("image");
 //                   if (filePart != null && filePart.getSubmittedFileName() != null) {
                     if (filePart != null && filePart.getContentType()!= null) {
                        detail.setImage(filePart.getInputStream());
                    }

                    String id = request.getParameter("id");

                    if(StringUtils.isNullOrEmpty(id))
                    {
                        detail = dao.addDetail(detail);
                    }
                    else
                    {
                        detail.setId_detail(Integer.valueOf(id));
                        dao.updateDetail(detail);
                    }
                    //dao.getDetailById(detail.getId_detail());
                    detail = dao.clearDetail();

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(400);
                }
            }
            else
                response.setStatus(415);
            
            request.setAttribute("detail", detail);
            
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
