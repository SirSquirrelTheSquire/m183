package com.project.DAO;

import com.project.beans.Employee;

import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.*;

@ManagedBean(name="employeeDAO")
public class EmployeeDAO {


    private List<Employee> employees = new ArrayList<Employee>();

    public EmployeeDAO() {

    }

    public List<Employee> getEmployees() {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/employee?autoReconnect=true&useSSL=false";
        String username = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM employees;");

            while(rs.next()) {
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                employees.add(new Employee(rs.getString(1), rs.getString(2), rs.getDate(3), rs.getString(4), rs.getBoolean(5)));
            }
        } catch(SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        } finally {
            try {
                if(rs != null) {
                    rs.close();
                }
                if(st != null) {
                    st.close();
                }
                if(con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return employees;
    }
}
