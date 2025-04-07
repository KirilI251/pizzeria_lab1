package com.pizzeria;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PizzaTest {

    // Тест створення піци з валідними значеннями
    @Test
    public void testPizzaCreationValid() {
        Pizza pizza = new Pizza("Тестова", 50, 5, 100);
        assertEquals("Тестова", pizza.getName());
        assertEquals(50, pizza.getCostPrice());
        assertEquals(5, pizza.getPreparationTime());
        assertEquals(100, pizza.getSellingPrice());
    }

    // Тест: ціна продажу не може бути меншою за собівартість
    @Test
    public void testSellingPriceCannotBeLowerThanCost() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Pizza("Дешева", 50, 3, 30);
        });
    }

    // Тест: від'ємні значення викликають помилку
    @Test
    public void testNegativeValuesThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Pizza("Помилка", -10, -5, -1);
        });
    }

    // Тест: equals та hashCode працюють коректно для піц з однаковим іменем
    @Test
    public void testEqualsAndHashCode() {
        Pizza p1 = new Pizza("Пепероні", 40, 4, 90);
        Pizza p2 = new Pizza("Пепероні", 50, 5, 100);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
