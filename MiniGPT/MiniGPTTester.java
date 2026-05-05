public class MiniGPTTester {
    void main() {
        var gpt = new MiniGPT("thegreatgatsby.txt", 2);
        gpt.generateText("test_results.txt", 100);
    }
}
