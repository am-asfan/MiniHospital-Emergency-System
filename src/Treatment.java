/**
 * Treatment.java
 * --------------
 * Represents a completed treatment record for one patient.
 *
 * When a patient is dequeued from the Emergency Queue and treated,
 * a Treatment object is created and pushed onto the TreatmentStack.
 *
 * WHY A SEPARATE CLASS?
 * A treatment involves more than just the patient — it also records
 * which doctor treated them, what was diagnosed, and when it happened.
 * Keeping this in its own class follows the OOP principle of
 * SINGLE RESPONSIBILITY.
 *
 * FIELDS:
 *   patientId   - Links back to the patient record in the BST
 *   patientName - Copied for quick display (avoids BST lookup)
 *   doctorName  - The doctor who performed the treatment
 *   diagnosis   - The medical diagnosis given
 *   treatment   - The treatment/procedure performed
 *   treatmentDate - The date the treatment was completed
 */
public class Treatment {

    private int patientId;
    private String patientName;
    private String doctorName;
    private String diagnosis;
    private String treatment;
    private String treatmentDate;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Creates a complete treatment record.
     *
     * @param patientId     ID of the patient who was treated
     * @param patientName   Name of the patient
     * @param doctorName    Name of the treating doctor
     * @param diagnosis     Medical diagnosis
     * @param treatment     Treatment or procedure performed
     * @param treatmentDate Date of treatment (e.g., "2026-09-07")
     */
    public Treatment(int patientId, String patientName, String doctorName,
                     String diagnosis, String treatment, String treatmentDate) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.treatmentDate = treatmentDate;
    }

    // ---------------------------------------------------------------
    // GETTERS
    // ---------------------------------------------------------------

    public int getPatientId()       { return patientId; }
    public String getPatientName()  { return patientName; }
    public String getDoctorName()   { return doctorName; }
    public String getDiagnosis()    { return diagnosis; }
    public String getTreatment()    { return treatment; }
    public String getTreatmentDate(){ return treatmentDate; }

    // ---------------------------------------------------------------
    // DISPLAY METHOD
    // ---------------------------------------------------------------

    /**
     * Prints the full treatment record in a readable format.
     */
    public void displayTreatment() {
        System.out.println("---------------------------------------");
        System.out.println("  Patient ID     : " + patientId);
        System.out.println("  Patient Name   : " + patientName);
        System.out.println("  Doctor         : " + doctorName);
        System.out.println("  Diagnosis      : " + diagnosis);
        System.out.println("  Treatment      : " + treatment);
        System.out.println("  Treatment Date : " + treatmentDate);
        System.out.println("---------------------------------------");
    }
}
