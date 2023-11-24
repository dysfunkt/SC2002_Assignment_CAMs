package cams.boundary.staff;

import java.io.IOException;
import java.util.Scanner;

import cams.boundary.BaseUI;
import cams.controller.report.ReportFactory;
import cams.util.exception.MenuChoiceInvalidException;
import cams.util.ui.ScannerHelper;

public class StaffReportMenuUI extends BaseUI{

    private String campID;

    protected StaffReportMenuUI(String ID){
        this.campID = ID;
    }

    private Scanner input = ScannerHelper.getScannerInput();

    protected int generateMenuScreen() {
        printHeader("Generate Report");
        System.out.println("Choose Report Type: ");
        System.out.println("1) Participant List");
        System.out.println("2) Camp Commitee Performance Report");
        System.out.println("3) Students' Enquiry Report");
        System.out.println("0) Cancel");
        printBreaks();
        int choice = doMenuChoice(3, 0);
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
                    ReportFactory.generatePerformanceReport(campID);
                } catch (IOException e) {
                    System.out.println("[ERROR] Failed to generate report. (" + e.getLocalizedMessage() + ")");
                }
                break;
            case 3:
                try {
                    ReportFactory.generateStudentEnquiryReport(campID);
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
        switch (choice) {
            case 1:
                ReportFactory.generateParticipantListReport(campID);
                break;
            case 2:
                ReportFactory.generateAttendeeListReport(campID);
                break;
            case 3:
                ReportFactory.generateCampCommitteeListReport(campID);
                break;
            default:
                break;
        }
    }
    
}
