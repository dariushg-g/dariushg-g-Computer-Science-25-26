public class CharNode {
    private Character letter;
    private CharNode previous;
    private CharNode next;
    private final boolean isSentinel;

    public CharNode(Character letter, CharNode previous, CharNode next) {
        this.letter = letter;
        this.previous = previous;
        this.next = next;
        this.isSentinel = false;
    }

    public CharNode(Character letter) {
        this(letter, null, null);
    }

    public CharNode() {
        this.letter = '\n';
        this.previous = this;
        this.next = this;
        this.isSentinel = true;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    public CharNode getPrevious() {
        return previous;
    }

    public void setPrevious(CharNode previous) {
        this.previous = previous;
    }

    public CharNode getNext() {
        return next;
    }

    public void setNext(CharNode next) {
        this.next = next;
    }

    public boolean isSentinel() {
        return isSentinel;
    }

    public String toString() {
        return letter.toString();
    }

}
