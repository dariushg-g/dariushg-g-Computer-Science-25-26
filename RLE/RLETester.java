import java.io.IOException;

public class RLETester {
    void main() {
        try {
            RLECompression.compress("test.txt");
            RLECompression.decompress("test.txt.bw.rle");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
