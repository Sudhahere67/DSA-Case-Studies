import java.util.*;

class Delivery {
    int over, ball;

    Delivery(int over, int ball) {
        this.over = over;
        this.ball = ball;
    }

    @Override
    public String toString() {
        return "(" + over + "," + ball + ")";
    }
}

public class CricketCountingSort {

    static Delivery[] countingSortByOver(Delivery[] in) {

        final int K = 50;
        int[] count = new int[K + 1];

        // Count occurrences
        for (Delivery d : in) {
            count[d.over]++;
        }

        // Prefix sums
        for (int i = 1; i <= K; i++) {
            count[i] += count[i - 1];
        }

        Delivery[] out = new Delivery[in.length];

        // Reverse traversal for stability
        for (int i = in.length - 1; i >= 0; i--) {
            Delivery d = in[i];
            out[--count[d.over]] = d;
        }

        return out;
    }

    public static void main(String[] args) {

        Delivery[] deliveries = {
            new Delivery(2,4),
            new Delivery(1,1),
            new Delivery(3,6),
            new Delivery(1,5),
            new Delivery(2,2),
            new Delivery(3,1),
            new Delivery(1,3),
            new Delivery(2,6),
            new Delivery(3,4),
            new Delivery(1,2)
        };

        Delivery[] sorted = countingSortByOver(deliveries);

        System.out.println("Sorted by Over:");

        for (Delivery d : sorted) {
            System.out.print(d + " ");
        }
    }
}