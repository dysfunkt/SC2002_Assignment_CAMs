package cams.ui;

public class StudentEnquiryMenuUI extends BaseUI{
    @Override
    protected int generateMenuScreen() {
        printHeader("Manage Enquiries");
        System.out.println("1) Submit Enquiries");
        System.out.println("2) View Enquiries");
        System.out.println("3) Back to Student Menu");
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
            default:
                throw new MenuChoiceInvalidException("Student Enquiries Menu");
        }
        return 0;
    }

    private void studentSubmitEnquiry(){

    }


    private void studentViewEnquiries(){

    }

}
