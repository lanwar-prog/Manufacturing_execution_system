/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public class Operation {
    
    private Integer id_operation;
    private String name;
    private Integer time;
    private Integer timesetup;
    private Integer timecompletion;
    private Integer id_tas;
    private String taskname;

    public Integer getId_operation() {
        return id_operation;
    }

    public void setId_operation(Integer id_operation) {
        this.id_operation = id_operation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getTimesetup() {
        return timesetup;
    }

    public void setTimesetup(Integer timesetup) {
        this.timesetup = timesetup;
    }

    public Integer getTimecompletion() {
        return timecompletion;
    }

    public void setTimecompletion(Integer timecompletion) {
        this.timecompletion = timecompletion;
    }

    public Integer getId_tas() {
        return id_tas;
    }

    public void setId_tas(Integer id_tas) {
        this.id_tas = id_tas;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    @Override
    public String toString() {
        return "Operation{" + "id_operation=" + id_operation + ", name=" + name + ", time=" + time + ", timesetup=" + timesetup + ", timecompletion=" + timecompletion + ", id_tas=" + id_tas + ", taskname=" + taskname + '}';
    }
    
}
