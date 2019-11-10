package com.project.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.sql.*;
import java.util.logging.*;

@ManagedBean(name = "loginBeanBean")
@SessionScoped
public class LoginBean implements Serializable {

    private final static Logger LOGGER = Logger.getLogger( Logger.GLOBAL_LOGGER_NAME );
    static Handler fileHandler = null;

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

        LogManager.getLogManager().reset();
        LOGGER.setLevel(Level.ALL);

        try {
            fileHandler = new FileHandler("employee.log");
            SimpleFormatter simple = new SimpleFormatter();
            fileHandler.setFormatter(simple);
            fileHandler.setLevel(Level.ALL);

            LOGGER.addHandler(fileHandler);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "File logger not working", ex);
        }

        Connection con;
        PreparedStatement pres;
        Statement s;
        ResultSet rs;

        String url = "jdbc:mysql://localhost:3306/employee?autoReconnect=true&useSSL=false";
        String usrn = "root";
        String pwd = "root";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, usrn, pwd);

            // Normal Statement
            /**
            s = con.createStatement();
            rs = s.executeQuery("SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'");
             **/

            // Prepared Statement

            pres = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            pres.setString(1, username);
            pres.setString(2, password);
            rs = pres.executeQuery();


            if(rs.next()) {
                LOGGER.log(Level.INFO, "User logged in");
                isLogged = true;
                return "table.xhtml?faces-redirect-true";
            } else {
                FacesMessage message = new FacesMessage("Username or Password is incorrect");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception : " + ex);
        }
        return null;
    }
}
