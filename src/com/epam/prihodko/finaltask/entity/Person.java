package com.epam.prihodko.finaltask.entity;

public class Person{
    private int id=0;
    private String name;
    private String sname;
    private String email;
    private String phone;
    private int accountId=0;

    public Person() {}
    public Person(String name, String surname, String email, String phone){
        this.name = name;
        this.sname = surname;
        this.email = email;
        this.phone = phone;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.sname = surname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setAccountId(int accountId){ this.accountId = accountId;}



    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return sname;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public int getAccountId() {
        return accountId;
    }

    @Override
    public String toString(){
        return
                "<td>"+this.getName()+"</td>"+
                "<td>"+this.getSurname()+"</td>"+
                "<td>"+this.getEmail()+"</td>"+
                "<td>"+this.getPhone()+"</td>";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (accountId != person.accountId) return false;
        if (id != person.id) return false;
        if (email != null ? !email.equals(person.email) : person.email != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (phone != null ? !phone.equals(person.phone) : person.phone != null) return false;
        if (sname != null ? !sname.equals(person.sname) : person.sname != null) return false;

        return true;
    }
    @Override
    public int hashCode() {
        int result = id;
        result = 3 * result + (name != null ? name.hashCode() : 0);
        result = 5 * result + (sname != null ? sname.hashCode() : 0);
        result = 7 * result + (email != null ? email.hashCode() : 0);
        result = 9 * result + (phone != null ? phone.hashCode() : 0);
        result = 11 * result + accountId;
        return result;
    }
}
