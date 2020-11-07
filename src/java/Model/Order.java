/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

public class Order {
    private Integer id_order;
    private Integer id_client;
    private String clientname;
    private String clientcity;
    private Date date;
    private Integer urgency;
    private Integer state;
    private Integer numberdetails;
    private Integer id_detail;
    private String detailname;
    private Date dateexecution;

    public Integer getId_order() {
        return id_order;
    }

    public void setId_order(Integer id_order) {
        this.id_order = id_order;
    }

    public Integer getId_client() {
        return id_client;
    }

    public void setId_client(Integer id_client) {
        this.id_client = id_client;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getClientcity() {
        return clientcity;
    }

    public void setClientcity(String clientcity) {
        this.clientcity = clientcity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUrgency() {
        return urgency;
    }

    public void setUrgency(Integer urgency) {
        this.urgency = urgency;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getNumberdetails() {
        return numberdetails;
    }

    public void setNumberdetails(Integer numberdetails) {
        this.numberdetails = numberdetails;
    }

    public Integer getId_detail() {
        return id_detail;
    }

    public void setId_detail(Integer id_detail) {
        this.id_detail = id_detail;
    }

    public String getDetailname() {
        return detailname;
    }

    public void setDetailname(String detailname) {
        this.detailname = detailname;
    }

    public Date getDateexecution() {
        return dateexecution;
    }

    public void setDateexecution(Date dateexecution) {
        this.dateexecution = dateexecution;
    }

    @Override
    public String toString() {
        return "Order{" + "id_order=" + id_order + ", id_client=" + id_client + ", clientname=" + clientname + ", clientcity=" + clientcity + ", date=" + date + ", urgency=" + urgency + ", state=" + state + ", numberdetails=" + numberdetails + ", id_detail=" + id_detail + ", detailname=" + detailname + ", dateexecution=" + dateexecution + '}';
    }

}
