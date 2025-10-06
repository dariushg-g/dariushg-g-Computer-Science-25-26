public class Text {
    private final CharNode SENTINEL;
    private CharNode cursor;
    private CharNode clipboardSentinel;

    // The SENTINEL node may *never* be removed/deleted
    // Anything that would delete the sentinel is simply not executed
    public Text() {
        this.SENTINEL = new CharNode();
        this.cursor = SENTINEL;
        this.clipboardSentinel = null;
    }

    public CharNode getHead() {
        return this.SENTINEL.getNext();
    }

    public CharNode getTail() {
        return this.SENTINEL.getPrevious();
    }

    // Move cursor right
    public void moveCursorRight() {
        this.cursor = this.cursor.getNext();
    }

    // Move cursor left
    public void moveCursorLeft() {
        this.cursor = this.cursor.getPrevious();
    }

    // Move cursor to start of line
    // (each '\n' newline is the last char in its line)
    public void moveCursorToStartOfLine() {
        while (this.cursor.getLetter() != '\n')
            this.cursor = this.cursor.getPrevious();
    }

    // Move cursor to end of line (i.e. to the newline char in its line)
    public void moveCursorToEndOfLine() {
        while (this.cursor.getLetter() != '\n')
            this.cursor = this.cursor.getNext();
    }

    // Move cursor to start of text
    public void moveCursorToStartOfText() {
        this.cursor = this.SENTINEL.getNext();
    }

    // Move cursor to end of text
    public void moveCursorToEndOfText() {
        this.cursor = this.SENTINEL.getPrevious();
    }

    // Insert a single char before cursor
    public void insertBeforeCursor(char c) {
        CharNode inst = new CharNode(c);
        this.cursor.setPrevious(inst);
        inst.setNext(this.cursor);
    }

    // Insert the String of chars before cursor
    public void insertBeforeCursor(String s) {
        CharNode prev = this.cursor.getPrevious();
        for (char c : s.toCharArray()) {
            CharNode inst = new CharNode(c);
            inst.setPrevious(prev);
            prev.setNext(inst);
            prev = inst;
        }
        prev = this.cursor;
    }

    // Insert a single char after cursor
    public void insertAfterCursor(char c) {
        CharNode inst = new CharNode(c);
        this.cursor.setNext(inst);
        inst.setPrevious(this.cursor);
    }

    // Insert a String of chars after cursor
    public void insertAfterCursor(String s) {
        CharNode next = this.cursor.getNext();
        for (char c : s.toCharArray()) {
            CharNode inst = new CharNode(c);
            inst.setNext(next);
            next.setPrevious(inst);
            next = inst;
        }
        next = this.cursor;
    }

    // Replace the char under the cursor with the given char
    public void replaceUnderCursor(char c) {
        this.cursor.setLetter(c);
    }

    // Replace the letter under the cursor with the first letter of s,
    // then the letter after the cursor with the second letter of s,
    // then the letter two letters after the cursor with the third letter of s,
    // etc.
    // Cursor should end on final char of replacement
    public void replaceUnderCursor(String s) {
        insertAfterCursor(s);
        this.cursor.getPrevious().setNext(this.cursor.getNext());
    }

    // Delete character under cursor; cursor moves to next char
    public void deleteUnderCursor() {
        this.cursor.getPrevious().setNext(this.cursor.getNext());
    }

    // Deletes the remainder of the line (except for the newline),
    // starting with the current cursor position
    // Cursor is now at the newline character
    public void deleteRemainderOfLine() {
        CharNode node = this.cursor;
        while (this.cursor.getLetter() != '\n') {
            node = this.cursor.getNext();
        }
        CharNode newNode = new CharNode('\n');
        this.cursor.setNext(newNode);
        newNode.setNext(node.getNext());
    }

    // Delete entire current line (including the newline)
    // Cursor moves to beginning of next line
    public void deleteEntireLine() {
        this.moveCursorToStartOfLine();
        this.deleteRemainderOfLine();
    }

    // Yank/cut the entire current line into clipboard
    // (overwriting any that might be there already)
    // Clipboard contains a sentinel for a list of CharNodes that
    // is the line
    // Cursor moves to start of next line
    public void cutLine() {
        this.moveCursorToStartOfLine();
        this.clipboardSentinel.setNext(this.cursor);
        CharNode node = this.clipboardSentinel.getNext();
        while (this.cursor.getLetter() != '\n') {
            this.cursor = this.cursor.getNext();
            node.setNext(this.cursor);
        }
    }

    // Paste the line from the clipboard after the current line
    public void pasteLine() {
        this.moveCursorToStartOfLine();
        CharNode node = this.clipboardSentinel.getNext();
        while (node != null) {
            this.insertAfterCursor(node.getLetter());
            node = node.getNext();
        }
    }

    public String toString() {
        CharNode node = this.SENTINEL.getNext();
        StringBuilder str = new StringBuilder();
        while (node != null) {
            str.append(node.toString());
            node = node.getNext();
        }
        return str.toString();
    }

}
