/*
 * Copyright (C) 2018 Angel
 * This program is for Inventory System of SPMU DPWH 3rd DEO only.
 * Developed by Angelito T. Baran
 * Admin Aide I
 */
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author SPM
 */
public class dbconnect {

    public static Connection con = null;
    public static String ipaddress = "localhost";

    public static Connection ConnectDb() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://192.168.254.103:3306/dpwh_inventory", "angelbarz", "");
            if (ipaddress.equals("localhost")) {
                con = DriverManager.getConnection("jdbc:mysql://" + ipaddress + ":3306/clone_tables3", "root", "");

            } else {
                con = DriverManager.getConnection("jdbc:mysql://" + ipaddress + ":3306/clone_tables3", "angelbarz", "");

            }
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

}
