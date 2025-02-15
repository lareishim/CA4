
package DAOs;

import DTOs.Expense;
import Exceptions.DAOException;

import java.util.List;

public interface ExpenseDaoInterface {

    List<Expense> getAllExpenses() throws DAOException;
}
