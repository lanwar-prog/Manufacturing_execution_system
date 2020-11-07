/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

public class Employee {
    private Integer id_employee;
    private String name;
    private String surname;
    private String login;
    private Integer role;
    private String password;
    private String phone;
    private Date datehider;
    private Date datesumplier;

    public Integer getId_employee() {
        return id_employee;
    }

    public void setId_employee(Integer id_employee) {
        this.id_employee = id_employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDatehider() {
        return datehider;
    }

    public void setDatehider(Date datehider) {
        this.datehider = datehider;
    }

    public Date getDatesumplier() {
        return datesumplier;
    }

    public void setDatesumplier(Date datesumplier) {
        this.datesumplier = datesumplier;
    }

    @Override
    public String toString() {
        return "Employee{" + "id_employee=" + id_employee + ", name=" + name + ", surname=" + surname + ", login=" + login + ", role=" + role + ", password=" + password + ", phone=" + phone + ", datehider=" + datehider + ", datesumplier=" + datesumplier + '}';
    }
    
}
