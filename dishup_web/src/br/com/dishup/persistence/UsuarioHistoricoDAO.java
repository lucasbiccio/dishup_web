package br.com.dishup.persistence;

import java.sql.Connection;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.object.EventoVO;
import br.com.dishup.object.UsuarioVO;

public interface UsuarioHistoricoDAO {

	public void insert(Connection connection, UsuarioVO usuarioVO,
			EventoVO eventoVO) throws DatabaseException,
			TableFieldCheckException, TableFieldNullValueException;
}
