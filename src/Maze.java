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

        return java.util.Arrays.copyOf(broken, count);
    }

    /**
     * Mission 2. Kruskal: the same, but take the walls from the cheapest to the most expensive.
     * Returns the indexes of the broken walls.
     */
    public static int[] cheapestMaze(Input.Walls w) {
        // TODO
        return new int[0];
    }

    /**
     * Mission 2. Prim with a priority queue. Returns the total cost of the cheapest maze.
     * It must be equal to the total cost of cheapestMaze.
     */
    public static long primCost(Input.Walls w) {
        // TODO
        return 0;
    }
}