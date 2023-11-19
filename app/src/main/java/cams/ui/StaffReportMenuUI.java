package cams.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

import cams.util.ScannerHelper;
import cams.MainApp;
import cams.object.appitem.*;
import cams.object.person.*;
import cams.util.CSVStringHelper;
import cams.util.IDHelper;
import cams.util.ReportIOHelper;

public class StaffReportMenuUI extends BaseUI{

    private Camp campToPrint;

    protected StaffReportMenuUI(Camp campToPrint){
        this.campToPrint = campToPrint;
    }

    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Generate Report");
        System.out.println("Choose Report Type: ");
        System.out.println("1) Participant List");
        System.out.println("2) Camp Commitee Performance Report");
        System.out.println("3) Students' Enquiry Report");
        System.out.println("0) Cancel");
        int choice = doMenuChoice(4, 0);
        input.nextLine();
        switch (choice) {
            case 1:
                try {
                    getFilter();
                } catch (IOException e) {
                    System.out.println("[ERROR] Failed to generate report. (" + e.getLocalizedMessage() + ")");
                }
                break;
            case 2:
                try {
                    ReportIOHelper.generatePerformanceReport(campToPrint);
                } catch (IOException e) {
                    System.out.println("[ERROR] Failed to generate report. (" + e.getLocalizedMessage() + ")");
                }
                break;
            case 3:
                try {
                    ReportIOHelper.generateStudentEnquiryReport(campToPrint);
                } catch (IOException e) {
                    System.out.println("[ERROR] Failed to generate report. (" + e.getLocalizedMessage() + ")");
                }
                break;
            case 0:
                return -1;
            default:
                throw new MenuChoiceInvalidException("Staff Report Menu");
        }
        return -1;
    }

    private void getFilter() throws IOException {
        System.out.println("Filter by: ");
        System.out.println("1) All Participants");
        System.out.println("2) Attendees");
        System.out.println("3) Camp Committee");
        System.out.println("0) Cancel");
        printBreaks();
        int choice = doMenuChoice(4, 0);
        input.nextLine();
        switch (choice) {
            case 1:
                ReportIOHelper.generateParticipantListReport(campToPrint);
                break;
            case 2:
                ReportIOHelper.generateAttendeeListReport(campToPrint);
                break;
            case 3:
                ReportIOHelper.generateCampCommitteeListReport(campToPrint);
                break;
            default:
                break;
        }
    }
    
}
