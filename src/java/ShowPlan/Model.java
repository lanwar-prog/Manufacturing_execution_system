package ShowPlan;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Model {

    private Date Created; // дата создания модели
    private Calendar startTime = Calendar.getInstance(); // начало модели
    private int numberModel; // id модели 
    private ArrayList<Line> lines = new ArrayList<Line>(); // список линий
    
    public String showLineAll() {
        loadModel();
        loadLines();
         String res = "";
        for (int i = 0; i < lines.size(); i++){
            res += "<iframe style=\"text-align: center; left: 0%;\" src=\"Line.jsp?line=" + lines.get(i).getIdWorkplace()+"\" frameborder=\"0\" onload=\"this.height=this.contentWindow.document.body.scrollHeight; this.width=this.contentWindow.document.body.scrollWidth;\" scrolling=\"no\"></iframe>";
        }
        return res;
    }
    
    public String getJSONData(int line){ // данные линий в JSON формате
        String JSON="";
        loadPlan(); // загрузка плана
        JSONArray JSONLines = new JSONArray(); // массив линий
        System.out.println("lines.size()="+lines.size());
        
        JSONObject JSONOrderDateExecution = new JSONObject(); // дата выполнения заказа      
        for (int i = 0; i < lines.size(); i++) { // цикл по всем линиям
            JSONObject JSONLine = new JSONObject(); // линия
            Line l=lines.get(i);
            JSONLine.put("id_line", l.getIdWorkplace());
            JSONLine.put("name", l.getCurrentName());
            JSONArray ar = new JSONArray(); // массив блоков на линии
            for(Block b:l.getBlocks()){ // цикл по блокам линии
                JSONObject JSONBlock = new JSONObject(); // блок
                JSONBlock.put("id_detail", b.getIdDetail());
                JSONBlock.put("id_operation", b.getIdOperation());
                JSONBlock.put("id_order", b.getIdOrder());
                JSONBlock.put("number_details", b.getNumberDetails());
                JSONBlock.put("time_length", b.getTimeLenght());
                JSONBlock.put("width", b.getWidth());
                if(!JSONOrderDateExecution.containsKey(Integer.toString(b.getIdOrder()))){
                    JSONOrderDateExecution.put(Integer.toString(b.getIdOrder()), getDateExecutionOrder(b.getIdOrder()));
                }
                ar.add(JSONBlock);
            }
            JSONLine.put("blocks", ar);
            if(line==-1 || line==l.getIdLine()){
                JSONLines.add(JSONLine);
            }
        }
        JSON=   "\nl="+JSONLines.toString()+";"+
                "\nd="+JSONOrderDateExecution.toString()+";";
        
        return JSON;
    }
    

    public String getHtmlPlan() { // вывод плана на графике
        loadPlan(); // загрузка плана
        String res = "";

        res += "<div class=\"shadow\" id=\"out2\">";

        //int width = this.widthLines();//
        
        res += "<table cellpadding=\"0\" cellspacing=\"0\"><tr><td>";

        res += "\n\n\n<table cellpadding=\"0\" cellspacing=\"0\">";//==========================================
        res += "<tr id='td_time_table' >"; //<td id='td_time_table'>"; я
        /*
        //таблица - шкала времени
        width=width+(60-width%60);
        res += "<table width=" + width + " cellpadding=\"0\" cellspacing=\"0\" class=\"\">";//============================
        res += "<tr  style=\"outline: 1pt solid #8767E6;\">";
        //res += "<td width=\"80px\"></td>";
        for (int i = 0; i <= width  / 60; i++) {
            String col = "FFF0BE";
            if (i % 2 == 0) {
                col = "BFAC8F";
            }
            res += "<td valign=\"top\"  bgcolor=\"" + col + "\" width=\"60px\"> <font size=\"1pt\" face=\"Arial\">";
            res += sdf.format(temp.getTime());
            res += "</font></td>";
            temp.add(Calendar.SECOND, 60);
        }
        res += "</tr>";
        res += "</table>";
        */
        //res += "</td></tr><tr><td valign=\"top\">"; я
        res += "</tr>";
        res += "\n<tr id=\"out\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:5px\">";
        //res += "<td id='table_lines'>";
        
        //for (int i = 0; i < lines.size(); i++) {
            //res += "<tr>"; я
            //res += "\n\t<td align=\"right\" width=\"80px\" style=\"margin-top:10pt\" height=\"10px\"><a href=\"Line.jsp?line=" + lines.get(i).getIdWorkplace() + "\"><font color=\"black\"\">Line:";
            /*res += "\n\t<table><tr><td style='height:20px;' align=\"right\" style=\"white-space: nowrap;margin-top:10pt\" height=\"10px\"><a href=\"javascript: showModalWin2('popupWin"+lines.get(i).getIdWorkplace()+"');\"><font color=\"black\"><nobr>Линия №";
            res += lines.get(i).getIdWorkplace()+"\t"+lines.get(i).getCurrentName();
            res += "</nobr></font></a></td></tr></table>";*/
            //res += "<div style=\"text-align: center\" id=\"popupWin"+lines.get(i).getIdWorkplace()+"\" class=\"modalwin\"><iframe src =\"Listorder.jsp\"width=\"100%\" height=\"400\"></iframe></div></tr>";
            //<jsp:include page=\"Listorder.jsp\""
            //<jsp:include page=\"Line.jsp?line=" + lines.get(i).getIdWorkplace()+"\"
        //}
        //res += "</table>"; я

        //res += "</td><td id='td_line_table'><div id=\"out\" onmouseover=\"addLine ()\" onmouseout=\"removeLine ()\"></div>"; я
        /*
        //res+= "<div id=\"out\" onmouseover=\"addLine ()\" onmouseout=\"removeLine ()\"><div id=\"int\"></div>";
        res += "<div id=\"out\" onmouseover=\"addLine ()\" onmouseout=\"removeLine ()\"><div id=\"int\"></div>";//===============
        for (int i = 0; i < lines.size(); i++) {

            res += "<table cellpadding=\"0\" cellspacing=\"0\" class=\"mainClass\" style=\"margin-top:5px\" onmouseover=\"myFuncOver(event)\" onmouseout=\"myFuncOut(event)\">";

            res += "\n<tr style=\"margin-top=20%\">";

            Line line = lines.get(i);

            //res += "\n\t<td align=\"right\" width=\"80px\"><a href=\"Line.jsp?line=" + line.getIdWorkplace() + "\"><font color=\"black\">Line:";
            //res += "" + line.getIdWorkplace();
            //res += "</font></a></td>";
            for (int j = 0; j < line.getSize(); j++) {
                Block block = line.getBlock(j);
                if (block.getIdOperation()<=-1) {
                    if(block.getIdOperation()==-3){
                        res += "\n\t<td width=\"" + block.getWidth() + "px\" title=\"Простой оборудования: " + block.getWidth() +"\" bgcolor=\"#DCDCDA\"></td>";
                    }
                    if(block.getIdOperation()==-1){
                        res += "\n\t<td width=\"" + block.getWidth() + "px\" title=\"Подготовка оборудования: " + block.getWidth() +"\" bgcolor=\"#00DA00\"></td>";
                    }
                    if(block.getIdOperation()==-2){
                        res += "\n\t<td width=\"" + block.getWidth() + "px\" title=\"Очистка оборудования: " + block.getWidth() +"\" bgcolor=\"#DA0000\"></td>";
                    }
                }else{
                    //Если деталь первая ( 0 в массиве) то выполняется условие
                    if (block.getNumberDetails() != 0) {
                        res += "\n\t<td align=\"center\" style=\"outline: 1pt solid #8767E6;\" ";
                        res += "width=\"" + block.getWidth() + "px\" ";
                        res += "class=\"" + block.getIdOrder() + "\"";
                        res += "title=\"Код заказа: " + block.getIdOrder() + ""
                                + "&#13;Код операции: " + block.getIdOperation() + ""                            
                                + "&#13;Код изделия: " + block.getIdDetail() + ""
                                + "&#13;Время работы: " + block.getWidth() + ""
                                + "&#13;Время начала: " + block.getTimeStart() + ""
                                + "&#13;Время окончания: " + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(block.getEndDate().getTime())+ ""                            
                                + "&#13;Кол-во изделий: " + block.getNumberDetails() + ""
                                + "&#13;Дата выполнения: " + block.getDateComplete() + "\"";

                        //res += "bgcolor=\"#" + block.getColor() + "\"";
                        res += "bgcolor=\"A5CBF0\"";
                        res += ">" + block.getIdOrder();
                        res += "</td>";
                    }
                }
            }
            res += "\n</tr>";
            res += "\n</table>\n\n\n";

        }
        res += "</div>";
        */
        res += "<tr></table></td></tr></table>";
        res += "</div>";
        
        for (int i = 0; i < lines.size(); i++) { // создание iframe со списком операций для каждой линии

        res += "<div style=\"text-align: center;\" id=\"popupWin"+lines.get(i).getIdWorkplace()+"\" class=\"modalwin\">"
                + "<iframe src=\"Line.jsp?line=" + lines.get(i).getIdWorkplace()+"\" frameborder=\"0\" onload=\"this.height=this.contentWindow.document.body.scrollHeight; this.width=this.contentWindow.document.body.scrollWidth;\" scrolling=\"no\"></iframe>"
                
                + "</div>";

        }
        return res;
    }

    /*public String getHtmlPlan() {
     loadPlan();
     String res = "";

     SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
     Calendar temp = Calendar.getInstance();
     temp = (Calendar) startTime.clone();

     res += "<div class=\"shadow\" id=\"out2\">";

     int width = this.widthLines() + 300;//
     res += "<table width=" + width + " cellpadding=\"0\" cellspacing=\"0\"><tr><td>";

     res += "\n\n\n<table cellpadding=\"0\" cellspacing=\"0\">";//==========================================
     res += "<tr><td width=\"80px\"></td><td>";

     //таблица - шкала времени
     res += "<table cellpadding=\"0\" cellspacing=\"0\" class=\"\">";//============================
     res += "<tr  style=\"outline: 1pt solid #8767E6;\">";
     //res += "<td width=\"80px\"></td>";
     for (int i = 0; i < (width - 200) / 60; i++) {
     String col = "FFF0BE";
     if (i % 2 == 0) {
     col = "BFAC8F";
     }
     res += "<td valign=\"top\"  bgcolor=\"" + col + "\" width=\"60px\"> <font size=\"1pt\" face=\"Arial\">";
     res += sdf.format(temp.getTime());
     res += "</font></td>";
     temp.add(Calendar.SECOND, 60);
     }
     res += "</tr>";
     res += "</table>";

     res += "</td></tr><tr><td>";

     res += "\n\n\n<table cellpadding=\"0\" cellspacing=\"0\">";
     for (int i = 0; i < lines.size(); i++) {
     res += "<tr>";
     res += "\n\t<td align=\"right\" width=\"80px\"><a href=\"Line.jsp?line=" + lines.get(i).getIdWorkplace() + "\"><font color=\"black\">Line:";
     res += "" + lines.get(i).getIdWorkplace();
     res += "</font></a></td>";
     res += "</tr>";
     }
     res += "</table>";

     res += "</td><td>";
     //res+= "<div id=\"out\" onmouseover=\"addLine ()\" onmouseout=\"removeLine ()\"><div id=\"int\"></div>";
     res += "<div id=\"out\" onmouseover=\"addLine ()\" onmouseout=\"removeLine ()\"><div id=\"int\"></div>";//===============
     for (int i = 0; i < lines.size(); i++) {

     res += "<table cellpadding=\"0\" cellspacing=\"0\" class=\"mainClass\" style=\"margin-bottom:3px\" onmouseover=\"myFuncOver(event)\" onmouseout=\"myFuncOut(event)\">";

     res += "\n<tr style=\"margin-top=20%\">";

     Line line = lines.get(i);

     //res += "\n\t<td align=\"right\" width=\"80px\"><a href=\"Line.jsp?line=" + line.getIdWorkplace() + "\"><font color=\"black\">Line:";
     //res += "" + line.getIdWorkplace();
     //res += "</font></a></td>";
     for (int j = 0; j < line.getSize(); j++) {
     Block block = line.getBlock(j);

     int skip = line.getSkip(j);
     if (skip > 0) {
     res += "\n\t<td width=\"" + line.getSkip(j) + "px\" title=\"Простой оборудования: " + skip + " \" bgcolor=\"#DCDCDA\"></td>";
     }

     if (block.getNumberDetails() == 0) {
     res += "\n<td width=\"" + block.getWidth() + "\"";
     //res += "bgcolor=\"#" + block.getColor() + "\"";

     String color = block.getColor();
     if (color.equals("00FFFF")) {
     res += "bgcolor=\"#00CC66\"";
     } else {
     res += "bgcolor=\"#CC3333\"";
     }

     //res += "bgcolor=\"#6F8542";
     if (block.getColor().equals("00FFFF")) {
     res += "title=\"Подготовка оборудования\"";
     } else {
     res += "title=\"Очистка оборудования\"";
     }
     res += ">";
     res += "</td>";
     }

     if (block.getNumberDetails() != 0) {
     res += "\n\t<td align=\"center\" style=\"outline: 1pt solid #8767E6;\" ";
     res += "width=\"" + block.getWidth() + "px\" ";
     res += "class=\"" + block.getIdOrder() + "\"";
     res += "title=\"Код заказа: " + block.getIdOrder() + ""
     + "&#13;Код изделия: " + block.getIdDetail() + ""
     + "&#13;Время работы: " + block.getWidth() + ""
     + "&#13;Время начала: " + block.getTimeStart() + ""
     + "&#13;Кол-во изделий: " + block.getNumberDetails() + ""
     + "&#13;Дата выполнения: " + block.getDateComplete() + "\"";

     //res += "bgcolor=\"#" + block.getColor() + "\"";
     res += "bgcolor=\"A5CBF0\"";
     res += ">" + block.getIdOrder();
     res += "</td>";

     }
     }
     res += "\n</tr>";
     res += "\n</table>\n\n\n";

     }
     res += "</div>";
     res += "</td></tr></table>";//======================================================

     res += "</td></tr></table>";
     res += "</div>";
     return res;
     }*/
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // формат даты для обработки
    SimpleDateFormat format_view = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); // формат даты для отображения

    public String getDateBegin() {
        loadPlan();
        String res = "";
        if (startTime != null) {
            res = format.format(startTime.getTime());
        }
        return res;
    }
    
    public String getDateBeginView() {
        loadPlan();
        String res = "";
        if (startTime != null) {
            res = format_view.format(startTime.getTime());
        }
        return res;
    }

    public String getEndDate() { // окончание плана в формате для обработки
        //loadPlan();
        Calendar resultCalendar = Calendar.getInstance();
        resultCalendar = this.startTime;

        for (int i = 0; i < lines.size(); i++) {

            //String datea = format.format(lines.get(i).getEndDate());
            //String dateb = format.format(resultCalendar);
            if (lines.get(i).getEndDate().after(resultCalendar)) {
                resultCalendar = lines.get(i).getEndDate();
            }
        }
        return format.format(resultCalendar.getTime());
    }

    public String getEndDateView() { // окончание плана в формате для отображения
        //loadPlan();
        Calendar resultCalendar = Calendar.getInstance();
        resultCalendar = this.startTime;

        for (int i = 0; i < lines.size(); i++) {

            //String datea = format.format(lines.get(i).getEndDate());
            //String dateb = format.format(resultCalendar);
            if (lines.get(i).getEndDate().after(resultCalendar)) {
                resultCalendar = lines.get(i).getEndDate();
            }
        }
        return format_view.format(resultCalendar.getTime());
    }
        
    public void loadPlan() { // загрузка плана
        loadModel(); // загрузка модели
        loadLines(); // загрузка линий
        loadBlock(); // загрузка блоков
    }

    private void loadModel() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();

            String name = "Ошибка";
            ResultSet rs = s.executeQuery("SELECT * FROM `mesdb`.`datamodel` ORDER BY `Created` DESC");
            //SimpleDateFormat formate = new SimpleDateFormat("yyyy-dd-mm");
            SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (rs.next()) {
                this.numberModel = rs.getInt("id_datamodel");
                try {
                    this.Created = frmt.parse(rs.getString("Created"));
                    this.startTime.setTime(frmt.parse(rs.getString("startTime")));
                } catch (ParseException e) {
                }
            }
            c.close();
        } catch (SQLException e) {
            //
        }
    }

    private void loadLines() {
        this.lines = new ArrayList<Line>();
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();

            String name = "Ошибка";
            ResultSet rs = s.executeQuery(
                    "select \n" +
                    "   line.id_line,\n" +
                    "   line.id_wodkplace,\n" +
                    "   line.id_datamodel,\n" +
                    "   workplace.name \n" +
                    "from \n" +
                    "	line,\n" +
                    "   workplace \n" +
                    "where \n" +
                    "	id_datamodel="+numberModel+" and\n" +
                    "   line.id_wodkplace=workplace.id_workplace");

            Line line;
            while (rs.next()) {
                line = new Line();
                line.setIdLine(rs.getInt("id_line"));
                line.setName(rs.getString("Name"));
                line.setIdWorkplace(rs.getString("id_wodkplace"));
                line.setStartTimeModel(this.startTime);
                this.lines.add(line);
            }
            c.close();
        } catch (SQLException e) {
            //
        }
    }

    private void loadBlock() {
        try {
            for (int i = 0; i < lines.size(); i++) {

                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
                Statement s = (Statement) c.createStatement();

                Line l = lines.get(i);
                int idLine = l.getIdLine();
                String query = "SELECT * FROM `mesdb`.`block` WHERE id_Line=" + idLine + " order by `id_Block`";
                ResultSet rs = s.executeQuery(query);

                Block b;
                
                while (rs.next()) {
                    b = new Block();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(rs.getTimestamp("timeStart"));

                    //#0000FF
                    //rs.getString("color");
                    b.setColor(rs.getString("color"));//#xx xx xx
                    b.setDateBegin(calendar);
                    b.setIdDetail(rs.getInt("id_detail"));
                    b.setIdOperation(rs.getInt("id_operation"));
                    b.setNumbersDetail(rs.getInt("numberDetails"));
                    b.setTimeLenght(rs.getInt("time"));
                    b.setIdOrder(rs.getInt("id_Order"));

                    String t = this.getDateExecutionOrder(b.getIdOrder());
                    b.setDateComplete(t);

                    l.addBlock(b);
                }
            }
        } catch (SQLException e) {
            //
        }
    }

    private String getDateExecutionOrder(int i) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();

            String query = "select * from mesdb.clientorder where id_order=" + i + "";
            ResultSet rs = s.executeQuery(query);

            if (rs.next()) {
                return rs.getString("DateExecution");
            } else {
                return "";
            }
        } catch (SQLException e) {
            return "";
        }
    }

    /*private int widthLines() {

        Calendar resultCalendar = Calendar.getInstance();
        resultCalendar = this.startTime;

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).getEndDate().after(resultCalendar)) {
                resultCalendar = lines.get(i).getEndDate();

            }
        }

        long w = (resultCalendar.getTimeInMillis() - startTime.getTimeInMillis()) / 1000;
        int res = (int) w;
        return res;

    }*/
}
