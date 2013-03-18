package br.com.dishup.persistence;

import java.sql.Connection;

import br.com.dishup.exception.CanalNotFoundException;
import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableForeignKeyViolationException;
import br.com.dishup.exception.TableUniqueViolationException;
import br.com.dishup.object.CanalVO;
import br.com.dishup.object.UsuarioVO;

public interface LogAutenticationDAO {

	public void insert(Connection connection, UsuarioVO usuarioVO,
			CanalVO canalVO) throws DatabaseException, CanalNotFoundException,
			TableFieldNullValueException, TableForeignKeyViolationException,
			TableUniqueViolationException;
}
