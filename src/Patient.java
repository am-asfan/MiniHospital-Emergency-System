/**
 * Patient.java
 * ------------
 * This class represents a single patient in the hospital system.
 *
 * OOP Principle Used: ENCAPSULATION
 * - All fields are declared private so they cannot be accessed directly
 *   from outside the class.
 * - Public getter and setter methods are provided to read and update fields.
 *
 * Why this class is needed:
 * - Every data structure in this project (BST, Queue, Stack, Linked List)
 *   stores Patient objects or references to them.
 * - Keeping all patient details in one class avoids repeating the same
 *   fields in multiple places.
 */
public class Patient {

    // ---------------------------------------------------------------
    // FIELDS (private - encapsulated)
    // ---------------------------------------------------------------

    private int patientId;          // Unique identifier used as the BST key
    private String patientName;     // Full name of the patient
    private int age;                // Age of the patient (must be positive)
    private String contactNumber;   // Patient's phone number
    private String medicalCondition; // Brief description of why they are here

    // Each patient has their own personal visit history stored in a Singly
    // Linked List. This is how the linked list connects to each patient.
    private VisitLinkedList visitHistory;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Creates a new Patient with the given details.
     * Also automatically creates a new (empty) visit history linked list
     * for this patient.
     *
     * @param patientId        Unique numeric ID for this patient
     * @param patientName      Full name
     * @param age              Age (positive integer)
     * @param contactNumber    Phone number
     * @param medicalCondition Reason for visit / medical condition
     */
    public Patient(int patientId, String patientName, int age,
                   String contactNumber, String medicalCondition) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.age = age;
        this.contactNumber = contactNumber;
        this.medicalCondition = medicalCondition;

        // Each patient starts with an empty linked list for their visits.
        // This is created automatically when a Patient object is made.
        this.visitHistory = new VisitLinkedList();
    }

    // ---------------------------------------------------------------
    // GETTERS  (public read access)
    // ---------------------------------------------------------------

    public int getPatientId()           { return patientId; }
    public String getPatientName()      { return patientName; }
    public int getAge()                 { return age; }
    public String getContactNumber()    { return contactNumber; }
    public String getMedicalCondition() { return medicalCondition; }
    public VisitLinkedList getVisitHistory() { return visitHistory; }

    // ---------------------------------------------------------------
    // SETTERS  (public write access with basic validation)
    // ---------------------------------------------------------------

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    // ---------------------------------------------------------------
    // DISPLAY METHOD
    // ---------------------------------------------------------------

    /**
     * Prints all patient details to the console in a formatted layout.
     * Called by the BST in-order traversal and search operations.
     */
    public void displayPatient() {
        System.out.println("---------------------------------------");
        System.out.println("  Patient ID       : " + patientId);
        System.out.println("  Name             : " + patientName);
        System.out.println("  Age              : " + age);
        System.out.println("  Contact Number   : " + contactNumber);
        System.out.println("  Medical Condition: " + medicalCondition);
        System.out.println("---------------------------------------");
    }

    /**
     * Returns a brief summary string (used in queue/stack display).
     */
    @Override
    public String toString() {
        return "Patient[ID=" + patientId + ", Name=" + patientName
                + ", Condition=" + medicalCondition + "]";
    }
}
