/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public class Client {
    private Integer id_client;
    private String nameorganization;
    private String telephone;
    private String city;
    private String minname;
    private String street;
    private String numberhouse;
    private String email;

    public Integer getId_client() {
        return id_client;
    }

    public void setId_client(Integer id_client) {
        this.id_client = id_client;
    }

    public String getNameorganization() {
        return nameorganization;
    }

    public void setNameorganization(String nameorganization) {
        this.nameorganization = nameorganization;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMinname() {
        return minname;
    }

    public void setMinname(String minname) {
        this.minname = minname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumberhouse() {
        return numberhouse;
    }

    public void setNumberhouse(String numberhouse) {
        this.numberhouse = numberhouse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client{" + "id_client=" + id_client + ", nameorganization=" + nameorganization + ", telephone=" + telephone + ", city=" + city + ", minname=" + minname + ", street=" + street + ", numberhouse=" + numberhouse + ", email=" + email + '}';
    }
    
}
