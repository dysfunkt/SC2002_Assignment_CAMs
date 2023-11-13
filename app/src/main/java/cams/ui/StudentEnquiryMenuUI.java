package cams.ui;

public class StudentEnquiryMenuUI extends BaseUI{
    @Override
    protected int generateMenuScreen() {
        printHeader("Student Enquiry Menu");
        System.out.println("1) Submit An Enquiry");
        System.out.println("2) View My Enquiries");
        System.out.println("3) Back to Student Menu");
        System.out.println("0) Exit Application");
        printBreaks();

        int choice = doMenuChoice(3, 0);

        switch (choice) {
            case 1:
                studentSubmitEnquiry();
                break;
            case 2:
                studentViewEnquiries();
                break;
            case 3:
                System.out.println("Switching back to Student Menu.");
                return -1;
            case 0:
                System.out.println("Closing application...");
                return 1; //shutdown
            default:
                throw new MenuChoiceInvalidException("Student Enquiry Menu");
        }
        return 0;
    }

    private void studentSubmitEnquiry(){

    }


    private void studentViewEnquiries(){

    }

}
