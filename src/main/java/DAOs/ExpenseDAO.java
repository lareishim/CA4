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