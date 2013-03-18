package br.com.dishup.persistence.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.dishup.exception.AutenticationAlreadyExistException;
import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EncryptException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableFieldTruncationException;
import br.com.dishup.exception.TableForeignKeyViolationException;
import br.com.dishup.object.PasswordVO;

public class HibernatePasswordDAO {

	private final String SQLSTATE_CODE_22001 = "22001";//String Data Right Truncation
	private final String SQLSTATE_CODE_23505 = "23505";//Unique Violation
	private final String SQLSTATE_CODE_23502 = "23502";//Not Null Violation
	private final String SQLSTATE_CODE_23514 = "23514";//Check Violation
	private final String SQLSTATE_CODE_23503 = "23503";//Foreign Key Violation
	
	public void insert(Connection connection, PasswordVO password) throws DatabaseException, AutenticationAlreadyExistException, TableFieldNullValueException, TableFieldCheckException, TableForeignKeyViolationException, TableFieldTruncationException{
		String sql = "INSERT INTO autenticacao_usuario (id_usuario ,assinatura_usuario) VALUES (?,?);";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, password.getUsuarioVO());
			stmt.setString(2, password.getAssinatura());
			stmt.execute();
		}catch(SQLException e){
			if(e.getSQLState().equals(SQLSTATE_CODE_23505))
				throw new AutenticationAlreadyExistException("Autenticacao para o ID: "+password.getUsuarioVO()+" ja existe no sistema");
			else if(e.getSQLState().equals(SQLSTATE_CODE_23502))
				throw new TableFieldNullValueException("Tabela nao aceita campos nulos");
			else if(e.getSQLState().equals(SQLSTATE_CODE_23514))
				throw new TableFieldCheckException("Algum campo está violando a regra (CHECK) da tabela");
			else if(e.getSQLState().equals(SQLSTATE_CODE_23503))
				throw new TableForeignKeyViolationException("Violacao de foreign key");
			else if(e.getSQLState().equals(SQLSTATE_CODE_22001))
				throw new TableFieldTruncationException("Algum campo esta excedendo o tamanho definido na tabela");
			else
				throw new DatabaseException("TABLE: autenticacao_usuario SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}

	public PasswordVO selectById(Connection connection, int id) throws DatabaseException, EncryptException{
		PasswordVO password = new PasswordVO();
		String sql = "SELECT id_usuario, assinatura_usuario FROM autenticacao_usuario WHERE id_usuario = ?;";
		PreparedStatement stmt;
		ResultSet rs;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				password = new PasswordVO(rs.getInt(1), rs.getString(2));
			else
				throw new EncryptException("Assinatura nao encontrada para o usuario informado");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: autenticacao_usuario SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return password;
	}
}
