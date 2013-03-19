package br.com.dishup.persistence.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.object.EventoVO;
import br.com.dishup.persistence.EventoDAO;

public class HibernateEventoDAO extends HibernateDaoSupport implements EventoDAO{

	public void insert(EventoVO eventoVO){
		getHibernateTemplate().save(eventoVO);
	}
	
	public EventoVO selectById(int id) {
		String sql = "SELECT evento FROM EventoVO evento WHERE id = " + id;
		EventoVO eventoVO = (EventoVO) getHibernateTemplate().find(sql);
		return eventoVO;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<EventoVO> selectAllOrderById(){
		String sql = "SELECT evento FROM EventoVO evento order by id";
		ArrayList<EventoVO> list = (ArrayList<EventoVO>) getHibernateTemplate().find(sql);
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
