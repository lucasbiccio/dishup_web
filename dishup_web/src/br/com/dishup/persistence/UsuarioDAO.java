package br.com.dishup.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.StatusUsuarioNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableForeignKeyViolationException;
import br.com.dishup.exception.TipoUsuarioNotFoundException;
import br.com.dishup.exception.UsuarioAlreadyExistException;
import br.com.dishup.exception.UsuarioNotFoundException;
import br.com.dishup.object.UsuarioVO;

/*********************************
 * @author Lucas Biccio Ribeiro
 * @since 27/01/2013
 * @version 1.0 Class responsible for encapsulate the usuario data access. 
 * ******************************
 * @author Lucas Biccio Ribeiro
 * @since 11/03/2013
 * @version 2.0 Attribute name has been removed from insertion and Connection object include as a method큦 parameter.
 ********************************/
public class UsuarioDAO {

	/**********************************************
	 * SQL STATE CODE - POSTGRES - doc: http://www.postgresql.org/docs/8.3/static/errcodes-appendix.html
	 **********************************************/
	private final String SQLSTATE_CODE_23505 = "23505";//Unique Violation
	private final String SQLSTATE_CODE_23514 = "23514";//Check Violation
	private final String SQLSTATE_CODE_23502 = "23502";//Not Null Violation
	private final String SQLSTATE_CODE_23503 = "23503";//Foreign Key Violation
	
	/****************************
	 * Method responsible for insert the user object into database.
	 * The user큦 id is enumarate by database.
	 * @param connection - Database connection
	 * @param usuario - UsuarioVO object
	 * @throws UsuarioAlreadyExistException
	 * @throws TableFieldCheckException
	 * @throws TableFieldNullValueException
	 * @throws DatabaseException
	 * @throws TableForeignKeyViolationException
	 ****************************/
	public void insert(Connection connection, UsuarioVO usuario) throws UsuarioAlreadyExistException, TableFieldCheckException, TableFieldNullValueException, DatabaseException, TableForeignKeyViolationException{
		String sql = "INSERT INTO usuario" +
				"(email_usuario, id_tipo_usuario, id_status_usuario, dt_inclusao_usuario, " +
				"dt_ultima_alteracao_usuario, dt_ultima_alteracao_senha_usuario, flag_solicitacao_alteracao_senha_usuario) " +
				"VALUES (?, ?, ?, current_timestamp, ?, ?, ?);";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, usuario.getEmail());
			stmt.setInt(2, usuario.getTipoUsuario().getIdTipoUsuario());
			stmt.setInt(3, usuario.getStatusUsuario().getIdStatus());
			//parametro da data de inclusao sera gerado pelo banco de dados (current_timestamp)
			if(usuario.getDataUltimaAlteracao() != null)
				stmt.setTimestamp(4, Timestamp.valueOf(usuario.getDataUltimaAlteracao()));
			else
				stmt.setNull(4, Types.NULL);
			if(usuario.getDataUltimaAlteracaoSenha() != null)
				stmt.setTimestamp(5, Timestamp.valueOf(usuario.getDataUltimaAlteracaoSenha()));
			else
				stmt.setNull(5,Types.NULL);
			stmt.setBoolean(6, usuario.isFlagSolicitacaoAlteracaoSenha());
			stmt.execute();
		}catch(SQLException e){
			if(e.getSQLState().equals(SQLSTATE_CODE_23505))
				throw new UsuarioAlreadyExistException("Usuario ja existente no sistema: "+usuario.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23514))
				throw new TableFieldCheckException("Erro na validacao dos campos para insercao (CHECK) - Usuario: "+usuario.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23502))
				throw new TableFieldNullValueException("Algum campo NULO esta sendo inserido em um campo que nao aceita nulo - Usuario: "+usuario.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23503))
				throw new TableForeignKeyViolationException("Violacao de chave estrangeira na insercao - Usuario: "+usuario.toString());
			else
				throw new DatabaseException("TABLE: usuario SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
	
	/*********************************
	 * Method responsible for validate if there is or there is not an user with some email into database
	 * @param connection - database connection
	 * @param email - user큦 e-mail
	 * @return - a boolean flag that indicates id the user큦 there is or there is not into database 
	 * @throws DatabaseException
	 *********************************/
	public boolean isUsuario(Connection connection, String email) throws DatabaseException{
		String sql = "select email_usuario from usuario where email_usuario = ?;";
		PreparedStatement stmt;
		ResultSet rs;
		boolean flag = false;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if(rs.next())
				flag = true;
		}catch(SQLException e){
			throw new DatabaseException("TABLE: usuario SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return flag;
	}
	
	/********************************
	 * Method responsible for return an user큦 data by some e-mail
	 * @param connection - database connection
	 * @param email - user큦 email
	 * @return - {@link UsuarioVO}
	 * @throws UsuarioNotFoundException
	 * @throws DatabaseException
	 * @throws TipoUsuarioNotFoundException
	 * @throws StatusUsuarioNotFoundException
	 *********************************/
	public UsuarioVO selectByEmail(Connection connection, String email) throws UsuarioNotFoundException, DatabaseException, TipoUsuarioNotFoundException, StatusUsuarioNotFoundException{
		String sql = "select id_usuario, email_usuario, id_tipo_usuario, id_status_usuario, dt_inclusao_usuario, dt_ultima_alteracao_usuario, " +
				"dt_ultima_alteracao_senha_usuario, flag_solicitacao_alteracao_senha_usuario from usuario where email_usuario = ?;";
		PreparedStatement stmt;
		ResultSet rs;
		UsuarioVO usuario = new UsuarioVO();
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if(rs.next()){
				usuario = new UsuarioVO(
						rs.getInt(1),  
						rs.getString(2), 
						new TipoUsuarioDAO().selectById(connection,rs.getInt(3)), 
						new StatusUsuarioDAO().selectById(connection,rs.getInt(4)), 
						rs.getString(5), 
						rs.getString(6), 
						rs.getString(7), 
						rs.getBoolean(8));
			}
			else
				throw new UsuarioNotFoundException("Usuario com email: "+email+" nao encontrado.");
		} catch (SQLException e){
			throw new DatabaseException("TABLE: usuario SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return usuario;
	}
	
	public void update(UsuarioVO usuario){
		//TODO ATUALIZAR USUARIO
	}
}
