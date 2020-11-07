package createPlan;

import java.util.Calendar;

public class Block {

    public String flag;//start - end
    private String color = "";
    private Task idTask;
    private int idDetail;
    private int idOperation;
    private Calendar dateBegin = Calendar.getInstance();
    private int numberDetail;
    public int idOrder;
    public Calendar blackTime = Calendar.getInstance();
    private float EndPrev; 
    private float Start;
    
    private float width;
        
    public boolean HavePause(){
        //if(Start>EndPrev){
        //    System.out.println("Пауза "+getOrderId()+":"+(Start-EndPrev));
        //}
        return Start>EndPrev;
    }
    
    public Block(float w,int or,int o,int nd,int id_dertail,float s,float ep){
        width=w;
        idOrder=or;
        idOperation=o;
        numberDetail=nd;
        idDetail=id_dertail;
        Start=s;
        EndPrev=ep;
    }    
    
    public int getOrderId(){
        return idOrder;
    }
    
    public String toString(){
        return Shower.blockToStr(Integer.toString(this.getOrderId()), (int)this.getWidth());//LIB ште
    }
    
    public int getIdOperation() {
        return this.idOperation;
    }

    public void setDateBegin(Calendar c) {
        this.dateBegin = (Calendar) c.clone();
    }

    public void setParam(Calendar c, int idDetail, int idOperation, int width, int numberDetail, Task idTask, String color, int idOrder, Calendar t) {
        this.dateBegin = (Calendar) c.clone();
        this.idDetail = idDetail;
        this.idOperation = idOperation;
        this.width = width;
        this.numberDetail = numberDetail;
        this.idTask = idTask;
        this.color = color;
        this.idOrder = idOrder;
        this.blackTime = t;
    }

    public Calendar getDateEnd() {
        Calendar res = Calendar.getInstance();
        res = (Calendar) this.dateBegin.clone();
        res.add(Calendar.SECOND, (int)width);   //LIB добавил (int) - продолжительность блока по 
        return res;                     //хорошему не обязана быть целым числом. Это предрассудки.
    }

    public void setTime(int flag) {
        System.out.println("setTime:"+flag);
        this.dateBegin.add(Calendar.SECOND, flag);
        this.width=(int)(this.getDateEnd().getTimeInMillis()-this.getDateBegin().getTimeInMillis())/1000;
    }

    public int getNumberDetail() {
        return this.numberDetail;
    }

    public float getWidth() {   //LIB был int стал float
        return this.width;
    }

    public int getIdDetail() {
        return this.idDetail;
    }

    public String getColor() {
        return this.color;
    }

    public Calendar getDateBegin() {
        return (Calendar) this.dateBegin.clone();
    }

    public Task getIdTask() {
        return idTask;
    }

    public void setIdTask(Task idTask) {
        this.idTask = idTask;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public Calendar getBlackTime() {
        return (Calendar) this.blackTime.clone();
    }

    public void setBlackTime(Calendar blackTime) {
        this.blackTime = (Calendar) blackTime.clone();
    }

    public float getEndPrev() {
        return EndPrev;
    }

    public void setEndPrev(float EndPrev) {
        this.EndPrev = EndPrev;
    }

    public float getStart() {
        return Start;
    }

    public void setStart(float Start) {
        this.Start = Start;
    }

    public void setWidth(float width) {
        this.width = width;
    }
    
    
}
