import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.PriorityQueue;


public class HuffmanCodeGenerator {

    HashMap<Character, Integer> frequencies;
    String[] codes;

    public HuffmanCodeGenerator(String filename) throws IOException {
        var frequency_map = new HashMap<Character, Integer>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int c;
            while ((c = br.read()) != -1) {
                char ch = (char) c;
                frequency_map.put(ch, frequency_map.getOrDefault(ch, 0) + 1);
            }
            br.close();
        } catch (IOException e) {
            System.err.println("error reading from file: " + filename);
        }

        this.frequencies = frequency_map;
        this.frequencies.put((char) 26, 1);
        this.codes = new String[128];


        var pq = sort_frequencies(frequency_map);
        var root = make_tree(pq);
        assign_codes(root, "");
    }

    public void makeCodeFile(String codeFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(codeFile, false))) {
            for (var code : this.codes) {
                writer.write(code == null ? "" : code);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getCode(char c) {
        return this.codes[c];
    }

    void assign_codes(HuffmanNode node, String curr) {
        if (node.is_leaf()) {
            this.codes[(int) node.getC()] = curr;
            return;
        }

        assign_codes(node.left, curr + "0");
        assign_codes(node.right, curr + "1");
    }

    HuffmanNode make_tree(PriorityQueue<HuffmanNode> frequencies) {
        var lowest = get_lowest_two(frequencies);

        if (lowest == null) {
            return frequencies.remove();
        }

        var node = new HuffmanNode(null, lowest[0].getFrequency() + lowest[1].getFrequency());

        node.left = lowest[0];
        node.right = lowest[1];
        lowest[0].parent = node;
        lowest[1].parent = node;

        frequencies.add(node);

        return make_tree(frequencies);
    }

    HuffmanNode[] get_lowest_two(PriorityQueue<HuffmanNode> frequencies) {
        if (frequencies.size() < 2)
            return null;
        return new HuffmanNode[] {frequencies.poll(), frequencies.poll()};
    }

    int getFrequency(char c) {
        var freq = this.frequencies.get(c);
        return (freq == null) ? 0 : freq;
    }

    PriorityQueue<HuffmanNode> sort_frequencies(HashMap<Character, Integer> frequency_map) {

        PriorityQueue<HuffmanNode> nodes = new PriorityQueue<>();
        for (var entry : frequency_map.entrySet()) {
            var node = new HuffmanNode(entry.getKey(), entry.getValue());
            nodes.add(node);
        }

        return nodes;
    }



}
