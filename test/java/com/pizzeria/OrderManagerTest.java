package com.pizzeria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderManagerTest {
    private OrderManager orderManager;
    private Pizza pizza;

    // Підготовка перед кожним тестом
    @BeforeEach
    public void setUp() {
        orderManager = new OrderManager();
        pizza = new Pizza("Маргарита", 40, 3, 80);
    }

    // Тест додавання замовлення
    @Test
    public void testAddOrder() {
        orderManager.addOrder(pizza);
        assertEquals(1, orderManager.getOrders().size());
    }

    // Тест очищення списку замовлень
    @Test
    public void testClearOrders() {
        orderManager.addOrder(pizza);
        orderManager.clearOrders();
        assertTrue(orderManager.getOrders().isEmpty());
    }

    // Тест загальної вартості замовлень
    @Test
    public void testGetTotalOrderValue() {
        orderManager.addOrder(pizza);
        assertEquals(80, orderManager.getTotalOrderValue());
    }

    // Тест видалення замовлення
    @Test
    public void testRemoveOrder() {
        orderManager.addOrder(pizza);
        Order order = orderManager.getOrders().get(0);
        orderManager.removeOrder(order);
        assertTrue(orderManager.getOrders().isEmpty());
    }

    // Тест оновлення статусу замовлення
    @Test
    public void testUpdateOrderStatus() {
        orderManager.addOrder(pizza);
        Order order = orderManager.getOrders().get(0); // Отримуємо саме замовлення
        orderManager.updateOrderStatus(order, OrderStatus.Приготування); // Передаємо замовлення, а не піцу
        assertEquals(OrderStatus.Приготування, order.getStatus());
    }
}
