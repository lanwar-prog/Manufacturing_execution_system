package createPlan;

public class Task {

    int idTask;
    String nameTask;

    public Task(int idTask, String nameTask) {
        this.idTask = idTask;
        this.nameTask = nameTask;
    }

    public int getTaskId() {
        return this.idTask;
    }

    public String getNameTask() {
        return this.nameTask;
    }

}
