package com.epam.prihodko.finaltask.entity;


public class Apartment{
    private int id;
    private int price;
    private int couchette;
    private int roomNumber;
    private String status;
    private int classId;

    public Apartment() {}

    public Apartment(int id, int price, int couchette, int roomNumber) {
        this.id = id;
        this.price = price;
        this.couchette = couchette;
        this.roomNumber = roomNumber;
    }
    public Apartment(int couchette, int roomNumber, String status) {
        this.status = status;
        this.couchette = couchette;
        this.roomNumber = roomNumber;
    }

    public int getId() {
        return id;
    }
    public int getPrice() {
        return price;
    }
    public int getCouchette() {
        return couchette;
    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public String getStatus() {
        return status;
    }
    public int getClassId() {
        return classId;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setCouchette(int couchette) {
        this.couchette = couchette;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Apartment apartment = (Apartment) o;

        if (classId != apartment.classId) return false;
        if (couchette != apartment.couchette) return false;
        if (id != apartment.id) return false;
        if (price != apartment.price) return false;
        if (roomNumber != apartment.roomNumber) return false;
        if (status != null ? !status.equals(apartment.status) : apartment.status != null) return false;

        return true;
    }
    @Override
    public int hashCode() {
        int result = id;
        result = 3 * result + price;
        result = 5 * result + couchette;
        result = 7 * result + roomNumber;
        result = 9 * result + (status != null ? status.hashCode() : 0);
        result = 11 * result + classId;
        return result;
    }
    @Override
    public String toString(){
        return
                "<td>"+this.getPrice()+"</td>"+
                "<td>"+this.getRoomNumber()+"</td>"+
                "<td>"+this.getCouchette()+"</td>"+
                "<td>"+this.getStatus()+"</td>"+
                "<td>"+this.getClassId()+"</td>";
    }
}
