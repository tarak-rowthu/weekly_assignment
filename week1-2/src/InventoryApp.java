import java.util.*;
import java.util.concurrent.*;

public class InventoryApp {

    static class InventoryManager {
        private ConcurrentHashMap<String, Integer> stock = new ConcurrentHashMap<>();
        private ConcurrentHashMap<String, Queue<Integer>> waitlist = new ConcurrentHashMap<>();

        public void addProduct(String productId, int count) {
            stock.put(productId, count);
            waitlist.put(productId, new ConcurrentLinkedQueue<>());
        }

        public int checkStock(String productId) {
            return stock.getOrDefault(productId, 0);
        }

        public synchronized String purchaseItem(String productId, int userId) {
            int available = stock.getOrDefault(productId, 0);

            if (available > 0) {
                stock.put(productId, available - 1);
                return "Success, remaining: " + (available - 1);
            } else {
                waitlist.get(productId).offer(userId);
                return "Added to waitlist, position: " + waitlist.get(productId).size();
            }
        }
    }

    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
        manager.addProduct("IPHONE", 2);

        System.out.println(manager.purchaseItem("IPHONE", 1));
        System.out.println(manager.purchaseItem("IPHONE", 2));
        System.out.println(manager.purchaseItem("IPHONE", 3));
    }
}