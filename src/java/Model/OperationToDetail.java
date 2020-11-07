/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public class OperationToDetail {
    private Integer id_operdet;
    private Integer id_detail;
    private Integer id_operation;
    private Integer priority;
    private String name;
    private Integer time;
    private Integer timesetup;
    private Integer timecompletion;
    private Integer id_tas;
    private String taskname;


    public Integer getId_operdet() {
        return id_operdet;
    }

    public void setId_operdet(Integer id_operdet) {
        this.id_operdet = id_operdet;
    }

    public Integer getId_detail() {
        return id_detail;
    }

    public void setId_detail(Integer id_detail) {
        this.id_detail = id_detail;
    }

    public Integer getId_operation() {
        return id_operation;
    }

    public void setId_operation(Integer id_operation) {
        this.id_operation = id_operation;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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
        return "OperationToDetail{" + "id_operdet=" + id_operdet + ", id_detail=" + id_detail + ", id_operation=" + id_operation + ", priority=" + priority + ", name=" + name + ", time=" + time + ", timesetup=" + timesetup + ", timecompletion=" + timecompletion + ", id_tas=" + id_tas + ", taskname=" + taskname + '}';
    }
    
}
