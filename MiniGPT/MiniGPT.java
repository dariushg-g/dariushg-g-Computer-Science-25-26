import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class MiniGPT {

	private HashMap<String, ArrayList<Character>> map;
	private int order;

	public MiniGPT(String fileName, int chainOrder) {
		this.map = new HashMap<>();
		this.order = chainOrder;
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			var str = new StringBuilder();

			for (var i = 0; i < chainOrder; i++) {
				var ch = (char) br.read();
				str.append(ch);
			}

			while (br.ready()) {
				var c = (char) br.read();
				var key = str.toString();

				if (this.map.containsKey(key)) {
					this.map.get(key).add(c);
				} else {
					this.map.put(key, new ArrayList<>(Arrays.asList(c)));
				}

				str.deleteCharAt(0);
				str.append(c);
			}
		} catch (IOException e) {
			System.err.println("error reading from file: " + fileName);
		}
	}

	public void generateText(String outputFileName, int numChars) {
		var content = new StringBuilder();
		String mostCommon = "";
		var highest = 0;
		for (var prefix : this.map.keySet()) {
			var vals = map.get(prefix).size();
			if (vals > highest) {
				mostCommon = prefix;
				highest = vals;
			}
		}

		Random random = new Random();

		content.append(mostCommon);
		while (content.length() < numChars) {
			var key = content.substring(content.length() - this.order);
			var next = get_next(key, random);
			content.append(next);
		}

		try {
			var pw = new PrintWriter(outputFileName);
			pw.print(content);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	char get_next(String str, Random random) {
		var values = map.get(str);
		if (values == null) {
			System.err.println("!!");
		}
		return values.get(random.nextInt(values.size()));
	}
}

