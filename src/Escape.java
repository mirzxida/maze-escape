import java.util.Arrays;

/**
 * Mission 3. Escape: DFS from cell 0 to the last cell (rows * cols - 1) in the maze of Mission 1.
 */
public class Escape {

    public record Result(int[] path, int visited, int deadEnds) {}

    public static Result escape(Input.Walls w, int[] broken) {
        int numCells = w.rows() * w.cols();
        int exitCell = numCells - 1;

        // Получаем список открытых проходов для каждой комнаты
        int[][] open = Graphs.open(w, broken);

        // Подсчет тупиков (комнаты, у которых ровно 1 открытый сосед)
        int deadEnds = 0;
        for (int i = 0; i < numCells; i++) {
            if (open[i].length == 1) {
                deadEnds++;
            }
        }

        // Для восстановления пути запоминаем, откуда мы пришли в каждую клетку
        int[] parent = new int[numCells];
        Arrays.fill(parent, -1);

        // Итеративный DFS
        boolean[] visited = new boolean[numCells];
        int[] stack = new int[numCells];
        int top = 0;

        stack[top++] = 0; // Кладем стартовую комнату в стек

        int visitedCount = 0;
        int exitVisitedCount = 0;

        while (top > 0) {
            int curr = stack[--top]; // Достаем верхний элемент

            if (!visited[curr]) {
                visited[curr] = true;
                visitedCount++;

                // Как только наткнулись на выход, запоминаем счетчик и завершаем поиск
                if (curr == exitCell) {
                    exitVisitedCount = visitedCount;
                    break;
                }

                int cols = w.cols();
                // Порядок обхода по заданию: вверх, вправо, вниз, влево.
                // В стек кладем в обратном порядке: влево, вниз, вправо, вверх.
                int[] nextCells = { curr - 1, curr + cols, curr + 1, curr - cols };

                for (int nxt : nextCells) {
                    if (nxt >= 0 && nxt < numCells && isOpen(curr, nxt, open)) {
                        if (!visited[nxt]) {
                            parent[nxt] = curr;
                            stack[top++] = nxt;
                        }
                    }
                }
            }
        }

        // Восстановление пути от выхода к старту
        int pathLen = 0;
        for (int curr = exitCell; curr != -1; curr = parent[curr]) {
            pathLen++;
        }

        int[] path = new int[pathLen];
        int c = exitCell;
        for (int i = pathLen - 1; i >= 0; i--) {
            path[i] = c;
            c = parent[c];
        }

        return new Result(path, exitVisitedCount, deadEnds);
    }

    // Вспомогательный метод для проверки, есть ли проход между текущей и следующей комнатой
    private static boolean isOpen(int curr, int nxt, int[][] open) {
        for (int neighbor : open[curr]) {
            if (neighbor == nxt) return true;
        }
        return false;
    }
}