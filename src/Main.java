import java.util.Scanner;

/**
 * Main.java
 * ---------
 * The entry point of the Mini Hospital Emergency Management System.
 *
 * This class contains the interactive console menu that connects
 * all four data structures into one realistic hospital workflow:
 *
 *   WORKFLOW:
 *   1. Register Patient  → stored in BST (by Patient ID)
 *   2. Add to Queue      → patient waits in FIFO Emergency Queue
 *   3. Treat Next        → dequeue patient, record in LIFO Stack
 *   4. Add Visit         → appended to patient's own Singly Linked List
 *
 * All data structure objects are created here and passed around as needed.
 *
 * INPUT HANDLING:
 *   - Scanner reads from System.in (keyboard)
 *   - All menu choices are validated
 *   - Invalid numeric input is caught and handled gracefully
 */
public class Main {

    // ---------------------------------------------------------------
    // DATA STRUCTURE INSTANCES
    // (created once, shared across all menu operations)
    // ---------------------------------------------------------------

    static PatientBST patientBST         = new PatientBST();       // BST for patient records
    static EmergencyQueue emergencyQueue = new EmergencyQueue();    // FIFO queue
    static TreatmentStack treatmentStack = new TreatmentStack();    // LIFO stack
    static Scanner scanner               = new Scanner(System.in);  // Keyboard input

    // ---------------------------------------------------------------
    // MAIN METHOD
    // ---------------------------------------------------------------

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println(" MINI HOSPITAL EMERGENCY MANAGEMENT SYSTEM");
        System.out.println("         Welcome to the System");
        System.out.println("=========================================");

        boolean running = true;

        while (running) {
            printMenu();

            int choice = readIntInput("Enter your choice: ");

            switch (choice) {
                case 1:  registerNewPatient();      break;
                case 2:  searchPatient();            break;
                case 3:  deletePatient();            break;
                case 4:  displayAllPatients();       break;
                case 5:  addPatientToQueue();        break;
                case 6:  treatNextPatient();         break;
                case 7:  displayQueue();             break;
                case 8:  addCompletedTreatment();   break;
                case 9:  popLatestTreatment();       break;
                case 10: displayTreatmentHistory(); break;
                case 11: addPatientVisit();          break;
                case 12: removePatientVisit();       break;
                case 13: searchPatientVisit();       break;
                case 14: displayPatientVisitHistory(); break;
                case 15:
                    System.out.println("\n Thank you for using the Hospital System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("[ERROR] Invalid choice. Please enter a number between 1 and 15.");
            }
        }

        scanner.close();
    }

    // ---------------------------------------------------------------
    // MENU DISPLAY
    // ---------------------------------------------------------------

    /**
     * Prints the main menu to the console.
     */
    static void printMenu() {
        System.out.println("\n=========================================");
        System.out.println(" MINI HOSPITAL EMERGENCY MANAGEMENT SYSTEM");
        System.out.println("=========================================");
        System.out.println("  PATIENT RECORDS");
        System.out.println("    1.  Register New Patient");
        System.out.println("    2.  Search Patient");
        System.out.println("    3.  Delete Patient");
        System.out.println("    4.  Display All Patients");
        System.out.println("-----------------------------------------");
        System.out.println("  EMERGENCY QUEUE");
        System.out.println("    5.  Add Patient to Emergency Queue");
        System.out.println("    6.  Treat Next Patient");
        System.out.println("    7.  Display Emergency Queue");
        System.out.println("-----------------------------------------");
        System.out.println("  TREATMENT HISTORY");
        System.out.println("    8.  Add Completed Treatment");
        System.out.println("    9.  Remove Latest Treatment");
        System.out.println("    10. Display Treatment History");
        System.out.println("-----------------------------------------");
        System.out.println("  PATIENT VISIT HISTORY");
        System.out.println("    11. Add Patient Visit");
        System.out.println("    12. Remove Patient Visit");
        System.out.println("    13. Search Patient Visit");
        System.out.println("    14. Display Patient Visit History");
        System.out.println("-----------------------------------------");
        System.out.println("    15. Exit");
        System.out.println("=========================================");
    }

    // ================================================================
    //  SECTION 1: PATIENT BST OPERATIONS
    // ================================================================

    /**
     * MENU OPTION 1 — Register New Patient
     *
     * Reads patient details from the user and inserts a new Patient
     * into the Binary Search Tree using the Patient ID as the key.
     *
     * Validation:
     *   - Patient ID must be a positive integer
     *   - Age must be a positive integer
     *   - Name and condition cannot be blank
     */
    static void registerNewPatient() {
        System.out.println("\n--- REGISTER NEW PATIENT ---");

        int id = readPositiveIntInput("Enter Patient ID (positive number): ");
        if (patientBST.search(id) != null) {
            System.out.println("[ERROR] Patient ID " + id + " is already registered in the system.");
            return;
        }

        String name = readNonEmptyStringInput("Enter Patient Name: ");
        int age = readPositiveIntInput("Enter Age: ");
        String contact = readNonEmptyStringInput("Enter Contact Number: ");
        String condition = readNonEmptyStringInput("Enter Medical Condition: ");

        Patient newPatient = new Patient(id, name, age, contact, condition);
        if (patientBST.insert(newPatient)) {
            System.out.println("[SUCCESS] Patient " + name
                    + " (ID: " + id + ") registered successfully.");
        }
    }

    /**
     * MENU OPTION 2 — Search Patient
     *
     * Searches the BST for a patient using their Patient ID.
     * Displays full patient details if found.
     */
    static void searchPatient() {
        System.out.println("\n--- SEARCH PATIENT ---");
        int id = readPositiveIntInput("Enter Patient ID to search: ");

        Patient found = patientBST.search(id);
        if (found != null) {
            System.out.println("[FOUND] Patient details:");
            found.displayPatient();

            int queuePos = emergencyQueue.getPosition(id);
            if (queuePos != -1) {
                System.out.println("  Queue Status     : Waiting in Emergency Queue (Position: " + queuePos + ")");
            } else {
                System.out.println("  Queue Status     : Not in waiting queue");
            }
            System.out.println("  Total Visits     : " + found.getVisitHistory().size() + " recorded visit(s)");
            System.out.println("---------------------------------------");
        } else {
            System.out.println("[NOT FOUND] No patient with ID " + id
                    + " exists in the system.");
        }
    }

    /**
     * MENU OPTION 3 — Delete Patient
     *
     * Deletes a patient from the BST by their Patient ID.
     * Handles all three BST deletion cases internally.
     */
    static void deletePatient() {
        System.out.println("\n--- DELETE PATIENT ---");
        int id = readPositiveIntInput("Enter Patient ID to delete: ");
        if (patientBST.search(id) == null) {
            System.out.println("[ERROR] Patient ID " + id + " not found. Cannot delete.");
            return;
        }

        if (emergencyQueue.removePatient(id)) {
            System.out.println("[INFO] Patient was also removed from the emergency waiting queue.");
        }
        patientBST.delete(id);
    }

    /**
     * MENU OPTION 4 — Display All Patients
     *
     * Performs an in-order traversal of the BST to display
     * all patients in ascending Patient ID order.
     */
    static void displayAllPatients() {
        System.out.println("\n--- DISPLAY ALL PATIENTS ---");
        patientBST.displayInOrder();
    }

    // ================================================================
    //  SECTION 2: EMERGENCY QUEUE OPERATIONS
    // ================================================================

    /**
     * MENU OPTION 5 — Add Patient to Emergency Queue
     *
     * Looks up a patient in the BST and adds them to the FIFO queue.
     * We search the BST first to make sure the patient is registered.
     */
    static void addPatientToQueue() {
        System.out.println("\n--- ADD PATIENT TO EMERGENCY QUEUE ---");
        int id = readPositiveIntInput("Enter Patient ID to add to queue: ");

        Patient patient = patientBST.search(id);
        if (patient == null) {
            System.out.println("[ERROR] Patient ID " + id
                    + " not found. Please register the patient first.");
            return;
        }

        int pos = emergencyQueue.getPosition(id);
        if (pos != -1) {
            System.out.println("[ERROR] Patient " + patient.getPatientName()
                    + " (ID: " + id + ") is already in the emergency queue at position " + pos + ".");
            return;
        }

        emergencyQueue.enqueue(patient);
    }

    /**
     * MENU OPTION 6 — Treat Next Patient (Dequeue)
     *
     * Removes the first patient from the emergency queue (FIFO).
     * Displays who is being treated next.
     */
    static void treatNextPatient() {
        System.out.println("\n--- TREAT NEXT PATIENT ---");
        Patient patient = emergencyQueue.dequeue();

        if (patient != null) {
            System.out.println("\n  Treating patient now:");
            patient.displayPatient();

            System.out.print("Do you want to record completed treatment for this patient now? (y/n): ");
            if (!scanner.hasNextLine()) {
                System.out.println("\nThank you for using the Hospital System. Goodbye!");
                System.exit(0);
            }
            String ans = scanner.nextLine().trim().toLowerCase();
            if (ans.equals("y") || ans.equals("yes")) {
                recordTreatmentForPatient(patient);
            } else {
                System.out.println("[INFO] You can record this treatment later using Option 8.");
            }
        }
    }

    /**
     * MENU OPTION 7 — Display Emergency Queue
     *
     * Displays all patients currently waiting in the queue,
     * from front (next to be treated) to rear (last arrived).
     */
    static void displayQueue() {
        System.out.println("\n--- EMERGENCY QUEUE ---");
        emergencyQueue.display();
    }

    // ================================================================
    //  SECTION 3: TREATMENT STACK OPERATIONS
    // ================================================================

    /**
     * MENU OPTION 8 — Add Completed Treatment (Push)
     *
     * Reads treatment details and pushes a new Treatment record
     * onto the TreatmentStack (LIFO).
     *
     * The patient must exist in the BST (we look them up first).
     */
    static void addCompletedTreatment() {
        System.out.println("\n--- ADD COMPLETED TREATMENT ---");

        int patientId = readPositiveIntInput("Enter Patient ID: ");

        Patient patient = patientBST.search(patientId);
        if (patient == null) {
            System.out.println("[ERROR] Patient ID " + patientId
                    + " not found. Cannot record treatment.");
            return;
        }

        recordTreatmentForPatient(patient);
    }

    /**
     * Helper to record treatment details for a patient and sync with their visit history.
     */
    static void recordTreatmentForPatient(Patient patient) {
        int patientId = patient.getPatientId();
        String doctorName   = readNonEmptyStringInput("Enter Doctor Name: ");
        String diagnosis    = readNonEmptyStringInput("Enter Diagnosis: ");
        String treatment    = readNonEmptyStringInput("Enter Treatment Performed: ");
        String date         = readNonEmptyStringInput("Enter Treatment Date (e.g. 2026-09-07): ");

        Treatment newTreatment = new Treatment(
                patientId, patient.getPatientName(),
                doctorName, diagnosis, treatment, date);

        treatmentStack.push(newTreatment);

        // Also add to patient's personal visit history linked list so records stay in sync
        int autoVisitId = patient.getVisitHistory().size() + 1;
        while (patient.getVisitHistory().searchVisit(autoVisitId) != null) {
            autoVisitId++;
        }
        Visit visit = new Visit(autoVisitId, date, doctorName, diagnosis, treatment);
        patient.getVisitHistory().addVisit(visit);
    }

    /**
     * MENU OPTION 9 — Pop Latest Treatment
     *
     * Removes and displays the most recently completed treatment
     * from the top of the TreatmentStack (LIFO).
     */
    static void popLatestTreatment() {
        System.out.println("\n--- REMOVE LATEST TREATMENT ---");
        Treatment latest = treatmentStack.pop();

        if (latest != null) {
            System.out.println("  [Removed Treatment Record]:");
            latest.displayTreatment();
        }
    }

    /**
     * MENU OPTION 10 — Display Treatment History
     *
     * Displays all treatment records from most recent (top)
     * to oldest (bottom of stack).
     */
    static void displayTreatmentHistory() {
        System.out.println("\n--- TREATMENT HISTORY ---");
        treatmentStack.display();
    }

    // ================================================================
    //  SECTION 4: VISIT LINKED LIST OPERATIONS
    // ================================================================

    /**
     * MENU OPTION 11 — Add Patient Visit
     *
     * Adds a new visit to a specific patient's singly linked list.
     *
     * HOW PATIENT-SPECIFIC VISIT HISTORY WORKS:
     *   1. We look up the Patient from the BST using their ID.
     *   2. Each Patient object has its own VisitLinkedList (created at registration).
     *   3. We call patient.getVisitHistory().addVisit(...) to add to THAT patient's list.
     *   4. This means different patients have completely separate visit histories.
     */
    static void addPatientVisit() {
        System.out.println("\n--- ADD PATIENT VISIT ---");

        int patientId = readPositiveIntInput("Enter Patient ID: ");

        Patient patient = patientBST.search(patientId);
        if (patient == null) {
            System.out.println("[ERROR] Patient ID " + patientId
                    + " not found. Cannot add visit.");
            return;
        }

        int visitId = readPositiveIntInput("Enter Visit ID: ");
        if (patient.getVisitHistory().searchVisit(visitId) != null) {
            System.out.println("[ERROR] Visit ID " + visitId + " already exists in this patient's history.");
            return;
        }

        String visitDate   = readNonEmptyStringInput("Enter Visit Date (e.g. 2026-09-07): ");
        String doctorName  = readNonEmptyStringInput("Enter Doctor Name: ");
        String diagnosis   = readNonEmptyStringInput("Enter Diagnosis: ");
        String treatment   = readNonEmptyStringInput("Enter Treatment: ");

        Visit newVisit = new Visit(visitId, visitDate, doctorName, diagnosis, treatment);
        patient.getVisitHistory().addVisit(newVisit);
    }

    /**
     * MENU OPTION 12 — Remove Patient Visit
     *
     * Removes a specific visit from a patient's visit history
     * by searching their linked list for the given Visit ID.
     */
    static void removePatientVisit() {
        System.out.println("\n--- REMOVE PATIENT VISIT ---");

        int patientId = readPositiveIntInput("Enter Patient ID: ");

        Patient patient = patientBST.search(patientId);
        if (patient == null) {
            System.out.println("[ERROR] Patient ID " + patientId + " not found.");
            return;
        }

        int visitId = readPositiveIntInput("Enter Visit ID to remove: ");
        patient.getVisitHistory().removeVisit(visitId);
    }

    /**
     * MENU OPTION 13 — Search Patient Visit
     *
     * Searches a patient's linked list for a specific visit by Visit ID.
     */
    static void searchPatientVisit() {
        System.out.println("\n--- SEARCH PATIENT VISIT ---");

        int patientId = readPositiveIntInput("Enter Patient ID: ");

        Patient patient = patientBST.search(patientId);
        if (patient == null) {
            System.out.println("[ERROR] Patient ID " + patientId + " not found.");
            return;
        }

        int visitId = readPositiveIntInput("Enter Visit ID to search: ");
        Visit found = patient.getVisitHistory().searchVisit(visitId);

        if (found != null) {
            System.out.println("[FOUND] Visit details:");
            found.displayVisit();
        } else {
            System.out.println("[NOT FOUND] Visit ID " + visitId
                    + " not found in " + patient.getPatientName()
                    + "'s visit history.");
        }
    }

    /**
     * MENU OPTION 14 — Display Patient Visit History
     *
     * Displays the full visit history linked list for a specific patient.
     */
    static void displayPatientVisitHistory() {
        System.out.println("\n--- PATIENT VISIT HISTORY ---");

        int patientId = readPositiveIntInput("Enter Patient ID: ");

        Patient patient = patientBST.search(patientId);
        if (patient == null) {
            System.out.println("[ERROR] Patient ID " + patientId + " not found.");
            return;
        }

        System.out.println("\n  Visit history for: " + patient.getPatientName()
                + " (ID: " + patientId + ")");
        patient.getVisitHistory().display();
    }

    // ================================================================
    //  INPUT VALIDATION HELPERS
    // ================================================================

    /**
     * Reads an integer from the user.
     * If the user types a non-integer, shows an error and asks again.
     *
     * @param prompt The message to display to the user
     * @return A valid integer
     */
    static int readIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                System.out.println("\nThank you for using the Hospital System. Goodbye!");
                System.exit(0);
            }
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue; // Ignore blank enter key presses silently without spamming error messages
            }
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Please enter a valid whole number.");
            }
        }
    }

    /**
     * Reads a POSITIVE integer (> 0) from the user.
     * Rejects zero and negative numbers.
     *
     * @param prompt The message to display to the user
     * @return A positive integer
     */
    static int readPositiveIntInput(String prompt) {
        while (true) {
            int value = readIntInput(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("[ERROR] Value must be a positive number (greater than 0).");
        }
    }

    /**
     * Reads a non-empty string from the user.
     * Rejects blank input (empty or whitespace only).
     *
     * @param prompt The message to display to the user
     * @return A non-empty trimmed string
     */
    static String readNonEmptyStringInput(String prompt) {
        while (true) {
            if (!prompt.isEmpty()) {
                System.out.print(prompt);
            }
            if (!scanner.hasNextLine()) {
                System.out.println("\nThank you for using the Hospital System. Goodbye!");
                System.exit(0);
            }
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("[ERROR] This field cannot be empty. Please try again.");
        }
    }
}
