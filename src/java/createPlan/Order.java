package createPlan;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Order {

    private int idOrder;
    private int idClient;
    private int idDetail;
    private int numberDetails;
    private Calendar dateExecution = Calendar.getInstance();
    private int nextOperation = 0;

    private ArrayList<Operation> operations = new ArrayList<Operation>();
   
    int[] OrderOperations;
    
    public int[] GetOrderOperations(){
        return OrderOperations;
    }
    
    public Order(int n,int[] o,int id,int id_detail){
        numberDetails=n;
        idDetail=id_detail;
        OrderOperations=o;
        idOrder=id;
    }    
    
    String format = "yyyy-MM-dd hh:mm:ss";
    SimpleDateFormat formater = new SimpleDateFormat(format);

    public Order(int idOrder, int idClient, String date, int numberDetails, int idDetail) {

        try {
            this.idOrder = idOrder;
            this.idClient = idClient;
            this.numberDetails = numberDetails;
            this.idDetail = idDetail;
            dateExecution.setTime(formater.parse(date));            

        } catch (ParseException e) {

        }
    }
    
    public int getIdOrder(){
        return this.idOrder;
    }

    public int getNextOperation() {
        if (this.nextOperation == operations.size()) {
            return -1;
        } else {
            return this.nextOperation;
        }
    }

    public void setNextOperation() {
        this.nextOperation++;
    }

    public String getTaskName(int i) {
        return this.operations.get(i).getTaskName();
    }
    
    public int getTaskId(int i) {
        return this.operations.get(i).getTaskId();
    }    

    public int getIdOperation(int i) {
        return this.operations.get(i).getIdOPeration();
    }

    public void setOperations(ArrayList<Operation> lst) {
        this.operations = lst;
    }

    public int getIdDetail() {
        return this.idDetail;
    }

    public int getSizeOperationsList() {
        return this.operations.size();
    }

    public int getNumberDetail() {
        return this.numberDetails;
    }

    public int getWidth(int numOperation) {
        int t = this.numberDetails * this.operations.get(numOperation).getWidth();
        return t;
    }

    public Operation getOperation(int i){
        return this.operations.get(i);
    }

}
