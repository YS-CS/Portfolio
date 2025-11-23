package expensetracker;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpenseTracker {

    private List<Expense> expenses;
    private ExpenseStorage storage;
    private Scanner scanner;
    private int nextId;


    public ExpenseTracker() {
        Path file = Paths.get("expenses.csv");
        this.storage = new ExpenseStorage(file);
        this.expenses = new ArrayList<>(storage.load());
        this.scanner = new Scanner(System.in);
        this.nextId = findNextId();
    }

    public void run() {
        boolean running = true;
        // when running if it is runnig give the options
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            if (choice.equals("1")) {
                addExpense();
            } else if (choice.equals("2")) {
                listExpenses();
            } else if (choice.equals("3")) {
                showTotal();
            } else if (choice.equals("4")) {
                running = false;
            } else {
                System.out.println("Unknown option");
            }
        }
        storage.save(expenses);
        System.out.println("Goodbye");
    }

    private int findNextId() {
        int max = 0;

        for (Expense e : expenses) {
            if (e.getId() > max) {
                max = e.getId();
            }
        }

        return max + 1;
    }

    // The menu that gives 4 options for the tracker.
    private void printMenu() {
        System.out.println();
        System.out.println("Expense Tracker");
        System.out.println("1) Add expense");
        System.out.println("2) List expenses");
        System.out.println("3) Show total");
        System.out.println("4) Exit");
        System.out.print("Choose an option: ");
    }

    private void addExpense() {
        System.out.print("Date (yyyy-mm-dd): ");
        String dateText = scanner.nextLine().trim();

        LocalDate date;

        try {
            date = LocalDate.parse(dateText);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date");
            return;
        }

        System.out.print("Category: ");
        String category = scanner.nextLine().trim();

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Amount: ");
        String amountText = scanner.nextLine().trim();

        double amount;

        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount");
            return;
        }

        Expense expense = new Expense(nextId, date, category, description, amount);
        expenses.add(expense);
        nextId = nextId + 1;

        System.out.println("Expense added");
    }

    private void listExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses");
            return;
        }

        System.out.println();
        System.out.println("ID  | Date       | Category      | Amount   | Description");
        System.out.println("----+------------+--------------+----------+----------------");

        for (Expense e : expenses) {
            String idText = String.format("%3d", e.getId());
            String dateText = e.getDate().toString();
            String catText = padRight(e.getCategory(), 12);
            String amountText = String.format("%.2f", e.getAmount());
            String descText = e.getDescription();

            System.out.println(idText + " | " + dateText + " | " + catText + " | " + amountText + " | " + descText);
        }
    }

    //Retrieves the total amount of expenses
    private void showTotal() {
        double total = 0;
        for (Expense e : expenses) {
            total = total + e.getAmount();
        }
        System.out.println("Total: " + String.format("%.2f", total));
    }

    private String padRight(String text, int length) {
        if (text == null) {
            text = "";
        }

        if (text.length() >= length) {
            return text.substring(0, length);
        }

        StringBuilder builder = new StringBuilder(text);

        while (builder.length() < length) {
            builder.append(' ');
        }

        return builder.toString();
    }
}
