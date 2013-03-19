package br.com.dishup.persistence.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.dishup.exception.CanalNotFoundException;
import br.com.dishup.exception.DatabaseException;
import br.com.dishup.object.CanalVO;
import br.com.dishup.object.UsuarioVO;
import br.com.dishup.persistence.LogAutenticationDAO;
import br.com.dishup.persistence.hibernate.sql.CanalDAO;

public class HibernateLogAutenticationDAO extends HibernateDaoSupport implements LogAutenticationDAO{
	
	public void insert(Connection connection, UsuarioVO usuarioVO, CanalVO canalVO) {
		String sql = "INSERT INTO log_autenticacao ( id_usuario, dt_autenticacao, id_canal) VALUES (?, current_timestamp, ?)";
		
		CanalDAO canalDAO = new CanalDAO();
		PreparedStatement stmt;
			
//			Connection connection = getHibernateTemplate().get
			try {
				stmt = connection.prepareStatement(sql);
				stmt.setInt(1, usuarioVO.getId());
				stmt.setInt(2, canalDAO.selectById(connection, canalVO.getId()).getId());
				stmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (DatabaseException e) {
				e.printStackTrace();
			} catch (CanalNotFoundException e) {
				e.printStackTrace();
			}
	}
}
// TODO MUDAR