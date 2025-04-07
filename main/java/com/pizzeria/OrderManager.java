package com.pizzeria;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> orders = new ArrayList<>(); // Список усіх замовлень

    // Додати замовлення з випадковим клієнтом
    public void addOrder(Pizza pizza) {
        Customer customer = Customer.generateRandom();
        orders.add(new Order(pizza, customer));
    }

    // Видалити конкретне замовлення
    public void removeOrder(Order order) {
        orders.remove(order);
    }

    // Оновити статус замовлення (якщо воно існує)
    public void updateOrderStatus(Order order, OrderStatus status) {
        if (orders.contains(order)) {
            order.setStatus(status);
        }
    }

    // Отримати список усіх замовлень
    public List<Order> getOrders() {
        return orders;
    }

    // Очистити всі замовлення
    public void clearOrders() {
        orders.clear();
    }

    // Повернути загальну вартість усіх замовлень (без чайових)
    public int getTotalOrderValue() {
        int total = 0;
        for (Order order : orders) {
            total += order.getPizza().getSellingPrice();
        }
        return total;
    }
}
