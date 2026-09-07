/**
 * EmergencyQueue.java
 * -------------------
 * Implements a manual FIFO (First-In, First-Out) queue for emergency patients.
 *
 * WHY A QUEUE?
 * ------------
 * In an emergency room, patients are served in the order they arrive.
 * The first patient to arrive must be treated first — this is FIFO.
 *
 * HOW IT WORKS (using two pointers):
 * -----------------------------------
 *   front → points to the FIRST patient in line (next to be treated)
 *   rear  → points to the LAST patient in line (most recently arrived)
 *
 *   Enqueue (add)   : New node added at the REAR end
 *   Dequeue (remove): Node removed from the FRONT end
 *
 * Visual example:
 *   [Ahmed] → [Sara] → [Mohamed] → null
 *    ↑ front                ↑ rear
 *
 *   After dequeue:
 *   [Sara] → [Mohamed] → null
 *    ↑ front     ↑ rear
 *
 * OPERATIONS:
 *   1. enqueue()  - Add patient to the back of the line
 *   2. dequeue()  - Remove and return the patient at the front
 *   3. display()  - Show all waiting patients
 *   4. isEmpty()  - Check if queue is empty
 *   5. size()     - Return number of waiting patients
 */
public class EmergencyQueue {

    private QueueNode front; // Points to the first patient (next to be treated)
    private QueueNode rear;  // Points to the last patient (most recently added)
    private int size;        // Tracks how many patients are currently waiting

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Creates an empty Emergency Queue.
     * front = null and rear = null means there are no patients waiting.
     */
    public EmergencyQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    // ---------------------------------------------------------------
    // 1. ENQUEUE — Add a patient to the back of the queue
    // ---------------------------------------------------------------

    /**
     * Adds a patient to the END of the emergency queue (rear).
     *
     * STEPS:
     * 1. Create a new QueueNode for this patient.
     * 2. If the queue is EMPTY → the new node becomes both front AND rear.
     * 3. If NOT empty → attach the new node after the current rear,
     *                   then update rear to point to the new node.
     * 4. Increase the size counter.
     *
     * @param patient The patient joining the queue
     */
    public void enqueue(Patient patient) {
        QueueNode newNode = new QueueNode(patient);

        if (isEmpty()) {
            // Queue was empty: new node is both the front and the rear
            front = newNode;
            rear = newNode;
        } else {
            // Attach new node after the current last node
            rear.next = newNode;
            // Update rear to point to the new last node
            rear = newNode;
        }

        size++;
        System.out.println("[SUCCESS] Patient " + patient.getPatientName()
                + " (ID: " + patient.getPatientId()
                + ") added to the emergency queue.");
    }

    // ---------------------------------------------------------------
    // 2. DEQUEUE — Remove and return the patient at the front
    // ---------------------------------------------------------------

    /**
     * Removes the patient at the FRONT of the queue (the next to be treated).
     *
     * STEPS:
     * 1. Check if queue is empty → if so, print error and return null.
     * 2. Save the front patient (to return them).
     * 3. Move front forward to the next node in line.
     * 4. If front becomes null after removal → also set rear to null
     *    (the queue is now empty).
     * 5. Decrease size counter and return the dequeued patient.
     *
     * @return The Patient who was at the front of the queue, or null if empty
     */
    public Patient dequeue() {
        if (isEmpty()) {
            System.out.println("[INFO] The emergency queue is empty. "
                    + "No patients are waiting.");
            return null;
        }

        // Save the patient from the front node
        Patient treatedPatient = front.patient;

        // Move front to the next node in line
        front = front.next;

        // If front is now null, the queue is empty — rear must also be null
        if (front == null) {
            rear = null;
        }

        size--;

        System.out.println("[INFO] Patient " + treatedPatient.getPatientName()
                + " (ID: " + treatedPatient.getPatientId()
                + ") is next for treatment.");

        return treatedPatient;
    }

    // ---------------------------------------------------------------
    // 3. DISPLAY — Show all patients currently waiting
    // ---------------------------------------------------------------

    /**
     * Displays all patients in the queue from front to rear (in waiting order).
     *
     * We use a temporary pointer (current) that starts at front
     * and follows next pointers without modifying the actual queue.
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("[INFO] The emergency queue is empty.");
            return;
        }

        System.out.println("\n===== EMERGENCY QUEUE =====");
        System.out.println("  Total patients waiting: " + size);
        System.out.println("-------------------------------------------");

        QueueNode current = front; // Start from the front of the queue
        int position = 1;

        while (current != null) {
            System.out.println("  Position " + position + ": "
                    + "ID=" + current.patient.getPatientId()
                    + " | Name=" + current.patient.getPatientName()
                    + " | Condition=" + current.patient.getMedicalCondition());
            current = current.next; // Move to the next patient in line
            position++;
        }

        System.out.println("===========================================");
    }

    // ---------------------------------------------------------------
    // 4. UTILITY METHODS
    // ---------------------------------------------------------------

    /**
     * Returns true if there are no patients in the queue.
     */
    public boolean isEmpty() {
        return front == null;
    }

    /**
     * Returns the number of patients currently waiting.
     */
    public int size() {
        return size;
    }

    /**
     * Peeks at the front patient without removing them.
     * Useful for displaying who is next without dequeuing.
     *
     * @return The patient at the front, or null if empty
     */
    public Patient peek() {
        if (isEmpty()) {
            return null;
        }
        return front.patient;
    }

    /**
     * Checks if a patient with the given ID is already waiting in the queue.
     */
    public boolean contains(int patientId) {
        return getPosition(patientId) != -1;
    }

    /**
     * Returns the 1-based position of the patient in the queue, or -1 if not found.
     */
    public int getPosition(int patientId) {
        QueueNode current = front;
        int pos = 1;
        while (current != null) {
            if (current.patient.getPatientId() == patientId) {
                return pos;
            }
            current = current.next;
            pos++;
        }
        return -1;
    }

    /**
     * Removes a patient from anywhere in the queue by their Patient ID.
     * Used if a patient is deleted from the system or cancels waiting.
     *
     * @param patientId The ID of the patient to remove
     * @return true if removed, false if not in queue
     */
    public boolean removePatient(int patientId) {
        if (isEmpty()) {
            return false;
        }

        // If the patient is at the front
        if (front.patient.getPatientId() == patientId) {
            front = front.next;
            if (front == null) {
                rear = null;
            }
            size--;
            return true;
        }

        QueueNode prev = front;
        QueueNode curr = front.next;

        while (curr != null) {
            if (curr.patient.getPatientId() == patientId) {
                prev.next = curr.next;
                if (curr == rear) {
                    rear = prev;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }
}
