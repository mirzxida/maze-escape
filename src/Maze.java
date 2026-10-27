import java.util.*;

public class Maze {

    /**
     * Mission 1. Take the walls in the order of the file. Break a wall when its two cells are in different sets.
     * Returns the indexes of the broken walls, in the order of breaking.
     */
    public static int[] breakWalls(Input.Walls w) {
        int numCells = w.rows() * w.cols();
        DisjointSet dsu = new DisjointSet(numCells);

        int[] broken = new int[numCells - 1];
        int count = 0;

        for (int k = 0; k < w.a().length; k++) {
            if (dsu.union(w.a()[k], w.b()[k])) {
                broken[count] = k;
                count++;
            }
        }

        return Arrays.copyOf(broken, count);
    }

    /**
     * Mission 2. Kruskal: the same, but take the walls from the cheapest to the most expensive.
     * Returns the indexes of the broken walls.
     */
    public static int[] cheapestMaze(Input.Walls w) {
        int numCells = w.rows() * w.cols();
        DisjointSet dsu = new DisjointSet(numCells);

        Integer[] walls = new Integer[w.a().length];
        for (int i = 0; i < walls.length; i++) walls[i] = i;

        Arrays.sort(walls, Comparator.comparingInt(i -> w.cost()[i]));

        int[] broken = new int[numCells - 1];
        int count = 0;

        for (int idx : walls) {
            if (dsu.union(w.a()[idx], w.b()[idx])) {
                broken[count] = idx;
                count++;
            }
        }

        return Arrays.copyOf(broken, count);
    }

    /**
     * Mission 2. Prim with a priority queue. Returns the total cost of the cheapest maze.
     * It must be equal to the total cost of cheapestMaze.
     */
    public static long primCost(Input.Walls w) {
        int numCells = w.rows() * w.cols();

        List<int[]>[] adj = new ArrayList[numCells];
        for (int i = 0; i < numCells; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < w.a().length; i++) {
            adj[w.a()[i]].add(new int[]{w.cost()[i], w.b()[i]});
            adj[w.b()[i]].add(new int[]{w.cost()[i], w.a()[i]});
        }

        boolean[] visited = new boolean[numCells];
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(edge -> edge[0]));
        long totalCost = 0;

        visited[0] = true;
        pq.addAll(adj[0]);

        int edgesUsed = 0;
        while (!pq.isEmpty() && edgesUsed < numCells - 1) {
            int[] current = pq.poll();
            int cost = current[0];
            int nextCell = current[1];

            if (visited[nextCell]) continue;

            visited[nextCell] = true;
            totalCost += cost;
            edgesUsed++;

            for (int[] edge : adj[nextCell]) {
                if (!visited[edge[1]]) {
                    pq.add(edge);
                }
            }
        }

        return totalCost;
    }
}