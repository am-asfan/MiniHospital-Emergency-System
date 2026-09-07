/**
 * QueueNode.java
 * --------------
 * Represents a single node in the Emergency Queue linked chain.
 *
 * The queue is implemented as a LINKED LIST of QueueNodes.
 * Each node holds:
 *   - A reference to the Patient waiting in the queue
 *   - A pointer to the NEXT node in line (the next waiting patient)
 *
 * Why a linked structure?
 *   - We don't know in advance how many patients will arrive.
 *   - A linked node chain grows and shrinks dynamically.
 *   - No wasted memory from a fixed-size array.
 */
public class QueueNode {

    // The patient stored in this queue slot
    Patient patient;

    // Pointer to the next patient in line
    QueueNode next;

    /**
     * Constructor: Creates a queue node for one patient.
     * next is null because this node is at the back of the line initially.
     *
     * @param patient The patient to place in this queue slot
     */
    public QueueNode(Patient patient) {
        this.patient = patient;
        this.next = null;
    }
}
