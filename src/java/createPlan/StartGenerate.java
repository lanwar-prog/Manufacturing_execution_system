package createPlan;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class StartGenerate {

    private Calendar dateTimeStart = Calendar.getInstance();
    private ArrayList<Workplace> workplaces = new ArrayList<Workplace>();
    private ArrayList<Order> orders = new ArrayList<Order>();
    private ArrayList<String> tasks = new ArrayList<String>();
    private Line[] lines;
    private Operation[] operations;
    public StartGenerate(String date, String time) {
        String datetime = date + " " + time + ":00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            dateTimeStart.setTime(format.parse(datetime));
            String pull = format.format(dateTimeStart.getTime());
            int d = 0;
        } catch (ParseException e) {
            int r = 0;
            int d = 10;
        }
    }

    public void generatePlan() {
        loadOrder();//+
        loadOperation();//+
        loadLines();
        loadOperations();
        loadTask();//+
        loadWorkplace();//+      
        State state = new State();

        state.setDateTime((Calendar) this.dateTimeStart.clone());
        state.setOrder(orders);
        state.setWorkplace(workplaces);
        state.setLines(lines);
        state.setOperations(operations);
        state.setDataModel(new DataModel());
        SimulatedAnnealing sa = new SimulatedAnnealing();
        sa.startAnneling(state);
        
        sa.Alg(10000, 0.0001f);        
        Calendar EndTime=(Calendar)dateTimeStart.clone();
        int max_width=sa.getMaxWidth();
        EndTime.add(Calendar.SECOND, max_width);
        
        state.SetPlanEndDate(EndTime);
        
        sa.savePlan(state);
        //state.savePlan();
        //return "";//state.getHtmlPlan();
    }

    private void loadOperations(){
        ArrayList<Operation> op=new ArrayList<Operation>();
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();
            Statement s2 = (Statement) c.createStatement();

            ResultSet rs = s.executeQuery(
                    "select\n" +
                    "    operation.id_operation,\n" +
                    "    operation.time,\n" +
                    "    operation.timesetup,\n" +
                    "    operation.timecompletion\n" +
                    "from\n" +
                    "	task,\n" +
                    "    workplacetask,\n" +
                    "    operation\n" +
                    "where\n" +
                    "	task.id_task=workplacetask.id_task and\n" +
                    "    operation.id_tas=task.id_task ");
            while(rs.next()) {
                op.add(new Operation(rs.getInt("id_operation"),rs.getFloat("time")));
            }
            operations=op.toArray(new Operation[op.size()]);
        } catch (SQLException e) {
        }    
    }

    private void loadLines(){
        ArrayList<Line> l=new ArrayList<Line>();
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();
            Statement s2 = (Statement) c.createStatement();

            ResultSet rs = s.executeQuery(
                    "select\n" +
                    "	workplacetask.id_workplace,\n" +
                    "    operation.id_operation,\n" +
                    "    operation.timesetup,\n" +
                    "    operation.timecompletion\n" +
                    "from\n" +
                    "	task,\n" +
                    "    workplacetask,\n" +
                    "    operation\n" +
                    "where\n" +
                    "	task.id_task=workplacetask.id_task and\n" +
                    "    operation.id_tas=task.id_task\n" +
                    "order by\n" +
                    "	workplacetask.id_workplace,\n" +
                    "    operation.id_operation");
            ArrayList<Integer> odp = new ArrayList<Integer>();
            int OldIdWorkplace=-1;
            float begin=-1,end=-1;
            while(rs.next()) {
                if(OldIdWorkplace!=rs.getInt("id_workplace") && (OldIdWorkplace!=-1)){
                    l.add(new Line( odp.toArray(new Integer[odp.size()]),begin,end,OldIdWorkplace));
                    odp.clear();
                }
                OldIdWorkplace=rs.getInt("id_workplace");
                odp.add(rs.getInt("id_operation"));
                begin=rs.getFloat("timesetup");
                end=rs.getFloat("timecompletion");           
            }
            if(OldIdWorkplace>-1){  //последняя линия
                l.add(new Line( odp.toArray(new Integer[odp.size()]),begin,end,OldIdWorkplace));
            }
            lines=l.toArray(new Line[l.size()]);
        } catch (SQLException e) {
        }    
    }
    
    private void loadWorkplace() {
        for (int i = 0; i < this.tasks.size(); i++) {
            ArrayList<Integer> lstIdWorkplaces = new ArrayList<Integer>();

            try {
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
                Statement s = (Statement) c.createStatement();
                Statement s2 = (Statement) c.createStatement();

                ResultSet rs = s.executeQuery("select * from `mesdb`.`task` where `id_task`=\'" + this.tasks.get(i) + "\'");
                if (rs.next()) {
                    int idTask = rs.getInt("id_task");

                    ResultSet rs2 = s2.executeQuery("select * from `mesdb`.`workplacetask` where `id_task`=" + idTask + "");
                    if (rs2.next()) {
                        int tW = rs2.getInt("id_workplace");

                        boolean flag = false;
                        for (int q = 0; q < lstIdWorkplaces.size(); q++) {
                            if (lstIdWorkplaces.get(q) == tW) {
                                flag = true;
                            }
                        }
                        if (flag == false) {
                            lstIdWorkplaces.add(tW);
                            Workplace w = new Workplace();

                            String taskName = getTaskName(idTask);

                            w.idWorkplace = tW;
                            w.task = new Task(idTask, taskName);

                            this.workplaces.add(w);
                        }
                    }
                }
            } catch (SQLException e) {
            }
        }
    }

    public String getTaskName(int id) {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();

            ResultSet rs = s.executeQuery("select * from `mesdb`.`task` where id_task=\"" + id + "\"");
            if (rs.next()) {
                return rs.getString("Name");
            } else {
                return "all";
            }
        } catch (SQLException e) {
            return "all";
        }
    }

    private void loadTask() {
        for (int i = 0; i < this.orders.size(); i++) {
            for (int j = 0; j < orders.get(i).getSizeOperationsList(); j++) {

                int r = orders.get(i).getTaskId(j);
                String t = Integer.toString(r);

                boolean flag = false;
                for (int q = 0; q < this.tasks.size(); q++) {
                    if (t.equals(this.tasks.get(q))) {
                        flag = true;
                        q = this.tasks.size();
                    }
                }
                if (flag == false) {
                    this.tasks.add(t);
                }
            }
        }
    }

    private void loadOperation() {
        ArrayList<Operation> operations;

        for (int i = 0; i < this.orders.size(); i++) {
            Order order = this.orders.get(i);
            operations = new ArrayList<Operation>();
            int idDetail = orders.get(i).getIdDetail();

            //select * from `mesdb`.`operationdetail` where `id_detail`=1;
            try {
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
                Statement s = (Statement) c.createStatement();
                Statement s2 = (Statement) c.createStatement();
                Statement s3 = (Statement) c.createStatement();

                ResultSet rs = s.executeQuery("select * from `mesdb`.`operationdetail` where id_detail=" + idDetail + "");

                while (rs.next()) {
                    int idOperation = rs.getInt("id_operation");
                    int priority = rs.getInt("Priority");

                    ResultSet rs2 = s2.executeQuery("select * from `mesdb`.`operation` where id_operation=" + idOperation + "");

                    if (rs2.next()) {
                        int time = rs2.getInt("Time");
                        int timeSetup = rs2.getInt("TimeSetup");
                        int timeEnd = rs2.getInt("TimeCompletion");
                        int idTask = rs2.getInt("id_tas");

                        ResultSet rs3 = s3.executeQuery("select * from `mesdb`.`Task` where id_task=" + idTask + "");

                        String nameTask = "not found";
                        if (rs3.next()) {
                            nameTask = rs3.getString("Name");
                        }

                        Operation o = new Operation(time, timeSetup, timeEnd, new Task(idTask, nameTask), priority, idOperation);
                        operations.add(o);
                    }
                }
            } catch (SQLException e) {
            }
            order.setOperations(operations);
        }
    }

    private void loadOrder() {
        //загружаем из бд
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/mesdb", "root", "123456");
            Statement s = (Statement) c.createStatement();

            ResultSet rs = s.executeQuery("SELECT * FROM `mesdb`.`clientorder` where State=0 ORDER BY DateExecution ASC;");

            while (rs.next()) {
                int idOrder = rs.getInt("id_order");
                int idClient = rs.getInt("id_client");
                String date = rs.getString("DateExecution");
                int numberDetails = rs.getInt("NumberDetails");
                int idDetail = rs.getInt("id_detail");

                Order order = new Order(idOrder, idClient, date, numberDetails, idDetail);
                this.orders.add(order);
            }

            ArrayList<Order> test = new ArrayList<Order>();

            c.close();

        } catch (SQLException e) {

        }
    }
}
