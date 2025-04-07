package com.pizzeria;

import java.util.Random;

public class Customer {
    private String name; // Ім'я клієнта
    private String phoneNumber; // Номер телефону
    private int tips; // Чайові

    // Конструктор клієнта
    public Customer(String name, String phoneNumber, int tips) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.tips = tips;
    }

    // Генерація випадкового клієнта
    public static Customer generateRandom() {
        String[] names = { "Олег", "Марина", "Артем", "Ірина", "Дмитро", "Катерина" };
        Random rand = new Random();
        String name = names[rand.nextInt(names.length)];
        String phone = "+380" + (100000000 + rand.nextInt(900000000)); // випадковий номер телефону
        int tips = rand.nextInt(51); // до 50 грн чайових
        return new Customer(name, phone, tips);
    }

    // Отримати ім’я клієнта
    public String getName() {
        return name;
    }

    // Отримати номер телефону
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Отримати чайові
    public int getTips() {
        return tips;
    }

    // Порівняння клієнтів за номером телефону
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Customer customer = (Customer) o;
        return phoneNumber.equals(customer.phoneNumber);
    }

    @Override
    public int hashCode() {
        return phoneNumber.hashCode();
    }
}
