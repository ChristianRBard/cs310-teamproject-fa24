//Caden Commit
//Connor Bain
//Christian Bard

package edu.jsu.mcis.cs310.tas_fa24;

import com.github.cliftonlabs.json_simple.Jsoner;
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
        System.err.println("Test Badge: " + b.toString());
        // output should be "Test Badge: #C4F37EFF (Welch, Travis C)"
        
        //Punch Adjust Test code. Also find Punch Test code.
        /*Punch p1 = punchDAO.find(3953);
        Shift s = shiftDAO.find(1);
        p1.adjust(s);
        System.out.println(p1.toString());
        System.out.println("#D2C39273 CLOCK OUT: MON 09/10/2018 15:15:00 (Interval Round)");
        System.out.println(p1.printAdjusted());
        System.out.println(s);*/

        // Badge List Test code
        Badge listTestBadge = badgeDAO.find("BE51FA92");
        LocalDate listTestDate = LocalDate.of(2018, 8, 1);
        Shift s2 = shiftDAO.find(b3);
        
        /*Badge listTestBadge = badgeDAO.find("7CB9642F");
        LocalDate listTestDate = LocalDate.of(2018, 9, 10);*/

        ArrayList<Punch> punchList = punchDAO.list(listTestBadge, listTestDate);
        
        Badge rangeListTestBadge = badgeDAO.find("7CB9642F");
        LocalDate rangeListTestDateStart = LocalDate.of(2018, 9, 8);
        LocalDate rangeListTestDateEnd = LocalDate.of(2018, 9, 14);
        ArrayList<Punch> rangePunchList = punchDAO.list(rangeListTestBadge, rangeListTestDateStart, rangeListTestDateEnd);
        
        
        // Punch List Test Code
        int counter = 0;
        for (Punch i : punchList) {
            counter++;
            System.out.println("Punch Number " + counter + ": " + i.toString());
        }
        counter = 0;
        for (Punch i : rangePunchList) {
            counter++;
            System.out.println("Date: " + i.getOriginalTimestamp() + "; " + "Punch Number " + counter + ": " + i.toString());
        }

        /* Get Punch/Badge/Shift Objects */
        Punch p = punchDAO.find(3634);
        Badge q = badgeDAO.find(p.getBadge().getId());
        Shift s = shiftDAO.find(b);
            
            
    }
}
