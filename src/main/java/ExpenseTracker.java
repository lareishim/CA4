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
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        List<Expense> expenses = expenseDAO.getAllExpenses();
                        for (Expense exp : expenses) {
                            System.out.println(exp);
                        }
                        break;
                    case 2:
                        // Code to add an expense
                        break;
                    case 3:
                        // Code to delete an expense
                        break;
                    case 4:
                        List<Income> incomes = incomeDAO.getAllIncomes();
                        for (Income inc : incomes) {
                            System.out.println(inc);
                        }
                        break;
                    case 5:
                        System.out.print("Enter income title: ");
                        String incTitle = sc.nextLine();
                        System.out.print("Enter income amount: ");
                        double incAmount = sc.nextDouble();
                        sc.nextLine(); // Consume newline
                        Income newIncome = new Income(0, incTitle, incAmount, new Date());
                        incomeDAO.addIncome(newIncome);
                        System.out.println("Income added successfully!");
                        break;
                    case 6:
                        System.out.print("Enter income ID to delete: ");
                        int incomeID = sc.nextInt();
                        incomeDAO.deleteIncome(incomeID);
                        System.out.println("Income deleted successfully!");
                        break;
                    case 7:
                        // Code to list income and expenses for a particular month
                        break;
                    case 8:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
            sc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
