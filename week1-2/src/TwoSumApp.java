import java.util.*;

public class TwoSumApp {

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            if (map.containsKey(target - num)) {
                System.out.println("Pair: " + num + ", " + (target - num));
            }
            map.put(num, 1);
        }
    }
}