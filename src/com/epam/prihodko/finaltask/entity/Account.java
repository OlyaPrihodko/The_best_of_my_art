package com.epam.prihodko.finaltask.entity;

public class Account{
    private int id = 0;
    private String login;
    private String password;
    private String role;

    public Account() {}
    public Account(String login,String password, String role) {
        this.login=login;
        this.password = password;
        this.role = role;
    }

    public void setId(int id){ this.id=id; }
    public void setLogin(String login){ this.login=login; }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }

    @Override
    public String toString(){
        return
            "<td>"+this.getLogin()+"</td>"+
            "<td>"+this.getPassword()+"</td>"+
            "<td>"+this.getRole()+"</td>";
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Account other = (Account) obj;

        if (this.id != other.id) {
            return false;
        }
        if (!this.login.equals(other.login)) {
            return false;
        }
        if (!this.password.equals(other.password)) {
            return false;
        }
        return this.role.equals(other.role);
    }
    @Override
    public int hashCode() {
        return  3 * new Integer(this.id).hashCode() +
                5 * this.login.hashCode() +
                7 * this.password.hashCode() +
                9 * this.role.hashCode();
    }
}
