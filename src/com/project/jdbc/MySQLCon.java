package com.project.jdbc;

import java.sql.*;
class MysqlCon{

    private static Connection con;
    private static Statement st;
    private static ResultSet rs;

    public static void main(String args[]){

        String[] employees;

        con = null;
        st = null;
        rs = null;

        String url = "jdbc:mysql://localhost:3306/employee";
        String username = "root";
        String password = "root";

        try {
            con = DriverManager.getConnection(url, username, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM employees;");

            if(rs.next()) {
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
            }
        } catch(SQLException e) {
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
    }

    public void passwordCheck(String tableName) {
        String url = "jdbc:mysql://localhost:3306/employee";
        String username = "root";
        String password = "root";

        try {
            con = DriverManager.getConnection(url, username, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM " + tableName + ";");


        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
}