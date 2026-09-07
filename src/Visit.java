/**
 * Visit.java
 * ----------
 * Represents a single hospital visit record for a patient.
 *
 * Every time a patient completes treatment and it is added to their history,
 * a Visit object is created and appended to that patient's VisitLinkedList.
 *
 * FIELDS:
 *   visitId   - Unique ID for this specific visit (user-assigned)
 *   visitDate - Date of the visit (e.g., "2026-09-07")
 *   doctorName - Doctor who saw the patient on this visit
 *   diagnosis  - Medical diagnosis given during this visit
 *   treatment  - Treatment or procedure performed
 *
 * NOTE ON RELATIONSHIP:
 *   Each Patient object has its OWN VisitLinkedList.
 *   Each node in that linked list stores ONE Visit object.
 *   So the chain is: Patient → VisitLinkedList → VisitNode → Visit
 */
public class Visit {

    private int visitId;
    private String visitDate;
    private String doctorName;
    private String diagnosis;
    private String treatment;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Creates a new visit record.
     *
     * @param visitId    Unique visit identifier
     * @param visitDate  Date of this visit
     * @param doctorName Name of the doctor
     * @param diagnosis  Diagnosis given
     * @param treatment  Treatment performed
     */
    public Visit(int visitId, String visitDate, String doctorName,
                 String diagnosis, String treatment) {
        this.visitId = visitId;
        this.visitDate = visitDate;
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    // ---------------------------------------------------------------
    // GETTERS
    // ---------------------------------------------------------------

    public int getVisitId()       { return visitId; }
    public String getVisitDate()  { return visitDate; }
    public String getDoctorName() { return doctorName; }
    public String getDiagnosis()  { return diagnosis; }
    public String getTreatment()  { return treatment; }

    // ---------------------------------------------------------------
    // DISPLAY
    // ---------------------------------------------------------------

    /**
     * Prints the full visit record in a readable format.
     */
    public void displayVisit() {
        System.out.println("  - - - - - - - - - - - - - - - - - -");
        System.out.println("  Visit ID   : " + visitId);
        System.out.println("  Visit Date : " + visitDate);
        System.out.println("  Doctor     : " + doctorName);
        System.out.println("  Diagnosis  : " + diagnosis);
        System.out.println("  Treatment  : " + treatment);
    }
}
