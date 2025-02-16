package DAOs;

import DTOs.Income;
import Exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IncomeDAO extends MySQLDao implements IncomeDaoInterface {

    @Override
    public void addIncome(Income income) throws DAOException {
        String query = "INSERT INTO income (title, amount, dateEarned) VALUES (?, ?, ?)";
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, income.getTitle());
            statement.setDouble(2, income.getAmount());
            statement.setDate(3, new java.sql.Date(income.getDateEarned().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error adding income: " + e.getMessage());
        }
    }

    @Override
    public void deleteIncome(int incomeID) throws DAOException {
        String query = "DELETE FROM income WHERE incomeID = ?";
        try (Connection connection = this.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, incomeID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting income: " + e.getMessage());
        }
    }

    @Override
    public List<Income> getAllIncomes() throws DAOException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Income> IncomesList = new ArrayList<>();

        try {
            connection = this.getConnection();
            String query = "SELECT * FROM income";
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int expenseID = resultSet.getInt("incomeID");
                String title = resultSet.getString("title");
                double amount = resultSet.getDouble("amount");
                Date dateIncurred = resultSet.getDate("dateEarned");

                Income income = new Income(expenseID, title, amount, dateIncurred);
                IncomesList.add(income);
            }
        } catch (SQLException e) {
            throw new DAOException("Error retrieving incomes" + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new DAOException("Error closing resources" + e.getMessage());
            }
        }

        return IncomesList;
    }

}