package com.pizzeria;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MenuManager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "menu.json"; // Константа для імені файлу

    // Метод для експорту меню із сортуванням
    public static void exportMenu(List<Pizza> menu, Comparator<Pizza> comparator) {
        menu.sort(comparator); // Сортуємо меню за переданим компаратором

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(FILE_NAME), "UTF-8")) {
            gson.toJson(menu, writer); // Записуємо JSON у файл
            System.out.println("Меню експортовано у " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Помилка при експорті меню: " + e.getMessage());
        }
    }

    // Метод для імпорту меню з файлу
    public static List<Pizza> importMenu() {
        try (Reader reader = new InputStreamReader(new FileInputStream(FILE_NAME), "UTF-8")) {
            Pizza[] pizzas = gson.fromJson(reader, Pizza[].class); // Зчитуємо JSON у масив об'єктів Pizza
            System.out.println("Меню імпортовано з " + FILE_NAME);
            return pizzas != null ? Arrays.asList(pizzas) : new ArrayList<>(); // Повертаємо список
        } catch (IOException e) {
            System.out.println("Помилка при імпорті меню: " + e.getMessage());
            return new ArrayList<>(); // Якщо сталася помилка — повертаємо порожній список
        }
    }
}
