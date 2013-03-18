package br.com.dishup.persistence;

import java.sql.Connection;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.StatusUsuarioNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableForeignKeyViolationException;
import br.com.dishup.exception.TipoUsuarioNotFoundException;
import br.com.dishup.exception.UsuarioAlreadyExistException;
import br.com.dishup.exception.UsuarioNotFoundException;
import br.com.dishup.object.UsuarioVO;

public interface UsuarioDAO {

	public void insert(Connection connection, UsuarioVO usuario) throws UsuarioAlreadyExistException, TableFieldCheckException, TableFieldNullValueException, DatabaseException, TableForeignKeyViolationException;
	
	public boolean isUsuario(Connection connection, String email) throws DatabaseException;
	
	public UsuarioVO selectByEmail(Connection connection, String email) throws UsuarioNotFoundException, DatabaseException, TipoUsuarioNotFoundException, StatusUsuarioNotFoundException;	
	
	public void update(UsuarioVO usuario);
}
