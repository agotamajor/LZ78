import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class LZ78 {
    public static void main(String[] args) throws IOException {
        // kodolas
        File file = new File("be.txt");
        Scanner scanner = new Scanner(file);
        FileWriter writer = new FileWriter("kiLZ78.txt");
        String string = "";
        while (scanner.hasNextLine()) {
            string += scanner.nextLine();
        }

        Map<String, Integer> dictionary = new TreeMap<>();
        int index = 1;
        int n = string.length();
        int i = 0;
        while (i < n) {
            char next = string.charAt(i);
            if (dictionary.containsKey(String.valueOf(next))) {
                String word = "" + next;
                if (i == n - 1) {
                    writer.write("<" + dictionary.get(word) + "," + " >;");
                    i = n;
                    break;
                }
                for (int j = i + 1; j < n; j++) {
                    char current = string.charAt(j);
                    word += current;
                    if (!dictionary.containsKey(word)) {
                        dictionary.put(word, index);
                        index++;
                        int length = word.length() - 1;
                        writer.write("<" + dictionary.get(word.substring(0, length)) + "," + current + ">;");
                        i = j + 1;
                        break;
                    }
                }
            } else {
                dictionary.put(String.valueOf(next), index);
                index++;
                writer.write("<0," + next + ">;");
                i++;
            }
        }
        writer.close();
        scanner.close();

        // dekodolas
        File decodeFile = new File("kiLZ78.txt");
        Scanner scan = new Scanner(decodeFile);
        String str = "";
        while (scan.hasNextLine()) {
            str += scan.nextLine();
        }
        scan.close();

        String decodeString = "";
        String[] tokens = str.split(";");
        for (String token: tokens) {
            String[] ts = token.split(",");
            int ind = Integer.parseInt(ts[0].substring(1, ts[0].length()));
            char c = ts[1].charAt(0);
            if (ind != 0) {
                for (Map.Entry<String, Integer> entry: dictionary.entrySet()) {
                    if (ind == entry.getValue()) {
                        decodeString += entry.getKey();
                    }
                }
            }
            decodeString += c;
        }
        System.out.println(decodeString);
    }
}
