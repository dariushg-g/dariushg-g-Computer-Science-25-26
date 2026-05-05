import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class RLECompression {

    // Creates a compressed version with fileName + ".rle.bw"
    public static void compress(String fileName) throws IOException {
        bwTransform(fileName);
        encode(fileName + ".bw");
    }

    // Decompresses a .rle.bw file
    public static void decompress(String fileName) throws IOException {
        decode(fileName);
        invertBWTransform(fileName.substring(0, fileName.length() - 4));
    }

    // Encodes the contents of a file using the RLE compression scheme:
    // single characters are left alone, and runs of 2+ characters are encoded as
    // that letter twice, followed by the length of the run, cast as a char
    public static void encode(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        PrintWriter pw = new PrintWriter(fileName + ".rle");

        char previousChar = (char) br.read();
        int count = 1;

        while (br.ready()) {
            char c = (char) br.read();
            // TO-DO
            // Now here: do things with the char you just read, dependent on the char you
            // just read

            if (c == previousChar) {
                count++;
            } else {
                if (count == 1) {
                    pw.append(previousChar);
                } else {
                    pw.append("" + previousChar + previousChar + (char) count);
                }
                count = 1;
                previousChar = c;
            }
        }

        if (count == 1) {
            pw.append(previousChar);
        } else {
            pw.append("" + previousChar + previousChar + (char) count);
        }

        br.close();
        pw.close();
    }

    // Decodes the above scheme
    public static void decode(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        PrintWriter pw = new PrintWriter(fileName.substring(0, fileName.length() - 4));

        char previousChar = (char) br.read();
        boolean in_count = false;
        while (br.ready()) {
            char c = (char) br.read();
            // TO-DO
            // Now here: do things with the char you just read, dependent on the char you
            // just read
            if (in_count) {
                for (int i = 1; i < c; i++) {
                    pw.append(previousChar);
                }
                in_count = false;
            } else if (c != previousChar) {
                pw.append(previousChar);
                previousChar = c;
            } else {
                in_count = true;
            }
        }

        if (in_count) {
        } else {
            pw.append(previousChar);
        }

        br.close();
        pw.close();
    }

    public static void bwTransform(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        // Add a null character at the beginning, as a
        // "beginning of file" character
        StringBuilder originalText = new StringBuilder("" + '\0');

        while (br.ready()) {
            char c = (char) br.read();
            originalText.append(c);
        }
        br.close();

        String[] rotations = new String[originalText.length()];
        rotations[0] = originalText.toString();
        // TO-DO
        // Now do the Burrows-Wheeler Transform
        for (int i = 1; i < rotations.length; i++) {
            char c = originalText.charAt(0);
            originalText.deleteCharAt(0);
            originalText.append(c);
            rotations[i] = originalText.toString();
        }

        Arrays.sort(rotations);
        var bw = new StringBuilder();
        for (var rotation : rotations)
            bw.append(rotation.charAt(rotation.length() - 1));

        // And then write the transformation into a file
        PrintWriter pw = new PrintWriter(fileName + ".bw");
        pw.append(bw);
        pw.close();
    }

    public static void invertBWTransform(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        StringBuilder originalText = new StringBuilder();

        while (br.ready()) {
            char c = (char) br.read();
            originalText.append(c);
        }
        br.close();

        StringBuilder[] reconstructions = new StringBuilder[originalText.length()];
        for (int i = 0; i < reconstructions.length; i++) {
            reconstructions[i] = new StringBuilder("" + originalText.charAt(i));
        }
        Arrays.sort(reconstructions);
        // TO-DO
        // Now undo the Burrows-Wheeler transform
        int z = 1;
        while (z < originalText.length()) {
            for (int i = 0; i < reconstructions.length; i++) {
                reconstructions[i].insert(0, originalText.charAt(i));
            }
            Arrays.sort(reconstructions);
            z++;
        }

        // TO-DO
        // And write the appropriate reconstruction into the file, without the null char
        PrintWriter pw = new PrintWriter(fileName.substring(0, fileName.length() - 3));
        String result = reconstructions[0].toString().replace("\0", "");
        pw.append(result);
        pw.close();
    }
}
