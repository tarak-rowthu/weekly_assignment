import java.util.*;

public class PlagiarismApp {

    static class Detector {
        Map<String, Set<String>> index = new HashMap<>();

        public void addDoc(String id, String text) {
            String[] words = text.split("\\s+");

            for (int i = 0; i < words.length - 4; i++) {
                String gram = String.join(" ", Arrays.copyOfRange(words, i, i + 5));
                index.computeIfAbsent(gram, k -> new HashSet<>()).add(id);
            }
        }

        public double similarity(String text, String docId) {
            String[] words = text.split("\\s+");
            int match = 0, total = words.length - 4;

            for (int i = 0; i < total; i++) {
                String gram = String.join(" ", Arrays.copyOfRange(words, i, i + 5));
                if (index.getOrDefault(gram, Set.of()).contains(docId)) match++;
            }

            return match * 100.0 / total;
        }
    }

    public static void main(String[] args) {
        Detector d = new Detector();

        d.addDoc("doc1", "this is a sample plagiarism detection system example");
        System.out.println(d.similarity("this is a sample plagiarism detection system", "doc1"));
    }
}