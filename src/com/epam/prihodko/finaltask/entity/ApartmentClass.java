package com.epam.prihodko.finaltask.entity;

public class ApartmentClass{
    private int id=0;
    private String type;

    public ApartmentClass() {
    }
    public ApartmentClass(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApartmentClass that = (ApartmentClass) o;

        if (id != that.id) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
    @Override
    public String toString(){

        return
            "<td>"+this.getType()+"</td>";
    }
}
