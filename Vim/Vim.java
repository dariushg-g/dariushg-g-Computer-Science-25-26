import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Vim {

    private Text text;
    private boolean inEditMode;
    private char editMode;
    private boolean shouldExit;

    public Vim() {
        this.text = new Text();
        this.inEditMode = false;
        this.editMode = '\0';
        this.shouldExit = false;
    }

    public void run() throws IOException {
        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
        while (!shouldExit) {
            char input = (char) kb.read();
            if (!inEditMode) {
                processChar(input);
            } else {
                processEdit(input);
            }
        }
        kb.close();
    }

    private void processChar(char input) {
        switch (input) {
            case 'h':
                this.text.moveCursorLeft();
                break;
            case 'l':
                this.text.moveCursorRight();
                break;
            case 'j':
                // Move cursor down
                // It moves down to the FIRST character in the next line
                // (**note: normally this is not how it works in Vim**)
                this.text.moveCursorToEndOfLine();
                this.text.moveCursorRight();
                this.text.moveCursorRight();
                break;
            case 'k':
                // Move cursor up
                // It moves up to the FIRST character in the above line
                // (**note: normally this is not how it works in Vim**)
                this.text.moveCursorToStartOfLine();
                this.text.moveCursorLeft();
                this.text.moveCursorLeft();
                this.text.moveCursorToStartOfLine();
                break;
            case '0':
                // Move cursor to start of line
                // (each '\n' newline is the last char in its line
                this.text.moveCursorToStartOfLine();
                break;
            case '$':
                // Move cursor to end of line (i.e. to the newline char in its line)
                this.text.moveCursorToEndOfLine();
                break;
            case 'g':
                // Move cursor to start of text
                this.text.moveCursorToStartOfText();
                break;
            case 'G':
                this.text.moveCursorToEndOfText();
                // Move cursor to end of text
                break;
            case 'i':
                // Insert text before cursor, until '^' character is input
                // Cursor moves to end of new text
                this.editMode = 'i';
                this.inEditMode = true;

                break;
            case 'a':
                // Append text after cursor, until '^' character is input
                this.editMode = 'a';
                this.inEditMode = true;
                this.text.moveCursorRight();
                break;
            case 'o':
                // Create new line after current line, and add text there until '^' is input
                // Cursor moves to end of new text
                this.text.moveCursorToEndOfLine();
                this.text.moveCursorRight();
                this.text.insertAfterCursor('\n');
                this.editMode = 'o';
                this.inEditMode = true;
                break;
            case 'O':
                this.text.moveCursorToStartOfLine();
                this.text.moveCursorLeft();
                this.text.insertAfterCursor('\n');
                this.editMode = 'o';
                this.inEditMode = true;
                break;
            case 'R':
                // Replace letters under cursor until '^' is input
                // Cursor moves to the next char after each letter input
                // Do not replace any letters in next line (or the newline);
                // instead add new letters if you run out of space
                // in the current line
                // Cursor should end on final char of replacement
                this.text.replaceUnderCursor(input);
                this.text.moveCursorRight();
                break;
            case 'x':
                // Delete character under cursor; cursor moves to next char
                this.text.deleteUnderCursor();
                break;
            case 'D':
                // Deletes the remainder of the line (except for the newline), starting with the
                // current cursor position
                // Cursor is now on the last remaining character in the line
                this.text.deleteRemainderOfLine();
                break;
            case 'd':
                // Delete entire current line (including the newline)
                // Cursor moves to beginning of next line
                this.text.deleteEntireLine();
                break;
            case 'y':
                // Yank/cut the entire current line into clipboard
                // (overwriting any that might be there already)
                // Clipboard contains a sentinel for a list of CharNodes that
                // is the line
                // Cursor moves to beginning of next line
                this.text.cutLine();
                break;
            case 'p':
                this.text.pasteLine();
                break;
            case 'q':
                this.shouldExit = true;
                // Quit and print the text
                break;
            default:
                this.processEdit(input);
        }
    }

    private void processEdit(char input) {
        switch (editMode) {
            case 'i':
                this.text.insertBeforeCursor(input);
            case 'a':
                this.text.insertAfterCursor(input);
            case 'o':
                this.text.insertAfterCursor(input);
            case 'O':
                this.text.insertAfterCursor(input);
            case 'R':
                this.text.replaceUnderCursor(input);
            default:
                if (input == 27)
                    this.inEditMode = false;
        }
    }

}
