public class DisjointSet {
    private int[] parent;
    private int[] size;
    private int count;

    // Конструктор: изначально каждый элемент в своей собственной группе
    public DisjointSet(int n) {
        parent = new int[n];
        size = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1; // начальный размер каждого дерева — 1
        }
    }

    // Поиск корня с оптимизацией "сжатие пути" (path compression)
    public int find(int i) {
        int root = i;
        // Находим реальный корень множества
        while (root != parent[root]) {
            root = parent[root];
        }
        // Сжатие пути: привязываем пройденные элементы напрямую к корню
        int curr = i;
        while (curr != root) {
            int nxt = parent[curr];
            parent[curr] = root;
            curr = nxt;
        }
        return root;
    }

    // Объединение двух множеств с оптимизацией "по размеру" (union by size)
    public boolean union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);

        // Если они уже в одной группе, возвращаем false (как просят в задании)
        if (rootI == rootJ) {
            return false;
        }

        // Меньшее дерево подвешиваем к корню большего
        if (size[rootI] < size[rootJ]) {
            parent[rootI] = rootJ;
            size[rootJ] += size[rootI];
        } else {
            parent[rootJ] = rootI;
            size[rootI] += size[rootJ];
        }

        count--; // Количество независимых групп уменьшилось на 1
        return true;
    }

    // Проверка, находятся ли элементы в одной группе
    public boolean connected(int i, int j) {
        return find(i) == find(j);
    }

    // Возвращает количество изолированных групп (компонент связности)
    public int count() {
        return count;
    }
}