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

        try {

            /* Get Punch/Badge/Shift Objects */
            Punch p = punchDAO.find(3634);
            Badge q = badgeDAO.find(p.getBadge().getId());
            Shift s = shiftDAO.find(b);

            /* Get/Adjust Daily Punch List */
            ArrayList<Punch> dailypunchlist = punchDAO.list(q, p.getOriginalTimestamp().toLocalDate());

            for (Punch punch : dailypunchlist) {
                punch.adjust(s);
            }

            /* JSON Conversion */
            String actualJSON = DAOUtility.getPunchListAsJSON(dailypunchlist);

            ArrayList<HashMap<String, String>> actual = (ArrayList) Jsoner.deserialize(actualJSON);
            System.out.println(actual);
            String expectedJSON = "[{\"originaltimestamp\":\"FRI 09\\/07\\/2018 06:50:35\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"FRI 09\\/07\\/2018 07:00:00\",\"adjustmenttype\":\"Shift Start\",\"terminalid\":\"104\",\"id\":\"3634\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"FRI 09\\/07\\/2018 12:03:54\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"FRI 09\\/07\\/2018 12:00:00\",\"adjustmenttype\":\"Lunch Start\",\"terminalid\":\"104\",\"id\":\"3687\",\"punchtype\":\"CLOCK OUT\"},{\"originaltimestamp\":\"FRI 09\\/07\\/2018 12:23:41\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"FRI 09\\/07\\/2018 12:30:00\",\"adjustmenttype\":\"Lunch Stop\",\"terminalid\":\"104\",\"id\":\"3688\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"FRI 09\\/07\\/2018 15:34:13\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"FRI 09\\/07\\/2018 15:30:00\",\"adjustmenttype\":\"Shift Stop\",\"terminalid\":\"104\",\"id\":\"3716\",\"punchtype\":\"CLOCK OUT\"}]";
            ArrayList<HashMap<String, String>> expected = (ArrayList) Jsoner.deserialize(expectedJSON);  
            System.out.println(expected);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


            
    }
}
