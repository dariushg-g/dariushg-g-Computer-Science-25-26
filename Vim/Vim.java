import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Vim {

    private Text text;
    private char editMode;
    private StringBuilder editStringBuilder;
    private boolean shouldExit;

    public Vim() {
        this.text = new Text();
        this.editMode = '\0';
        this.editStringBuilder = new StringBuilder("");
        this.shouldExit = false;
    }

    public void run() throws IOException {
        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
        while (!shouldExit) {
            char input = (char) kb.read();
            if (editMode == '\0') {
                processCommand(input);
            } else {
                processEdit(input);
            }
        }
        kb.close();
    }

    // Should print out what you think it should print out ;-)
    private void processCommand(char input) {
        // NOTE: 'h', 'i', 'q', and 'w' have already been coded
        // as an example to demonstrate
        // You must code the rest yourself.
        switch (input) {
            case 'h':
                // Move cursor left
                text.moveCursorLeft();
                break;
            case 'l':
                // Move cursor right
                text.moveCursorRight();
                break;
            case 'j':
                // Move cursor down
                // It moves down to the FIRST character in the next line
                // (**note: normally this is not how it works in Vim**)
                text.moveCursorToEndOfLine();
                text.moveCursorRight();
                break;
            case 'k':
                // Move cursor up
                // It moves up to the FIRST character in the above line
                // (**note: normally this is not how it works in Vim**)
                text.moveCursorToStartOfLine();
                text.moveCursorLeft();
                text.moveCursorToStartOfLine();
            case '0':
                // Move cursor to start of line
                // (each '\n' newline is the last char in its line
                text.moveCursorToStartOfLine();
                break;
            case '$':
                // Move cursor to end of line (i.e. to the newline char in its line)
                text.moveCursorToEndOfLine();
                break;
            case 'g':
                // Move cursor to start of text
                text.moveCursorToStartOfText();
                break;
            case 'G':
                // Move cursor to end of text
                text.moveCursorToEndOfText();
                break;
            case 'i':
                // Insert text before cursor, until '^' character is input
                // Cursor moves to end of new text
                editMode = 'i';
                break;
            case 'a':
                // Append text after cursor, until '^' character is input
                // Cursor moves to end of new text
                editMode = 'a';
                break;
            case 'o':
                // Create new line after current line, and add text there until '^' is input
                // Cursor moves to end of new text
                text.moveCursorToEndOfLine();
                text.insertAfterCursor('\n');
                text.moveCursorRight();
                editMode = 'o';
                break;
            case 'O':
                // Same as above, but BEFORE current line
                // Cursor moves to end of new text
                text.moveCursorToStartOfLine();
                text.insertBeforeCursor('\n');
                text.moveCursorLeft();
                editMode = 'O';
                break;
            case 'R':
                // Replace letters under cursor until '^' is input
                // Cursor moves to the next char after each letter input
                // Do not replace any letters in next line (or the newline);
                // instead add new letters if you run out of space
                // in the current line
                // Cursor should end on final char of replacement
                editMode = 'R';
                break;
            case 'x':
                // Delete character under cursor; cursor moves to next char
                editMode = 'x';
                text.deleteUnderCursor();
                break;
            case 'D':
                // Deletes the remainder of the line (except for the newline), starting with the
                // current cursor position
                // Cursor is now on the last remaining character in the line
                text.deleteRemainderOfLine();
                break;
            case 'd':
                // Delete entire current line (including the newline)
                // Cursor moves to beginning of next line
                text.deleteEntireLine();
                text.moveCursorToStartOfLine();
                break;
            case 'y':
                // Yank/cut the entire current line into clipboard
                // (overwriting any that might be there already)
                // Clipboard contains a sentinel for a list of CharNodes that
                // is the line
                // Cursor moves to beginning of next line
                text.cutLine();
                break;
            case 'p':
                // Paste the line from the clipboard after the current line
                text.pasteLine();
                break;
            case 'q':
                // Quit and print the text
                shouldExit = true;
                // Note how there is no "break;" here:
                // that makes it so that the next case gets executed as well
            case 'w':
                // Write the text out (i.e. print the text)
                System.out.println(text);
                break;
            default:

        }
    }

    private void processEdit(char input) {

        // 'i' has been coded as an example
        // You must code the remaining insertModes
        // If you're feeling fancy and would like to learn more about
        // switch statements and how to take advantage of desired behavior

        if (input == '^') {
            String editString = editStringBuilder.toString();
            switch (editMode) {
                case 'i':
                    text.insertBeforeCursor(editString);
                    break;
                case 'a':
                    text.insertAfterCursor(editString);
                    break;
                case 'o':
                    text.insertBeforeCursor(editString);
                    break;
                case 'O':
                    text.insertBeforeCursor(editString);
                    break;
                case 'R':
                    text.replaceUnderCursor(editString);
                    break;
                default:
                    System.out.println(text.toString());
            }

            // Clear the edit mode and the edit String
            editMode = '\0';
            editStringBuilder.setLength(0);
            return;
        }

        // Otherwise just add the new letter to the end of the String we're editing
        editStringBuilder.append(input);

    }

}
