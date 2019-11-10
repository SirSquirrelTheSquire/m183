package com.project.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@ManagedBean(name="user")
public class User {

    public static final int PASSWORD_LENGTH = 10;

    private String username;
    private String password;
    private String isAdmin;

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String addUser() {

        Connection con;
        Statement ps;

        String url = "jdbc:mysql://localhost:3306/employee?autoReconnect=true&useSSL=false";
        String usrn = "root";
        String pwd = "root";

        if(password.length() >= PASSWORD_LENGTH) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, usrn, pwd);
                ps = con.createStatement();
                ps.executeUpdate("INSERT INTO users (username, password, isAdmin) VALUES ('" + username + "', '" + password + "', " + isAdmin + ")");
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Exception : " + ex);
            }

            return "added";

        } else {
            FacesMessage message = new FacesMessage("Password must be at least 10 characters long");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
    }
}
