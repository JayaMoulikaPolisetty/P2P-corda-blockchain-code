package com.template.beans;

public class AddressBean {

    private String door_number;
    private String street_name;
    private String area;
    private String city;
    private String state;
    private long pin;
    private String landmark;


    public AddressBean(String door_number, String street_name, String area, String city, String state, long pin, String landmark) {
        this.door_number = door_number;
        this.street_name = street_name;
        this.area = area;
        this.city = city;
        this.state = state;
        this.pin = pin;
        this.landmark = landmark;
    }

    public String getDoor_number() {
        return door_number;
    }

    public void setDoor_number(String door_number) {
        this.door_number = door_number;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getPin() {
        return pin;
    }

    public void setPin(long pin) {
        this.pin = pin;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "door_number='" + door_number + '\'' +
                ", street_name='" + street_name + '\'' +
                ", area='" + area + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pin=" + pin +
                ", landmark='" + landmark + '\'' +
                '}';
    }
}
