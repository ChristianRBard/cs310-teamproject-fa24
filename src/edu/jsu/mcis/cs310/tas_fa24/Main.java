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
        
        //Punch p = punchDAO.find(4943);

        Punch p1 = punchDAO.find(3716);
        Punch p2 = punchDAO.find(5004);

        /* Adjust Punches According to Shift Rulesets */
        
        
        
        Shift s = shiftDAO.find(1);
        Shift s2 = shiftDAO.find(2);
        
        //p.adjust(s);
        p1.adjust(s);
        p2.adjust(s2);
        
        // output should be "Test Badge: #C4F37EFF (Welch, Travis C)"
        
        //System.err.println("Test Badge: " + b.toString());
        //System.out.println(p.toString());
        //System.out.println(p.printAdjusted());
        System.out.println("#28DC3FB8 CLOCK OUT: FRI 09/07/2018 15:30:00 (Shift Stop)");
        System.out.println(p1.printAdjusted());
        System.out.println(s2);
        //System.out.println(p2.printAdjusted());
        /*System.out.println("Test Shift 1: " + s.toString());
        System.out.println("Test Shift 2: " + s2.toString());*/
        //p.adjust(s);
    }
}
