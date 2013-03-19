package br.com.dishup.persistence.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.object.PaisVO;
import br.com.dishup.persistence.PaisDAO;

public class HibernatePaisDAO extends HibernateDaoSupport implements PaisDAO {
	
	public void insert(PaisVO pais) {
		getHibernateTemplate().save(pais);
	}
	
	public PaisVO selectBySigla(String sigla){
		String sql = "SELECT pais FROM PaisVO pais WHERE sigla = '" + sigla + "'";
		PaisVO pais = (PaisVO) getHibernateTemplate().find(sql);
		return pais;
	}
	
	public PaisVO selectById(int id){
		String sql = "SELECT pais FROM PaisVO pais WHERE id = " + id;
		PaisVO pais = (PaisVO) getHibernateTemplate().find(sql);
		return pais;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<PaisVO> selectAllOrderById(){
		String sql = "SELECT pais FROM PaisVO pais ORDER BY id";
		ArrayList<PaisVO> lista = (ArrayList<PaisVO>) getHibernateTemplate().find(sql);
		return lista;
	}
	
	public void deleteById(Connection connection, int id) throws DatabaseException{
		String sql = "DELETE FROM pais WHERE id_pais = ?;";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		}catch(SQLException e){
			throw new DatabaseException("TABLE: pais SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
}
