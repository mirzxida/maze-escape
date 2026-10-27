import java.util.*;

public class Zones {

    /**
     * Mission 4. Kosaraju. Returns zoneOf: zoneOf[cell] = number of the zone, numbers 0 .. zones-1.
     * Number the zones in the order in which the second pass finds them. 
     */
    public static int[] kosaraju(Input.Slides s) {
        int n = s.n();
        int[][] from = Graphs.slidesFrom(s);
        int[][] into = Graphs.slidesInto(s);

        boolean[] visited = new boolean[n];
        int[] finished = new int[n];
        int finishedCount = 0;

        // Массивы для итеративного DFS (чтобы избежать StackOverflow)
        int[] stack = new int[n];
        int[] edgeIndex = new int[n]; // Запоминает, какую по счету горку мы сейчас проверяем для каждой комнаты

        // 1. Первый проход DFS (по обычным горкам - from)
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int top = 0;
                stack[top++] = i;
                visited[i] = true;

                while (top > 0) {
                    int u = stack[top - 1]; // Смотрим на верхний элемент
                    if (edgeIndex[u] < from[u].length) {
                        int slide = from[u][edgeIndex[u]++]; // Берем горку и двигаем индекс
                        int v = s.to()[slide];
                        if (!visited[v]) {
                            visited[v] = true;
                            stack[top++] = v;
                        }
                    } else {
                        // Если все горки из комнаты проверены, достаем её из стека и помечаем завершенной
                        top--;
                        finished[finishedCount++] = u;
                    }
                }
            }
        }

        // 2. Второй проход DFS (по развернутым горкам - into)
        int[] zoneOf = new int[n];
        Arrays.fill(zoneOf, -1);
        int zoneCount = 0;

        // Идем в обратном порядке от времени завершения первого DFS
        for (int i = n - 1; i >= 0; i--) {
            int startNode = finished[i];
            if (zoneOf[startNode] == -1) {
                int top = 0;
                stack[top++] = startNode;
                zoneOf[startNode] = zoneCount;

                while (top > 0) {
                    int u = stack[--top];
                    for (int slide : into[u]) {
                        int v = s.from()[slide]; // По обратному ребру идем к источнику
                        if (zoneOf[v] == -1) {
                            zoneOf[v] = zoneCount;
                            stack[top++] = v;
                        }
                    }
                }
                zoneCount++; // Переходим к нумерации следующей зоны
            }
        }

        return zoneOf;
    }

    /**
     * Mission 5. The graph of zones: the edge X to Y costs the cheapest slide from X to Y.
     * Returns two numbers: flow stages and the cost of the cheapest route.
     */
    public static long[] routes(Input.Slides s, int[] zoneOf, int zoneCount) {
        int n = s.n();

        // Группируем комнаты по их зонам
        List<Integer>[] cellsInZone = new ArrayList[zoneCount];
        for (int i = 0; i < zoneCount; i++) cellsInZone[i] = new ArrayList<>();
        for (int i = 0; i < n; i++) cellsInZone[zoneOf[i]].add(i);

        // Массивы для DP
        long[] dpCost = new long[zoneCount];
        Arrays.fill(dpCost, Long.MAX_VALUE);
        int startZone = zoneOf[0];
        int endZone = zoneOf[n - 1];
        dpCost[startZone] = 0;

        int[] dpStages = new int[zoneCount];
        Arrays.fill(dpStages, 1); // Каждая зона сама по себе — цепочка из 1 элемента

        int[][] from = Graphs.slidesFrom(s);

        // Проходим по зонам строго в топологическом порядке
        for (int z = 0; z < zoneCount; z++) {
            for (int u : cellsInZone[z]) {
                for (int slide : from[u]) {
                    int v = s.to()[slide];
                    int nextZ = zoneOf[v];

                    if (z != nextZ) { // Если горка ведет в другую зону
                        long slideCost = s.time()[slide];
                        if (dpCost[z] != Long.MAX_VALUE) {
                            dpCost[nextZ] = Math.min(dpCost[nextZ], dpCost[z] + slideCost);
                        }
                        dpStages[nextZ] = Math.max(dpStages[nextZ], dpStages[z] + 1);
                    }
                }
            }
        }

        long maxStages = 0;
        for (int stages : dpStages) {
            maxStages = Math.max(maxStages, stages);
        }

        return new long[]{ maxStages, dpCost[endZone] };
    }
}