package models;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Angelito
 */
public class methods {

    public static Connection con = dbconnect.ConnectDb();
    public static Statement smt = null;
    public static ResultSet rs = null;
    public static int option = 1;
    public static int committed = 0;
    public static int messagecounter = 0;
    public static String systemmessage = "System Message";
    public static String warningmessage = "Warning Message";
    public static String errormessage = "Error Message";
    public static String emptyfields = "Empty fields detected!";
    public static String fieldserror = "Fields Error";
    public static String getResult[];

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//SIMLPE DATE FORMAT

    public static DateFormat timeformat = new SimpleDateFormat("HH:mm:ss");

    public static void dialogMessage(JLabel dialogmessage, String message, JLabel dialogicon, String icon, JPanel dialogpanel, int messageoption) {
        switch (messageoption) {
            case 1:
                dialogmessage.setText(message);
//                dialogicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit File_32px.png")));
        }
    }

    public static String getTotalCount(String sql) {
        String get = "";
        try {

            smt = con.createStatement();
            rs = smt.executeQuery(sql);
            if (rs.next()) {
                get = rs.getString("count");
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return get;
    }

    public static String[] getResult(String sql, String[] getResult, String errorResult) {
        try {

            smt = con.createStatement();
            rs = smt.executeQuery(sql);
            if (rs.next()) {
                int y = 1;
                for (int x = 0; x < getResult.length; x++) {
                    getResult[x] = rs.getString(y);
                    y++;
                }
                committed = 1;
                methods.messagecounter = 0;

//                JOptionPane.showMessageDialog(null, getResult);
            } else {
                if (errorResult == null) {
                    methods.messagecounter = 5;
                } else {
                    JOptionPane.showMessageDialog(null, errorResult, "Error Messge", JOptionPane.WARNING_MESSAGE);
                }
            }

            smt.close();
            rs.close();

        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return getResult;

    }

    public static void commitTransaction(String sql, String messageTitle, String messageText) {
//        int commited = 0;
        try {

            smt = con.createStatement();
            smt.execute(sql);
            committed = 1;

            smt.close();

            if (messageTitle == null || messageText == null) {

            } else {
                JOptionPane.showMessageDialog(null, messageTitle, messageText, JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * diplay datatable
     *
     * @param sql
     * @param dataTable
     * @param total_rows
     */
    public static void displayTable(String sql, JTable dataTable) {

        try {

            smt = con.createStatement();
            rs = smt.executeQuery(sql);
            dataTable.setModel(DbUtils.resultSetToTableModel(rs));
//            total_rows.setText(String.format("%03d", dataTable.getRowCount()));
            smt.close();
            rs.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Table error", JOptionPane.WARNING_MESSAGE);
        }

    }

    /**
     * display combobox data
     *
     * @param sql
     * @param combo2
     * @param getString *
     */
    public static void displayComboBox(String sql, JComboBox combo2, String getString) {

        try {
            combo2.removeAllItems();

            smt = con.createStatement();
            rs = smt.executeQuery(sql);
//            combo2.addItem("-- Select Entity --");
            while (rs.next()) {
                combo2.addItem(rs.getString(getString));
            }
            smt.close();
            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Combo Error", JOptionPane.WARNING_MESSAGE);
        }

    }

    public static void displayComboBoxNoFirst(String sql, JComboBox combo2, String getString) {

        try {
            combo2.removeAllItems();

            smt = con.createStatement();
            rs = smt.executeQuery(sql);
            while (rs.next()) {
                combo2.addItem(rs.getString(getString));
            }
            smt.close();
            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Combo Error", JOptionPane.WARNING_MESSAGE);
        }

    }

    /**
     * display JList data
     *
     * @param sql
     * @param combo2
     * @param getString *
     */
    public static void displayList(String sql, JList combo2, String getString) {

        try {
            combo2.removeAll();

            smt = con.createStatement();
            rs = smt.executeQuery(sql);
            while (rs.next()) {
//                combo2.add(rs.getString(getString));
            }
            smt.close();
            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Combo Error", JOptionPane.WARNING_MESSAGE);
        }

    }

    public static int getEntityID(String columnid, String table, String columnselect, JComboBox mycombo, String selectedName, String errorcombo) {
        int id;
        String fetch[] = {columnid};
        if (selectedName != null) {
            fetch = methods.getResult("select " + columnid + " from " + table + " where  " + columnselect + " = '" + selectedName + "'", fetch, errorcombo);
            id = Integer.parseInt(fetch[0]);
        } else {
            fetch = methods.getResult("select " + columnid + " from " + table + " where  " + columnselect + " = '" + mycombo.getSelectedItem() + "'", fetch, errorcombo);
            if (fetch[0].equals(columnid)) {
                id = 0;
            } else {
                id = Integer.parseInt(fetch[0]);

            }
        }

        return id;
    }

    /**
     * selecting data to 2nd combo from main combob
     *
     * @param sql
     * @param combo
     * @param getString
     */
    public static void setSelectedCombo(String sql, JComboBox combo, String getString) {
        try {

            smt = con.createStatement();
            rs = smt.executeQuery(sql);
            if (rs.next()) {
                combo.setSelectedItem(rs.getString(getString));
            }
            smt.close();
            rs.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void setLabelField(String sql, JLabel label, String getString) {
        try {

            smt = con.createStatement();
            rs = smt.executeQuery(sql);
            if (rs.next()) {
                label.setText(rs.getString(getString));
            }
            smt.close();
            rs.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * print report
     *
     * @param reportfile
     * @param sql
     *
     * @param subreport
     * @param sem
     * @param ay
     * @param act
     */
    public static void printReport(String reportfile, String sql, String subreport,String sem,String ay, String act) {
        try {
//            con = dbconnect.ConnectDb();
            String dir = "C:\\Users\\aimah\\Desktop\\nbcc attendance monitoring system\\ATTENDANCE_MONITORING\\reports\\";
            JasperDesign jd = JRXmlLoader.load(dir + "" + reportfile);
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            Map map = new HashMap();
             map.put("dir", dir);
              if (sem != null)map.put("sem_name", sem);
              if (ay != null)map.put("ay_name", ay);
              if (act != null)map.put("act_name", act);
            System.out.print(sql + "\n");
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, map, con);
            JasperViewer.viewReport(jp, false);
//            PrintRequestAttributeSet printRequestAttrs = new HashPrintRequestAttributeSet();
//            JRPrintServiceExporter exporter = new JRPrintServiceExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
//            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttrs);
//            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE);
//            exporter.exportReport();

//            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * popup components function for jtable
     *
     * @param jtable
     * @param popupmenu
     */
    public static void tableClickPopup(JTable jtable, final JPopupMenu popupmenu) {
        jtable.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JTable source = (JTable) e.getSource();
                    int row = source.rowAtPoint(e.getPoint());
                    int column = source.columnAtPoint(e.getPoint());
                    if (!source.isRowSelected(row)) {
                        source.changeSelection(row, column, false, false);
                    }
                    popupmenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    /**
     * jtextfield validator
     *
     *
     * @param mytext
     * @param mylabel
     * @return
     */
    public static boolean jtextfieldValidator(JTextField mytext, JLabel mylabel) {
        if (mytext.getText().isEmpty()) {
            mylabel.setForeground(Color.red);
            return true;
        } else {
            mylabel.setForeground(Color.BLACK);
            return false;
        }
    }

    /**
     * jtextarea validator
     *
     *
     * @param mytextarea
     * @param mylabel
     * @return
     */
    public static boolean jtextareaValidator(JTextArea mytextarea, JLabel mylabel) {
        if (mytextarea.getText().isEmpty()) {
            mylabel.setForeground(Color.red);
            return true;
        } else {
            mylabel.setForeground(Color.BLACK);
            return false;
        }
    }

    /**
     * jcalendar validator
     *
     *
     * @param mydate
     * @param mylabel
     * @return
     */
    public static boolean jdateValidator(JDateChooser mydate, JLabel mylabel) {
        if (mydate.getDate() == null) {
            mylabel.setForeground(Color.red);
            return true;
        } else {
            mylabel.setForeground(Color.BLACK);
            return false;
        }

    }

    /**
     * jFormatedField validator
     *
     *
     * @param myformattedfield
     * @param mylabel
     * @return
     */
    public static boolean jFormatedField(JFormattedTextField myformattedfield, JLabel mylabel) {
        if (myformattedfield.getValue() == null) {
            mylabel.setForeground(Color.red);
            return true;
        } else {
            mylabel.setForeground(Color.BLACK);
            return false;
        }

    }

    /**
     * checkbox validator
     *
     *
     * @param option
     * @param mypanel
     * @param paneltitle
     * @return
     */
    public static boolean jcheckboxValidator(int option, JPanel mypanel, String paneltitle) {
        if (option == 0) {
            mypanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), paneltitle, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12), java.awt.Color.red));
            return true;
        } else {
            mypanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), paneltitle, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 0, 12), java.awt.Color.black));
            return false;
        }

    }

    public static int DeleteEntity(String getreferecncetable, String deletetable, String columnidname, String columnid) {
        int options = JOptionPane.showConfirmDialog(null, "Are you sure you want to move to trash?");
        if (options == 0) {
            String setString[] = {"total"};
            methods.getResult = methods.getResult("select count(*) as total from " + getreferecncetable + " where " + columnidname + " = '" + columnid + "' ", setString, null);
            int totalcount = Integer.parseInt(methods.getResult[0]);
            if (totalcount > 0) {
                methods.messagecounter = 5;
                columnid = null;
            } else {
                methods.commitTransaction("update " + deletetable + " set archive = 1 where " + columnidname + " = '" + columnid + "'", "Successfully Delete", null);
                methods.messagecounter = 3;
            }
        }
        return messagecounter;
    }

    public static void userlogs(String ref, String refnum, String description) {
//        methods.commitTransaction("insert into logs values(null,'" + ref + "','" + refnum + "','" + MAINMENU.userid + "','" + description + "',CURRENT_TIMESTAMP)", null, null);
    }

}
