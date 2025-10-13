public class Text {
    private final CharNode SENTINEL;
    private CharNode cursor;
    private CharNode clipboardSentinel;

    // The SENTINEL node may *never* be removed/deleted
    // Anything that would delete the sentinel is simply not executed
    public Text() {
        this.SENTINEL = new CharNode();
        this.SENTINEL.setNext(SENTINEL);
        this.SENTINEL.setPrevious(SENTINEL);
        this.cursor = SENTINEL;
        this.clipboardSentinel = new CharNode();
        this.clipboardSentinel.setNext(this.clipboardSentinel);
        this.clipboardSentinel.setPrevious(this.clipboardSentinel);
    }

    public CharNode getHead() {
        return this.SENTINEL.getNext();
    }

    public CharNode getTail() {
        return this.SENTINEL.getPrevious();
    }

    // Move cursor right
    public void moveCursorRight() {
        this.cursor = this.cursor.getNext() == this.SENTINEL ? this.cursor : this.cursor.getNext();
    }

    // Move cursor left
    public void moveCursorLeft() {
        this.cursor = this.cursor.getPrevious() == this.SENTINEL ? this.cursor
                : this.cursor.getPrevious();
    }

    // Move cursor to start of line
    // (each '\n' newline is the last char in its line)
    public void moveCursorToStartOfLine() {
        while (this.cursor != this.SENTINEL
                && this.cursor.getLetter() != '\n')
            this.cursor = this.cursor.getPrevious();
    }

    // Move cursor to end of line (i.e. to the newline char in its line)
    public void moveCursorToEndOfLine() {
        while (this.cursor != this.SENTINEL && this.cursor.getLetter() != '\n')
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
        CharNode prev = cursor.getPrevious();

        prev.setNext(inst);
        inst.setPrevious(prev);
        inst.setNext(this.cursor);
        cursor.setPrevious(inst);
        this.cursor = prev;
    }

    // Insert the String of chars before cursor
    public void insertBeforeCursor(String s) {
        CharNode post = this.cursor;
        CharNode prev = this.cursor.getPrevious();
        for (char c : s.toCharArray()) {
            CharNode inst = new CharNode(c);

            prev.setNext(inst);
            inst.setPrevious(prev);
            inst.setNext(post);
            post.setPrevious(inst);
            prev = inst;
        }
        this.cursor = prev;
    }

    // Insert a single char after cursor
    public void insertAfterCursor(char c) {
        CharNode inst = new CharNode(c);
        CharNode post = this.cursor.getNext();

        this.cursor.setNext(inst);
        inst.setPrevious(this.cursor);
        inst.setNext(post);
        post.setPrevious(inst);
        this.cursor = post;
    }

    // Insert a String of chars after cursor
    public void insertAfterCursor(String s) {
        CharNode next = this.cursor.getNext();
        CharNode prev = this.cursor;
        for (char c : s.toCharArray()) {
            CharNode inst = new CharNode(c);

            prev.setNext(inst);
            inst.setPrevious(prev);
            inst.setNext(next);
            next.setPrevious(inst);
            prev = inst;
        }
        this.cursor = next;
    }

    // Replace the char under the cursor with the given char
    public void replaceUnderCursor(char c) {
        if (this.cursor != this.SENTINEL && cursor.getLetter() != '\n')
            this.cursor.setLetter(c);
        else
            this.insertAfterCursor(c);
    }

    // Replace the letter under the cursor with the first letter of s,
    // then the letter after the cursor with the second letter of s,
    // then the letter two letters after the cursor with the third letter of s,
    // etc.
    // Cursor should end on final char of replacement
    public void replaceUnderCursor(String s) {
        if (this.cursor == this.SENTINEL)
            return;
        CharNode cur = this.cursor;
        int count = 0;

        while (count < s.length() && this.SENTINEL != cur && cur.getLetter() != '\n') {
            cur.setLetter(s.charAt(count));
            cur = cur.getNext();
            count++;
        }

        while (count < s.length()) {
            CharNode n = new CharNode(s.charAt(count));
            count++;
            CharNode left = cur.getPrevious();
            left.setNext(n);
            n.setPrevious(left);
            n.setNext(cur);
            cur.setPrevious(n);
        }
        cursor = cur.getPrevious();
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
        while (node != this.clipboardSentinel) {
            this.insertAfterCursor(node.getLetter());
            node = node.getNext();
        }
    }

    public String toString() {
        CharNode node = this.SENTINEL.getNext();
        if (node == null)
            return "";
        StringBuilder str = new StringBuilder();
        while (node != this.SENTINEL) {
            str.append(node.toString());
            node = node.getNext();
        }
        return str.toString();
    }

}

