/**
 * PatientBST.java
 * ---------------
 * Implements a Binary Search Tree (BST) to store Patient records.
 *
 * WHY BST?
 * --------
 * We use Patient ID as the key. The BST property guarantees:
 *   - Left subtree IDs < parent ID
 *   - Right subtree IDs > parent ID
 *
 * This allows efficient operations:
 *   - Insert   : O(log n) average
 *   - Search   : O(log n) average
 *   - Delete   : O(log n) average
 *   - In-Order : O(n) — automatically gives sorted order
 *
 * OPERATIONS IMPLEMENTED:
 *   1. insert()        - Add a new patient
 *   2. search()        - Find a patient by ID
 *   3. delete()        - Remove a patient (handles all 3 cases)
 *   4. inOrder()       - Display all patients in ascending ID order
 *   5. isEmpty()       - Check if the tree has any patients
 */
public class PatientBST {

    // The root is the very first node inserted into the tree.
    // Every other node is reachable from the root by following left/right links.
    private PatientNode root;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    /**
     * Creates an empty BST.
     * root = null means the tree has no nodes yet.
     */
    public PatientBST() {
        root = null;
    }

    // ---------------------------------------------------------------
    // 1. INSERT
    // ---------------------------------------------------------------

    /**
     * Public method: called from Main.java to insert a patient.
     * It delegates to the private recursive helper insertRecursive().
     *
     * We use RECURSION so we don't need complex loop logic.
     * The recursion automatically navigates left or right until
     * it finds an empty spot (null) and places the new node there.
     *
     * @param patient The Patient object to insert
     */
    public boolean insert(Patient patient) {
        if (search(patient.getPatientId()) != null) {
            System.out.println("[ERROR] Patient ID " + patient.getPatientId()
                    + " already exists. Duplicate IDs are not allowed.");
            return false;
        }
        root = insertRecursive(root, patient);
        return true;
    }

    /**
     * Private recursive helper for insert.
     *
     * @param current The current node we are examining
     * @param patient The Patient to insert
     * @return The (possibly updated) node at this position
     */
    private PatientNode insertRecursive(PatientNode current, Patient patient) {
        // BASE CASE: empty spot found — place the new node here
        if (current == null) {
            return new PatientNode(patient);
        }

        // Compare the new patient's ID with the current node's ID
        if (patient.getPatientId() < current.patient.getPatientId()) {
            current.left = insertRecursive(current.left, patient);
        } else if (patient.getPatientId() > current.patient.getPatientId()) {
            current.right = insertRecursive(current.right, patient);
        }

        return current;
    }

    // ---------------------------------------------------------------
    // 2. SEARCH
    // ---------------------------------------------------------------

    /**
     * Public method: Search for a patient by their ID.
     * Returns the Patient object if found, or null if not found.
     *
     * @param patientId The ID to search for
     * @return Patient object if found, null otherwise
     */
    public Patient search(int patientId) {
        PatientNode result = searchRecursive(root, patientId);
        if (result != null) {
            return result.patient;
        }
        return null;
    }

    /**
     * Private recursive helper for search.
     *
     * HOW IT WORKS:
     * 1. If current is null → ID not found → return null.
     * 2. If ID matches current node → FOUND → return current node.
     * 3. If target ID < current ID → search LEFT subtree.
     * 4. If target ID > current ID → search RIGHT subtree.
     *
     * This is exactly like Binary Search on a sorted array — we
     * eliminate half the tree at each step.
     *
     * @param current   The node currently being examined
     * @param patientId The ID we are looking for
     * @return The node containing the patient, or null
     */
    private PatientNode searchRecursive(PatientNode current, int patientId) {

        // BASE CASE 1: Reached a null → not found
        if (current == null) {
            return null;
        }

        // BASE CASE 2: Found the matching ID
        if (patientId == current.patient.getPatientId()) {
            return current;
        }

        // Decide which direction to search
        if (patientId < current.patient.getPatientId()) {
            return searchRecursive(current.left, patientId);   // Search left
        } else {
            return searchRecursive(current.right, patientId);  // Search right
        }
    }

    // ---------------------------------------------------------------
    // 3. DELETE
    // ---------------------------------------------------------------

    /**
     * Public method: Delete a patient by their ID.
     *
     * BST deletion must handle THREE cases:
     *   Case 1 — Leaf node (no children):
     *     Simply remove the node (return null to parent).
     *
     *   Case 2 — One child:
     *     Replace the node with its only child.
     *
     *   Case 3 — Two children:
     *     Find the IN-ORDER SUCCESSOR (the smallest node in the RIGHT subtree).
     *     Copy the successor's data into the current node.
     *     Delete the successor from the right subtree.
     *     (The in-order successor always has at most one child, so it's
     *      a simpler deletion.)
     *
     * @param patientId The ID of the patient to delete
     */
    public void delete(int patientId) {
        // First check that the patient actually exists
        if (search(patientId) == null) {
            System.out.println("[ERROR] Patient ID " + patientId
                    + " not found. Cannot delete.");
            return;
        }
        root = deleteRecursive(root, patientId);
        System.out.println("[SUCCESS] Patient ID " + patientId
                + " has been deleted from the system.");
    }

    /**
     * Private recursive helper for delete.
     *
     * @param current   The node currently being examined
     * @param patientId The ID of the patient to delete
     * @return The updated node at this position (or replacement node)
     */
    private PatientNode deleteRecursive(PatientNode current, int patientId) {

        // BASE CASE: Reached null — ID not in this subtree
        if (current == null) {
            return null;
        }

        if (patientId < current.patient.getPatientId()) {
            // Target is in the LEFT subtree
            current.left = deleteRecursive(current.left, patientId);

        } else if (patientId > current.patient.getPatientId()) {
            // Target is in the RIGHT subtree
            current.right = deleteRecursive(current.right, patientId);

        } else {
            // *** FOUND THE NODE TO DELETE ***

            // CASE 1: Leaf node (no children)
            // Just remove it by returning null to the parent
            if (current.left == null && current.right == null) {
                return null;
            }

            // CASE 2a: Only has a RIGHT child
            // Replace this node with its right child
            if (current.left == null) {
                return current.right;
            }

            // CASE 2b: Only has a LEFT child
            // Replace this node with its left child
            if (current.right == null) {
                return current.left;
            }

            // CASE 3: Has TWO children
            // Find the in-order successor: the SMALLEST node in the RIGHT subtree
            // (This is the node that comes just after the deleted node in sorted order)
            PatientNode successor = findMinNode(current.right);

            // Copy successor's patient data into the current node
            // (we are NOT actually moving the node, just copying the data)
            current.patient = successor.patient;

            // Now delete the successor from the right subtree
            // (The successor has at most one right child, so this is Case 1 or 2)
            current.right = deleteRecursive(current.right,
                    successor.patient.getPatientId());
        }

        return current;
    }

    /**
     * Helper: Finds the node with the MINIMUM Patient ID in a given subtree.
     * In a BST, the minimum is always the LEFTMOST node.
     * Used internally during deletion (Case 3).
     *
     * @param node The root of the subtree to search
     * @return The node with the smallest Patient ID
     */
    private PatientNode findMinNode(PatientNode node) {
        // Keep going left until there is no more left child
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // ---------------------------------------------------------------
    // 4. IN-ORDER TRAVERSAL (Displays patients in ascending ID order)
    // ---------------------------------------------------------------

    /**
     * Public method: Display all patients in ascending Patient ID order.
     *
     * WHY IN-ORDER?
     * In a BST, in-order traversal visits: LEFT → ROOT → RIGHT
     * Because of the BST property (left < root < right),
     * this always produces a sorted ascending sequence.
     */
    public void displayInOrder() {
        if (root == null) {
            System.out.println("[INFO] No patients registered in the system.");
            return;
        }
        System.out.println("\n===== ALL PATIENTS =====");
        inOrderRecursive(root);
        System.out.println("=============================================");
    }

    /**
     * Private recursive helper for in-order traversal.
     * Order: Left → Visit Current → Right
     *
     * @param current The current node being visited
     */
    private void inOrderRecursive(PatientNode current) {
        if (current == null) {
            return; // Base case: nothing to visit
        }
        inOrderRecursive(current.left);     // 1. Visit all LEFT children first
        current.patient.displayPatient();   // 2. Visit (print) this node
        inOrderRecursive(current.right);    // 3. Visit all RIGHT children
    }

    // ---------------------------------------------------------------
    // 5. UTILITY METHODS
    // ---------------------------------------------------------------

    /**
     * Returns true if the BST has no patients.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns the root node (used internally and for testing).
     */
    public PatientNode getRoot() {
        return root;
    }
}
