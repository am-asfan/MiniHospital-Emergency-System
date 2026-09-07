/**
 * VisitLinkedList.java
 * --------------------
 * Implements a manual Singly Linked List to store a patient's visit history.
 *
 * WHY A SINGLY LINKED LIST?
 * -------------------------
 * A patient's visits happen in sequence over time. Each visit is added after
 * the previous one. We only ever traverse the list from beginning to end
 * (oldest visit to newest). A singly linked list is ideal for this because:
 *   - It grows dynamically (no fixed size)
 *   - Traversal is always in one direction (forward)
 *   - Simple structure that is easy to understand and explain
 *
 * HOW EACH PATIENT HAS THEIR OWN HISTORY:
 * ----------------------------------------
 * The Patient class has a field: private VisitLinkedList visitHistory;
 * This means each Patient object carries its OWN instance of VisitLinkedList.
 * When you call patient.getVisitHistory().addVisit(...), you are adding to
 * THAT specific patient's list only.
 *
 * STRUCTURE:
 *   head → [Visit 1] → [Visit 2] → [Visit 3] → null
 *
 * OPERATIONS:
 *   1. addVisit()     - Add a visit to the END of the list
 *   2. removeVisit()  - Remove a visit by Visit ID
 *   3. searchVisit()  - Find a visit by Visit ID
 *   4. display()      - Show all visits in chronological order
 *   5. isEmpty()      - Check if there are any visits
 */
public class VisitLinkedList {

    private VisitNode head; // Points to the first (oldest) visit
    private int size;       // Total number of visits stored

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Creates an empty visit history linked list.
     * head = null means no visits have been recorded yet.
     */
    public VisitLinkedList() {
        head = null;
        size = 0;
    }

    // ---------------------------------------------------------------
    // 1. ADD VISIT — Append a new visit to the END of the list
    // ---------------------------------------------------------------

    /**
     * Adds a new visit to the END (tail) of the linked list.
     * Adding at the end preserves chronological order
     * (first visit added is first in the list).
     *
     * STEPS:
     * 1. Create a new VisitNode for this visit.
     * 2. If list is EMPTY → new node becomes the head.
     * 3. If NOT empty → traverse to the last node,
     *                    then attach the new node at the end.
     * 4. Increase size.
     *
     * @param visit The new visit record to add
     */
    public void addVisit(Visit visit) {
        // Check for duplicate Visit ID
        if (searchVisit(visit.getVisitId()) != null) {
            System.out.println("[ERROR] Visit ID " + visit.getVisitId()
                    + " already exists for this patient.");
            return;
        }

        VisitNode newNode = new VisitNode(visit);

        if (isEmpty()) {
            // List was empty: this visit is the very first
            head = newNode;
        } else {
            // Traverse to the last node in the list
            VisitNode current = head;
            while (current.next != null) {
                current = current.next; // Keep moving forward
            }
            // Attach the new node after the last node
            current.next = newNode;
        }

        size++;
        System.out.println("[SUCCESS] Visit ID " + visit.getVisitId()
                + " added to patient's history.");
    }

    // ---------------------------------------------------------------
    // 2. REMOVE VISIT — Remove a visit by Visit ID
    // ---------------------------------------------------------------

    /**
     * Removes the visit with the given Visit ID from the list.
     *
     * STEPS:
     * 1. Check if list is empty → error.
     * 2. If the HEAD node matches → move head forward (remove head).
     * 3. Otherwise, traverse the list keeping track of the PREVIOUS node.
     *    When we find the matching node, we bypass it by setting
     *    previous.next = current.next.
     * 4. If not found → error message.
     *
     * The bypassed node is no longer referenced and will be garbage collected.
     *
     * @param visitId The ID of the visit to remove
     */
    public void removeVisit(int visitId) {
        if (isEmpty()) {
            System.out.println("[INFO] No visits recorded yet. Cannot remove.");
            return;
        }

        // Case: The head node is the one to remove
        if (head.visit.getVisitId() == visitId) {
            head = head.next; // Move head forward (old head is removed)
            size--;
            System.out.println("[SUCCESS] Visit ID " + visitId + " removed.");
            return;
        }

        // Traverse the list looking for the node to remove
        VisitNode previous = head;
        VisitNode current = head.next;

        while (current != null) {
            if (current.visit.getVisitId() == visitId) {
                // Bypass current node: link previous directly to current's next
                previous.next = current.next;
                size--;
                System.out.println("[SUCCESS] Visit ID " + visitId + " removed.");
                return;
            }
            // Move both pointers forward
            previous = current;
            current = current.next;
        }

        // If we reach here, the visit was not found
        System.out.println("[ERROR] Visit ID " + visitId
                + " not found in this patient's history.");
    }

    // ---------------------------------------------------------------
    // 3. SEARCH VISIT — Find a visit by Visit ID
    // ---------------------------------------------------------------

    /**
     * Searches for a visit by Visit ID.
     *
     * We traverse the entire list from head to tail (linear search).
     * A BST is not used here because visits are not globally unique
     * across all patients — they are local to each patient's list.
     *
     * @param visitId The Visit ID to search for
     * @return The Visit object if found, or null if not found
     */
    public Visit searchVisit(int visitId) {
        VisitNode current = head; // Start from the beginning

        while (current != null) {
            if (current.visit.getVisitId() == visitId) {
                return current.visit; // Found it
            }
            current = current.next; // Move to the next visit
        }

        return null; // Not found
    }

    // ---------------------------------------------------------------
    // 4. DISPLAY — Show all visits in chronological order
    // ---------------------------------------------------------------

    /**
     * Displays all visits for this patient from oldest to newest.
     *
     * We traverse from head to the last node, printing each visit.
     * The list is NOT modified.
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("[INFO] No visit history recorded for this patient.");
            return;
        }

        System.out.println("  ====== Visit History ======");
        System.out.println("  Total visits: " + size);

        VisitNode current = head;
        int visitNumber = 1;

        while (current != null) {
            System.out.println("\n  [Visit #" + visitNumber + "]");
            current.visit.displayVisit();
            current = current.next;
            visitNumber++;
        }

        System.out.println("  ===============================================");
    }

    // ---------------------------------------------------------------
    // 5. UTILITY METHODS
    // ---------------------------------------------------------------

    /**
     * Returns true if the visit history is empty.
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns the total number of visits recorded.
     */
    public int size() {
        return size;
    }
}
