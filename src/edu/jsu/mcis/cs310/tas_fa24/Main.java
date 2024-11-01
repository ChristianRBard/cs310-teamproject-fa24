//Caden Commit
//Connor Bain
//Christian Bard

package edu.jsu.mcis.cs310.tas_fa24;

import edu.jsu.mcis.cs310.tas_fa24.dao.*;
import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        
        // test database connectivity; get DAOs

        DAOFactory daoFactory = new DAOFactory("tas.jdbc");
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();
        
        // find badge

        Badge b = badgeDAO.find("C4F37EFF");
        
        Badge b3 = badgeDAO.find("4382D92D");
        
        Punch p = punchDAO.find(3433);
        
        Shift s = shiftDAO.find(1);
        Shift s2 = shiftDAO.find(b3);
        
        Badge listTestBadge = badgeDAO.find("7CB9642F");
        LocalDate listTestDate = LocalDate.of(2018, 9, 10);
        ArrayList<Punch> punchList = punchDAO.list(listTestBadge, listTestDate);
        
        Badge rangeListTestBadge = badgeDAO.find("7CB9642F");
        LocalDate rangeListTestDateStart = LocalDate.of(2018, 9, 8);
        LocalDate rangeListTestDateEnd = LocalDate.of(2018, 9, 14);
        ArrayList<Punch> rangePunchList = punchDAO.list(rangeListTestBadge, rangeListTestDateStart, rangeListTestDateEnd);
        
        // output should be "Test Badge: #C4F37EFF (Welch, Travis C)"
        
        System.err.println("Test Badge: " + b.toString());
        System.out.println(p.toString());
        System.out.println("Test Shift 1: " + s.toString());
        System.out.println("Test Shift 2: " + s2.toString());
        int counter = 0;
        for (Punch i : punchList) {
            counter++;
            System.out.println("Punch Number " + counter + ": " + i.toString());
        }
        counter = 0;
        for (Punch i : rangePunchList) {
            counter++;
            System.out.println("Date: " + i.getOriginalTimeStamp() + "; " + "Punch Number " + counter + ": " + i.toString());
        }
    }

}
