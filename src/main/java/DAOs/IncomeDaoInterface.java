package DAOs;

import DTOs.Income;
import Exceptions.DAOException;

import java.util.List;

public interface IncomeDaoInterface {
    List<Income> getAllIncomes() throws DAOException;
}
