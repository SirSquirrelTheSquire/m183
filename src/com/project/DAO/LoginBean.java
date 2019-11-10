package com.project.DAO;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@ManagedBean(name = "loginBeanBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    public boolean isLogged = false;

    public LoginBean() {

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

    public String action() {

        Connection con;
        Statement ps;
        ResultSet rs;

        String url = "jdbc:mysql://localhost:3306/employee?autoReconnect=true&useSSL=false";
        String usrn = "root";
        String pwd = "root";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, usrn, pwd);
            ps = con.createStatement();
            rs = ps.executeQuery("SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'");
            //rs.next();

            if(rs.next() == true) {
                isLogged = true;
                return "table.xhtml?faces-redirect-true";
            } else {
                FacesMessage message = new FacesMessage("Username or Password is incorrect");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return null;
            }
            /**
            if(username.equalsIgnoreCase(rs.getString(1).toString()) && password.equals(rs.getString(2).toString())) {
                isLogged = true;
                return "table.xhtml?faces-redirect-true";
            } else {
                FacesMessage message = new FacesMessage("Username or Password is incorrect");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return null;
            }
             **/

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception : " + ex);
        }
        return null;
    }
}
