package br.com.dishup.persistence.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.dishup.exception.CanalNotFoundException;
import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableForeignKeyViolationException;
import br.com.dishup.exception.TableUniqueViolationException;
import br.com.dishup.object.CanalVO;
import br.com.dishup.object.UsuarioVO;
import br.com.dishup.persistence.hibernate.sql.CanalDAO;

public class HibernateLogAutenticationDAO {
	
	/**********************************************
	 * SQL STATE CODE - POSTGRES - doc: http://www.postgresql.org/docs/8.3/static/errcodes-appendix.html
	 **********************************************/
	private final String SQLSTATE_CODE_23505 = "23505";//Unique Violation
	private final String SQLSTATE_CODE_23502 = "23502";//Not Null Violation
	private final String SQLSTATE_CODE_23503 = "23503";//Foreign Key Violation

	public void insert(Connection connection, UsuarioVO usuarioVO, CanalVO canalVO) throws DatabaseException, CanalNotFoundException, TableFieldNullValueException, TableForeignKeyViolationException, TableUniqueViolationException{
		String sql = "INSERT INTO log_autenticacao ( id_usuario, dt_autenticacao, id_canal) VALUES (?, current_timestamp, ?);";
		CanalDAO canalDAO = new CanalDAO();
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, usuarioVO.getId());
			stmt.setInt(2, canalDAO.selectById(connection, canalVO.getId()).getId());
			stmt.execute();
		}catch(SQLException e){
			if(e.getSQLState().equals(SQLSTATE_CODE_23502))
				throw new TableFieldNullValueException("Tabela nao aceita campos nulo.");
			else if(e.getSQLState().equals(SQLSTATE_CODE_23503))
				throw new TableForeignKeyViolationException("Violacao de chave estrangeira.");
			else if(e.getSQLState().equals(SQLSTATE_CODE_23505))
				throw new TableUniqueViolationException("Violacao na insercao do historico por chave primaria duplicada.");
			else
				throw new DatabaseException("TABLE log_autenticacao SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
}
