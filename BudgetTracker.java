import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    String description;
    double amount;
    String category;
    LocalDate date;

    Transaction(String description, double amount, String category, LocalDate date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " - " + category + " - " + description + ": $"
                + amount;
    }
}

class BudgetTracker {
    private List<Transaction> transactions;
    private static final String FILE_NAME = "transactions.txt";

    BudgetTracker() {
        transactions = new ArrayList<>();
        loadTransactions();
    }

    void addTransaction(String description, double amount, String category, LocalDate date) {
        transactions.add(new Transaction(description, amount, category, date));
        saveTransactions();
    }

    void viewTransactions() {
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    double calculateBalance() {
        double balance = 0;
        for (Transaction transaction : transactions) {
            balance += transaction.amount;
        }
        return balance;
    }

    void viewMonthlyReport(int year, int month) {
        System.out.println("Monthly Report for " + year + "-" + (month < 10 ? "0" : "") + month);
        double income = 0;
        double expenses = 0;
        for (Transaction transaction : transactions) {
            if (transaction.date.getYear() == year && transaction.date.getMonthValue() == month) {
                if (transaction.amount > 0) {
                    income += transaction.amount;
                } else {
                    expenses += transaction.amount;
                }
            }
        }
        System.out.println("Total Income: $" + income);
        System.out.println("Total Expenses: $" + expenses);
        System.out.println("Net Balance: $" + (income + expenses));
    }

    private void saveTransactions() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.description + "," + transaction.amount + "," + transaction.category + ","
                        + transaction.date);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    private void loadTransactions() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String description = parts[0];
                double amount = Double.parseDouble(parts[1]);
                String category = parts[2];
                LocalDate date = LocalDate.parse(parts[3]);
                transactions.add(new Transaction(description, amount, category, date));
            }
        } catch (IOException | DateTimeParseException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BudgetTracker tracker = new BudgetTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Expense");
            System.out.println("2. Add Income");
            System.out.println("3. View Transactions");
            System.out.println("4. View Balance");
            System.out.println("5. View Monthly Report");
            System.out.println("6. Exit");
            int choice = 0;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please enter a number.");
                continue;
            }

            if (choice == 1) {
                try {
                    System.out.print("Enter expense description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter expense amount: ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter expense category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter date (yyyy-mm-dd): ");
                    String dateString = scanner.nextLine();
                    LocalDate date = LocalDate.parse(dateString);
                    tracker.addTransaction(description, -amount, category, date);
                    System.out.println("Expense added.");
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use yyyy-mm-dd.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount. Please enter a numeric value.");
                }
            } else if (choice == 2) {
                try {
                    System.out.print("Enter income description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter income amount: ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter income category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter date (yyyy-mm-dd): ");
                    String dateString = scanner.nextLine();
                    LocalDate date = LocalDate.parse(dateString);
                    tracker.addTransaction(description, amount, category, date);
                    System.out.println("Income added.");
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use yyyy-mm-dd.");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount. Please enter a numeric value.");
                }
            } else if (choice == 3) {
                tracker.viewTransactions();
            } else if (choice == 4) {
                double balance = tracker.calculateBalance();
                System.out.println("Current balance: $" + balance);
            } else if (choice == 5) {
                try {
                    System.out.print("Enter year: ");
                    int year = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter month: ");
                    int month = Integer.parseInt(scanner.nextLine());
                    tracker.viewMonthlyReport(year, month);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter numeric values for year and month.");
                }
            } else if (choice == 6) {
                break;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }

        scanner.close();
    }
}
