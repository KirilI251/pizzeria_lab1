package com.pizzeria;

import java.util.*;

public class CustomerManager {
    private final Map<String, Customer> customers = new HashMap<>(); // ключ — номер телефону

    // Додати нового клієнта
    public void addCustomer(Customer customer) {
        if (customer == null || customer.getPhoneNumber() == null || customer.getPhoneNumber().trim().isEmpty()) {
            throw new IllegalArgumentException("Невалідні дані клієнта.");
        }
        if (customers.containsKey(customer.getPhoneNumber())) {
            throw new IllegalArgumentException("Клієнт з таким номером вже існує.");
        }
        customers.put(customer.getPhoneNumber(), customer);
    }

    // Отримати клієнта за телефоном
    public Optional<Customer> getCustomerByPhone(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(customers.get(phoneNumber));
    }

    // Оновити клієнта
    public void updateCustomer(String phoneNumber, Customer updatedCustomer) {
        if (phoneNumber == null || updatedCustomer == null) {
            throw new IllegalArgumentException("Невалідні дані для оновлення.");
        }
        if (!customers.containsKey(phoneNumber)) {
            throw new NoSuchElementException("Клієнта з таким номером не знайдено.");
        }
        customers.put(phoneNumber, updatedCustomer);
    }

    // Видалити клієнта
    public boolean removeCustomer(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        return customers.remove(phoneNumber) != null;
    }

    // Отримати всіх клієнтів
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }
}
