package com.pizzeria;

import java.util.List;
import java.util.Scanner;

public class PizzeriaUI {
    private final PizzeriaCode code = new PizzeriaCode();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new PizzeriaUI().start(); // Запуск головного меню
    }

    // Головний цикл програми з обробкою вибору користувача
    public void start() {
        while (true) {
            // Основне меню
            System.out.println(
                    "\nМеню:\n1. Автозамовлення\n2. Замовлення вручну\n3. Кухня\n4. Показати замовлення\n5. Баланс\n6. Експорт меню\n7. Імпорт меню\n8. Керування піцами\n9. Вихід\n");
            int choice = readInt("Ваш вибір: ");
            switch (choice) {
                case 1:
                    code.autoGenerateOrder();
                    System.out.println("Автоматичне замовлення створено.");
                    break;
                case 2:
                    manualOrder(); // Створення замовлення вручну
                    break;
                case 3:
                    kitchen(); // Функціонал кухні
                    break;
                case 4:
                    printOrders(); // Виведення усіх замовлень
                    break;
                case 5:
                    // Вивід фінансового балансу та кількості проданих піц
                    System.out.println("Баланс: " + code.getBalance() + " грн");
                    System.out.println("Продано піц: " + code.getTotalSoldPizzas());
                    break;
                case 6:
                    exportMenu(); // Збереження меню у файл
                    break;
                case 7:
                    boolean imported = code.importMenu(); // Завантаження меню з файлу
                    System.out.println(imported ? "Меню оновлено!" : "Імпорт не вдався.");
                    break;
                case 8:
                    pizzaCrud(); // Меню для керування піцами (CRUD)
                    break;
                case 9:
                    System.out.println("Вихід...");
                    return;
                case 0:
                    // Пасхалка
                    System.out.println("Ви зламали гру! +200 грн");
                    code.addMoney(200);
                    break;
                default:
                    System.out.println("Невірне введення.");
            }
        }
    }

    // Створення замовлення вручну
    private void manualOrder() {
        if (code.getMenu().isEmpty()) {
            System.out.println("Меню порожнє. Неможливо створити замовлення.");
            return;
        }

        System.out.println("Оберіть піцу:");
        printMenu();
        int choice = readInt("Ваш вибір: ");
        if (code.manualCreateOrder(choice - 1)) {
            System.out.println("Замовлення додано.");
        } else {
            System.out.println("Невірний вибір.");
        }
    }

    // Обробка приготування піци та виконання замовлень
    private void kitchen() {
        System.out.println("Кухня:\n1. Приготувати піцу\n2. Виконати замовлення");
        int choice = readInt("Ваш вибір: ");
        switch (choice) {
            case 1: {
                List<Pizza> menu = code.getMenu();
                if (menu.isEmpty()) {
                    System.out.println("Меню порожнє. Немає піц для приготування.");
                    return;
                }
                printMenu();
                int pizzaIndex = readInt("Оберіть номер піци для приготування: ");
                scanner.nextLine();

                if (pizzaIndex < 1 || pizzaIndex > menu.size()) {
                    System.out.println("Невірний вибір.");
                    return;
                }

                Pizza selected = menu.get(pizzaIndex - 1);

                if (code.getBalance() < selected.getCostPrice()) {
                    System.out.println("Недостатньо коштів для приготування " + selected.getName());
                    return;
                }

                // Імітація часу приготування
                System.out.println("Готуємо " + selected.getName() + "...");
                System.out.println("Час приготування: " + selected.getPreparationTime() + " секунд...");

                try {
                    Thread.sleep(selected.getPreparationTime() * 1000L);
                } catch (InterruptedException e) {
                    System.out.println("Приготування перервано.");
                    return;
                }

                boolean ok = code.producePizza(pizzaIndex - 1);
                System.out.println(ok ? selected.getName() + " успішно приготовлена і додана на склад."
                        : "Помилка при приготуванні.");
                break;
            }
            case 2: {
                if (code.getOrders().isEmpty()) {
                    System.out.println("Немає замовлень.");
                    return;
                }

                printOrders();
                int orderIndex = readInt("Оберіть номер замовлення для виконання: ") - 1;
                String result = code.fulfillOrder(orderIndex);
                System.out.println(result);
                break;
            }
            default:
                System.out.println("Невірний вибір.");
        }
    }

    // Меню сортування та експорту
    private void exportMenu() {
        System.out.println("Сортування: 1. Назва  2. Ціна  3. Собівартість\n");
        int sortOption = readInt("Ваш вибір: ");
        code.exportMenu(sortOption);
        System.out.println("Меню експортовано.");
    }

    // CRUD-меню для управління піцами
    private void pizzaCrud() {
        while (true) {
            System.out.println("\n1. Переглянути\n2. Додати\n3. Редагувати\n4. Видалити\n5. Назад\n");
            int choice = readInt("Ваш вибір: ");
            switch (choice) {
                case 1: {
                    printMenu();
                    break;
                }
                case 2: {
                    // Додавання нової піци
                    String name = readString("Назва: ");
                    int price = readInt("Ціна продажу: ");
                    int time = readInt("Час приготування (сек): ");
                    int cost = readInt("Собівартість: ");

                    boolean added = code.addPizza(new Pizza(name, cost, time, price));
                    if (added) {
                        System.out.println("Піцу додано.");
                    } else {
                        System.out.println("Помилка: піца з такою назвою вже існує.");
                    }
                    break;
                }
                case 3: {
                    // Редагування піци
                    printMenu();
                    int index = readInt("Оберіть номер для редагування: ") - 1;
                    System.out.print("Нова назва: ");
                    String name = scanner.nextLine();
                    System.out.print("Нова ціна: ");
                    String p = scanner.nextLine();
                    System.out.print("Новий час: ");
                    String t = scanner.nextLine();
                    System.out.print("Нова собівартість: ");
                    String c = scanner.nextLine();

                    try {
                        Integer price = p.isEmpty() ? null : Integer.parseInt(p);
                        Integer time = t.isEmpty() ? null : Integer.parseInt(t);
                        Integer cost = c.isEmpty() ? null : Integer.parseInt(c);

                        boolean ok = code.editPizza(index,
                                name.isEmpty() ? null : name, price, time, cost);
                        System.out.println(ok ? "Піцу оновлено." : "Невірний вибір.");
                    } catch (NumberFormatException e) {
                        System.out.println("Помилка: введено нечислове значення. Редагування скасовано.");
                    }
                    break;
                }
                case 4: {
                    // Видалення піци
                    printMenu();
                    int index = readInt("Оберіть номер для видалення: ") - 1;
                    boolean ok = code.deletePizza(index);
                    System.out.println(ok ? "Піцу видалено." : "Невірний вибір.");
                    break;
                }
                case 5: {
                    return; // Повернення до головного меню
                }
            }
        }
    }

    // Вивід усіх піц у меню
    private void printMenu() {
        List<Pizza> menu = code.getMenu();
        if (menu.isEmpty()) {
            System.out.println("Меню порожнє.");
            return;
        }
        for (int i = 0; i < menu.size(); i++) {
            Pizza p = menu.get(i);
            System.out.printf("%d. %s - Ціна: %d грн, Час: %d сек, Собівартість: %d грн, Доступно: %d\n",
                    i + 1, p.getName(), p.getSellingPrice(), p.getPreparationTime(), p.getCostPrice(),
                    p.getAvailableQuantity());
        }
    }

    // Вивід усіх замовлень
    private void printOrders() {
        List<Order> orders = code.getOrders();
        if (orders.isEmpty()) {
            System.out.println("Немає замовлень.");
            return;
        }

        int total = 0;
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            System.out.printf("%d. %s\n", i + 1, order);
            total += order.getPizza().getSellingPrice();
        }

        System.out.printf("Загальна сума замовлень: %d грн\n", total);
    }

    // Читання цілого числа з перевіркою
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть ціле число.");
            }
        }
    }

    // Читання текстового рядка
    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
