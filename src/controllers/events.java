/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import models.methods;
import views.ACTIVATION;
import views.ATTENDANCE;
import views.DASHBOARD;
import static views.DASHBOARD.setEnabledActbutton;
import views.EVENT;
import views.EVENTSUBJECT;
import views.SPONSOR;

/**
 *
 * @author Sitty Pangcatan
 */
public class events {

    public static int userID;
    static int eventID;
    public static int ayID, ActID, durationID, semesterID, sponsorID, statusID = 1;
    public static String activityName;

    public static int SaveEvent(String savetype) {
        int returnstatement = 0;
        methods.jtextfieldValidator(EVENT.text_event, EVENT.label_event);
        if (methods.jtextfieldValidator(EVENT.text_event, EVENT.label_event) == true) {
            JOptionPane.showMessageDialog(null, methods.emptyfields,"Error Message", JOptionPane.ERROR_MESSAGE);

        } else {
            methods.commitTransaction("insert into tbl_events values(null,'" + EVENT.text_event.getText() + "')", "Successfully Save","Succcess Message");
            eventID = methods.getEntityID("EventID", "tbl_events", "eventName", null, EVENT.text_event.getText(), "Event ID");

            if (methods.committed == 1) {

                if (savetype.equals("save1")) {
                    EVENT.load_button.setEnabled(true);
                    returnstatement = 1;
                } else {
                    methods.displayComboBox("select * from tbl_events", ACTIVATION.combo_event, "eventName");
                    ACTIVATION.combo_event.setSelectedItem(EVENT.text_event.getText());
                    returnstatement = 2;
                }
                methods.committed = 0;

            }
        }
        return returnstatement;

    }

    public static int saveSponsor() {
        methods.jtextfieldValidator(SPONSOR.text_sponsor, SPONSOR.label_sponsor);
        if (methods.jtextfieldValidator(SPONSOR.text_sponsor, SPONSOR.label_sponsor) == true) {
            JOptionPane.showMessageDialog(null, "Error Message", methods.emptyfields, JOptionPane.ERROR_MESSAGE);
        } else {
            methods.commitTransaction("insert into tbl_event_sponsor values(null,'" + SPONSOR.text_sponsor.getText() + "')", "Succcess Message", "Successfully Save");
            if (methods.committed == 1) {
                methods.displayComboBox("select * from tbl_event_sponsor", ACTIVATION.combo_sponsor, "sponsor");
                ACTIVATION.combo_sponsor.setSelectedItem(SPONSOR.text_sponsor.getText());
                methods.committed = 0;
            }
        }
        return 1;
    }

    public static void ActivationForm() {
        methods.displayComboBox("select * from tbl_events", ACTIVATION.combo_event, "eventName");
        methods.displayComboBox("select * from tbl_ay", ACTIVATION.combo_ay, "academic_year");
        methods.displayComboBox("select * from tbl_event_duration", ACTIVATION.combo_duration, "duration");
        methods.displayComboBox("select * from tbl_semester", ACTIVATION.combo_semester, "semester");
        methods.displayComboBox("select * from tbl_event_sponsor", ACTIVATION.combo_sponsor, "sponsor");
    }

    public static void saveActivation() {
        eventID = methods.getEntityID("EventID", "tbl_events", "eventName", ACTIVATION.combo_event, null, "Event ID");
        ayID = methods.getEntityID("ayID", "tbl_ay", "academic_year", ACTIVATION.combo_ay, null, "Academic year ID");
        sponsorID = methods.getEntityID("sponsorID", "tbl_event_sponsor", "sponsor", ACTIVATION.combo_sponsor, null, "Sponsor ID");
        semesterID = methods.getEntityID("semID", "tbl_semester", "semester", ACTIVATION.combo_semester, null, "semester ID");
        durationID = methods.getEntityID("durationID", "tbl_event_duration", "duration", ACTIVATION.combo_duration, null, "duration ID");
        statusID = 1;
        activityName = ACTIVATION.combo_event.getSelectedItem() + " " + ACTIVATION.combo_ay.getSelectedItem();
        int  
            countCurrentActAY = Integer.parseInt(methods.getTotalCount("select count(*) as count from tbl_activities where eventID = '" + eventID + "'  and Academic_Year = '" + ayID + "'"));
        
        if (countCurrentActAY > 0) {
            JOptionPane.showMessageDialog(null, "Activity is already exist", "Error Message", JOptionPane.ERROR_MESSAGE);
        } else {

            methods.commitTransaction("insert into tbl_activities values("
                    + "null,"
                    + "'" + activityName + "',"
                    + "'" + eventID + "',"
                    + "'" + sponsorID + "',"
                    + "'" + ayID + "',"
                    + "'" + semesterID + "',"
                    + "'" + durationID + "',"
                    + "'" + statusID + "',"
                    + "'" + methods.simpleDateFormat.format(ACTIVATION.date_time_started.getDate()).toString() + "',"
                    + "'" + methods.simpleDateFormat.format(ACTIVATION.date_time_ended.getDate()).toString() +"',"
                    + "'Sign-in',"
                    + "CURRENT_TIMESTAMP"
                    + ")", "Successfully Save", "Succcess Message");
            ActID = methods.getEntityID("activity_id", "tbl_activities", "act_name", null, activityName, "Activity ID");
            if (methods.committed == 1) {
                ACTIVATION.button_load_subject.setEnabled(true);
                methods.committed = 0;
                ACTIVATION.jButton3.setEnabled(false);//save button activation
            }

        }
    }

    public static void searchSubject(JTable searchtable) {
        methods.displayTable("select subject_id from tbl_section where sy = '" + mainmenu.currentAY + "' and sem ='" + mainmenu.currentSem + "'\n"
                + "group by subject_id  ", searchtable);
    }

    public static void eventSubjectForm() {
        methods.displayComboBox("select * from tbl_activities where statusID = 1", EVENTSUBJECT.event_current, "act_name");
    }

    public static int SaveSubjectEvents(JTable loadedsubject) {
        ActID = methods.getEntityID("activity_id", "tbl_activities", "act_name", EVENTSUBJECT.event_current, null, "Activity ID");
        if (loadedsubject.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Error Message", "Please add Subject.", JOptionPane.ERROR_MESSAGE);
        } else {
            int counter = Integer.parseInt(methods.getTotalCount("select count(*) as count from tbl_subject_events where actID = '" + ActID + "' and event_ID = '" + eventID + "'"));
            if (counter < 0) {
                JOptionPane.showMessageDialog(null, "Error Message", "Subject is already added.", JOptionPane.ERROR_MESSAGE);
            } else {
                for (int rows = 0; rows < loadedsubject.getRowCount(); rows++) {
                    methods.commitTransaction("insert into tbl_subject_events values (null,'" + ActID + "','" + eventID + "','" + loadedsubject.getValueAt(rows, 0) + "')", null, null);
                }
                if (methods.committed == 1) {
                    methods.committed = 0;
                    JOptionPane.showMessageDialog(null, "Successfully Added.", "System Message", JOptionPane.INFORMATION_MESSAGE);
                    methods.displayComboBox("select act_name from tbl_activities", DASHBOARD.combo_event, "act_name");
                    EVENTSUBJECT.button_save1.setEnabled(true);
                }
            }

        }
        return 1;
    }

    /**
     * ATTENDANCE Form
     *
     *
     * @param actName
     */
    public static void getActivity(String actName) {

        ATTENDANCE.event_current.setSelectedItem(actName);
        ActID = methods.getEntityID("activity_id", "tbl_activities", "act_name", ATTENDANCE.event_current, null, "No Active Events Available. Attendance is closed!");
        String[] getActivity = {"activity_Id", "act_name", "Sponsor", "SY", "AY", "duration", "Date Time Started", "Date Time Ended", "session"};
        getActivity = methods.getResult("SELECT a.`activity_Id` 'Act. ID',a.`act_name`,c.sponsor 'Sponsor',e.semester 'SY',d.academic_year 'AY',f.duration 'Duration',concat(DATE_FORMAT(a.`time_started`, '%M %d, %Y'),' ',time_format(a.`time_started`,'%h:%i %p')) 'Date Time Started',concat(DATE_FORMAT(a.`time_ended`, '%M %d, %Y'),' ',time_format(a.`time_ended`,'%h:%i %p')) 'Date Time Ended',a.session,g.status 'Status' from tbl_activities as a\n"
                + "left join tbl_events as b on b.eventID = a.eventID\n"
                + "LEFT join tbl_event_sponsor as c on c.sponsorID = a.sponsorID\n"
                + "LEFT JOIN tbl_ay as d on d.ayID = a.Academic_Year\n"
                + "LEFT join tbl_semester as e on e.semID = a.Semester\n"
                + "left join tbl_event_duration as f on f.durationID = a.durationID\n"
                + "left join tbl_status as g on g.statusID = a.statusID where a.activity_Id = '" + ActID + "' and a.statusID = 1", getActivity, null);
        ATTENDANCE.label_time.setText(getActivity[6]);
        ATTENDANCE.label_time1.setText(getActivity[7]);
        ATTENDANCE.label_ay.setText(getActivity[4]);
        ATTENDANCE.label_sem.setText(getActivity[3]);
        ATTENDANCE.label_session.setText(getActivity[8]);
        ATTENDANCE.sign_button.setText(getActivity[8]);
    }

    public static void SearchEventInactive() {
        int count = Integer.parseInt(methods.getTotalCount("select count(*) as count from tbl_activities where statusID ='2' and act_name = '" + ATTENDANCE.event_current.getSelectedItem() + "'"));
        if (count > 0) {
//            System.out.print(count);
            ATTENDANCE.counter = 5;
            ATTENDANCE.error_pane.setVisible(true);
            ATTENDANCE.error_text.setText("Error Message: " + ATTENDANCE.event_current.getSelectedItem() + " is now officially closed!.");
//            JOptionPane.showMessageDialog(null, "Event Name:" + ATTENDANCE.event_current.getSelectedItem() + " is officially closed.\nPlease select another Active Events.", "System Message", JOptionPane.INFORMATION_MESSAGE);
            methods.displayComboBoxNoFirst("select * from tbl_activities where statusID = 1", ATTENDANCE.event_current, "act_name");

            if (ATTENDANCE.event_current.getItemCount() == 0) {
                ATTENDANCE.label_time.setText("Not Available");
                ATTENDANCE.label_time1.setText("Not Available");
                ATTENDANCE.label_ay.setText("Not Available");
                ATTENDANCE.label_sem.setText("Not Available");
                ATTENDANCE.label_session.setText("Not Available");
                ATTENDANCE.sign_button.setText("Not Available");
            } else {
                events.getActivity(ATTENDANCE.event_current.getSelectedItem().toString());
            }

        }

    }

    /**
     * view subjects form
     *
     * @param comb_events
     * @param subjects
     */
    public static void viewsubjects(JComboBox comb_events, JTable subjects) {
        methods.displayTable("SELECT a.`subject_ID` FROM tbl_subject_events  as a left JOIN tbl_activities as b on b.activity_Id = a.`actID` where b.act_name = '" + comb_events.getSelectedItem() + "' ", subjects);
    }
    public static String StudentID;

    /**
     * search student functio
     *
     * @param text
     */
    public static void searchStudent(JTextField text) {
        if (methods.jtextfieldValidator(text, ATTENDANCE.label_student) == true) {
            JOptionPane.showMessageDialog(null, "Please Enter Student ID");
        } else {
            String[] getStudentName = {"enrollment_id", "Stud_ID", "lastname", "firstname", "middlename", "section_id"};
            getStudentName = methods.getResult("select enroll.enrollment_id,stud.`Stud_ID`,lastname,firstname,middlename,sec.section_id from \n"
                    + "tbl_section sec \n"
                    + "inner join tbl_enrolldetails enroll on enroll.section_id = sec.section_id  \n"
                    + "inner join tbl_subject_events sube on sube.subject_id = sec.subject_id \n"
                    + "inner join tbl_enrollees enrol on enrol.enrollee_id = enroll.enrollment_id \n"
                    + "inner join tbl_student stud on stud.stud_id = enrol.stud_id where sube.`actID` = '" + ActID + "' and stud.`Stud_ID` like '%" + text.getText() + "%' limit 1", getStudentName, "Error Fetching Student");
            if (getStudentName[0].equals("enrollment_id")) {
//            JOptionPane.showMessageDialog(null, "No Records Found!");
            } else {
                StudentID = getStudentName[1];
                ATTENDANCE.text_fname.setText(getStudentName[3]);
                ATTENDANCE.text_mname.setText(getStudentName[4]);
                ATTENDANCE.text_lname.setText(getStudentName[2]);
                ATTENDANCE.sign_button.setEnabled(true);
            }
        }

    }

    /**
     * Save Session Attendance
     *
     * @param session
     * @param sessione*
     */
    public static void saveSessionStudent(String session) {
        if (methods.jtextfieldValidator(ATTENDANCE.search, ATTENDANCE.label_student) == true) {
            JOptionPane.showMessageDialog(null, "Please input Student ID.");
        } else {
            if (session.equals("Sign-in")) {
                int count_student = Integer.parseInt(methods.getTotalCount("select count(*) as count from tbl_attendance  where Stud_ID = '" + StudentID + "' and ActivityID = '" + ActID + "'"));
                if (count_student == 0) {
                    methods.commitTransaction("insert into tbl_attendance values("
                            + "null,'" + StudentID + "','" + ActID + "','" + userID + "',CURRENT_TIMESTAMP,'0000-00-00 00:00:00')", "Successfully Sign-in", methods.systemmessage);
                    if (methods.committed == 1) {
                        methods.committed = 0;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Already Sign-in!");
                }
            }
            if (session.equals("Sign-out")) {
                int count_student = Integer.parseInt(methods.getTotalCount("select count(*) as count from tbl_attendance  where Stud_ID = '" + StudentID + "' and ActivityID = '" + ActID + "' and timeout = '0000-00-00 00:00:00'"));
                if (count_student > 0) {
                    methods.commitTransaction("update tbl_attendance set timeout = CURRENT_TIMESTAMP,userID = '" + userID + "' WHERE Stud_ID = '" + StudentID + "' and ActivityID = '" + ActID + "' and timein!='0000-00-00 00:00:00'", "Successfully Sign-out", methods.systemmessage);
                    ATTENDANCE.sign_button.setEnabled(false);
                    if (methods.committed == 1) {
                        methods.committed = 0;
                    }
                } else {
                    int count_student2 = Integer.parseInt(methods.getTotalCount("select count(*) as count from tbl_attendance  where Stud_ID = '" + StudentID + "' and ActivityID = '" + ActID + "' and timeout != '0000-00-00 00:00:00'"));
                    if (count_student2 > 0) {
                        JOptionPane.showMessageDialog(null, "Already Sign-out.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Please Sign-in first.");
                    }

                }
            }
            ATTENDANCE.text_fname.setText("");
            ATTENDANCE.text_mname.setText("");
            ATTENDANCE.text_lname.setText("");
            ATTENDANCE.search.setText("");
            ATTENDANCE.sign_button.setEnabled(false);
        }

    }

    /* Deactivate activity function button event**/
    public static void deactivateAct(String actID) {
        int option = JOptionPane.showConfirmDialog(null, "Do you wish to Deactivate?");
        if (option == 0) {
            methods.commitTransaction("update tbl_activities set statusID = 2 where activity_Id = '" + actID + "' and statusID = 1", "Successfully Deactivate", methods.systemmessage);
            if (methods.committed == 1) {
                setEnabledActbutton(false);
            }
        }

    }

}
