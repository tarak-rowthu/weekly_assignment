import java.util.concurrent.*;

public class RateLimiterApp {

    static class Bucket {
        int tokens;
        long last;

        Bucket(int max) {
            tokens = max;
            last = System.currentTimeMillis();
        }
    }

    static class RateLimiter {
        ConcurrentHashMap<String, Bucket> map = new ConcurrentHashMap<>();
        int MAX = 5;

        public synchronized boolean allow(String id) {
            map.putIfAbsent(id, new Bucket(MAX));
            Bucket b = map.get(id);

            if (b.tokens > 0) {
                b.tokens--;
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        RateLimiter rl = new RateLimiter();

        for (int i = 0; i < 7; i++) {
            System.out.println(rl.allow("user"));
        }
    }
}