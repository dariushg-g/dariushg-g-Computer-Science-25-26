import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HuffmanEncoder {
    String[] codes;

    public HuffmanEncoder(String codeFile) {
        this.codes = new String[128];
        try (BufferedReader br = new BufferedReader(new FileReader(codeFile))) {
            for (int i = 0; i < 128; i++) {
                var line = br.readLine();
                codes[i] = line;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void encodeFile(String fileToCompress) {
        encodeFileToHuffmanCodes(fileToCompress, "encoded");

        try (var br = new BufferedReader(new FileReader("encoded"))) {
            var writer = new BufferedWriter(new FileWriter(fileToCompress + ".huf", false));

            while (br.ready()) {
                var curr = new StringBuilder(8);
                for (int i = 0; i < 8; i++) {
                    curr.append((char) br.read());
                }

                writer.write((char) Integer.parseInt(curr.toString(), 2));
                curr = new StringBuilder(8);
            }

            br.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void encodeFileToHuffmanCodes(String fileToCompress, String encodedFile) {
        try (var br = new BufferedReader(new FileReader(fileToCompress))) {
            var writer = new BufferedWriter(new FileWriter(encodedFile, false));
            int c;
            var count = 0;
            while ((c = br.read()) != -1) {
                writer.write(codes[c]);
                count += codes[c].length();
            }

            writer.write(codes[26]);
            count += codes[26].length();

            var pad = 8 - (count % 8);
            for (int i = 0; i < pad; i++) {
                writer.write('0');
            }
            br.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String encodeChar(char input) {
        return codes[(int) input];
    }
}
