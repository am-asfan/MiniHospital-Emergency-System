/**
 * PatientNode.java
 * ----------------
 * Represents a single node inside the Binary Search Tree (BST).
 *
 * Think of it like a box that holds:
 *   - The actual Patient data
 *   - A pointer (reference) to the LEFT child node
 *   - A pointer (reference) to the RIGHT child node
 *
 * BST Rule (this is the key rule of the whole BST):
 *   - LEFT child has a SMALLER Patient ID than the parent node.
 *   - RIGHT child has a LARGER Patient ID than the parent node.
 *
 * Why a separate node class?
 *   - The Patient class should only know about patient data.
 *   - The PatientNode class handles the BST structure (linking nodes together).
 *   - This follows the OOP principle of SINGLE RESPONSIBILITY.
 */
public class PatientNode {

    // The actual patient data stored in this node
    Patient patient;

    // Pointer to the left child (smaller Patient ID)
    PatientNode left;

    // Pointer to the right child (larger Patient ID)
    PatientNode right;

    /**
     * Constructor: Creates a new leaf node (no children yet).
     * When a node is first created, it has no left or right children,
     * so both are set to null.
     *
     * @param patient The Patient object to store in this node
     */
    public PatientNode(Patient patient) {
        this.patient = patient;
        this.left = null;   // No left child yet
        this.right = null;  // No right child yet
    }
}
