package com.project.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.sql.*;
import java.util.logging.*;
import java.util.regex.Pattern;

@ManagedBean(name="employee")
public class Employee {

    private final static Logger LOGGER = Logger.getLogger(Employee.class.getName());
    static Handler fileHandler = null;

    private String lastName;
    private String firstName;
    private Date joiningDate;
    private java.util.Date convertDate;
    private String email;
    private boolean genderDB;
    private String gender;

    public Employee(String lastName, String firstName, Date joiningDate, String email, boolean gender) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.joiningDate = joiningDate;
        this.email = email;
        this.genderDB = gender;

        if(genderDB == true) {
            this.gender = "Male";
        } else {
            this.gender = "Female";
        }
    }

    public Employee() {

    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public java.util.Date getConvertDate() {
        return convertDate;
    }

    public void setConvertDate(java.util.Date convertDate) {
        this.convertDate = convertDate;
    }

    public void convertToSQLDate(java.util.Date convertDate) {
        this.joiningDate = new java.sql.Date(convertDate.getTime());
    }

    public String addEmployee() {

        try {
            LOGGER.setLevel(Level.ALL);
            fileHandler = new FileHandler("./employee.log");
            SimpleFormatter simple = new SimpleFormatter();
            fileHandler.setFormatter(simple);

            LOGGER.addHandler(fileHandler);

        } catch (Exception ex) {

        }

        convertToSQLDate(convertDate);
        Connection con;
        Statement ps;

        String url = "jdbc:mysql://localhost:3306/employee?autoReconnect=true&useSSL=false";
        String usrn = "root";
        String pwd = "root";

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);

        if (pat.matcher(email).matches()) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(url, usrn, pwd);
                ps = con.createStatement();
                ps.executeUpdate("INSERT INTO employees (lastName, firstName, joiningDate, email, gender) VALUES ('" + lastName + "', '" + firstName + "', '" + joiningDate + "', '" + email + "', " + gender + ")");
                LOGGER.log(Level.INFO, "New Employee Created");
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error occurred :", ex);
            }

            return "added";
        } else {
            FacesMessage message = new FacesMessage("Email is not valid.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
    }
}
