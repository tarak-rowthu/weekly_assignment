public class ParkingApp {

    static class Parking {
        String[] table;

        Parking(int size) {
            table = new String[size];
        }

        int hash(String plate) {
            return Math.abs(plate.hashCode()) % table.length;
        }

        int park(String plate) {
            int i = hash(plate);

            while (table[i] != null) {
                i = (i + 1) % table.length;
            }

            table[i] = plate;
            return i;
        }
    }

    public static void main(String[] args) {
        Parking p = new Parking(10);

        System.out.println("Parked at: " + p.park("ABC123"));
    }
}