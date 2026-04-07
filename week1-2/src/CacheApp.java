import java.util.*;

public class CacheApp {

    static class Cache {
        Map<String, String> L1 = new LinkedHashMap<>();
        Map<String, String> L2 = new HashMap<>();
        Map<String, String> L3 = new HashMap<>();

        public String get(String key) {
            if (L1.containsKey(key)) return "L1: " + L1.get(key);

            if (L2.containsKey(key)) {
                String val = L2.get(key);
                L1.put(key, val);
                return "L2: " + val;
            }

            String val = L3.get(key);
            if (val != null) {
                L2.put(key, val);
                return "L3: " + val;
            }

            return "Not Found";
        }
    }

    public static void main(String[] args) {
        Cache c = new Cache();
        c.L3.put("video1", "data");

        System.out.println(c.get("video1"));
        System.out.println(c.get("video1"));
    }
}