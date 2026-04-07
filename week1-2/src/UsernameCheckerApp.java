import java.util.*;
import java.util.concurrent.*;

public class UsernameCheckerApp {

    static class UsernameChecker {
        private ConcurrentHashMap<String, Integer> userMap = new ConcurrentHashMap<>();
        private ConcurrentHashMap<String, Integer> attempts = new ConcurrentHashMap<>();

        public boolean checkAvailability(String username) {
            attempts.merge(username, 1, Integer::sum);
            return !userMap.containsKey(username);
        }

        public void register(String username, int userId) {
            userMap.put(username, userId);
        }

        public List<String> suggestAlternatives(String username) {
            List<String> suggestions = new ArrayList<>();
            for (int i = 1; i <= 3; i++) suggestions.add(username + i);
            suggestions.add(username.replace("_", "."));
            return suggestions;
        }

        public String getMostAttempted() {
            return attempts.entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("None");
        }
    }

    public static void main(String[] args) {
        UsernameChecker checker = new UsernameChecker();

        checker.register("john_doe", 1);

        System.out.println(checker.checkAvailability("john_doe")); // false
        System.out.println(checker.checkAvailability("jane_smith")); // true
        System.out.println(checker.suggestAlternatives("john_doe"));
        System.out.println("Most attempted: " + checker.getMostAttempted());
    }
}