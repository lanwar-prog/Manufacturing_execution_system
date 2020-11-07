package createPlan;

public class Operation {

    private int time;
    private int timePrepration;
    private int timeComplition;
    private int priority;
    private int idOperation;

    private Task task;
    float width;
    public Operation(int id,float w){
        width=w;
        idOperation=id;
    }
    public Operation(int time, int timeSetup, int timeEnd, Task task, int priority, int idOperation) {
        this.time = time;
        this.timePrepration = timeSetup;
        this.timeComplition = timeEnd;
        this.task = task;
        this.priority = priority;
        this.idOperation = idOperation;
    }
    
    public Task getTask(){
        return task;
    }
    
    public String getTaskName(){
        return this.task.getNameTask();
    }
    
    public int getTaskId(){
        return this.task.getTaskId();
    }
    
    public int getIdOPeration(){
        return this.idOperation;
    }
    
    public int getWidth(){
        return this.time;
    }
}
