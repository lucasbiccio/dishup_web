package br.com.dishup.persistence.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.object.CidadeVO;
import br.com.dishup.persistence.CidadeDAO;

public class HibernateCidadeDAO extends HibernateDaoSupport implements CidadeDAO{

	public void insert(CidadeVO cidade) {
		getHibernateTemplate().save(cidade);
	}
	
	public CidadeVO selectById(int id){
		String sql = "SELECT cidade FROM Cidade cidade WHERE id_cidade = " + id;
		CidadeVO cidade = (CidadeVO) getHibernateTemplate().find(sql);
		return cidade;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<CidadeVO> selectAllOrderById() {
		String sql = "SELECT cidade FROM Cidade cidade ORDER BY id";
		ArrayList<CidadeVO> lista = (ArrayList<CidadeVO>) getHibernateTemplate().find(sql);
		return lista;
	}
	
	/***********************
	 * Method responsible for delete a Cidade from database by an Id
	 * @param id - Cidade's id
	 * @throws DatabaseException 
	 **********************/
	public void deleteById(Connection connection, int id) throws DatabaseException{
		String sql = "DELETE FROM cidade WHERE id_cidade = ?;";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		}catch(SQLException e){
			throw new DatabaseException("TABLE: cidade SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
}
