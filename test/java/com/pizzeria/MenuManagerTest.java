package com.pizzeria;

import org.junit.jupiter.api.*;
import java.io.File;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MenuManagerTest {

    private static final String TEST_FILE = "menu.json";

    private List<Pizza> sampleMenu = Arrays.asList(
            new Pizza("B", 30, 3, 50),
            new Pizza("A", 20, 2, 40),
            new Pizza("C", 25, 4, 60));

    // Перед кожним тестом видаляємо тестовий файл
    @BeforeEach
    public void cleanUp() {
        File file = new File(TEST_FILE);
        if (file.exists())
            file.delete();
    }

    // Тест експорту та імпорту меню з сортуванням
    @Test
    public void testExportImportMenu() {
        MenuManager.exportMenu(sampleMenu, Comparator.comparing(Pizza::getName));
        File file = new File(TEST_FILE);
        assertTrue(file.exists());

        List<Pizza> imported = MenuManager.importMenu();
        assertEquals(3, imported.size());
        assertEquals("A", imported.get(0).getName()); // Перевірка сортування за назвою
    }

    // Тест імпорту з відсутнього файлу
    @Test
    public void testImportFromMissingFile() {
        List<Pizza> imported = MenuManager.importMenu();
        assertTrue(imported.isEmpty());
    }
}
