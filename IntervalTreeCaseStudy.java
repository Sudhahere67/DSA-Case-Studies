import java.util.*;

class IntervalNode {
    int start, end;
    int maxEnd;
    String streamId;

    IntervalNode left, right;

    public IntervalNode(String id, int start, int end) {
        this.streamId = id;
        this.start = start;
        this.end = end;
        this.maxEnd = end;
    }
}

public class IntervalTreeCaseStudy {

    // Insert into BST
    static IntervalNode insert(IntervalNode root, String id, int start, int end) {

        if (root == null)
            return new IntervalNode(id, start, end);

        if (start < root.start)
            root.left = insert(root.left, id, start, end);
        else
            root.right = insert(root.right, id, start, end);

        root.maxEnd = Math.max(root.end,
                Math.max(
                        root.left != null ? root.left.maxEnd : Integer.MIN_VALUE,
                        root.right != null ? root.right.maxEnd : Integer.MIN_VALUE));

        return root;
    }

    // Find overlapping intervals
    static void findOverlapping(
            IntervalNode node,
            int lo,
            int hi,
            List<String> results) {

        if (node == null)
            return;

        // Pruning
        if (lo > node.maxEnd)
            return;

        // Left subtree
        findOverlapping(node.left, lo, hi, results);

        // Check overlap
        if (node.start <= hi && lo <= node.end) {
            results.add(node.streamId +
                    " -> [" + format(node.start) +
                    "," + format(node.end) + "]");
        }

        // Right subtree
        if (node.start <= hi)
            findOverlapping(node.right, lo, hi, results);
    }

    // Convert minutes to HH:MM
    static String format(int minutes) {
        int h = minutes / 60;
        int m = minutes % 60;
        return String.format("%02d:%02d", h, m);
    }

    // Print tree
    static void printTree(IntervalNode root, String space) {

        if (root == null)
            return;

        printTree(root.right, space + "     ");

        System.out.println(space +
                root.streamId +
                " [" + format(root.start) +
                "," + format(root.end) +
                ", max=" + format(root.maxEnd) + "]");

        printTree(root.left, space + "     ");
    }

    public static void main(String[] args) {

        IntervalNode root = null;

        // Insert Streams
        root = insert(root, "S1", 840, 870); //14:00-14:30
        root = insert(root, "S2", 850, 860); //14:10-14:20
        root = insert(root, "S3", 875, 900); //14:35-15:00
        root = insert(root, "S4", 855, 885); //14:15-14:45
        root = insert(root, "S5", 830, 845); //13:50-14:05
        root = insert(root, "S6", 865, 895); //14:25-14:55
        root = insert(root, "S7", 880, 890); //14:40-14:50
        root = insert(root, "S8", 840, 855); //14:00-14:15

        System.out.println("========================================");
        System.out.println("INTERVAL TREE FOR SPLUNK LOG STREAMS");
        System.out.println("========================================\n");

        System.out.println("Tree Structure:\n");
        printTree(root, "");

        int queryStart = 865; //14:25
        int queryEnd = 890;   //14:50

        List<String> overlaps = new ArrayList<>();

        findOverlapping(root, queryStart, queryEnd, overlaps);

        System.out.println("\n========================================");
        System.out.println("QUERY WINDOW");
        System.out.println("========================================");
        System.out.println("[" + format(queryStart) +
                ", " + format(queryEnd) + "]");

        System.out.println("\n========================================");
        System.out.println("OVERLAPPING STREAMS");
        System.out.println("========================================");

        for (String s : overlaps)
            System.out.println(s);

        System.out.println("\n========================================");
        System.out.println("TIME COMPLEXITY");
        System.out.println("========================================");
        System.out.println("Insertion : O(log n)");
        System.out.println("Query     : O(log n + k)");
        System.out.println("Deletion  : O(log n)");
    }
}