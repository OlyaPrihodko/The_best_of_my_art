package com.epam.prihodko.finaltask.entity;

public class Check{
    private int id=0;
    private int price;
    private int apatrmentId;
    private int orderId;

    public Check(){}

    public Check(int id, int price) {
        this.id = id;
        this.price = price;
    }


    public void setId(int id) {
        this.id = id;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setApatrmentId(int apatrmentId) {
        this.apatrmentId = apatrmentId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }
    public int getPrice() {
        return price;
    }
    public int getOrderId() {
        return orderId;
    }
    public int getApatrmentId() {
        return apatrmentId;
    }

    @Override
    public String toString(){
        return
                "<td>"+this.getPrice()+" $"+"</td>"+
                "<td>"+this.getApatrmentId()+"</td>"+
                "<td>"+this.getOrderId()+"</td>";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Check check = (Check) o;

        if (apatrmentId != check.apatrmentId) return false;
        if (id != check.id) return false;
        if (orderId != check.orderId) return false;
        if (price != check.price) return false;

        return true;
    }
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + price;
        result = 31 * result + apatrmentId;
        result = 31 * result + orderId;
        return result;
    }
}
