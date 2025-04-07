package com.pizzeria;

import java.util.*;

public class PizzeriaCode {
    private OrderManager orderManager = new OrderManager(); // Менеджер замовлень
    private PizzaCatalog catalog = new PizzaCatalog(); // Каталог піц
    private int balance = 60; // Початковий баланс
    private int totalSoldPizzas; // Загальна кількість проданих піц

    // Отримати список усіх піц у меню
    public List<Pizza> getMenu() {
        return catalog.getAll();
    }

    // Отримати список усіх замовлень
    public List<Order> getOrders() {
        return orderManager.getOrders();
    }

    // Автоматично створити замовлення з випадковою піцою
    public void autoGenerateOrder() {
        List<Pizza> menu = catalog.getAll();
        if (menu.isEmpty())
            return;
        Pizza randomPizza = menu.get(new Random().nextInt(menu.size()));
        orderManager.addOrder(randomPizza);
    }

    // Створити замовлення вручну за індексом піци
    public boolean manualCreateOrder(int pizzaIndex) {
        List<Pizza> menu = catalog.getAll();
        if (pizzaIndex >= 0 && pizzaIndex < menu.size()) {
            orderManager.addOrder(menu.get(pizzaIndex));
            return true;
        }
        return false;
    }

    // Приготувати піцу: списати кошти та додати на склад
    public boolean producePizza(int pizzaIndex) {
        List<Pizza> menu = catalog.getAll();
        if (pizzaIndex >= 0 && pizzaIndex < menu.size()) {
            Pizza selected = menu.get(pizzaIndex);
            if (balance < selected.getCostPrice())
                return false;
            subtractMoney(selected.getCostPrice());
            selected.restock(1); // Додати 1 піцу на склад
            return true;
        }
        return false;
    }

    // Виконати замовлення за індексом
    public String fulfillOrder(int orderIndex) {
        List<Order> orders = orderManager.getOrders();
        if (orderIndex >= 0 && orderIndex < orders.size()) {
            Order order = orders.get(orderIndex);
            Pizza pizza = order.getPizza();
            if (pizza.getAvailableQuantity() > 0) {
                pizza.reduceAvailableQuantity(1); // Зменшити кількість на складі
                int tips = order.getCustomer().getTips(); // Отримати чайові
                addMoney(pizza.getSellingPrice() + tips); // Додати кошти до балансу
                totalSoldPizzas++; // Збільшити лічильник проданих піц
                orders.remove(orderIndex); // Видалити виконане замовлення
                return String.format("Продано %s. Чайові: %d грн", pizza.getName(), tips);
            } else {
                return "Немає піци на складі";
            }
        }
        return "Невірний вибір";
    }

    // Експортувати меню з вибраним варіантом сортування
    public void exportMenu(int sortOption) {
        Comparator<Pizza> comparator = Comparator.comparing(Pizza::getName); // За замовчуванням — по назві
        switch (sortOption) {
            case 2:
                comparator = Comparator.comparingInt(Pizza::getSellingPrice); // По ціні
                break;
            case 3:
                comparator = Comparator.comparingInt(Pizza::getCostPrice); // По собівартості
                break;
        }
        MenuManager.exportMenu(catalog.getAll(), comparator);
    }

    // Імпортувати меню з JSON-файлу
    public boolean importMenu() {
        List<Pizza> imported = MenuManager.importMenu();
        if (!imported.isEmpty()) {
            for (Pizza p : imported) {
                catalog.addPizza(p);
            }
            return true;
        }
        return false;
    }

    // Отримати поточний баланс
    public int getBalance() {
        return balance;
    }

    // Отримати кількість проданих піц
    public int getTotalSoldPizzas() {
        return totalSoldPizzas;
    }

    // Додати нову піцу в каталог
    public boolean addPizza(Pizza pizza) {
        return catalog.addPizza(pizza);
    }

    // Редагувати піцу за індексом
    public boolean editPizza(int index, String name, Integer price, Integer time, Integer cost) {
        List<Pizza> menu = catalog.getAll();
        if (index >= 0 && index < menu.size()) {
            Pizza pizza = menu.get(index);

            // Використати старі значення, якщо нові не передано
            String newName = (name != null && !name.isEmpty()) ? name : pizza.getName();
            int newPrice = (price != null) ? price : pizza.getSellingPrice();
            int newTime = (time != null) ? time : pizza.getPreparationTime();
            int newCost = (cost != null) ? cost : pizza.getCostPrice();

            // Перевірка на унікальність назви
            String normalizedNewName = newName.trim().toLowerCase();
            for (int i = 0; i < menu.size(); i++) {
                if (i != index && menu.get(i).getName().trim().toLowerCase().equals(normalizedNewName)) {
                    System.out.println("Помилка: піца з такою назвою вже існує.");
                    return false;
                }
            }

            // Перевірка на логіку ціноутворення
            if (newPrice < newCost) {
                throw new IllegalArgumentException("Ціна продажу не може бути меншою за собівартість!");
            }

            // Оновлення полів
            pizza.setName(newName);
            pizza.setCostPrice(newCost);
            pizza.setSellingPrice(newPrice);
            pizza.setPreparationTime(newTime);
            pizza.resetStock(); // Скидання залишку

            return true;
        }
        return false;
    }

    // Видалити піцу за індексом
    public boolean deletePizza(int index) {
        List<Pizza> menu = catalog.getAll();
        if (index >= 0 && index < menu.size()) {
            Pizza pizzaToDelete = menu.get(index);
            // Видалити всі замовлення з цією піцою
            orderManager.getOrders().removeIf(order -> order.getPizza().equals(pizzaToDelete));
            return catalog.deletePizzaByIndex(index);
        }
        return false;
    }

    // Додати кошти до балансу
    public void addMoney(int amount) {
        balance += amount;
    }

    // Відняти кошти з балансу
    private void subtractMoney(int amount) {
        if (balance >= amount) {
            balance -= amount;
        }
    }
}
