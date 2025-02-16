import DAOs.ExpenseDAO;
import DAOs.IncomeDAO;
import DTOs.Expense;
import DTOs.Income;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ExpenseTracker {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ExpenseTracker", "root", "")) {
            IncomeDAO incomeDAO = new IncomeDAO();
            ExpenseDAO expenseDAO = new ExpenseDAO();
            Scanner sc = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\n1. List All Expenses");
                System.out.println("2. Add Expense");
                System.out.println("3. Delete Expense");
                System.out.println("4. List All Income");
                System.out.println("5. Add Income");
                System.out.println("6. Delete Income");
                System.out.println("7. List Income & Expenses for a Month");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");

                int choice = getValidInteger(sc);

                switch (choice) {
                    case 1:
                        List<Expense> expenses = expenseDAO.getAllExpenses();
                        if (expenses.isEmpty()) {
                            System.out.println("No expenses found.");
                        } else {
                            for (Expense exp : expenses) {
                                System.out.println(exp);
                            }
                        }
                        break;
                    case 2:
                        sc.nextLine();
                        System.out.print("Enter expense title: ");
                        String expTitle = getValidString(sc);

                        System.out.print("Enter expense category: ");
                        String expCategory = getValidString(sc);

                        System.out.print("Enter expense amount: ");
                        double expAmount = getValidDouble(sc);

                        Expense newExpense = new Expense(0, expTitle, expCategory, expAmount, new Date());
                        expenseDAO.addExpense(newExpense);
                        System.out.println("Expense added successfully!");
                        break;

                    case 3:
                        System.out.print("Enter expense ID to delete: ");
                        int expenseID = getValidInteger(sc);

                        expenseDAO.deleteExpense(expenseID);
                        System.out.println("Expense deleted successfully!");
                        break;

                    case 4:
                        List<Income> incomes = incomeDAO.getAllIncomes();
                        if (incomes.isEmpty()) {
                            System.out.println("No income records found.");
                        } else {
                            for (Income inc : incomes) {
                                System.out.println(inc);
                            }
                        }
                        break;

                    case 5:
                        sc.nextLine();
                        System.out.print("Enter income title: ");
                        String incTitle = getValidString(sc);

                        System.out.print("Enter income amount: ");
                        double incAmount = getValidDouble(sc);

                        Income newIncome = new Income(0, incTitle, incAmount, new Date());
                        incomeDAO.addIncome(newIncome);
                        System.out.println("Income added successfully!");
                        break;

                    case 6:
                        System.out.print("Enter income ID to delete: ");
                        int incomeID = getValidInteger(sc);

                        incomeDAO.deleteIncome(incomeID);
                        System.out.println("Income deleted successfully!");
                        break;

                    case 7:
                        System.out.print("Enter year (YYYY): ");
                        int year = getValidInteger(sc, 2000, 2100);

                        System.out.print("Enter month (1-12): ");
                        int month = getValidInteger(sc, 1, 12);

                        System.out.println("\n--- Income for " + year + "-" + month + " ---");
                        List<Income> monthlyIncome = incomeDAO.getIncomesByMonth(year, month);
                        if (monthlyIncome.isEmpty()) {
                            System.out.println("No income recorded for this month.");
                        } else {
                            for (Income inc : monthlyIncome) {
                                System.out.println(inc);
                            }
                        }

                        System.out.println("\n--- Expenses for " + year + "-" + month + " ---");
                        List<Expense> monthlyExpenses = expenseDAO.getExpensesByMonth(year, month);
                        if (monthlyExpenses.isEmpty()) {
                            System.out.println("No expenses recorded for this month.");
                        } else {
                            for (Expense exp : monthlyExpenses) {
                                System.out.println(exp);
                            }
                        }
                        break;

                    case 8:
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                }
            }
            sc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //  Gets a valid integer input from the user.

    private static int getValidInteger(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input. Please enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }


    // Gets a valid integer input with a range constraint.

    private static int getValidInteger(Scanner sc, int min, int max) {
        int num;
        do {
            num = getValidInteger(sc);
            if (num < min || num > max) {
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            }
        } while (num < min || num > max);
        return num;
    }


    // Gets a valid double input from the user.

    private static double getValidDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a valid decimal number: ");
            sc.next();
        }
        return sc.nextDouble();
    }


    // Gets a valid string input from the user (non-empty).

    private static String getValidString(Scanner sc) {
        String input;
        do {
            input = sc.nextLine().trim();
            if (input.isEmpty() || !input.matches("^[a-zA-Z]+$")) {
                System.out.print("Invalid input. Please enter letters only: ");
            }
        } while (input.isEmpty() || !input.matches("^[a-zA-Z]+$"));
        return input;
    }

}
