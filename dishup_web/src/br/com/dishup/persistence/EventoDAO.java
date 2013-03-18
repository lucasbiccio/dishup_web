package br.com.dishup.persistence;

import java.sql.Connection;
import java.util.ArrayList;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.EventoNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableUniqueViolationException;
import br.com.dishup.object.EventoVO;

public interface EventoDAO {

	public void insert(Connection connection, EventoVO eventoVO)
			throws DatabaseException, TableFieldNullValueException,
			TableUniqueViolationException, TableFieldCheckException;

	public EventoVO selectById(Connection connection, int id)
			throws DatabaseException, EventoNotFoundException;

	public ArrayList<EventoVO> selectAllOrderById(Connection connection)
			throws DatabaseException, EmptyTableException;

	public void deleteById(Connection connection, int id)
			throws DatabaseException;
}
