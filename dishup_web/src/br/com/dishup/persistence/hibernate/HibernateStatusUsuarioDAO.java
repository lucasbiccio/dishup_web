package br.com.dishup.persistence.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.StatusUsuarioAlreadyExistException;
import br.com.dishup.exception.StatusUsuarioNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.object.StatusUsuarioVO;

public class HibernateStatusUsuarioDAO {
	
	/**********************************************
	 * SQL STATE CODE - POSTGRES - doc: http://www.postgresql.org/docs/8.3/static/errcodes-appendix.html
	 **********************************************/
	private final String SQLSTATE_CODE_23505 = "23505";//Unique Violation
	private final String SQLSTATE_CODE_23514 = "23514";//Check Violation
	private final String SQLSTATE_CODE_23502 = "23502";//Not Null Violation
	
	public void insert(Connection connection, StatusUsuarioVO statusUsuario) throws TableFieldCheckException, TableFieldNullValueException, StatusUsuarioAlreadyExistException, DatabaseException{
		String sql = "INSERT INTO status_usuario (id_status_usuario, nm_status_usuario, desc_status_usuario) VALUES (?, ?, ?);";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, statusUsuario.getIdStatus());
			stmt.setString(2, statusUsuario.getNomeStatus());
			stmt.setString(3, statusUsuario.getDescStatus());
			stmt.execute();
		}catch(SQLException e){
			if(e.getSQLState().equals(SQLSTATE_CODE_23505)){
				throw new StatusUsuarioAlreadyExistException("Status Usuario ja existe no sistema: "+statusUsuario.toString());
			}else if(e.getSQLState().equals(SQLSTATE_CODE_23514)){
				throw new TableFieldCheckException("Violacao de CHECK em algum campo na insercao: "+statusUsuario.toString());
			}else if(e.getSQLState().equals(SQLSTATE_CODE_23502)){
				throw new TableFieldNullValueException("Violacao em algum campo na insercao com valores NULOS: "+statusUsuario.toString());
			}
			else
				throw new DatabaseException("TABLE: status_usuario SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
	
	public void deleteById(Connection connection, int id) throws DatabaseException{
		String sql = "DELETE FROM status_usuario WHERE id_status_usuario = ?;";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		}catch(SQLException e){
			throw new DatabaseException("TABLE: status_usuario SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
	
	public StatusUsuarioVO selectById(Connection connection, int id) throws StatusUsuarioNotFoundException, DatabaseException{
		String sql = "SELECT id_status_usuario, nm_status_usuario, desc_status_usuario FROM status_usuario WHERE id_status_usuario = ?;";
		PreparedStatement stmt;
		StatusUsuarioVO statusUsuario = new StatusUsuarioVO();
		ResultSet rs;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				statusUsuario = new StatusUsuarioVO(rs.getInt(1), rs.getString(2), rs.getString(3));
			else
				throw new StatusUsuarioNotFoundException("Status Usuario com ID = "+id+" nao encontrado no sistema.");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: status_usuario SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return statusUsuario;
	}
	
	public ArrayList<StatusUsuarioVO> selectAllOrderById(Connection connection) throws EmptyTableException, DatabaseException{
		String sql = "SELECT id_status_usuario, nm_status_usuario, desc_status_usuario FROM status_usuario ORDER BY id_status_usuario";
		PreparedStatement stmt;
		StatusUsuarioVO statusUsuario = new StatusUsuarioVO();
		ArrayList<StatusUsuarioVO> list = new ArrayList<StatusUsuarioVO>();
		ResultSet rs;
		try{
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				statusUsuario = new StatusUsuarioVO(rs.getInt(1), rs.getString(2), rs.getString(3));
				list.add(statusUsuario);
			}
			if(list.isEmpty() == true)
				throw new EmptyTableException("Tabela Status Usuario esta vazia.");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: status_usuario SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return list;
	}
}
