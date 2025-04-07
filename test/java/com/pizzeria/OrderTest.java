package com.pizzeria;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    // Тест ініціалізації замовлення
    @Test
    public void testOrderInitialization() {
        Pizza pizza = new Pizza("Гавайська", 90, 6, 160);
        Customer customer = new Customer("Ім’я", "+380123456789", 0);
        Order order = new Order(pizza, customer);

        assertEquals(pizza, order.getPizza());
        assertEquals(OrderStatus.Черга, order.getStatus());
        assertEquals(customer.getName(), order.getCustomer().getName());
    }

    // Тест виводу замовлення у рядку
    @Test
    public void testToStringOutput() {
        Pizza pizza = new Pizza("Маргарита", 40, 3, 80);
        Customer customer = new Customer("Тест", "+380000000000", 0);
        Order order = new Order(pizza, customer);

        String result = order.toString();
        assertTrue(result.contains("Маргарита"));
        assertTrue(result.contains("Черга"));
    }

    // Тест створення замовлення з null піцою або клієнтом
    @Test
    public void testOrderCreationWithNullPizzaOrCustomer() {
        Customer customer = new Customer("Ім’я", "+380111111111", 0);
        Pizza pizza = new Pizza("Сирна", 50, 4, 100);

        assertThrows(IllegalArgumentException.class, () -> {
            new Order(null, customer);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Order(pizza, null);
        });
    }

    // Тест equals та hashCode для замовлень з однаковим клієнтом та назвою піци
    @Test
    public void testEqualsAndHashCode() {
        Pizza pizza1 = new Pizza("Пепероні", 45, 4, 100);
        Customer customer1 = new Customer("Артем", "+380501234567", 10);

        Pizza pizza2 = new Pizza("Пепероні", 50, 5, 110); // Та ж назва — equals для Pizza спрацює
        Customer customer2 = new Customer("Артем", "+380501234567", 5); // Той самий номер

        Order order1 = new Order(pizza1, customer1);
        Order order2 = new Order(pizza2, customer2);

        assertEquals(order1, order2);
        assertEquals(order1.hashCode(), order2.hashCode());
    }
}
