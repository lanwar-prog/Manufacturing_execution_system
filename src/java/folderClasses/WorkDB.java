package folderClasses;

import ShowPlan.Block;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WorkDB {
    
    //Ковалев - соединение с БД
    private Connection c;
    private Statement s;
    private ResultSet rs;
    private String str; 

    public WorkDB(){
        
    }
    
    public String TestConnectDB(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        ConnectDB(request, response);
        //c.close();
        //s.close();
        return str;
    }
    public void ConnectDB(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        try {
              //ConnectDB() ;
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "123456");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "utf-8");
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
             c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", properties);
             s = (Statement) c.createStatement();
            
             //c.close(); 
            str = "Соединеине с базой данный mesdb установлено";
      
        } catch (SQLException ex) {
            Logger.getLogger(WorkDB.class.getName()).log(Level.SEVERE, null, ex);
            HttpSession session = request.getSession();
            session.setAttribute("stringState", "Нет соединения с базой. "+"(" +ex+") "
                    + "<a href=\"EnterPage.jsp\">Страница входа</a>");
            response.sendRedirect("Error.jsp");
           // c.close();
           // s.close();
           // return "Нет соединения с базой по причине: "+ ex;
        }
    }
 
    //Ковалев --------------------+
    
    public int getWidthOperationStart(int idOperation) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();
            ResultSet rs = s.executeQuery("select * from `mesdb`.`operation` where id_operation=" + idOperation + "");
            //LIB
            //int res = 0;
            int res = 5;
            if (rs.next()) {
                res = rs.getInt("TimeSetup");
            }else{
                System.out.println("НЕ найдена продолжительность операции "+idOperation);
            }
            c.close();
            return res;
        } catch (SQLException e) {
            return 0;
        }

    }

    public int getWidthOperationEnd(int idOperation) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();
            ResultSet rs = s.executeQuery("select * from `mesdb`.`operation` where id_operation=" + idOperation + "");
            //LIB
            //int res = 0;
            int res = 5;
            if (rs.next()) {
                res = rs.getInt("TimeCompletion");
            }else{
                System.out.println("НЕ найдена продолжительность операции "+idOperation);
            }
            c.close();
            return res;
        } catch (SQLException e) {
            return 0;
        }

    }

    /**
     *
     * @param name
     * @param time
     * @param timeStart
     * @param timeEnd
     * @param task
     * @return
     */
    public boolean addOperation(String name, String time, String timeStart, String timeEnd, String task) {

        String table = "`mesdb`.`operation`";
        String nameId = "id_operation";

        int id = getMaxId(table, nameId) + 1;

        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "123456");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "utf-8");

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", properties);
            Statement s = (Statement) c.createStatement();

            String query = "insert into mesdb.operation values(" + id + ", '" + name + "', " + time + ", " + timeStart + ", " + timeEnd + ", " + task + ");";
            s.executeUpdate(query);
            c.close();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    public static String FloatFormat(float f){
        String s="";
        if(f%1==0){ //целое
            s=String.valueOf((int)f);
        }else{
            Float.toString(f);
        }
        return s;
    }

    //выполнение запроса не возвращающего результатов к БД MySQL
    public boolean QueryDB(String SQLQuery){
        System.out.println("Выполнение запроса к БД:"+SQLQuery);
        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "123456");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "utf-8");
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", properties);
            Statement s = (Statement) c.createStatement();
            s.executeUpdate(SQLQuery);
            c.close();
            //System.out.println("Успех");
            return true;
        } catch (SQLException e) {
            //System.out.println("Былинный отказ - "+e.getMessage());
            return false;
        }
    } 
    //выполненин запроса к БД MySQL и возврат результатов в виде ArrayList с элементами - массивами Object
    public ArrayList<Object[]> GetSelectFromDB(String SQLSelect) {
        System.out.println("Получение выборки из БД:"+SQLSelect);
        ArrayList res=new ArrayList();
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();
            ResultSet rs = s.executeQuery(SQLSelect);
            ResultSetMetaData rsmd=rs.getMetaData();
            int FieldCount=rsmd.getColumnCount();
            try{
                while (rs.next()) {
                    Object[] RowData = new Object[FieldCount];
                    for(int j = 0; j < FieldCount; j++) {
                        //System.out.println(rsmd.getColumnType(j+1));
                        switch(rsmd.getColumnType(j+1)){  //разбираю сейчас только integer и varchar, float добавился...
                            case Types.FLOAT:
                            case Types.REAL:                                
                                RowData[j] = rs.getFloat(j+1);
                                break;
                            case Types.INTEGER:
                                RowData[j] = rs.getInt(j+1);
                                break;
                            case Types.VARCHAR:
                                RowData[j] = rs.getString(j+1);
                                if(RowData[j]==null){
                                    RowData[j]="";
                                }
                                break;
                            default:
                                //System.out.println("Не обработан тип "+rsmd.getColumnType(j+1));
                                break;
                        }
                    }
                    res.add(RowData);
                }
                //System.out.println("Успех");
            }catch(java.sql.SQLException e){
                //System.out.println("Былинный отказ:"+e.getMessage());
            }
            c.close();
            return res;
        } catch (SQLException e) {
            return res;
        }
    }
    //добавление или изменение названия склада
    public boolean AddEditWorkHouse(String id_workhouse,String name){
        Formatter f=new Formatter(Locale.US);
        f.format(   
                "INSERT INTO "+
                "   `mesdb`.`workhouse`(\n" +
                "	`id_workhouse`,\n" +
                "	`name`)\n" +
                "VALUES('%1$s',\n" +
                "	'%2$s')\n" +
                "on duplicate key\n" +
                "	update name='%2$s'",
                id_workhouse,
                name);
        return QueryDB(f.toString());
    }
    //добавление или изменение названия товара
    public boolean AddEditEdIzm(String id_ed_izm,String name){
        Formatter f=new Formatter(Locale.US);
        f.format(
                "INSERT INTO "+
                "   `mesdb`.`ed_izm`(\n" +
                "	`id_ed_izm`,\n" +
                "	`name`)\n" +
                "VALUES('%1$s',\n" +
                "	'%2$s')\n" +
                "on duplicate key\n" +
                "	update name='%2$s'",
                id_ed_izm,
                name);
        return QueryDB(f.toString());
    }
    //добавление или изменение названия товара
    public boolean AddEditArticle(String id_article,String id_ed_izm,String name){
        Formatter f=new Formatter(Locale.US);
        f.format(
                "INSERT INTO "+
                "   `mesdb`.`article`(\n" +
                "	`id_article`,\n" +
                "	`id_ed_izm`,\n" +
                "	`name`)\n" +
                "VALUES('%1$s',\n" +
                "       '%2$s',\n"+
                "	'%3$s')\n" +
                "on duplicate key update \n" +
                "	name='%3$s',\n"+
                "       id_ed_izm='%2$s'",
                id_article,
                id_ed_izm,
                name);
        return QueryDB(f.toString());
    }
    //добавление или изменение названия подразделения
    public boolean AddEditUnit(String id_unit,String name){
        Formatter f=new Formatter(Locale.US);
        f.format(
                "INSERT INTO "+
                "   `mesdb`.`unit`(\n" +
                "	`id_unit`,\n" +
                "	`name`)\n" +
                "VALUES('%1$s',\n" +
                "	'%2$s')\n" +
                "on duplicate key update \n" +
                "	name='%2$s'",
                id_unit,
                name);
        return QueryDB(f.toString());
    }
    //добавление или изменение названия подразделения
    public boolean AddEditEmployee1C(String id_employee,String name){
        Formatter f=new Formatter(Locale.US);
        f.format(
                "INSERT INTO "+
                "   `mesdb`.`employee_1c`(\n" +
                "	`id_employee`,\n" +
                "	`name`)\n" +
                "VALUES('%1$s',\n" +
                "	'%2$s')\n" +
                "on duplicate key update \n" +
                "	name='%2$s'",
                id_employee,
                name);
        return QueryDB(f.toString());
    }

    //добавление или изменение остатка товара
    public boolean AddEditArticleRest(String id_article,String id_workhouse,float coming,float expense,float rest){
        Formatter f=new Formatter(Locale.US);
        f.format(
                "INSERT INTO `mesdb`.`article_rest`(\n" +
                "	`id_article`,\n" +
                "	`id_workhouse`,\n" +
                "	`coming`,\n" +
                "	`expense`,\n" +
                "	`rest`)\n" +
                "VALUES(\n" +
                "	'%1$s',\n" +
                "	'%2$s',\n" +
                "	%3$f,\n" +
                "	%4$f,\n" +
                "	%5$f)\n" +
                "on duplicate key update\n" +
                "	`coming`=%3$f,\n" +
                "	`expense`=%4$f,\n" +
                "	`rest`=%5$f",
                id_article,
                id_workhouse,
                coming,
                expense,
                rest);
        return QueryDB(f.toString());
    }

    //добавление или изменение материалов в эксплуатации
    public boolean AddEditArticleExp(String id_article,String id_unit,String id_employee,float coming,float expense,float rest){
        Formatter f=new Formatter(Locale.US);
        f.format(
                "INSERT INTO `mesdb`.`article_exp`(\n" +
                "	`id_article`,\n" +
                "	`id_unit`,\n" +
                "	`id_employee_1c`,\n" +
                "	`coming`,\n" +
                "	`expense`,\n" +
                "	`rest`)\n" +
                "VALUES(\n" +
                "	'%1$s',\n" +
                "	'%2$s',\n" +
                "	'%3$s',\n" +
                "	%4$f,\n" +
                "	%5$f,\n" +
                "	%6$f)\n" +
                "on duplicate key update\n" +
                "	`coming`=%4$f,\n" +
                "	`expense`=%5$f,\n" +
                "	`rest`=%6$f",
                id_article,
                id_unit,
                id_employee,
                coming,
                expense,
                rest);
        return QueryDB(f.toString());
    }
    //удаление всех остатков из таблицы
    public boolean DelAllArticleRest(){
        return QueryDB("delete from article_rest");
    }
    //удаление материалов в эксплуатации
    public boolean DelAllArticleExp(){
        return QueryDB("delete from article_exp");
    }
    //выборка из таблицы article_rest - отчёт по остаткам товаров на складах
    public ArrayList<Object[]> GetArticleRest(){
        return GetSelectFromDB(
                "select\n" +
                "   article.id_article,\n" +
                "   workhouse.id_workhouse,\n" +
                "   article.name as 'article_name',\n" +
                "   workhouse.name as 'workhouse_name',\n" +
                "   article_rest.coming,\n" +
                "   article_rest.expense,\n" +
                "   article_rest.rest,\n" +
                "   ed_izm.name as 'ed_izm_name',\n" +
                "   ed_izm.id_ed_izm \n" +
                "from\n" +
                "	article_rest\n" +
                "    inner join article on\n" +
                "		article.id_article=article_rest.id_article\n" +
                "	inner join workhouse on\n" +
                "		workhouse.id_workhouse=article_rest.id_workhouse\n" +
                "	inner join ed_izm on\n" +
                "		ed_izm.id_ed_izm=article.id_ed_izm\n" +
                "order by\n" +
                "	article_rest.id_workhouse,\n" +
                "    article_rest.id_article");
    }
    //выборка из таблицы article_учз - отчёт по материалам в эксплуатации
    public ArrayList<Object[]> GetArticleExp(){
        return GetSelectFromDB(
                "select\n" +
                "   article_exp.*,\n" +
                "   article.name as article_name,\n" +
                "   unit.name as unit_name,\n" +
                "   employee_1c.name as employee_1c_name \n" +
                "from\n" +
                "   article_exp\n" +
                "   inner join article on\n" +
                "       article.id_article=article_exp.id_article\n" +
                "   inner join unit on\n" +
                "       unit.id_unit=article_exp.id_unit\n" +
                "   inner join employee_1c on\n" +
                "       employee_1c.id_employee=article_exp.id_employee_1c\n" +
                "order by\n" +
                "   article_exp.id_unit,\n" +
                "   article_exp.id_article,\n" +
                "   article_exp.id_employee_1c");
    }
    
    public boolean addOper(String kodDetail, String kodOperation) {
        String table = "`mesdb`.`operationdetail`";
        String nameId = "id_operdet";

        int id = getMaxId(table, nameId) + 1;

        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "123456");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "utf-8");

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", properties);
            Statement s = (Statement) c.createStatement();

            String query = "insert into `mesdb`.`operationdetail` values (" + id + ", " + kodDetail + ", " + kodOperation + ", 1)";
            s.executeUpdate(query);
            c.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addMeterial(String name, String number, String deminsion, String idSuplier) {
        String table = "`mesdb`.`materials`";
        String nameId = "id_material";

        int id = getMaxId(table, nameId) + 1;

        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "123456");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "utf-8");

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", properties);
            Statement s = (Statement) c.createStatement();

            String query = "insert into `mesdb`.`materials` values(" + id + ", '" + name + "', " + number + ", '" + deminsion + "')";
            //String query2 = "insert ";
            s.executeUpdate(query);
            //s.executeUpdate(query2);
            c.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addTask(String name) {
        String table = "`mesdb`.`task`";
        String nameId = "id_task";

        int id = getMaxId(table, nameId) + 1;
        String query = "insert into " + table + " values(" + id + ", '" + name + "');";

        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "123456");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "utf-8");

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", properties);
            Statement s = (Statement) c.createStatement();

            s.executeUpdate(query);
            c.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public String getPassword(String login) {

        String password = "";
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM `mesdb`.`employee` WHERE login = '" + login + "'");
            if (rs.next()) {
                password = rs.getString("Password");
                String t = password;
            }
            c.close();
            return password;
        } catch (SQLException e) {
            return "";
        }
    }

    public boolean getLogin(String login) throws SQLException {

        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
        Statement s = (Statement) c.createStatement();

        ResultSet rs = s.executeQuery("SELECT * FROM employee WHERE login = '" + login + "'");
        if (rs.next()) {
            c.close();
            return true;
        } else {
            c.close();
            return false;
        }
    }

    public int getRole(String login) throws SQLException {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb?useUnicode=true&characterEncoding=UTF-8", "root", "123456");
            Statement s = (Statement) c.createStatement();
            int role = 0;
            ResultSet rs = s.executeQuery("SELECT * FROM `mesdb`.`employee` WHERE login = '" + login + "'");
            if (rs.next()) {
                role = rs.getInt("Role");
            }

            c.close();
            return role;
        } catch (SQLException ex) {
            Logger.getLogger(WorkDB.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    public String getFIO(String login) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();

            String name = "������";
            ResultSet rs = s.executeQuery("SELECT * FROM `mesdb`.`employee` WHERE login = '" + login + "'");
            if (rs.next()) {
                name = rs.getString("Name");// + " " + rs.getString("Surname");
                name += " " + rs.getString("Surname");
            }

            c.close();
            return name;
        } catch (SQLException e) {
            return "������";
        }
    }

    public boolean addOrder(int idDetail, int numberDetail, String city, int organizationId, String date, int urgency, String dateExecution) {
        String table = "`mesdb`.`clientorder`";
        String nameId = "id_order";

        String[] a = dateExecution.split("-");
        String test = "" + a[0] + "-" + a[1] + "-" + a[2] + " 8:00:00";

        int id = getMaxId(table, nameId) + 1;
        String query = "INSERT INTO " + table + " VALUES(" + id + ", " + organizationId + ", NOW(), "
                + "" + urgency + ", 0, " + numberDetail + ", " + idDetail + ", '" + test + "')";

        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "123456");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "utf-8");

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();

            s.executeUpdate(query);
            c.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addClient(String nameOrganisation, String minName, String telephone, String address, String city) {
        String table = "`mesdb`.`client`";
        String nameid = "id_client";
        int id = getMaxId(table, nameid) + 1;
        String query = "insert " + table + " values(" + id + ", '" + nameOrganisation + "',"
                + " '" + telephone + "', '" + address + "', '" + city + "', '" + minName + "');";

        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "123456");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "utf-8");

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", properties);
            Statement s = (Statement) c.createStatement();

            s.executeUpdate(query);
            c.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addEmployee(String name, String surname, int role, String login, String password, String telephone, String date) {
        String table = "`mesdb`.`employee`";
        String nameId = "id_employee";

        int id = getMaxId(table, nameId) + 1;
        String query = "insert " + table + " VALUES (" + id + ", '" + name + "', '" + surname + "', '" + login + "', "
                + role + ", '" + password + "', '" + telephone + "', NOW(), " + null + ")";

        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "123456");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "utf-8");

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", properties);//, "root", "123456");//
            Statement s = (Statement) c.createStatement();

            s.executeUpdate(query);
            c.close();
            return true;
        } catch (SQLException e) {
            e.getErrorCode();
            return false;
        }
    }

    public int getMaxId(String table, String nameId) {
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

    public boolean addSuplier(String name, String minname, String city, String street, String house, String phone, String email) {
        String table = "`mesdb`.`supplier`";
        String nameId = "id_supplier";
        int id = getMaxId(table, nameId) + 1;
        String query = "INSERT INTO " + table + " VALUES(" + id + ", '" + name + "', '" + minname + "', "
                + "'" + city + "', '" + street + "', '" + house + "', '" + phone + "', '" + email + "')";

        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "123456");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "utf-8");

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", properties);
            Statement s = (Statement) c.createStatement();

            s.executeUpdate(query);
            c.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addDetail(String name, String description) {
        String table = "`mesdb`.`detail`";
        String nameId = "id_detail";
        int id = getMaxId(table, nameId) + 1;
        String query = "INSERT INTO " + table + " VALUES (" + id + ", '" + name + "', '" + description + "', NOW())";

        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "123456");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "utf-8");

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", properties);
            Statement s = (Statement) c.createStatement();

            s.executeUpdate(query);
            c.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addPressform(String name) {
        String table = "`mesdb`.`pressforms`";
        String nameId = "id_pressform";
        int id = getMaxId(table, nameId) + 1;
        String query = "INSERT INTO " + table + " values(" + id + ", '" + name + "', 1);";

        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "123456");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "utf-8");

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", properties);
            Statement s = (Statement) c.createStatement();

            s.executeUpdate(query);
            c.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean addWorkplace(String name) {
        String table = "`mesdb`.`workplace`";
        String nameId = "id_workplace";
        int id = getMaxId(table, nameId) + 1;

        String query = "INSERT INTO" + table + " values(" + id + ", '" + name + "', NOW(), 1, null);";

        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "123456");
            properties.setProperty("useUnicode", "true");
            properties.setProperty("characterEncoding", "utf-8");

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", properties);
            Statement s = (Statement) c.createStatement();

            s.executeUpdate(query);
            c.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean lineUsed(int line) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();
            Statement sL = (Statement) c.createStatement();

            ResultSet rs = s.executeQuery("select * from `mesdb`.`datamodel` order by `Created` DESC");

            if (rs.next()) {
                int idDatamodel = rs.getInt("id_datamodel");

                ResultSet rsL = sL.executeQuery("select * from `mesdb`.`line` where id_datamodel=" + idDatamodel + " and id_wodkplace=" + line + "");
                if (rsL.next()) {
                    c.close();
                    return true;
                } else {
                    c.close();
                    return false;
                }
            } else {
                c.close();
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public ArrayList<Block> getTaskLine(int line) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();
            Statement s2 = (Statement) c.createStatement();
            Statement s3 = (Statement) c.createStatement();
            ResultSet rs = s.executeQuery("select * from `mesdb`.`datamodel` order by `Created` DESC");
            if (rs.next()) {
                int idDatamodel = rs.getInt("id_datamodel");
                ResultSet rs2 = s2.executeQuery("select * from `mesdb`.`line` where id_datamodel=" + idDatamodel + " and id_wodkplace=" + line + "");
                if (rs2.next()) {
                    int idLine = rs2.getInt("id_line");
                    ResultSet rs3 = s3.executeQuery("select b.*, d.`Name` as detailname, o.`Name` as opername from `mesdb`.`block` b left join `mesdb`.`detail` d on d.`id_detail` = b.`id_detail` left join `mesdb`.`operation` o on o.`id_operation` = b.`id_operation` where b.`id_Line`=" + idLine + " order by b.`timeStart`");
                    ArrayList<Block> b = new ArrayList<Block>();
                    while (rs3.next()) {
                        Block block = new Block();

                        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = rs3.getTimestamp("timeStart");
                        Calendar temp = Calendar.getInstance();
                        temp.setTime(date);

                        block.setDateBegin(temp);
                        block.setTimeLenght(rs3.getInt("time"));
                        block.setNumbersDetail(rs3.getInt("numberDetails"));
                        block.setIdDetail(rs3.getInt("id_detail"));
                        block.setDetailName(rs3.getString("detailname"));
                        block.setIdOperation(rs3.getInt("id_operation"));
                        block.setOperName(rs3.getString("opername"));
                        block.setColor(rs3.getString("color"));
                        b.add(block);
                    }
                    return b;
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } catch (SQLException e) {
            return null;
        } /*catch (ParseException e) {
            return null;
        }*/
    }
    
    public Calendar checkWorkTime(Calendar start, int time) {
        int work_days = (time / 3600) / 8;
        int h = (time / 3600) % 8;
        int m = (time / 60) % 60;
        int s = time % 60;
        start.add(Calendar.DATE, work_days);
        start.add(Calendar.HOUR_OF_DAY, h);
        if (start.get(Calendar.HOUR_OF_DAY) >= 17) {
            start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
            start.set(Calendar.HOUR_OF_DAY, 9 + start.get(Calendar.HOUR_OF_DAY) - 17);
        }
        start.add(Calendar.MINUTE, m);
        if (start.get(Calendar.HOUR_OF_DAY) >= 17) {
            start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
            start.set(Calendar.HOUR_OF_DAY, 9 + start.get(Calendar.HOUR_OF_DAY) - 17);
        }
        start.add(Calendar.SECOND, s);
        if (start.get(Calendar.HOUR_OF_DAY) >= 17) {
            start.set(Calendar.DATE, start.get(Calendar.DATE) + 1);
            start.set(Calendar.HOUR_OF_DAY, 9 + start.get(Calendar.HOUR_OF_DAY) - 17);
        }
        
        return start;
    }
}
