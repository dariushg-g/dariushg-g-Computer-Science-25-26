/**
 * Represents a node of a circular DOUBLY-linked list.
 *
 * In ChocolateHashMap, each bucket is a circular doubly-linked list that begins
 * with
 * a sentinel BatchNode. For a sentinel node:
 * - entry == null
 * - previous points to itself
 * - next points to itself
 *
 * Non-sentinel nodes hold an actual entry.
 */
public class BatchNode {
    private String key;
    private ChocolateBatch value;
    private BatchNode previous;
    private BatchNode next;
    private final boolean isSentinel;

    public BatchNode(String key, ChocolateBatch value, BatchNode previous, BatchNode next) {
        this.key = key;
        this.value = value;
        this.previous = previous;
        this.next = next;
        this.isSentinel = false;
    }

    public BatchNode(String key, ChocolateBatch value) {
        this(key, value, null, null);
    }

    // Sentinel constructor
    public BatchNode() {
        this.key = null;
        this.value = null;
        this.previous = this;
        this.next = this;
        this.isSentinel = true;
    }

    public String getKey() {
        return key;
    }

    public ChocolateBatch getValue() {
        return value;
    }

    public BatchNode getPrevious() {
        return previous;
    }

    public BatchNode getNext() {
        return next;
    }

    public boolean isSentinel() {
        return isSentinel;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(ChocolateBatch value) {
        this.value = value;
    }

    public void setPrevious(BatchNode previous) {
        this.previous = previous;
    }

    public void setNext(BatchNode next) {
        this.next = next;
    }

    // --- Optional helper operations ---
    // These helpers are here so you can focus on hashing logic instead of pointer
    // bookkeeping.

    /**
     * Inserts newNode immediately BEFORE this node.
     * Example: sentinel.insertBefore(newNode) appends to the end of the list.
     */
    public void insertBefore(BatchNode newNode) {
        BatchNode prev = this.previous;
        newNode.previous = prev;
        newNode.next = this;
        prev.next = newNode;
        this.previous = newNode;
    }

    /**
     * Removes this node from its list (no-op if this node is a sentinel).
     */
    public void unlink() {
        if (this.isSentinel)
            return;

        BatchNode prev = this.previous;
        BatchNode nxt = this.next;

        prev.next = nxt;
        nxt.previous = prev;

        // Clear pointers to help debugging accidental reuse
        this.previous = null;
        this.next = null;
    }
}