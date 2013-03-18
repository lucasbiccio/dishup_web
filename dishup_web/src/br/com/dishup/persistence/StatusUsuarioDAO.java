package br.com.dishup.persistence;

import java.sql.Connection;
import java.util.ArrayList;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.StatusUsuarioAlreadyExistException;
import br.com.dishup.exception.StatusUsuarioNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.object.StatusUsuarioVO;

public interface StatusUsuarioDAO {

	public void insert(Connection connection, StatusUsuarioVO statusUsuario)
			throws TableFieldCheckException, TableFieldNullValueException,
			StatusUsuarioAlreadyExistException, DatabaseException;

	public void deleteById(Connection connection, int id)
			throws DatabaseException;

	public StatusUsuarioVO selectById(Connection connection, int id)
			throws StatusUsuarioNotFoundException, DatabaseException;

	public ArrayList<StatusUsuarioVO> selectAllOrderById(Connection connection)
			throws EmptyTableException, DatabaseException;
}
