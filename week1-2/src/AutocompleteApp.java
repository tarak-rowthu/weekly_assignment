import java.util.*;

public class AutocompleteApp {

    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        Map<String, Integer> freq = new HashMap<>();
    }

    static class Autocomplete {
        TrieNode root = new TrieNode();

        public void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                node.children.putIfAbsent(c, new TrieNode());
                node = node.children.get(c);
                node.freq.merge(word, 1, Integer::sum);
            }
        }

        public List<String> search(String prefix) {
            TrieNode node = root;
            for (char c : prefix.toCharArray()) {
                if (!node.children.containsKey(c)) return List.of();
                node = node.children.get(c);
            }

            return node.freq.keySet().stream().limit(5).toList();
        }
    }

    public static void main(String[] args) {
        Autocomplete ac = new Autocomplete();

        ac.insert("java");
        ac.insert("javascript");

        System.out.println(ac.search("jav"));
    }
}