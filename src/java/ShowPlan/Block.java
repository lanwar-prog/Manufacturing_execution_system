package ShowPlan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import folderClasses.WorkDB;

public class Block {

    private int idDetail; // id детали
    private int idOperation; // id операции
    private String detailName; // название детали
    private String operName; // название операции
    private Calendar dateBegin = Calendar.getInstance(); // дата, время начала
    private String color; // цвет операции на графике
    private int timeLenght; // продолжительность операции
    private int numberDetails; // количество деталей
    private int idOrder; // id заказа
    private String DateComplete; // дата, время завершения
    
    public void setDateComplete(String s){
        this.DateComplete = s;
    }
    
    public String getDateComplete(){
        return this.DateComplete;
    }

    public Calendar getEndDate() {
        Calendar c = Calendar.getInstance();
        c = (Calendar) dateBegin.clone();
        WorkDB wdb = new WorkDB();
        return wdb.checkWorkTime(c, timeLenght);
    }

    public int getIdOrder() {
        return this.idOrder;
    }

    public void setIdDetail(int id) {
        this.idDetail = id;
    }

    public void setIdOperation(int id) {
        this.idOperation = id;
    }

    public void setDateBegin(Calendar c) {
        this.dateBegin = c;
    }

    public void setColor(String c) {
        this.color = c;
    }

    public void setNumbersDetail(int i) {
        this.numberDetails = i;
    }

    public void setTimeLenght(int time) {
        this.timeLenght = time;
    }

    public int getIdDetail() {
        return this.idDetail;
    }

    public Calendar getDateBegin() {
        return this.dateBegin;
    }

    public int getWidth() {
        return timeLenght;
    }

    public int getIdOperation() {
        return this.idOperation;
    }

    public String getColor() {
        return this.color;
    }

    public String getTimeStart() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String res = format.format(this.dateBegin.getTime());
        return res;
    }

    public int getNumberDetails() {
        return this.numberDetails;
    }

    public int getTimeLenght() {
        return this.timeLenght;
    }

    public void setIdOrder(int i) {
        this.idOrder = i;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }
    
    
}
