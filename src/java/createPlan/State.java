package createPlan;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import folderClasses.WorkDB;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class State {

    private Calendar dateBeginPlan = Calendar.getInstance();
    private Calendar endDate;
    private ArrayList<String> tasks = new ArrayList<String>();
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<Workplace> workplaces = new ArrayList<Workplace>();
    private Line[] lines;
    private Operation[] operations;
    private DataModel datamodel;// = new DataModel();
    WorkDB database = new WorkDB();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Calendar getDateBeginPlan(){
        return dateBeginPlan;
    }
    
    public void setLines(Line[] l){
        lines=l;
    }
    public void setOperations(Operation[] o){
        operations=o;
    }
    
    public ArrayList<Order> getOrders(){
        return orders;
    }
    public void setDateTime(Calendar cal) {
        this.dateBeginPlan = cal;
    }

    public void setWorkplace(ArrayList<Workplace> lst) {
        this.workplaces = lst;
    }

    public void setOrder(ArrayList<Order> lst) {
        this.orders = lst;
    }

    public String getHtmlPlan() {
      
        return "HELLO WORLD!";
    }

    //public int getEnergy() {
    // кол-во невыполненных заказов в срок
    // или общее время выполнения заказов
    // энергия вычисляется в datamodel
    //  this.datamodel.getEnergy();
    // return 0;
    //}
    public ArrayList<String> getListTask() {
        return this.tasks;
    }

    public Calendar getEndDate() {
        return this.datamodel.getEndDate();
    }
    public Calendar getPlanEndDate(){
        return this.endDate;
    }
    
    public void SetPlanEndDate(Calendar c){
        this.endDate=c;
        //this.datamodel.StartTimeDataModel=c;
    }
    
    public void loadOperatios(){
        
    }
    
    public Operation[] getOperations(){
        return operations;
    }
    
    public Line[] getLines(){
        return lines;
    }
  
    private String getColor(int order) {
        if (order == 0) {
            return "8B0000";
        }
        if (order == 1) {
            return "008B8B";
        }
        if (order == 2) {
            return "00008B";
        }
        if (order == 3) {
            return "9B30FF";
        }
        if (order == 4) {
            return "FF0000";
        }
        if (order == 5) {
            return "2E8B57";
        }
        if (order == 6) {
            return "BDB76B";
        } 
        if (order==7)
            return "808080";
        if (order==8)
            return "404080";
        if (order==9)
            return "208020";
        if (order==10)
            return "10FF00";
        else {
            return "BABA00";
        }
    }

    private int getOptimalWorkplace(String task) {
        ArrayList<Integer> tempWork = new ArrayList<Integer>();

        //выбираем все р.места с подходящим типом задач
        for (int i = 0; i < this.workplaces.size(); i++) {
            String t = workplaces.get(i).getTaskName();
            if (t.equals(task)) {
                tempWork.add(i);
            }
        }

        Calendar minC = Calendar.getInstance();
        minC = datamodel.lines.get(0).getEndDate();
        int testLine = tempWork.get(0);
        for (int i = 0; i < datamodel.lines.size(); i++) {
            if (minC.after(datamodel.lines.get(0).getEndDate())) {
                minC = datamodel.lines.get(0).getEndDate();
                testLine = tempWork.get(i);
            }
        }

        return testLine;
    }

    public void setDataModel(DataModel model) {
        this.datamodel = model;
    }

    public DataModel getDataModel() {
        return this.datamodel;
    }

    public void savePlan() {
        WorkDB wdb = new WorkDB();
        try {
            wdb.QueryDB("DELETE FROM datamodel");
            wdb.QueryDB("DELETE FROM line");
            wdb.QueryDB("DELETE FROM block");
            int idDatamodel = saveDatamodel();
            for (int i = 0; i < datamodel.getSizeLines(); i++) {
                Line line = datamodel.getLine(i);
                int idLine = saveLine(line, idDatamodel);
                saveBlocks(idLine, line);
            }
        }
        catch (Exception e) {
        }
    }

    private int saveDatamodel() {

        String tableName = "`mesdb`.`datamodel`";
        String idName = "id_datamodel";
        int idDM = this.getMaxId(tableName, idName) + 1;
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateComleted = sdfNow.format(datamodel.getEndDate().getTime());
        String dateStart = sdfNow.format(dateBeginPlan.getTime());
        String query = "INSERT `mesdb`.`datamodel` VALUES (" + idDM + ", "
                + "NOW(), '" + dateComleted + "', 1, '" + dateStart + "')";

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();

            s.executeUpdate(query);
        } catch (SQLException e) {
        }
        return idDM;
    }

    private int saveLine(Line l, int idDataModel) {
        String tableName = "`mesdb`.`line`";
        String idName = "id_line";
        int idline = getMaxId(tableName, idName) + 1;
        String query = "INSERT " + tableName + " values(" + idline + ", '" + l.getName() + "', " + l.getIdWorkplace() + ", " + idDataModel + ")";

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();

            s.executeUpdate(query);
        } catch (SQLException e) {

        }
        return idline;
    }

    private void saveBlocks(int idline, Line line) {
        String tableName = "`mesdb`.`block`";
        String idName = "id_Block";
        System.out.println("Сохранение линии idline - "+idline);
        
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();
        
            for (int i = 0; i < line.getSizeBlocks(); i++) {
                Block b = line.getBlock(i);

                int idBlock = getMaxId(tableName, idName) + 1;
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateRes = sdfNow.format(b.getDateBegin().getTime());

                String query;
                if (b.getIdDetail() == 0) {
                    query = "INSERT " + tableName + " values (" + idBlock + ", 1, 0, '" + dateRes + "', "
                            + "" + b.getWidth() + ", 1, '" + b.getColor() + "', 0, " + idline + ")";

                } else {
                    query = "INSERT " + tableName + " values (" + idBlock + ", " + b.getIdDetail() + "," + b.idOrder + ", '" + dateRes + "', "
                            + "" + b.getWidth() + ", " + b.getIdOperation() + ", '" + b.getColor() + "', " + b.getNumberDetail() + ", " + idline + ")";
                }

                try {
                    System.out.println("\t\t блок №"+i+"\t"+query);
                    s.executeUpdate(query);

                } catch (SQLException e) {
                    System.out.println("Исключение при сохранении плана:"+e.getMessage());
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Сохранение линии idline - "+idline+" ....");
    }

    private int getMaxId(String table, String nameId) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();

            int id = 0;
            ResultSet rs = s.executeQuery("SELECT " + nameId + " FROM " + table + " ORDER BY " + nameId + " DESC;");
            if (rs.next()) {
                id = rs.getInt(nameId);
            }
            c.close();
            return id;
        } catch (SQLException e) {
            return 0;
        }
    }
}
