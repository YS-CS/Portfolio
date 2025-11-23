package expensetracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Handles saving and loading expenses from a CSV file
public class ExpenseStorage {
    private Path filePath;
    public ExpenseStorage(Path filePath) {
        this.filePath = filePath;
    }

    //Loads all expenses from the CSV file
    public List<Expense> load() {
        List<Expense> result = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return result;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 5) {
                    continue;
                }
                //makes the arrya be assigned its variables
                int id = Integer.parseInt(parts[0]);
                LocalDate date = LocalDate.parse(parts[1]);
                String category = parts[2];
                String description = parts[3];
                double amount = Double.parseDouble(parts[4]);
                Expense expense = new Expense(id, date, category, description, amount);
                result.add(expense);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

     // saves all expenses to the CSV file
    public void save(List<Expense> expenses) {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            for (Expense expense : expenses) {
                String line = expense.getId()
                        + "," + expense.getDate()
                        + "," + escape(expense.getCategory())
                        + "," + escape(expense.getDescription())
                        + "," + expense.getAmount();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace(",", " ");
    }
}
