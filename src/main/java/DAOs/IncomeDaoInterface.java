package DAOs;

import DTOs.Income;
import Exceptions.DAOException;

import java.util.List;

public interface IncomeDaoInterface {
    void addIncome(Income income) throws DAOException;

    void deleteIncome(int incomeID) throws DAOException;

    List<Income> getIncomesByMonth(int year, int month) throws DAOException;

    List<Income> getAllIncomes() throws DAOException;
}
