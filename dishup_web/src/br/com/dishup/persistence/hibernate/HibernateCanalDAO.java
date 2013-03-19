package br.com.dishup.persistence.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.object.CanalVO;
import br.com.dishup.persistence.CanalDAO;

public class HibernateCanalDAO extends HibernateDaoSupport implements CanalDAO {


	public void insert(CanalVO canalVO){
		getHibernateTemplate().save(canalVO);
	}

	public CanalVO selectById(int id){
		CanalVO canalVO = (CanalVO) getHibernateTemplate().find("SELECT canal FROM Canal canal WHERE id =" + id);
		return canalVO;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<CanalVO> selectAllOrderById() {
		String sql = "SELECT canal FROM Canal canal ORDER BY id";
		ArrayList<CanalVO> list = (ArrayList<CanalVO>) getHibernateTemplate().find(sql);
		return list;
	}

	public void deleteById(Connection connection, int id)
			throws DatabaseException {
		String sql = "DELETE FROM canal WHERE id_canal = ?;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		} catch (SQLException e) {
			throw new DatabaseException("TABLE: canal SQLCODE: "
					+ e.getErrorCode() + " SQLSTATE: " + e.getSQLState()
					+ " SQLMESSAGE:" + e.getMessage());
		}
	}
}
