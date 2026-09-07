/**
 * TreatmentNode.java
 * ------------------
 * Represents a single node in the Treatment Stack linked chain.
 *
 * The stack is implemented as a linked list of TreatmentNodes.
 * Each node holds:
 *   - A completed Treatment record
 *   - A pointer to the node BELOW it in the stack
 *
 * The "top" of the stack is always the most recently pushed node.
 * Nodes link downward: top → next → next → null (bottom)
 *
 * Why linked nodes instead of an array?
 *   - The stack can grow or shrink dynamically.
 *   - No maximum size limit needed.
 */
public class TreatmentNode {

    Treatment treatment; // The completed treatment record stored here
    TreatmentNode next;  // Pointer to the node below this one in the stack

    /**
     * Constructor: Creates a new stack node for one treatment.
     *
     * @param treatment The treatment record to store
     */
    public TreatmentNode(Treatment treatment) {
        this.treatment = treatment;
        this.next = null; // No node below yet (will be set during push)
    }
}
