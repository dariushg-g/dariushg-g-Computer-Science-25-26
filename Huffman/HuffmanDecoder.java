import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class HuffmanDecoder {
    HashMap<String, Character> codes;

    public HuffmanDecoder(String codeFile) {
        this.codes = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(codeFile))) {
            for (int i = 0; i < 128; i++) {
                var line = br.readLine();
                if (!line.isEmpty())
                    this.codes.put(line, (char) i);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decodeFile(String encodedFile) {
        if (!encodedFile.endsWith(".huf")) {
            throw new IllegalArgumentException();
        }

        try (var br = new BufferedReader(new FileReader(encodedFile))) {
            var writer = new BufferedWriter(new FileWriter("decoded", false));


            while (br.ready()) {
                writer.write(
                        String.format("%8s", Integer.toBinaryString(br.read())).replace(' ', '0'));
            }

            br.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        decodeFileFromHuffmanCodes("decoded", encodedFile.substring(0, encodedFile.length() - 4));
    }

    public void decodeFileFromHuffmanCodes(String encodedFile, String decodedFile) {
        try (var br = new BufferedReader(new FileReader(encodedFile))) {
            var writer = new BufferedWriter(new FileWriter(decodedFile, false));
            int c;
            String curr = "";
            while ((c = br.read()) != -1) {
                curr += (char) c;
                if (isCode(curr)) {
                    var decoded = decodeChar(curr);
                    if (decoded == 26) {
                        br.close();
                        writer.close();
                        return;
                    }
                    writer.write(decoded);
                    curr = "";
                }
            }
            br.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isCode(String binary) {
        return binary.isEmpty() ? false : codes.get(binary) != null;
    }

    public char decodeChar(String binary) {
        return codes.get(binary);
    }
}
