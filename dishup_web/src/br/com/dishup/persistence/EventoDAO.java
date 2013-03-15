package br.com.dishup.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.EventoNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableUniqueViolationException;
import br.com.dishup.object.EventoVO;

public class EventoDAO {

	/**********************************************
	 * SQL STATE CODE - POSTGRES - doc: http://www.postgresql.org/docs/8.3/static/errcodes-appendix.html
	 **********************************************/
	private final String SQLSTATE_CODE_23505 = "23505";//Unique Violation
	private final String SQLSTATE_CODE_23502 = "23502";//Not Null Violation
	private final String SQLSTATE_CODE_23514 = "23514";//Check Violation
	
	public void insert(Connection connection , EventoVO eventoVO) throws DatabaseException, TableFieldNullValueException, TableUniqueViolationException, TableFieldCheckException{
		String sql = "INSERT INTO evento(id_evento, nm_evento, desc_evento) VALUES (?, ?, ?);";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, eventoVO.getId());
			stmt.setString(2, eventoVO.getNome());
			stmt.setString(3, eventoVO.getDescricao());
			stmt.execute();
		}catch(SQLException e){
			if(e.getSQLState().equals(SQLSTATE_CODE_23502))
				throw new TableFieldNullValueException("Objeto para insercao nao pode ter valores nulos: "+eventoVO.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23505))
				throw new TableUniqueViolationException("Objeto para insercao ja existe no sistema: "+eventoVO.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23514))
				throw new TableFieldCheckException("Alguma violacao (CHECK) foi capturada durante a insercao: "+eventoVO.toString());
			else
				throw new DatabaseException("TABLE: evento SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
	
	public EventoVO selectById(Connection connection , int id) throws DatabaseException, EventoNotFoundException{
		String sql = "SELECT id_evento, nm_evento, desc_evento FROM evento WHERE id_evento = ?;";
		PreparedStatement stmt;
		ResultSet rs;
		EventoVO eventoVO = new EventoVO();
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
			 eventoVO = new EventoVO(rs.getInt(1), rs.getString(2), rs.getString(3));
			else
				throw new EventoNotFoundException("EVENTO com o ID ("+id+") nao encontrado no sistema");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: evento SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return eventoVO;
	}
	
	public ArrayList<EventoVO> selectAllOrderById(Connection connection) throws DatabaseException, EmptyTableException{
		String sql = "SELECT id_evento, nm_evento, desc_evento FROM evento;";
		PreparedStatement stmt;
		ResultSet rs;
		ArrayList<EventoVO> list = new ArrayList<EventoVO>();
		try{
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			 list.add(new EventoVO(rs.getInt(1), rs.getString(2), rs.getString(3)));
			if(list.isEmpty())
				throw new EmptyTableException("Tabela EVENTO esta vazia");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: evento SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return list;
	}
	
	public void deleteById(Connection connection, int id) throws DatabaseException{
		String sql = "DELETE FROM evento WHERE id_evento = ?;";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		}catch(SQLException e){
			throw new DatabaseException("TABLE: evento SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
}
