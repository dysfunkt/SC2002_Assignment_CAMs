package cams.ui;

import cams.MainApp;
import cams.object.Camp;
import cams.object.person.*;
import cams.util.ScannerHelper;

import org.checkerframework.checker.units.qual.s;
import java.util.Scanner;

public class CampCommitteeMenuUI extends BaseUI{

    private Student currentCommittee;
    private Scanner input = ScannerHelper.getScannerInput();

    public CampCommitteeMenuUI (Student committee){
        this.currentCommittee = committee;
    }

    @Override
    protected int generateMenuScreen() {

        printHeader("Camp Committee Menu");
        System.out.println("1) "); 
        printBreaks();

        int choice = doMenuChoice(10, 0);
        switch(choice){
            case 1:
            
            default:
        }
        return 0;
    }
}
