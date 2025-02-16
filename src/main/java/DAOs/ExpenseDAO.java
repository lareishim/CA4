package DAOs;

import DTOs.Expense;
import Exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpenseDAO extends MySQLDao implements ExpenseDaoInterface {


    @Override
    public void addExpense(Expense expense) throws DAOException {
        String query = "INSERT INTO expenses (title, category, amount, dateIncurred) VALUES (?, ?, ?, ?)";
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, expense.getTitle());
            statement.setString(2, expense.getCategory());
            statement.setDouble(3, expense.getAmount());
            statement.setDate(4, new java.sql.Date(expense.getDateIncurred().getTime()));

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error adding expense: " + e.getMessage());
        }
    }

    @Override
    public void deleteExpense(int expenseID) throws DAOException {
        String query = "DELETE FROM expenses WHERE expenseID = ?";
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, expenseID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting expense: " + e.getMessage());
        }
    }

    @Override
    public List<Expense> getExpensesByMonth(int year, int month) throws DAOException {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT * FROM expenses WHERE YEAR(dateIncurred) = ? AND MONTH(dateIncurred) = ?";

        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, year);
            statement.setInt(2, month);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int expenseID = resultSet.getInt("expenseID");
                String title = resultSet.getString("title");
                String category = resultSet.getString("category");
                double amount = resultSet.getDouble("amount");
                Date dateIncurred = resultSet.getDate("dateIncurred");

                Expense expense = new Expense(expenseID, title, category, amount, dateIncurred);
                expenses.add(expense);
            }
        } catch (SQLException e) {
            throw new DAOException("Error retrieving expenses for month: " + e.getMessage());
        }
        return expenses;
    }

    @Override
    public List<Expense> getAllExpenses() throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Expense> ExpensesList = new ArrayList<>();

        try {
            connection = this.getConnection();
            String query = "SELECT * FROM expenses";
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int expenseID = resultSet.getInt("expenseID");
                String title = resultSet.getString("title");
                String category = resultSet.getString("category");
                double amount = resultSet.getDouble("amount");
                Date dateIncurred = resultSet.getDate("dateIncurred");

                Expense expense = new Expense(expenseID, title, category, amount, dateIncurred);
                ExpensesList.add(expense);
            }
        } catch (SQLException e) {
            throw new DAOException("Error retrieving expenses" + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new DAOException("Error closing resources" + e.getMessage());
            }
        }

        return ExpensesList;
    }

}