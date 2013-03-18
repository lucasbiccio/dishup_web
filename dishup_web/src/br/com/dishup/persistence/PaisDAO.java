package br.com.dishup.persistence;

import java.sql.Connection;
import java.util.ArrayList;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.PaisAlreadyExistException;
import br.com.dishup.exception.PaisNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableFieldTruncationException;
import br.com.dishup.object.PaisVO;

public interface PaisDAO {
	
	public void insert(Connection connection, PaisVO pais) throws TableFieldTruncationException, PaisAlreadyExistException, TableFieldNullValueException, TableFieldCheckException, DatabaseException;
	
	public PaisVO selectBySigla(Connection connection, String sigla) throws PaisNotFoundException, DatabaseException;
	
	public PaisVO selectById(Connection connection, int id) throws PaisNotFoundException, DatabaseException;
	
	public ArrayList<PaisVO> selectAllOrderById(Connection connection) throws EmptyTableException, DatabaseException;
	
	public void deleteById(Connection connection, int id) throws DatabaseException;
}
