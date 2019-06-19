/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import models.methods;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import views.DASHBOARD;

/**
 *
 * @author Sitty Pangcatan
 */
public class mainmenu {

    public static String currentSem = "", currentAY = "";
    public static String sqlAct = "SELECT a.`activity_Id` 'Act. ID',a.`act_name` 'Activity Name',c.sponsor 'Sponsor',concat(e.semester,'-',d.academic_year )'SY - AY',f.duration 'Duration',concat(DATE_FORMAT(a.`time_started`, '%M %d, %Y'),' ',time_format(a.`time_started`,'%h:%i %p')) 'Date Time Started',concat(DATE_FORMAT(a.`time_ended`, '%M %d, %Y'),' ',time_format(a.`time_ended`,'%h:%i %p')) 'Date Time Ended',a.session 'Session',g.status 'Status' from tbl_activities as a\n"
            + "left join tbl_events as b on b.eventID = a.eventID\n"
            + "LEFT join tbl_event_sponsor as c on c.sponsorID = a.sponsorID\n"
            + "LEFT JOIN tbl_ay as d on d.ayID = a.Academic_Year\n"
            + "LEFT join tbl_semester as e on e.semID = a.Semester\n"
            + "left join tbl_event_duration as f on f.durationID = a.durationID\n"
            + "left join tbl_status as g on g.statusID = a.statusID ";

    public static void getcurrentSemAY() {
        String[] getcurrentSem = {"semester", "academic_year"};
        methods.getResult("select b.semester,c.academic_year from tbl_attendance_settings as a\n"
                + "left join tbl_semester as b on b.`semID` =a.`semesterID`\n"
                + "left join tbl_ay as c on c.`ayID` = a.`ayID`\n"
                + "where status = 1 limit 1", getcurrentSem, "Error Fetching Current Sem and AY!");
        mainmenu.currentSem = getcurrentSem[0];
        mainmenu.currentAY = getcurrentSem[1];
        DASHBOARD.label_active.setText(currentSem + " " + currentAY);
        dashboardPanel();
    }

    public static void dashboardPanel() {
        DASHBOARD.label_dashinactive.setText(methods.getTotalCount("select count(*) as count from tbl_activities as a left join tbl_semester as b on b.`semID` =a.`Semester`\n"
                + "left join tbl_ay as c on c.`ayID` = a.`Academic_Year` where statusID = 2  and b.semester = '" + currentSem + "' and c.academic_year = '" + currentAY + "'"));
        DASHBOARD.label_dashactive.setText(methods.getTotalCount("select count(*) as count from tbl_activities  as a left join tbl_semester as b on b.`semID` =a.`Semester`\n"
                + "left join tbl_ay as c on c.`ayID` = a.`Academic_Year` where statusID = 1 and b.semester = '" + currentSem + "' and c.academic_year = '" + currentAY + "'"));
        DASHBOARD.label_dashtotalact.setText(methods.getTotalCount("select count(*) as count from tbl_activities as a left join tbl_semester as b on b.`semID` =a.`Semester`\n"
                + "left join tbl_ay as c on c.`ayID` = a.`Academic_Year` where b.semester = '" + currentSem + "' and c.academic_year = '" + currentAY + "'"));
        int count1 = Integer.parseInt(methods.getTotalCount("select count('stud_id') 'count' from tbl_enrollees where course_id = 1 and `status_SY` = '" + currentAY + "' and `status_Sem` = '" + currentSem + "'"));
        int count2 = Integer.parseInt(methods.getTotalCount("select count('stud_id') 'count' from tbl_enrollees where course_id = 2 and `status_SY` = '" + currentAY + "' and `status_Sem` = '" + currentSem + "'"));
        int count3 = Integer.parseInt(methods.getTotalCount("select count('stud_id') 'count' from tbl_enrollees where course_id = 3 and `status_SY` = '" + currentAY + "' and `status_Sem` = '" + currentSem + "'"));
        int count4 = Integer.parseInt(methods.getTotalCount("select count('stud_id') 'count' from tbl_enrollees where course_id = 4 and `status_SY` = '" + currentAY + "' and `status_Sem` = '" + currentSem + "'"));
        int count5 = Integer.parseInt(methods.getTotalCount("select count('stud_id') 'count' from tbl_enrollees where course_id = 5 and `status_SY` = '" + currentAY + "' and `status_Sem` = '" + currentSem + "'"));
        int count6 = Integer.parseInt(methods.getTotalCount("select count('stud_id') 'count' from tbl_enrollees where course_id = 6 and `status_SY` = '" + currentAY + "' and `status_Sem` = '" + currentSem + "'"));
        int count7 = Integer.parseInt(methods.getTotalCount("select count('stud_id') 'count' from tbl_enrollees where course_id = 7 and `status_SY` = '" + currentAY + "' and `status_Sem` = '" + currentSem + "'"));
        int count8 = Integer.parseInt(methods.getTotalCount("select count('stud_id') 'count' from tbl_enrollees where course_id = 8 and `status_SY` = '" + currentAY + "' and `status_Sem` = '" + currentSem + "'"));
        int count9 = Integer.parseInt(methods.getTotalCount("select count('stud_id') 'count' from tbl_enrollees where course_id = 9 and `status_SY` = '" + currentAY + "' and `status_Sem` = '" + currentSem + "'"));
        int count10 = Integer.parseInt(methods.getTotalCount("select count('stud_id') 'count' from tbl_enrollees where course_id = 10 and `status_SY` = '" + currentAY + "' and `status_Sem` = '" + currentSem + "'"));

        // try {
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        data.setValue(count2, "BSIT", "");

        data.setValue(count1, "BSBA-MM", "");
        data.setValue(count3, "BSBA-FM", "");
        data.setValue(count4, "BSED-Eng", "");
        data.setValue(count5, "BSBA-OM", "");
        data.setValue(count6, "BSED-Math", "");
        data.setValue(count7, "BEEd", "");
        data.setValue(count8, "BECEd", "");
        data.setValue(count9, "BEEd-Gen Curr", "");
        data.setValue(count10, "BEEd-Pre-School", "");
        System.out.print("select count('stud_id') 'count' from tbl_enrollees where course_id = 1 and `status_SY` = '" + currentAY + "' and `status_Sem` = '" + currentSem + "'");
        JFreeChart chart = ChartFactory.createBarChart3D("", "", "", data, PlotOrientation.VERTICAL, true, true, true);
        CategoryPlot p = chart.getCategoryPlot();
        ChartPanel barPanel = new ChartPanel(chart);

        p.setRangeGridlinePaint(Color.gray);
        p.getRenderer().setSeriesPaint(0, new Color(0, 153, 0));
        p.getRenderer().setSeriesPaint(1, new Color(249, 191, 59));
        p.getRenderer().setSeriesPaint(2, new Color(255, 153, 0));
        p.getRenderer().setSeriesPaint(3, new Color(244, 179, 80));
        p.getRenderer().setSeriesPaint(4, new Color(0, 0, 204));
        p.getRenderer().setSeriesPaint(5, new Color(75, 119, 190));
        p.getRenderer().setSeriesPaint(6, new Color(107, 185, 240));

        p.getRenderer().setSeriesPaint(7, new Color(34, 167, 240));
        p.getRenderer().setSeriesPaint(8, new Color(82, 179, 217));
        p.getRenderer().setSeriesPaint(9, new Color(89, 171, 227));

        p.setBackgroundPaint(Color.WHITE);
        DASHBOARD.dash_chart.removeAll();
        DASHBOARD.dash_chart.add(barPanel, BorderLayout.CENTER);
        DASHBOARD.dash_chart.validate();
        //   } catch (Exception e) {
        //     JOptionPane.showMessageDialog(null, e);
        //  }

    }

    public static void searchEvent(JTextField Text_search, JTable Table_event) {
        methods.displayTable("select eventID 'Event No', eventName 'Event Name' from tbl_events where eventName like '%" + Text_search.getText() + "%'", Table_event);
    }

    public static void searchActivities(JTextField Text_search, JTable Table_event) {
        methods.displayTable(sqlAct + "where a.activity_Id like '%" + Text_search.getText() + "%' and e.semester  = '" + mainmenu.currentSem + "' and d.academic_year  = '" + mainmenu.currentAY + "'   or a.`act_name` like '%" + Text_search.getText() + "%' and e.semester  = '" + mainmenu.currentSem + "' and d.academic_year  = '" + mainmenu.currentAY + "'   or c.sponsor like '%" + Text_search.getText() + "%' and e.semester  = '" + mainmenu.currentSem + "' and d.academic_year  = '" + mainmenu.currentAY + "' ", Table_event);

        Table_event.getColumnModel().getColumn(0).setPreferredWidth(30);
        Table_event.getColumnModel().getColumn(1).setPreferredWidth(100);
        Table_event.getColumnModel().getColumn(2).setPreferredWidth(100);
        Table_event.getColumnModel().getColumn(3).setPreferredWidth(60);
        Table_event.getColumnModel().getColumn(4).setPreferredWidth(60);
        Table_event.getColumnModel().getColumn(5).setPreferredWidth(100);
        Table_event.getColumnModel().getColumn(6).setPreferredWidth(100);
        Table_event.getColumnModel().getColumn(7).setPreferredWidth(30);
        Table_event.getColumnModel().getColumn(8).setPreferredWidth(30);
    }

    /**
     * Dashboard attendance panel*
     */
    public static void attendancepanel() {

        methods.displayComboBoxNoFirst("select act_name from tbl_activities  as a LEFT join tbl_ay as ay on ay.ayID = a.`Academic_Year` left join tbl_semester as sem on sem.semID = a.`Semester` where sem.semester = '" + currentSem + "' and ay.academic_year = '" + currentAY + "' ", DASHBOARD.combo_event, "act_name");
        displayStudentAttendance(DASHBOARD.search_attendance, DASHBOARD.combo_reportA);

    }

    public static String sqlAttendance1 = "select stud.`Stud_ID`,concat(lastname,', ',firstname,' ',substring(middlename,1,1),'.') as 'Student Name',sec.section_id 'Section ID',enrol.current_status 'Current Year' from \n"
            + "tbl_section sec \n"
            + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id  \n"
            + "inner join tbl_subject_events sube on sube.subject_id = sec.subject_id \n"
            + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id \n"
            + "inner join tbl_student stud on stud.stud_id = enrol.stud_id "
            + "inner join tbl_activities as act on act.activity_id = sube.actID ",
            sqlAttendance2 = "select stud.`Stud_ID`,concat(lastname,', ',firstname,' ',substring(middlename,1,1),'.') as 'Student Name',sec.section_id 'Section ID',enrol.current_status 'Current Year',if(att.timein='0000-00-00 00:00:00','Not yet',concat(DATE_FORMAT(att.timein, '%M %d, %Y'),' ',time_format(att.timein,'%h:%i %p'))) as 'Sign-in',if(att.timeout='0000-00-00 00:00:00','Not yet',concat(DATE_FORMAT(att.timeout, '%M %d, %Y'),' ',time_format(att.timeout,'%h:%i %p'))) as 'Sign-out' \n"
            + "from tbl_attendance as att\n"
            + "inner join tbl_activities as act on act.activity_id = att.`ActivityID`\n"
            + "inner join tbl_subject_events sube on sube.`actID` = act.`activity_Id`\n"
            + "inner join tbl_section sec on  sube.subject_id = sec.subject_id \n"
            + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id  \n"
            + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id \n"
            + "inner join tbl_student stud on stud.stud_id = enrol.stud_id \n"
            + "where stud.stud_id = att.`Stud_ID` and sube.`subject_ID` = sec.subject_id",
            sqlAttendance3 = "select stud.`Stud_ID`,concat(lastname,', ',firstname,' ',substring(middlename,1,1),'.') as 'Student Name',sec.section_id 'Section Name',enrol.current_status 'Current Year', if(ab.`attendanceID`!=\"\",'ATTENDED','ABSENT') as status  from \n"
            + "tbl_section sec \n"
            + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id  \n"
            + "inner join tbl_subject_events sube on sube.subject_id = sec.subject_id "
            + "inner  join tbl_activities as act on act.activity_id = sube.actID  \n"
            + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id \n"
            + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
            + "left  join tbl_attendance as ab on ab.`Stud_ID` = stud.`Stud_ID` "
            + " and ab.`ActivityID` = sube.`actID` ";

    public static void displayStudentAttendance(JTextField search, JComboBox report_type) {
        if (report_type.getSelectedItem().toString().equals("Expected Audience")) {

            methods.displayTable(sqlAttendance1 + " where act.`act_name` = '" + DASHBOARD.combo_event.getSelectedItem() + "' and concat(lastname,' ',firstname,' ',substring(middlename,1,1)) like '%" + search.getText() + "%' or "
                    + " stud.`Stud_ID` like '%" + search.getText() + "%' and act.`act_name` = '" + DASHBOARD.combo_event.getSelectedItem() + "' order by  concat(lastname,',',firstname,' ',substring(middlename,1,1),'.') asc", DASHBOARD.table_attendance);

        }
        if (report_type.getSelectedItem().toString().equals("Attendees")) {

            methods.displayTable(sqlAttendance2 + " and act.`act_name` = '" + DASHBOARD.combo_event.getSelectedItem() + "' and  concat(lastname,' ',firstname,' ',substring(middlename,1,1)) like '%" + search.getText() + "%' "
                    + "group by att.stud_id,att.`ActivityID` order by  concat(lastname,',',firstname,' ',substring(middlename,1,1),'.') asc", DASHBOARD.table_attendance);

        }
        if (report_type.getSelectedItem().toString().equals("Attendance Status")) {
            int actidd = methods.getEntityID("activity_id", "tbl_activities", "act_name", DASHBOARD.combo_event, null, "Error Fetching Act ID");
            methods.displayTable(sqlAttendance3 + "where sube.`actID` ='" + actidd + "' and  concat(lastname,' ',firstname,' ',substring(middlename,1,1),'.') like '%" + search.getText() + "%' order by  concat(lastname,',',firstname,' ',substring(middlename,1,1),'.') asc", DASHBOARD.table_attendance);
            System.out.print(sqlAttendance3 + "where sube.`actID` ='" + actidd + "' and  concat(lastname,' ',firstname,' ',substring(middlename,1,1),'.') like '%" + search.getText() + "%' order by  concat(lastname,',',firstname,' ',substring(middlename,1,1),'.') asc");

        }

    }

    /**
     * *Dashboard report Monitoring pane
     *
     * @param reporttype
     * @param filterby
     * @param reporttable
     */
    public static void displayReport(JComboBox reporttype, JComboBox filterby, JComboBox entityCombo, JTable reporttable) {
        filterbyComboOption(filterby, reporttype.getSelectedItem().toString());
        FilterByEntity(reporttype, filterby, entityCombo);
        DisplayTableReport(reporttype.getSelectedItem().toString(), filterby, reporttable);

    }

    static void dislay(boolean statement) {
        DASHBOARD.label_filter.setVisible(statement);
        DASHBOARD.event_label1.setVisible(statement);
        DASHBOARD.combo_filter3.setVisible(statement);
        DASHBOARD.ay_label.setVisible(statement);
        DASHBOARD.combo_filter2.setVisible(statement);
        DASHBOARD.label_semester.setVisible(statement);
        DASHBOARD.combo_filter2.setVisible(statement);
        DASHBOARD.combo_filter1.setVisible(statement);
    }

    public static void filterbyComboOption(JComboBox filterby, String filtertype) {
        if (filtertype.equals("Events")) {
            filterby.removeAllItems();
            filterby.setVisible(false);
            dislay(false);

        }
        if (filtertype.equals("Activities")) {
            filterby.removeAllItems();
            filterby.setVisible(true);
            filterby.addItem("None");
            filterby.addItem("Events");
            filterby.addItem("AY");
            filterby.addItem("Events/AY");
            filterby.addItem("AY/Sem");
            filterby.addItem("Events/AY/Sem");
            dislay(true);
            DASHBOARD.event_label1.setText("Events:");
            DASHBOARD.label_filter.setVisible(true);

        }
        if (filtertype.equals("Attendance")) {
            filterby.removeAllItems();
            filterby.setVisible(true);
            dislay(true);
            filterby.addItem("Expected Audience");
            filterby.addItem("Attendees");
            filterby.addItem("Attendance Status");
            DASHBOARD.event_label1.setText("Activities:");
        }
    }

    public static void FilterByEntity(JComboBox filtertype, JComboBox filterby, JComboBox entitycombo) {
        if (filtertype.getSelectedItem().equals("Events")) {

        }
        if (filtertype.getSelectedItem().equals("Activities")) {
            methods.displayComboBoxNoFirst("select eventName from tbl_events", DASHBOARD.combo_filter3, "eventName");
            methods.displayComboBoxNoFirst("select academic_year from tbl_ay", entitycombo, "academic_year");
            methods.displayComboBoxNoFirst("select semester from tbl_semester", DASHBOARD.combo_filter2, "semester");
            if (filterby.getSelectedItem().equals("None")) {
                dislay(false);
                DASHBOARD.label_filter.setVisible(true);
            }
            if (filterby.getSelectedItem().equals("Events")) {
                dislay(false);
                DASHBOARD.label_filter.setVisible(true);
                DASHBOARD.event_label1.setVisible(true);
                DASHBOARD.combo_filter3.setVisible(true);
            }
            if (filterby.getSelectedItem().equals("AY")) {
                dislay(false);
                DASHBOARD.label_filter.setVisible(true);
                DASHBOARD.ay_label.setVisible(true);
                DASHBOARD.combo_filter1.setVisible(true);
            }
            if (filterby.getSelectedItem().equals("Events/AY")) {
                dislay(false);
                DASHBOARD.label_filter.setVisible(true);
                DASHBOARD.event_label1.setVisible(true);
                DASHBOARD.combo_filter3.setVisible(true);
                DASHBOARD.ay_label.setVisible(true);
                DASHBOARD.combo_filter1.setVisible(true);
            }
            if (filterby.getSelectedItem().equals("AY/Sem")) {
                dislay(false);
                DASHBOARD.label_filter.setVisible(true);
                DASHBOARD.ay_label.setVisible(true);
                DASHBOARD.combo_filter1.setVisible(true);
                DASHBOARD.label_semester.setVisible(true);
                DASHBOARD.combo_filter2.setVisible(true);
            }
            if (filterby.getSelectedItem().equals("Events/AY/Sem")) {
                dislay(true);
            }

        }
        if (filtertype.getSelectedItem().equals("Attendance")) {

            methods.displayComboBoxNoFirst("select academic_year from tbl_ay", entitycombo, "academic_year");
            methods.displayComboBoxNoFirst("select semester from tbl_semester", DASHBOARD.combo_filter2, "semester");
            selectAYEvents();

        }
    }

    public static void selectAYEvents() {
        if (DASHBOARD.combo_report.getSelectedItem().equals("Attendance")) {
            int getAYID = methods.getEntityID("ayID", "tbl_ay", "academic_year", DASHBOARD.combo_filter1, null, "Academic Year Error");
            int getSEMID = methods.getEntityID("semID", "tbl_semester", "semester", DASHBOARD.combo_filter2, null, "Semester Error");
            methods.displayComboBoxNoFirst("select act_name from tbl_activities where Academic_Year = '" + getAYID + "' and Semester = '" + getSEMID + "'", DASHBOARD.combo_filter3, "act_name");

        }

    }

    public static String printAttendacneSQL, actFinalSQL, Attendacefilename;
    public static String filter1, filter2, filter3;

    public static void DisplayTableReport(String filtertype, JComboBox filterby, JTable table) {
        if (filtertype.equals("Events")) {
            methods.displayTable("select * from tbl_events", table);
            DASHBOARD.total_countpanel.setVisible(false);
        }
        if (filtertype.equals("Activities")) {
            String query = "";
            DASHBOARD.total_countpanel.setVisible(false);
            if (filterby.getSelectedItem().equals("None")) {
                query = "";
                filter1 = "None";
                filter2 = "None";
                filter3 = "None";
            }
            if (filterby.getSelectedItem().equals("Events")) {
                query = "where b.eventName like '%" + DASHBOARD.combo_filter3.getSelectedItem() + "%'";
                filter1 = "None";
                filter2 = "None";
                filter3 = DASHBOARD.combo_filter3.getSelectedItem().toString();
            }
            if (filterby.getSelectedItem().equals("AY")) {
                query = "where d.academic_year like '%" + DASHBOARD.combo_filter1.getSelectedItem() + "%'";
                filter1 = DASHBOARD.combo_filter1.getSelectedItem().toString();
                filter2 = "None";
                filter3 = "None";
            }
            if (filterby.getSelectedItem().equals("Events/AY")) {
                query = "where b.eventName like '%" + DASHBOARD.combo_filter3.getSelectedItem() + "%' and d.academic_year  like '%" + DASHBOARD.combo_filter1.getSelectedItem() + "%'";
                filter1 = DASHBOARD.combo_filter1.getSelectedItem().toString();
                filter2 = "None";
                filter3 = DASHBOARD.combo_filter3.getSelectedItem().toString();
            }
            if (filterby.getSelectedItem().equals("AY/Sem")) {
                query = "where d.academic_year like '%" + DASHBOARD.combo_filter1.getSelectedItem() + "%' and e.semester  like '%" + DASHBOARD.combo_filter2.getSelectedItem() + "%'";
                filter1 = DASHBOARD.combo_filter1.getSelectedItem().toString();
                filter2 = DASHBOARD.combo_filter2.getSelectedItem().toString();
                filter3 = "None";
            }
            if (filterby.getSelectedItem().equals("Events/AY/Sem")) {
                filter1 = DASHBOARD.combo_filter1.getSelectedItem().toString();
                filter2 = DASHBOARD.combo_filter2.getSelectedItem().toString();
                filter3 = DASHBOARD.combo_filter3.getSelectedItem().toString();
                query = "where b.eventName  =  '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and"
                        + " d.academic_year  =  '" + DASHBOARD.combo_filter1.getSelectedItem() + "' and e.semester  = '" + DASHBOARD.combo_filter2.getSelectedItem() + "'";
            }
            methods.displayTable(sqlAct + " " + query, table);
            actFinalSQL = sqlAct + " " + query;
        }
        if (filtertype.equals("Attendance")) {
            DASHBOARD.total_countpanel.setVisible(true);
            int getActID = methods.getEntityID("activity_id", "tbl_activities", "act_name", DASHBOARD.combo_filter3, null, "No Events found! ");
            int getAYID = methods.getEntityID("ayID", "tbl_ay", "academic_year", DASHBOARD.combo_filter1, null, "Academic Year Error");
            int getsemID = methods.getEntityID("semID", "tbl_semester", "semester", DASHBOARD.combo_filter2, null, "Semester Error");
            if (filterby.getSelectedItem().toString().equals("Expected Audience")) {
                DASHBOARD.label_monpres.setVisible(false);
                DASHBOARD.label_monpres1.setVisible(false);
                DASHBOARD.label_1.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count from\n"
                        + "tbl_section sec\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_subject_events sube on sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "inner join tbl_activities as act on act.activity_id = sube.actID\n"
                        + "\n"
                        + "where act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and enrol.current_status = 'First Year' and act.Semester  = '" + getsemID + "'"));
                DASHBOARD.label_2.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count from\n"
                        + "tbl_section sec\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_subject_events sube on sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "inner join tbl_activities as act on act.activity_id = sube.actID\n"
                        + "\n"
                        + "where act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and enrol.current_status = 'Second Year' and act.Semester  = '" + getsemID + "'"));
                DASHBOARD.label_3.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count from\n"
                        + "tbl_section sec\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_subject_events sube on sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "inner join tbl_activities as act on act.activity_id = sube.actID\n"
                        + "\n"
                        + "where act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and enrol.current_status = 'Third Year' and act.Semester  = '" + getsemID + "'"));
                DASHBOARD.label_4.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count from\n"
                        + "tbl_section sec\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_subject_events sube on sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "inner join tbl_activities as act on act.activity_id = sube.actID\n"
                        + "\n"
                        + "where act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and enrol.current_status = 'Fourth Year' and act.Semester  = '" + getsemID + "'"));
                DASHBOARD.label_total.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count from\n"
                        + "tbl_section sec\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_subject_events sube on sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "inner join tbl_activities as act on act.activity_id = sube.actID\n"
                        + "\n"
                        + "where act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and act.Semester  = '" + getsemID + "'"));

                methods.displayTable(sqlAttendance1 + " where act.act_name  like  '%" + DASHBOARD.combo_filter3.getSelectedItem() + "%' and activity_Id = '" + getActID + "' and Academic_Year  ='" + getAYID + "' and act.Semester = '" + getsemID + "'group by concat(lastname,',',firstname,' ',substring(middlename,1,1),'.')   order by  concat(lastname,',',firstname,' ',substring(middlename,1,1),'.') asc", table);
                printAttendacneSQL = sqlAttendance1 + " where act.act_name like '%" + DASHBOARD.combo_filter3.getSelectedItem() + "%' and activity_Id = '" + getActID + "' and Academic_Year  ='" + getAYID + "' and act.Semester = '" + getsemID + "' group by concat(lastname,',',firstname,' ',substring(middlename,1,1),'.')  order by  concat(lastname,',',firstname,' ',substring(middlename,1,1),'.') asc";
                Attendacefilename = "expected_audience.jrxml";
                DASHBOARD.label_monpres1.setText("0");

            }
            if (filterby.getSelectedItem().toString().equals("Attendees")) {
                DASHBOARD.label_1.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count from tbl_attendance as att\n"
                        + "inner join tbl_activities as act on act.activity_id = att.`ActivityID`\n"
                        + "inner join tbl_subject_events sube on sube.`actID` = act.`activity_Id`\n"
                        + "inner join tbl_section sec on  sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "where stud.stud_id = att.`Stud_ID` and sube.`subject_ID` = sec.subject_id\n"
                        + "and  act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and enrol.current_status = 'First Year' and act.Semester  = '" + getsemID + "'"));
                DASHBOARD.label_2.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count from tbl_attendance as att\n"
                        + "inner join tbl_activities as act on act.activity_id = att.`ActivityID`\n"
                        + "inner join tbl_subject_events sube on sube.`actID` = act.`activity_Id`\n"
                        + "inner join tbl_section sec on  sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "where stud.stud_id = att.`Stud_ID` and sube.`subject_ID` = sec.subject_id\n"
                        + "and  act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and enrol.current_status = 'Second Year' and act.Semester  = '" + getsemID + "'"));
                DASHBOARD.label_3.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count from tbl_attendance as att\n"
                        + "inner join tbl_activities as act on act.activity_id = att.`ActivityID`\n"
                        + "inner join tbl_subject_events sube on sube.`actID` = act.`activity_Id`\n"
                        + "inner join tbl_section sec on  sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "where stud.stud_id = att.`Stud_ID` and sube.`subject_ID` = sec.subject_id\n"
                        + "and  act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and enrol.current_status = 'Third Year' and act.Semester  = '" + getsemID + "'"));
                DASHBOARD.label_4.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count from tbl_attendance as att\n"
                        + "inner join tbl_activities as act on act.activity_id = att.`ActivityID`\n"
                        + "inner join tbl_subject_events sube on sube.`actID` = act.`activity_Id`\n"
                        + "inner join tbl_section sec on  sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "where stud.stud_id = att.`Stud_ID` and sube.`subject_ID` = sec.subject_id\n"
                        + "and  act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and enrol.current_status = 'Fourth Year' and act.Semester  = '" + getsemID + "'"));
                DASHBOARD.label_total.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count from tbl_attendance as att\n"
                        + "inner join tbl_activities as act on act.activity_id = att.`ActivityID`\n"
                        + "inner join tbl_subject_events sube on sube.`actID` = act.`activity_Id`\n"
                        + "inner join tbl_section sec on  sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "where stud.stud_id = att.`Stud_ID` and sube.`subject_ID` = sec.subject_id\n"
                        + "and  act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and act.Semester  = '" + getsemID + "'   "));
                methods.displayTable(sqlAttendance2 + " and  act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and sube.`actID` = '" + getActID + "' and Academic_Year  ='" + getAYID + "' and act.Semester = '" + getsemID + "' group by concat(lastname,',',firstname,' ',substring(middlename,1,1),'.') order by  concat(lastname,',',firstname,' ',substring(middlename,1,1),'.') asc ", table);
                printAttendacneSQL = sqlAttendance2 + " and act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and sube.`actID` = '" + getActID + "' and Academic_Year  ='" + getAYID + "' and act.Semester = '" + getsemID + "' group by concat(lastname,',',firstname,' ',substring(middlename,1,1),'.')  order by  concat(lastname,',',firstname,' ',substring(middlename,1,1),'.') asc  ";
                Attendacefilename = "attendance_present.jrxml";
                DASHBOARD.label_monpres1.setText("0");
                DASHBOARD.label_monpres.setVisible(false);
                DASHBOARD.label_monpres1.setVisible(false);
            }
            if (filterby.getSelectedItem().toString().equals("Attendance Status")) {
                DASHBOARD.label_monpres.setVisible(true);
                DASHBOARD.label_monpres1.setVisible(true);
                DASHBOARD.label_1.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count  from\n"
                        + "tbl_section sec\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_subject_events sube on sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "left outer join tbl_attendance as ab on ab.`Stud_ID` = stud.`Stud_ID` and ab.`ActivityID` = sube.`actID`\n"
                        + "left join tbl_activities as act on act.activity_Id = sube.`actID`\n"
                        + "where  act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and enrol.current_status = 'First Year' and act.Semester  = '" + getsemID + "'"));
                DASHBOARD.label_2.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count  from\n"
                        + "tbl_section sec\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_subject_events sube on sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "left outer join tbl_attendance as ab on ab.`Stud_ID` = stud.`Stud_ID` and ab.`ActivityID` = sube.`actID`\n"
                        + "left join tbl_activities as act on act.activity_Id = sube.`actID`\n"
                        + "where  act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and enrol.current_status = 'Second Year' and act.Semester  = '" + getsemID + "'"));
                DASHBOARD.label_3.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count  from\n"
                        + "tbl_section sec\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_subject_events sube on sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "left outer join tbl_attendance as ab on ab.`Stud_ID` = stud.`Stud_ID` and ab.`ActivityID` = sube.`actID`\n"
                        + "left join tbl_activities as act on act.activity_Id = sube.`actID`\n"
                        + "where  act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and enrol.current_status = 'Third Year' and act.Semester  = '" + getsemID + "'"));
                DASHBOARD.label_4.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count  from\n"
                        + "tbl_section sec\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_subject_events sube on sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "left outer join tbl_attendance as ab on ab.`Stud_ID` = stud.`Stud_ID` and ab.`ActivityID` = sube.`actID`\n"
                        + "left join tbl_activities as act on act.activity_Id = sube.`actID`\n"
                        + "where  act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and enrol.current_status = 'Fourth Year' and act.Semester  = '" + getsemID + "'"));
                DASHBOARD.label_total.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count  from\n"
                        + "tbl_section sec\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_subject_events sube on sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "left outer join tbl_attendance as ab on ab.`Stud_ID` = stud.`Stud_ID` and ab.`ActivityID` = sube.`actID`\n"
                        + "left join tbl_activities as act on act.activity_Id = sube.`actID`\n"
                        + "where  act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and act.Semester  = '" + getsemID + "'"));
                DASHBOARD.label_monpres1.setText(methods.getTotalCount("select count(stud.`Stud_ID`) as count  from\n"
                        + "tbl_section sec\n"
                        + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id\n"
                        + "inner join tbl_subject_events sube on sube.subject_id = sec.subject_id\n"
                        + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id\n"
                        + "inner join tbl_student stud on stud.stud_id = enrol.stud_id\n"
                        + "left outer join tbl_attendance as ab on ab.`Stud_ID` = stud.`Stud_ID` and ab.`ActivityID` = sube.`actID`\n"
                        + "left join tbl_activities as act on act.activity_Id = sube.`actID`\n"
                        + "where  act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and ab.`attendanceID`!='' and act.Semester  = '" + getsemID + "'"));
                methods.displayTable(sqlAttendance3 + " where act.act_name ='" + DASHBOARD.combo_filter3.getSelectedItem() + "' and sube.`actID` = '" + getActID + "' and Academic_Year  ='" + getAYID + "' and act.Semester = '" + getsemID + "'  group by concat(lastname,',',firstname,' ',substring(middlename,1,1),'.') order by  concat(lastname,',',firstname,' ',substring(middlename,1,1),'.') asc", table);
                printAttendacneSQL = sqlAttendance3 + " where act.act_name = '" + DASHBOARD.combo_filter3.getSelectedItem() + "' and sube.`actID` = '" + getActID + "' and Academic_Year  ='" + getAYID + "' and act.Semester = '" + getsemID + "' group by concat(lastname,',',firstname,' ',substring(middlename,1,1),'.')  order by  concat(lastname,',',firstname,' ',substring(middlename,1,1),'.') asc";
                Attendacefilename = "attendance_absent.jrxml";
            }

        }

    }

    public static void PrintReport(JComboBox filterby) {
        if (filterby.getSelectedItem().toString().equals("Events")) {
            methods.printReport("event.jrxml", "select * from tbl_events", null, null, null, null);

        }
        if (filterby.getSelectedItem().toString().equals("Activities")) {

            methods.printReport("activities.jrxml", actFinalSQL, null, filter2, filter1, filter3);

        }
        if (filterby.getSelectedItem().toString().equals("Attendance")) {
            methods.printReport(Attendacefilename, printAttendacneSQL, null, DASHBOARD.combo_filter2.getSelectedItem().toString(), DASHBOARD.combo_filter1.getSelectedItem().toString(), DASHBOARD.combo_filter3.getSelectedItem().toString());
        }

    }
}
