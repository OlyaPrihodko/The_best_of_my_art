package com.epam.prihodko.finaltask.entity;

import java.sql.Date;

public class Order{
    private int id=0;
    private String apartmentClass;
    private int couchette;
    private int roomNumber;
    private Date date_in;
    private Date date_out;
    private String status;
    private int personId;

    public Order(){}

    public Order( String apartmentClass,int roomNumber,int couchette, Date date_in, Date date_out, String status) {

        this.couchette = couchette;
        this.date_in = date_in;
        this.date_out = date_out;

        this.apartmentClass = apartmentClass;
        this.roomNumber = roomNumber;
        this.status = status;
    }

    public Order(int id, String apartmentClass,int roomNumber,int couchette, Date date_in, Date date_out, String status) {
        this.id=id;
        this.couchette = couchette;
        this.date_in = date_in;
        this.date_out = date_out;
        this.apartmentClass = apartmentClass;
        this.roomNumber = roomNumber;
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setCouchette(int couchette) {
        this.couchette = couchette;
    }
    public void setRoomNumber(int room_number) {
        this.roomNumber = room_number;
    }
    public void setDate_in(Date date_in) {
        this.date_in = date_in;
    }
    public void setDate_out(Date date_out) {
        this.date_out = date_out;
    }
    public void setApartmentClass(String apartmentClass) {
        this.apartmentClass = apartmentClass;
    }
    public void setPersonId(int personId) {
        this.personId = personId;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public int getCouchette() {
        return couchette;
    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public Date getDate_in() {
        return date_in;
    }
    public Date getDate_out() {
        return date_out;
    }
    public String getApartmentClass() {
        return apartmentClass;
    }
    public int getPersonId() {
        return personId;
    }
    public String getStatus() {
        return status;
    }

    @Override
    public String toString(){
        return
                "<td>"+this.getApartmentClass()+"</td>"+
                "<td>"+this.getRoomNumber()+"</td>"+
                "<td>"+this.getCouchette()+"</td>"+
                "<td>"+this.getDate_in()+"</td>"+
                "<td>"+this.getDate_out()+"</td>"+
                "<td>"+this.getStatus()+"</td>";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (couchette != order.couchette) return false;
        if (id != order.id) return false;
        if (personId != order.personId) return false;
        if (roomNumber != order.roomNumber) return false;
        if (apartmentClass != null ? !apartmentClass.equals(order.apartmentClass) : order.apartmentClass != null)
            return false;
        if (date_in != null ? !date_in.equals(order.date_in) : order.date_in != null) return false;
        if (date_out != null ? !date_out.equals(order.date_out) : order.date_out != null) return false;
        if (status != null ? !status.equals(order.status) : order.status != null) return false;

        return true;
    }
    @Override
    public int hashCode() {
        int result = id;
        result = 3 * result + (apartmentClass != null ? apartmentClass.hashCode() : 0);
        result = 5 * result + couchette;
        result = 7 * result + roomNumber;
        result = 9 * result + (date_in != null ? date_in.hashCode() : 0);
        result = 11 * result + (date_out != null ? date_out.hashCode() : 0);
        result = 13 * result + (status != null ? status.hashCode() : 0);
        result = 15 * result + personId;
        return result;
    }
}
