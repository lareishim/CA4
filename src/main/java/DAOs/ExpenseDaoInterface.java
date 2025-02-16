
package DAOs;

import DTOs.Expense;
import Exceptions.DAOException;

import java.util.List;

public interface ExpenseDaoInterface {

    void addExpense(Expense expense) throws DAOException;

    void deleteExpense(int expenseID) throws DAOException;

    List<Expense> getExpensesByMonth(int year, int month) throws DAOException;

    List<Expense> getAllExpenses() throws DAOException;
}
