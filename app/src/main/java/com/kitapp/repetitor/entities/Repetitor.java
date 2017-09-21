package com.kitapp.repetitor.entities;

/**
 * Created by denis on 9/20/17.
 */

public class Repetitor {
    private int id;
    private String fio;
    private int age;
    private int city_id;
    private String address;
    private String discipline;
    private int price;
    private String units;
    private String place;
    private int stage;
    private String phone;
    private String phone2;
    private String comments;

    public Repetitor(int id, String fio, int age, int city_id, String address, String discipline,
                     int price, String units, String place, int stage, String phone, String phone2,
                     String comments) {
        this.id = id;
        this.fio = fio;
        this.age = age;
        this.city_id = city_id;
        this.address = address;
        this.discipline = discipline;
        this.price = price;
        this.units = units;
        this.place = place;
        this.stage = stage;
        this.phone = phone;
        this.phone2 = phone2;
        this.comments = comments;
    }

    public Repetitor() {
        this.id = -1;
        this.fio = "";
        this.age = -1;
        this.city_id = -1;
        this.address = "";
        this.discipline = "";
        this.price = -1;
        this.units = "";
        this.place = "";
        this.stage = -1;
        this.phone = "";
        this.phone2 = "";
        this.comments = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
