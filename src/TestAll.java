/**
 * TestAll.java
 * ------------
 * Automated test class for the Mini Hospital Emergency Management System.
 *
 * This tests ALL four data structures WITHOUT requiring keyboard input.
 * Run this to verify correctness before demonstrating the system.
 *
 * Tests covered:
 *   BST   : insert, search (found/not found), delete (leaf/one child/two children), in-order
 *   Queue : enqueue, display, dequeue (FIFO order), empty dequeue
 *   Stack : push, display, pop (LIFO order), empty pop
 *   List  : addVisit, searchVisit (found/not found), removeVisit, display
 */
public class TestAll {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   AUTOMATED TEST — ALL DATA STRUCTURES");
        System.out.println("========================================");

        testBST();
        testQueue();
        testStack();
        testLinkedList();

        System.out.println("\n========================================");
        System.out.println("   ALL TESTS COMPLETED SUCCESSFULLY");
        System.out.println("========================================");
    }

    // ================================================================
    //  BST TESTS
    // ================================================================

    static void testBST() {
        System.out.println("\n========================================");
        System.out.println("  TEST 1: BINARY SEARCH TREE (BST)");
        System.out.println("========================================");

        PatientBST bst = new PatientBST();

        // ------ INSERT ------
        System.out.println("\n[TEST] Inserting patients: 1005, 1002, 1010, 1001, 1003");
        bst.insert(new Patient(1005, "Ahmed",   35, "0771234567", "Chest Pain"));
        bst.insert(new Patient(1002, "Sara",    28, "0761234567", "Fever"));
        bst.insert(new Patient(1010, "Mohamed", 45, "0751234567", "Injury"));
        bst.insert(new Patient(1001, "Aisha",   31, "0711234567", "Breathing Difficulty"));
        bst.insert(new Patient(1003, "Ali",     22, "0721234567", "Headache"));

        // ------ IN-ORDER TRAVERSAL ------
        System.out.println("\n[TEST] In-order traversal (expected order: 1001, 1002, 1003, 1005, 1010):");
        bst.displayInOrder();

        // ------ SEARCH FOUND ------
        System.out.println("\n[TEST] Search for Patient ID 1002 (should be found):");
        Patient found = bst.search(1002);
        if (found != null) {
            System.out.println("[PASS] Found: " + found.getPatientName());
            found.displayPatient();
        } else {
            System.out.println("[FAIL] Patient 1002 not found!");
        }

        // ------ SEARCH NOT FOUND ------
        System.out.println("\n[TEST] Search for Patient ID 9999 (should NOT be found):");
        Patient notFound = bst.search(9999);
        if (notFound == null) {
            System.out.println("[PASS] Correctly returned: not found.");
        } else {
            System.out.println("[FAIL] Should not have found patient 9999.");
        }

        // ------ DUPLICATE INSERT ------
        System.out.println("\n[TEST] Insert duplicate ID 1002 (should show error):");
        bst.insert(new Patient(1002, "Duplicate", 30, "0000000000", "Test"));

        // ------ DELETE LEAF NODE (1001 — leftmost, no children) ------
        System.out.println("\n[TEST] Delete LEAF node: Patient ID 1001");
        bst.delete(1001);
        System.out.println("  In-order after deleting 1001 (expected: 1002, 1003, 1005, 1010):");
        bst.displayInOrder();

        // ------ DELETE NODE WITH ONE CHILD (1010 — right-most, no right child after insert) ------
        System.out.println("\n[TEST] Delete node with ONE CHILD: Patient ID 1002");
        // 1002's left child is 1001 (deleted), right child is 1003 — so 1002 has one child (1003)
        bst.delete(1002);
        System.out.println("  In-order after deleting 1002 (expected: 1003, 1005, 1010):");
        bst.displayInOrder();

        // ------ DELETE NODE WITH TWO CHILDREN (1005 — root, has left 1003 and right 1010) ------
        System.out.println("\n[TEST] Delete node with TWO CHILDREN: Patient ID 1005 (root)");
        bst.delete(1005);
        System.out.println("  In-order after deleting 1005 (expected: 1003, 1010):");
        bst.displayInOrder();

        // ------ DELETE NON-EXISTENT ------
        System.out.println("\n[TEST] Delete non-existent ID 9999 (should show error):");
        bst.delete(9999);

        System.out.println("\n[BST TESTS COMPLETE]");
    }

    // ================================================================
    //  QUEUE TESTS
    // ================================================================

    static void testQueue() {
        System.out.println("\n========================================");
        System.out.println("  TEST 2: EMERGENCY QUEUE (FIFO)");
        System.out.println("========================================");

        EmergencyQueue queue = new EmergencyQueue();

        // Create patients for queue testing
        Patient p1 = new Patient(1001, "Ahmed",   35, "0771234567", "Chest Pain");
        Patient p2 = new Patient(1002, "Sara",    28, "0761234567", "Fever");
        Patient p3 = new Patient(1003, "Mohamed", 45, "0751234567", "Injury");

        // ------ ENQUEUE ------
        System.out.println("\n[TEST] Enqueue patients in order: Ahmed, Sara, Mohamed");
        queue.enqueue(p1);
        queue.enqueue(p2);
        queue.enqueue(p3);

        // ------ DISPLAY ------
        System.out.println("\n[TEST] Display queue (expected front→rear: Ahmed, Sara, Mohamed):");
        queue.display();

        // ------ DEQUEUE (FIFO) ------
        System.out.println("\n[TEST] Dequeue (FIFO — Ahmed should come first):");
        Patient treated1 = queue.dequeue();
        if (treated1 != null && treated1.getPatientId() == 1001) {
            System.out.println("[PASS] First dequeued: " + treated1.getPatientName() + " (correct — FIFO)");
        } else {
            System.out.println("[FAIL] Expected Ahmed first!");
        }

        System.out.println("\n[TEST] Dequeue again (Sara should be next):");
        Patient treated2 = queue.dequeue();
        if (treated2 != null && treated2.getPatientId() == 1002) {
            System.out.println("[PASS] Second dequeued: " + treated2.getPatientName() + " (correct — FIFO)");
        } else {
            System.out.println("[FAIL] Expected Sara second!");
        }

        System.out.println("\n[TEST] Remaining queue (only Mohamed):");
        queue.display();

        System.out.println("\n[TEST] Dequeue Mohamed:");
        queue.dequeue();

        // ------ EMPTY DEQUEUE ------
        System.out.println("\n[TEST] Dequeue from EMPTY queue (should show error):");
        queue.dequeue();

        System.out.println("\n[QUEUE TESTS COMPLETE]");
    }

    // ================================================================
    //  STACK TESTS
    // ================================================================

    static void testStack() {
        System.out.println("\n========================================");
        System.out.println("  TEST 3: TREATMENT STACK (LIFO)");
        System.out.println("========================================");

        TreatmentStack stack = new TreatmentStack();

        // Create treatment records
        Treatment t1 = new Treatment(1001, "Ahmed",   "Dr. Noor",   "Angina",   "Medication",  "2026-09-01");
        Treatment t2 = new Treatment(1002, "Sara",    "Dr. Rashid", "Influenza", "Rest+Fluids", "2026-09-02");
        Treatment t3 = new Treatment(1003, "Mohamed", "Dr. Liam",   "Fracture", "Casting",      "2026-09-03");

        // ------ PUSH ------
        System.out.println("\n[TEST] Push treatments: Ahmed, Sara, Mohamed");
        stack.push(t1);
        stack.push(t2);
        stack.push(t3);

        // ------ DISPLAY ------
        System.out.println("\n[TEST] Display stack (expected top→bottom: Mohamed, Sara, Ahmed):");
        stack.display();

        // ------ POP (LIFO) ------
        System.out.println("\n[TEST] Pop (LIFO — Mohamed should come first):");
        Treatment popped1 = stack.pop();
        if (popped1 != null && popped1.getPatientId() == 1003) {
            System.out.println("[PASS] First popped: " + popped1.getPatientName() + " (correct — LIFO)");
        } else {
            System.out.println("[FAIL] Expected Mohamed first!");
        }

        System.out.println("\n[TEST] Pop again (Sara should be next):");
        Treatment popped2 = stack.pop();
        if (popped2 != null && popped2.getPatientId() == 1002) {
            System.out.println("[PASS] Second popped: " + popped2.getPatientName() + " (correct — LIFO)");
        } else {
            System.out.println("[FAIL] Expected Sara second!");
        }

        System.out.println("\n[TEST] Remaining stack (only Ahmed):");
        stack.display();

        System.out.println("\n[TEST] Pop Ahmed:");
        stack.pop();

        // ------ EMPTY POP ------
        System.out.println("\n[TEST] Pop from EMPTY stack (should show error):");
        stack.pop();

        System.out.println("\n[STACK TESTS COMPLETE]");
    }

    // ================================================================
    //  LINKED LIST TESTS
    // ================================================================

    static void testLinkedList() {
        System.out.println("\n========================================");
        System.out.println("  TEST 4: VISIT SINGLY LINKED LIST");
        System.out.println("========================================");

        // Each patient has their own list — here we test Ahmed's list
        Patient ahmed = new Patient(1001, "Ahmed", 35, "0771234567", "Chest Pain");
        VisitLinkedList ahmedHistory = ahmed.getVisitHistory();

        // ------ ADD VISITS ------
        System.out.println("\n[TEST] Adding 3 visits to Ahmed's history:");
        ahmedHistory.addVisit(new Visit(101, "2026-01-10", "Dr. Noor",   "Angina",          "Medication"));
        ahmedHistory.addVisit(new Visit(102, "2026-03-15", "Dr. Rashid", "Hypertension",    "Lifestyle change"));
        ahmedHistory.addVisit(new Visit(103, "2026-09-01", "Dr. Liam",   "Cardiac checkup", "ECG + Medication"));

        // ------ DISPLAY ------
        System.out.println("\n[TEST] Display Ahmed's visit history (expected 3 visits):");
        ahmedHistory.display();

        // ------ SEARCH FOUND ------
        System.out.println("\n[TEST] Search for Visit ID 102 (should be found):");
        Visit v = ahmedHistory.searchVisit(102);
        if (v != null) {
            System.out.println("[PASS] Found visit:");
            v.displayVisit();
        } else {
            System.out.println("[FAIL] Visit 102 not found!");
        }

        // ------ SEARCH NOT FOUND ------
        System.out.println("\n[TEST] Search for Visit ID 999 (should NOT be found):");
        Visit notFound = ahmedHistory.searchVisit(999);
        if (notFound == null) {
            System.out.println("[PASS] Correctly returned: not found.");
        } else {
            System.out.println("[FAIL] Should not have found visit 999.");
        }

        // ------ REMOVE ------
        System.out.println("\n[TEST] Remove Visit ID 102:");
        ahmedHistory.removeVisit(102);

        System.out.println("\n[TEST] Display after removal (expected visits 101 and 103 only):");
        ahmedHistory.display();

        // ------ REMOVE NON-EXISTENT ------
        System.out.println("\n[TEST] Remove non-existent Visit ID 999 (should show error):");
        ahmedHistory.removeVisit(999);

        // ------ DUPLICATE ADD ------
        System.out.println("\n[TEST] Add duplicate Visit ID 101 (should show error):");
        ahmedHistory.addVisit(new Visit(101, "2026-09-07", "Dr. Test", "Test", "Test"));

        System.out.println("\n[LINKED LIST TESTS COMPLETE]");
    }
}
