/**
 * TreatmentStack.java
 * -------------------
 * Implements a manual LIFO (Last-In, First-Out) stack for treatment history.
 *
 * WHY A STACK?
 * ------------
 * When we want to review recent treatments, the MOST RECENT treatment
 * is the most relevant — it should be on top and accessible first.
 * A stack perfectly models this "most recent first" access pattern.
 *
 * HOW IT WORKS (using a single top pointer):
 * -------------------------------------------
 *   top → [Latest Treatment]
 *              ↓
 *         [Previous Treatment]
 *              ↓
 *         [Oldest Treatment]
 *              ↓ null
 *
 *   Push (add)    : New node inserted AT THE TOP
 *   Pop  (remove) : Node removed FROM THE TOP
 *
 * OPERATIONS:
 *   1. push()    - Add a completed treatment record to the top
 *   2. pop()     - Remove and return the most recent treatment
 *   3. peek()    - View the top treatment without removing it
 *   4. display() - Show all treatment records from top to bottom
 *   5. isEmpty() - Check if the stack has any records
 */
public class TreatmentStack {

    private TreatmentNode top; // Points to the most recently added treatment
    private int size;          // Tracks total number of treatment records

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Creates an empty TreatmentStack.
     * top = null means no treatments have been recorded yet.
     */
    public TreatmentStack() {
        top = null;
        size = 0;
    }

    // ---------------------------------------------------------------
    // 1. PUSH — Add a treatment record to the top of the stack
    // ---------------------------------------------------------------

    /**
     * Pushes a completed treatment record onto the top of the stack.
     *
     * STEPS:
     * 1. Create a new TreatmentNode for this record.
     * 2. Point the new node's 'next' to the current top.
     *    (The new node is now on top of all previous records.)
     * 3. Update top to point to the new node.
     * 4. Increase size.
     *
     * Example (before push, stack has [Sara]):
     *   top → [Sara]
     *
     * After pushing [Ahmed]:
     *   top → [Ahmed] → [Sara] → null
     *
     * @param treatment The completed treatment to push
     */
    public void push(Treatment treatment) {
        TreatmentNode newNode = new TreatmentNode(treatment);

        // New node points down to the old top
        newNode.next = top;

        // New node becomes the new top
        top = newNode;

        size++;
        System.out.println("[SUCCESS] Treatment record for Patient "
                + treatment.getPatientName()
                + " (ID: " + treatment.getPatientId()
                + ") saved to treatment history.");
    }

    // ---------------------------------------------------------------
    // 2. POP — Remove and return the most recent treatment
    // ---------------------------------------------------------------

    /**
     * Removes and returns the treatment record from the TOP of the stack.
     * This is always the most recently completed treatment.
     *
     * STEPS:
     * 1. Check if stack is empty → if so, print error and return null.
     * 2. Save the top treatment record.
     * 3. Move top down to the next node.
     * 4. Decrease size and return the saved record.
     *
     * @return The most recent Treatment record, or null if empty
     */
    public Treatment pop() {
        if (isEmpty()) {
            System.out.println("[INFO] Treatment history is empty. "
                    + "No records to remove.");
            return null;
        }

        // Save the top treatment
        Treatment latestTreatment = top.treatment;

        // Move top to the node below
        top = top.next;

        size--;
        System.out.println("[SUCCESS] Latest treatment record removed.");
        return latestTreatment;
    }

    // ---------------------------------------------------------------
    // 3. PEEK — View top without removing
    // ---------------------------------------------------------------

    /**
     * Returns the top treatment record WITHOUT removing it.
     * Useful for displaying who was most recently treated.
     *
     * @return The top Treatment, or null if empty
     */
    public Treatment peek() {
        if (isEmpty()) {
            System.out.println("[INFO] Treatment history is empty.");
            return null;
        }
        return top.treatment;
    }

    // ---------------------------------------------------------------
    // 4. DISPLAY — Show all treatment records from top to bottom
    // ---------------------------------------------------------------

    /**
     * Displays all treatment records from most recent (top) to oldest (bottom).
     *
     * We use a temporary pointer (current) starting at top,
     * following 'next' pointers downward through the stack.
     * The actual stack is NOT modified.
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("[INFO] Treatment history is empty. "
                    + "No records to display.");
            return;
        }

        System.out.println("\n===== TREATMENT HISTORY =====");
        System.out.println("  Total records: " + size);

        TreatmentNode current = top; // Start at the top (most recent)
        int recordNumber = 1;

        while (current != null) {
            System.out.println("\n  [Record #" + recordNumber + "]");
            current.treatment.displayTreatment();
            current = current.next; // Move down the stack
            recordNumber++;
        }

        System.out.println("=================================================");
    }

    // ---------------------------------------------------------------
    // 5. UTILITY METHODS
    // ---------------------------------------------------------------

    /**
     * Returns true if there are no treatment records in the stack.
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Returns the number of treatment records in the stack.
     */
    public int size() {
        return size;
    }
}
