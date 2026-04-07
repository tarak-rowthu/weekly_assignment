import java.util.*;

public class DNSCacheApp {

    static class Entry {
        String ip;
        long expiry;

        Entry(String ip, long ttl) {
            this.ip = ip;
            this.expiry = System.currentTimeMillis() + ttl * 1000;
        }
    }

    static class DNSCache {
        private Map<String, Entry> cache = new HashMap<>();
        private int hits = 0, misses = 0;

        public String resolve(String domain) {
            Entry e = cache.get(domain);

            if (e != null && e.expiry > System.currentTimeMillis()) {
                hits++;
                return "HIT: " + e.ip;
            }

            misses++;
            String ip = queryDNS(domain);
            cache.put(domain, new Entry(ip, 5));
            return "MISS: " + ip;
        }

        private String queryDNS(String domain) {
            return "1.1.1.1";
        }

        public void stats() {
            System.out.println("Hit rate: " + (hits * 100.0 / (hits + misses)));
        }
    }

    public static void main(String[] args) throws Exception {
        DNSCache dns = new DNSCache();

        System.out.println(dns.resolve("google.com"));
        System.out.println(dns.resolve("google.com"));

        Thread.sleep(6000);

        System.out.println(dns.resolve("google.com"));
        dns.stats();
    }
}