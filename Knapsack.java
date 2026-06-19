import java.util.*;

public class Knapsack {

    static List<Integer> knapsack01(
            int[] weights,
            int[] values,
            int W) {

        int n = weights.length;

        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {

            for (int w = 0; w <= W; w++) {

                // Skip item
                dp[i][w] = dp[i - 1][w];

                // Take item
                if (weights[i - 1] <= w) {

                    dp[i][w] = Math.max(
                        dp[i][w],
                        dp[i - 1][w - weights[i - 1]]
                        + values[i - 1]
                    );
                }
            }
        }

        List<Integer> chosen =
                new ArrayList<>();

        int w = W;

        for (int i = n; i >= 1; i--) {

            if (dp[i][w] != dp[i - 1][w]) {

                chosen.add(i);

                w -= weights[i - 1];
            }
        }

        Collections.reverse(chosen);

        return chosen;
    }

    public static void main(String[] args) {

        int[] weights =
            {5,8,3,10,4,6,7,2};

        int[] values =
            {40,50,20,70,30,35,45,15};

        int W = 24;

        List<Integer> result =
            knapsack01(weights, values, W);

        System.out.println(result);
    }
}