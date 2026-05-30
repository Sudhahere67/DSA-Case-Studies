import java.util.*;

public class SpotifySparseTableCaseStudy {

    static class SparseTableMin {

        int[][] table;
        int K, n;

        SparseTableMin(int[] arr) {

            n = arr.length;
            K = (int)(Math.log(n) / Math.log(2)) + 1;

            table = new int[K][n];

            // Level 0
            for(int i = 0; i < n; i++) {
                table[0][i] = arr[i];
            }

            // Build sparse table
            for(int k = 1; k < K; k++) {

                for(int i = 0; i + (1 << k) <= n; i++) {

                    table[k][i] = Math.min(
                            table[k - 1][i],
                            table[k - 1][i + (1 << (k - 1))]
                    );
                }
            }
        }

        int queryMin(int lo, int hi) {

            int length = hi - lo + 1;

            int k = (int)(Math.log(length) / Math.log(2));

            return Math.min(
                    table[k][lo],
                    table[k][hi - (1 << k) + 1]
            );
        }

        void printTable() {

            System.out.println("\nSPARSE TABLE\n");

            for(int k = 0; k < K; k++) {

                System.out.print("k = " + k + " : ");

                for(int i = 0; i + (1 << k) <= n; i++) {

                    System.out.print(table[k][i] + "  ");
                }

                System.out.println();
            }
        }
    }

    public static void main(String[] args) {

        int[] skipPositions = {42, 18, 55, 9, 31, 22, 6, 47};

        SparseTableMin st = new SparseTableMin(skipPositions);

        System.out.println("==================================================");
        System.out.println(" SPOTIFY LISTENER FATIGUE ANALYSIS - SPARSE TABLE ");
        System.out.println("==================================================");

        System.out.println("\nInput Array:");
        System.out.println(Arrays.toString(skipPositions));

        st.printTable();

        System.out.println("\n==================================================");
        System.out.println(" QUERY 1 : RANGE MINIMUM [1..6]");
        System.out.println("==================================================");

        int ans1 = st.queryMin(1, 6);

        System.out.println("Elements = [18, 55, 9, 31, 22, 6]");
        System.out.println("Result = " + ans1);

        System.out.println("\n==================================================");
        System.out.println(" QUERY 2 : RANGE MINIMUM [2..5]");
        System.out.println("==================================================");

        int ans2 = st.queryMin(2, 5);

        System.out.println("Elements = [55, 9, 31, 22]");
        System.out.println("Result = " + ans2);

        System.out.println("\n==================================================");
        System.out.println(" PERFORMANCE COMPARISON");
        System.out.println("==================================================");

        int n = 1024;
        int logn = 10;

        int sparseQuery = 1;
        int sparseUpdate = n * logn;

        int segQuery = logn;
        int segUpdate = logn;

        System.out.println("\nFor n = 1024");
        System.out.println("Sparse Table Query Cost  = " + sparseQuery);
        System.out.println("Sparse Table Update Cost = " + sparseUpdate);

        System.out.println("Segment Tree Query Cost  = " + segQuery);
        System.out.println("Segment Tree Update Cost = " + segUpdate);

        System.out.println("\nSpotify Mix (50 Queries, 5 Updates)");

        int sparse1 = 50 * sparseQuery + 5 * sparseUpdate;
        int seg1 = 50 * segQuery + 5 * segUpdate;

        System.out.println("Sparse Table Operations = " + sparse1);
        System.out.println("Segment Tree Operations = " + seg1);

        System.out.println("\n1:1 Mix (50 Queries, 50 Updates)");

        int sparse2 = 50 * sparseQuery + 50 * sparseUpdate;
        int seg2 = 50 * segQuery + 50 * segUpdate;

        System.out.println("Sparse Table Operations = " + sparse2);
        System.out.println("Segment Tree Operations = " + seg2);

        System.out.println("\nUpdate Heavy Mix (5 Queries, 50 Updates)");

        int sparse3 = 5 * sparseQuery + 50 * sparseUpdate;
        int seg3 = 5 * segQuery + 50 * segUpdate;

        System.out.println("Sparse Table Operations = " + sparse3);
        System.out.println("Segment Tree Operations = " + seg3);

        System.out.println("\n==================================================");
        System.out.println(" TIME COMPLEXITIES");
        System.out.println("==================================================");

        System.out.println("Sparse Table Build  : O(n log n)");
        System.out.println("Sparse Table Query  : O(1)");
        System.out.println("Sparse Table Update : O(n log n)");

        System.out.println("\nSegment Tree Query  : O(log n)");
        System.out.println("Segment Tree Update : O(log n)");

        System.out.println("\n==================================================");
        System.out.println(" END OF CASE STUDY ");
        System.out.println("==================================================");
    }
}