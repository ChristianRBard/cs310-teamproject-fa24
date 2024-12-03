/**
 * <p>Represents an employee in the organization.</p>
 * Contains personal information, department details, and employment specifics.
 * @Author ChristianRBard
 */

package edu.jsu.mcis.cs310.tas_fa24;

import java.sql.Date;


public class Employee {
    
    /**
     * <p>Constructs an Employee object.</p>
     *
     * @param id The unique identifier of the employee.
     * @param badgeId The badge ID of the employee.
     * @param firstName The first name of the employee.
     * @param middleName The middle name of the employee.
     * @param lastName The last name of the employee.
     * @param employeeTypeId The ID representing the type of employment.
     * @param department The department the employee belongs to.
     * @param shiftId The shift ID associated with the employee.
     * @param active The activation date of the employee.
     */
    
    private int id;
    private Badge badgeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private int employeeTypeId;
    private Department department;
    private Shift shiftId;
    private Date active;

    public Employee(int id, Badge badgeId, String firstName, String middleName, String lastName, 
                    int employeeTypeId, Department department, Shift shiftId, Date active) {
        this.id = id;
        this.badgeId = badgeId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.employeeTypeId = employeeTypeId;
        this.department = department;
        this.shiftId = shiftId;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public Badge getBadgeId() {
        return badgeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getEmployeeTypeId() {
        return employeeTypeId;
    }

    public Department getDepartment() {
        return department;
    }

    public Shift getShiftId() {
        return shiftId;
    }

    public Date getActiveDate() {
        return active;
    }

    @Override
    public String toString() {
        return "ID #" + id + ": " + lastName + ", " + firstName + " " + middleName 
               + " (#" + badgeId + "), Type: " + getEmployeeTypeDescription()
               + ", Department: " + department.getDescription() + ", Active: " + formatDate(active);
    }

    private String getEmployeeTypeDescription() {
        return switch (employeeTypeId) {
            case 1 -> "Full-Time";
            default -> "Temporary / Part-Time";
        }; //default: return "Unknown Type";
    }

    private String formatDate(Date date) {
        // Assuming you want the format MM/dd/yyyy for the active date
        return new java.text.SimpleDateFormat("MM/dd/yyyy").format(date);
    }
}