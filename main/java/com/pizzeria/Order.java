package com.pizzeria;

import java.util.Objects;

public class Order {
    private Pizza pizza; // Піца, яку замовлено
    private OrderStatus status; // Статус замовлення
    private Customer customer; // Клієнт, який зробив замовлення

    // Конструктор: створює замовлення з піцою та клієнтом
    public Order(Pizza pizza, Customer customer) {
        if (pizza == null || customer == null) {
            throw new IllegalArgumentException("Піца та клієнт не можуть бути null");
        }
        this.pizza = pizza;
        this.customer = customer;
        this.status = OrderStatus.Черга; // Початковий статус – у черзі
    }

    // Геттер для піци
    public Pizza getPizza() {
        return pizza;
    }

    // Геттер для статусу замовлення
    public OrderStatus getStatus() {
        return status;
    }

    // Сеттер для статусу замовлення
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // Геттер для клієнта
    public Customer getCustomer() {
        return customer;
    }

    // Перевизначення equals: порівнює замовлення за клієнтом і піцою
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Order order = (Order) o;
        return customer.equals(order.customer) && pizza.equals(order.pizza);
    }

    // Перевизначення hashCode
    @Override
    public int hashCode() {
        return Objects.hash(customer, pizza);
    }

    // Рядкове представлення замовлення
    @Override
    public String toString() {
        return String.format("Замовлення від %s (%s): %s, %s, %d грн",
                customer.getName(), customer.getPhoneNumber(), pizza.getName(), status, pizza.getSellingPrice());
    }
}
