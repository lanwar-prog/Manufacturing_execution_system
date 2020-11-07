package Contr;

import Dao.EdDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Dao.MaterialsDao;
import Dao.SupplierDao;
import Model.Materials;
import com.mysql.jdbc.StringUtils;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;

public class MaterialsContr extends HttpServlet {

    private static final long serialVersionUID = 102831973239L;
    private static String INSERT_OR_EDIT = "/AddMaterial.jsp";
    private static String LIST = "/ListMaterials.jsp";
    private MaterialsDao dao;
    private EdDao ed_dao;
    private SupplierDao supplier_dao;

    public MaterialsContr() {
        super();
        dao = new MaterialsDao();
        ed_dao = new EdDao();
        supplier_dao = new SupplierDao();
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
            out.println("<h1>Servlet MaterialsContr at " + request.getContextPath() + "</h1>");
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
                    Integer materialsId = Integer.valueOf(request.getParameter("id"));
                    dao.deleteMaterials(materialsId);
                    forward = LIST;
                    request.setAttribute("materials", dao.getAllMaterials());    
                } else if (action.equalsIgnoreCase("edit")){
                    forward = INSERT_OR_EDIT;
                    Integer materialsId = Integer.valueOf(request.getParameter("id"));
                    Materials material = dao.getMaterialsById(materialsId);
                    request.setAttribute("material", material);
                } else if (action.equalsIgnoreCase("list")){
                    forward = LIST;
                    request.setAttribute("materials", dao.getAllMaterials());
                } else {
                    forward = INSERT_OR_EDIT;
                    request.setAttribute("material", dao.clearMaterials());
                }

                request.setAttribute("eds", ed_dao.getAllEd());
                request.setAttribute("suppliers", supplier_dao.getAllSupplier());
                
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
            Materials materials = new Materials();
            
            if (!(StringUtils.isNullOrEmpty(request.getParameter("name")) ||
                StringUtils.isNullOrEmpty(request.getParameter("number")) || 
                StringUtils.isNullOrEmpty(request.getParameter("ed_id")) ||
                StringUtils.isNullOrEmpty(request.getParameter("supplier_id"))))
            {
                try {
                    materials.setName(request.getParameter("name"));
                    materials.setNumber(Integer.valueOf(request.getParameter("number")));
                    materials.setEd_id(Integer.valueOf(request.getParameter("ed_id")));
                    materials.setSupplier_id(Integer.valueOf(request.getParameter("supplier_id")));

                    String id = request.getParameter("id");

                    if(StringUtils.isNullOrEmpty(id))
                    {
                        materials = dao.addMaterials(materials);
                    }
                    else
                    {
                        materials.setId_material(Integer.valueOf(id));
                        dao.updateMaterials(materials);
                    }
                    //dao.getMaterialsById(materials.getId_material());
                    materials = dao.clearMaterials();

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatus(400);
                }
            }
            else
                response.setStatus(415);
            
            request.setAttribute("material", materials);
            request.setAttribute("eds", ed_dao.getAllEd());
            request.setAttribute("suppliers", supplier_dao.getAllSupplier());
            
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
