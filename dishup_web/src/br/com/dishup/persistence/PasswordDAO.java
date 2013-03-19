package br.com.dishup.persistence;

import java.sql.Connection;

import br.com.dishup.exception.AutenticationAlreadyExistException;
import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EncryptException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableFieldTruncationException;
import br.com.dishup.exception.TableForeignKeyViolationException;
import br.com.dishup.object.PasswordVO;

public interface PasswordDAO {

	public void insert(PasswordVO password);

	public PasswordVO selectById(int id);
}
