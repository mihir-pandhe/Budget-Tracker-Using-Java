import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Expense {
    String description;
    double amount;

    Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return description + ": $" + amount;
    }
}

class BudgetTracker {
    private List<Expense> expenses;

    BudgetTracker() {
        expenses = new ArrayList<>();
    }

    void addExpense(String description, double amount) {
        expenses.add(new Expense(description, amount));
    }

    void viewExpenses() {
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    public static void main(String[] args) {
        BudgetTracker tracker = new BudgetTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter description: ");
                String description = scanner.nextLine();
                System.out.print("Enter amount: ");
                double amount = scanner.nextDouble();
                tracker.addExpense(description, amount);
                System.out.println("Expense added.");
            } else if (choice == 2) {
                tracker.viewExpenses();
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }

        scanner.close();
    }
}
