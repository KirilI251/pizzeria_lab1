package com.pizzeria;

public class Pizza {
    private String name; // Назва піци
    private int costPrice; // Собівартість
    private int preparationTime; // Час приготування (в хвилинах)
    private int sellingPrice; // Ціна продажу
    private int availableQuantity; // Кількість на складі
    private int soldQuantity; // Кількість проданих піц

    public Pizza() {
    }

    // Конструктор з перевіркою на коректність значень
    public Pizza(String name, int costPrice, int preparationTime, int sellingPrice) {
        if (costPrice < 0 || sellingPrice < 0 || preparationTime < 0) {
            throw new IllegalArgumentException("Значення ціни та часу не можуть бути від'ємними!");
        }
        if (sellingPrice < costPrice) {
            throw new IllegalArgumentException("Ціна продажу не може бути меншою за собівартість!");
        }
        this.name = name;
        this.costPrice = costPrice;
        this.preparationTime = preparationTime;
        this.availableQuantity = 0;
        this.soldQuantity = 0;
        this.sellingPrice = sellingPrice;
    }

    // Геттери
    public String getName() {
        return name;
    }

    public int getCostPrice() {
        return costPrice;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    // Сеттери з перевірками
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва не може бути порожньою!");
        }
        this.name = name.trim();
    }

    public void setCostPrice(int costPrice) {
        if (costPrice < 0) {
            throw new IllegalArgumentException("Собівартість не може бути від'ємною!");
        }
        if (costPrice > sellingPrice) {
            throw new IllegalArgumentException("Собівартість не може бути більше ціни продажу!");
        }
        this.costPrice = costPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        if (sellingPrice < 0) {
            throw new IllegalArgumentException("Ціна продажу не може бути від'ємною!");
        }
        if (sellingPrice < costPrice) {
            throw new IllegalArgumentException("Ціна продажу не може бути меншою за собівартість!");
        }
        this.sellingPrice = sellingPrice;
    }

    public void setPreparationTime(int preparationTime) {
        if (preparationTime < 0) {
            throw new IllegalArgumentException("Час приготування не може бути від'ємним!");
        }
        this.preparationTime = preparationTime;
    }

    // Зменшити кількість доступних піц і збільшити лічильник проданих
    public void reduceAvailableQuantity(int count) {
        if (availableQuantity >= count) {
            availableQuantity -= count;
            soldQuantity += count;
        } else {
            throw new IllegalStateException("Недостатньо піц на складі.");
        }
    }

    // Поповнення складу
    public void restock(int count) {
        if (count > 0) {
            availableQuantity += count;
        } else {
            throw new IllegalArgumentException("Кількість має бути більшою за нуль.");
        }
    }

    // Обнулити склад (після редагування або видалення)
    public void resetStock() {
        this.availableQuantity = 0;
    }

    // Метод порівняння піц по імені (ігноруючи регістр)
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pizza pizza = (Pizza) o;
        return name.equalsIgnoreCase(pizza.name);
    }

    // Генерація хеш-коду на основі назви
    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }

    // Представлення піци у вигляді рядка
    @Override
    public String toString() {
        return "Pizza{name='" + name + "', costPrice=" + costPrice +
                ", preparationTime=" + preparationTime + ", sellingPrice=" + sellingPrice + "}";
    }
}
