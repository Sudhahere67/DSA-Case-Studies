import java.util.*;

public class CaseStudy3 {

    // Find Dead Blocks using Iterative DFS
    static Set<String> findDeadBlocks(
            Map<String, List<String>> cfg,
            String entry) {

        Set<String> visited = new HashSet<>();
        Deque<String> stack = new ArrayDeque<>();

        stack.push(entry);

        while (!stack.isEmpty()) {

            String node = stack.pop();

            if (visited.contains(node))
                continue;

            visited.add(node);

            List<String> neighbors =
                    new ArrayList<>(cfg.getOrDefault(node,
                            new ArrayList<>()));

            Collections.reverse(neighbors);

            for (String next : neighbors) {
                if (!visited.contains(next))
                    stack.push(next);
            }
        }

        Set<String> dead = new HashSet<>(cfg.keySet());
        dead.removeAll(visited);

        return dead;
    }

    // Recursive DFS for Post Order
    static void dfsPostOrder(
            String node,
            Map<String, List<String>> cfg,
            Set<String> visited,
            List<String> order) {

        visited.add(node);

        for (String next :
                cfg.getOrDefault(node,
                        new ArrayList<>())) {

            if (!visited.contains(next))
                dfsPostOrder(next, cfg, visited, order);
        }

        order.add(node);
    }

    static List<String> computePostOrder(
            Map<String, List<String>> cfg,
            String entry) {

        Set<String> visited = new HashSet<>();
        List<String> order = new ArrayList<>();

        dfsPostOrder(entry, cfg, visited, order);

        return order;
    }

    public static void main(String[] args) {

        Map<String, List<String>> cfg =
                new LinkedHashMap<>();

        cfg.put("B0", Arrays.asList("B1", "B2"));
        cfg.put("B1", Arrays.asList("B3", "B4"));
        cfg.put("B2", Arrays.asList("B5"));
        cfg.put("B3", Arrays.asList("B6"));
        cfg.put("B4", Arrays.asList("B6"));
        cfg.put("B5", Arrays.asList("B6"));
        cfg.put("B6", Arrays.asList("B8"));
        cfg.put("B7", Arrays.asList("B8"));
        cfg.put("B8", new ArrayList<>());

        System.out.println("=================================");
        System.out.println(" LLVM DEAD CODE ELIMINATION ");
        System.out.println("=================================");

        Set<String> dead =
                findDeadBlocks(cfg, "B0");

        Set<String> reachable =
                new LinkedHashSet<>(cfg.keySet());

        reachable.removeAll(dead);

        System.out.println("\nReachable Blocks:");
        System.out.println(reachable);

        System.out.println("\nDead Blocks:");
        System.out.println(dead);

        List<String> postOrder =
                computePostOrder(cfg, "B0");

        System.out.println("\nPost Order Traversal:");
        System.out.println(postOrder);

        System.out.println("\nReverse Post Order:");

        List<String> reverse =
                new ArrayList<>(postOrder);

        Collections.reverse(reverse);

        System.out.println(reverse);

        System.out.println("\nB8 Status:");
        System.out.println("ALIVE");

        System.out.println("\n=================================");
        System.out.println(" TIME COMPLEXITIES ");
        System.out.println("=================================");
        System.out.println("DFS Reachability : O(V + E)");
        System.out.println("Post Order DFS   : O(V + E)");
    }
}