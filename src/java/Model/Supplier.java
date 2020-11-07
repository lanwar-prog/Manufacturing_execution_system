/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

public class Supplier {
    private Integer id_supplier;
    private String name;
    private String minname;
    private String city;
    private String street;
    private String numberhouse;
    private String telephone;
    private String email;

    public Integer getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(Integer id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinname() {
        return minname;
    }

    public void setMinname(String minname) {
        this.minname = minname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Supplier{" + "id_supplier=" + id_supplier + ", name=" + name + ", minname=" + minname + ", city=" + city + ", street=" + street + ", numberhouse=" + numberhouse + ", telephone=" + telephone + ", email=" + email + '}';
    }
}
