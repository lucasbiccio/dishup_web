package br.com.dishup.persistence;

import java.sql.Connection;
import java.util.ArrayList;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TipoUsuarioAlreadyExistException;
import br.com.dishup.exception.TipoUsuarioNotFoundException;
import br.com.dishup.object.TipoUsuarioVO;

public interface TipoUsuarioDAO {
	
	
	public void insert(Connection connection, TipoUsuarioVO tipoUsuario) throws TipoUsuarioAlreadyExistException, TableFieldCheckException, TableFieldNullValueException, DatabaseException;
	
	public TipoUsuarioVO selectById(Connection connection, int id) throws TipoUsuarioNotFoundException, DatabaseException;
	
	public ArrayList<TipoUsuarioVO> selectAllOrderById(Connection connection) throws EmptyTableException, DatabaseException;
	
	public void deleteById(Connection connection, int id) throws DatabaseException;
}
