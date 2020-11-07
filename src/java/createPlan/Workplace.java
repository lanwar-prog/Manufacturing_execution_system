package createPlan;

public class Workplace {

    public Task task;
    public int idWorkplace;

    public String getTaskName() {
        return this.task.nameTask;
    }

    public int getTaskId() {
        return this.task.idTask;
    }
}
