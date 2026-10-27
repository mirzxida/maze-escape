public class DisjointSetTest {

    // Главный метод, с которого начинается запуск
    public static void main(String[] args) {
        testMyDSU();
    }

    public static void testMyDSU() {
        DisjointSet dsu = new DisjointSet(5);

        // Используем обычные if с выбросом ошибки, чтобы не настраивать флаг -ea в IDEA
        if (dsu.count() != 5) throw new RuntimeException("Изначально должно быть 5 групп");

        if (dsu.union(0, 1) != true) throw new RuntimeException("Успешное объединение 0 и 1");
        if (dsu.union(1, 2) != true) throw new RuntimeException("Успешное объединение 1 и 2");
        if (dsu.union(0, 2) != false) throw new RuntimeException("0 и 2 уже соединены, должно быть false");

        if (dsu.connected(0, 2) != true) throw new RuntimeException("0 и 2 должны быть связаны");
        if (dsu.connected(0, 3) != false) throw new RuntimeException("0 и 3 не связаны");
        if (dsu.count() != 3) throw new RuntimeException("Осталось 3 группы: {0,1,2}, {3}, {4}");

        System.out.println("Мои тесты для DSU успешно пройдены!");
    }
}