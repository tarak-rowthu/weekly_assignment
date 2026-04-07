import java.util.*;
import java.util.concurrent.*;

public class AnalyticsApp {

    static class Analytics {
        ConcurrentHashMap<String, Integer> views = new ConcurrentHashMap<>();
        ConcurrentHashMap<String, Set<String>> users = new ConcurrentHashMap<>();

        public void process(String url, String user) {
            views.merge(url, 1, Integer::sum);
            users.computeIfAbsent(url, k -> ConcurrentHashMap.newKeySet()).add(user);
        }

        public void dashboard() {
            views.entrySet().stream()
                    .sorted((a, b) -> b.getValue() - a.getValue())
                    .limit(5)
                    .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
        }
    }

    public static void main(String[] args) {
        Analytics a = new Analytics();

        a.process("/news", "u1");
        a.process("/news", "u2");
        a.process("/sports", "u1");

        a.dashboard();
    }
}