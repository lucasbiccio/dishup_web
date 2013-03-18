package br.com.dishup.persistence.hibernate.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TipoUsuarioAlreadyExistException;
import br.com.dishup.exception.TipoUsuarioNotFoundException;
import br.com.dishup.object.TipoUsuarioVO;

public class TipoUsuarioDAO {
	
	/**********************************************
	 * SQL STATE CODE - POSTGRES - doc: http://www.postgresql.org/docs/8.3/static/errcodes-appendix.html
	 **********************************************/
	private final String SQLSTATE_CODE_23505 = "23505";//Unique Violation
	private final String SQLSTATE_CODE_23514 = "23514";//Check Violation
	private final String SQLSTATE_CODE_23502 = "23502";//Not Null Violation
	
	public void insert(Connection connection, TipoUsuarioVO tipoUsuario) throws TipoUsuarioAlreadyExistException, TableFieldCheckException, TableFieldNullValueException, DatabaseException{
		String sql = "INSERT INTO tipo_usuario(id_tipo_usuario, nm_tipo_usuario, desc_tipo_usuario)VALUES (?, ?, ?);";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, tipoUsuario.getIdTipoUsuario());
			stmt.setString(2,tipoUsuario.getNomeTipoUsuario());
			stmt.setString(3, tipoUsuario.getDescTipoUsuario());
			stmt.execute();
		}catch(SQLException e){
			if(e.getSQLState().equals(SQLSTATE_CODE_23505)){
				throw new TipoUsuarioAlreadyExistException("TipoUsuario nao encontrado no sistema: "+tipoUsuario.toString());
			}else if(e.getSQLState().equals(SQLSTATE_CODE_23514)){
				throw new TableFieldCheckException("Violacao de CHECK em algum campo na insercao: "+tipoUsuario.toString());
			}else if(e.getSQLState().equals(SQLSTATE_CODE_23502)){
				throw new TableFieldNullValueException("Violacao em algum campo na insercao com valores NULOS: "+tipoUsuario.toString());
			}
			else
				throw new DatabaseException("TABLE: tipo_usuario SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
	
	public TipoUsuarioVO selectById(Connection connection, int id) throws TipoUsuarioNotFoundException, DatabaseException{
		String sql = "SELECT id_tipo_usuario, nm_tipo_usuario, desc_tipo_usuario FROM tipo_usuario WHERE id_tipo_usuario = ?;";
		PreparedStatement stmt;
		ResultSet rs;
		TipoUsuarioVO tipoUsuario = new TipoUsuarioVO();
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next()){
				tipoUsuario = new TipoUsuarioVO(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
			else
				throw new TipoUsuarioNotFoundException("TipoUsuario com ID: "+id+" nao encontrado!");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: tipo_usuario SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return tipoUsuario;
	}
	
	public ArrayList<TipoUsuarioVO> selectAllOrderById(Connection connection) throws EmptyTableException, DatabaseException{
		String sql = "SELECT id_tipo_usuario, nm_tipo_usuario, desc_tipo_usuario FROM tipo_usuario ORDER BY id_tipo_usuario;";
		ArrayList<TipoUsuarioVO> list = new ArrayList<TipoUsuarioVO>();
		TipoUsuarioVO tipoUsuario = new TipoUsuarioVO();
		PreparedStatement stmt;
		ResultSet rs;
		try{
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				tipoUsuario = new TipoUsuarioVO(rs.getInt(1), rs.getString(2), rs.getString(3));
				list.add(tipoUsuario);
			}
			if(list.isEmpty())
				throw new EmptyTableException("Tabela TipoUsuario esta vazia");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: tipo_usuario SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return list;
	}
	
	public void deleteById(Connection connection, int id) throws DatabaseException{
		String sql = "DELETE FROM tipo_usuario WHERE id_tipo_usuario = ?;";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		}catch(SQLException e){
			throw new DatabaseException("TABLE: tipo_usuario SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
}
