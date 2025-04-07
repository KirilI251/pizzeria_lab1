package com.pizzeria;

import java.util.*;

public class PizzaCatalog {
    // Початкове меню піц
    private List<Pizza> pizzas = new ArrayList<>(Arrays.asList(
            new Pizza("Маргарита", 40, 3, 80),
            new Pizza("Пепперони", 60, 5, 120),
            new Pizza("Гавайска", 90, 6, 160)));

    // Нормалізація імені (видалення пробілів та приведення до нижнього регістру)
    private String normalizeName(String name) {
        return name.trim().toLowerCase();
    }

    // Додати нову піцу (перевіряється, чи такої назви ще немає)
    public boolean addPizza(Pizza pizza) {
        String normalizedName = normalizeName(pizza.getName());
        if (pizzas.stream().anyMatch(p -> normalizeName(p.getName()).equals(normalizedName))) {
            return false; // Піца з таким іменем вже існує
        }
        pizzas.add(pizza);
        return true;
    }

    // Видалити піцу за назвою
    public boolean removePizza(String name) {
        return pizzas.removeIf(p -> p.getName().equalsIgnoreCase(name));
    }

    // Знайти піцу за назвою (ігноруючи регістр і пробіли)
    public Optional<Pizza> findPizzaByName(String name) {
        String normalizedName = normalizeName(name);
        return pizzas.stream()
                .filter(p -> normalizeName(p.getName()).equals(normalizedName))
                .findFirst();
    }

    // Отримати весь список піц
    public List<Pizza> getAll() {
        return pizzas;
    }

    // Оновити піцу за назвою (повністю замінити на нову версію)
    public boolean updatePizza(String name, Pizza newPizza) {
        Optional<Pizza> existing = findPizzaByName(name);
        if (existing.isPresent()) {
            pizzas.remove(existing.get());
            pizzas.add(newPizza);
            return true;
        }
        return false;
    }

    // Видалити піцу за індексом
    public boolean deletePizzaByIndex(int index) {
        if (index >= 0 && index < pizzas.size()) {
            pizzas.remove(index);
            return true;
        }
        return false;
    }

    // Отримати піцу за індексом
    public Pizza getPizzaByIndex(int index) {
        return pizzas.get(index);
    }
}
