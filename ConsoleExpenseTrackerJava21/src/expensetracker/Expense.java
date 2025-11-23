package expensetracker;

import java.time.LocalDate;

// Represents a single expense entry
public class Expense {

    private int id;
    private LocalDate date;
    private String category;
    private String description;
    private double amount;

    // basic constructor for an expense.
    public Expense(int id, LocalDate date, String category, String description, double amount) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.description = description;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
