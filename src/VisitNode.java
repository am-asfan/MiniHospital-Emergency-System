/**
 * VisitNode.java
 * --------------
 * Represents a single node in a patient's visit history linked list.
 *
 * Each node wraps one Visit record and holds a pointer to the
 * NEXT visit in the list (chronological order).
 *
 * Structure:
 *   [Visit 1] → [Visit 2] → [Visit 3] → null
 *    ↑ head
 *
 * This is a SINGLY linked list: each node only points FORWARD (next).
 * There is no backward (previous) pointer — that would be a doubly linked list.
 */
public class VisitNode {

    Visit visit;     // The visit record stored in this node
    VisitNode next;  // Pointer to the next visit in the list

    /**
     * Constructor: Creates a visit node for one visit.
     *
     * @param visit The visit record to store
     */
    public VisitNode(Visit visit) {
        this.visit = visit;
        this.next = null; // No next node yet — will be set when a later visit is added
    }
}
