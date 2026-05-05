import java.io.IOException;

public class HuffmanCodeGeneratorTester {
    void main() throws IOException {
        var huffman = new HuffmanCodeGenerator("test.txt");
        huffman.makeCodeFile("codeFile");
        var encoder = new HuffmanEncoder("codeFile");
        encoder.encodeFile("test.txt");
        var decoder = new HuffmanDecoder("codeFile");
        decoder.decodeFile("tested.txt.huf");
    }
}
