package br.com.dishup.persistence.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.object.TipoUsuarioVO;
import br.com.dishup.persistence.TipoUsuarioDAO;

public class HibernateTipoUsuarioDAO extends HibernateDaoSupport implements TipoUsuarioDAO {
	
	public void insert(TipoUsuarioVO tipoUsuario) {
		getHibernateTemplate().save(tipoUsuario);
	}
	
	public TipoUsuarioVO selectById(int id){
		String sql = "SELECT tipoUsuario FROM TipoUsuarioVO tipoUsuario WHERE id = " + id;
		TipoUsuarioVO tipoUsuario = (TipoUsuarioVO) getHibernateTemplate().find(sql);
		return tipoUsuario;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TipoUsuarioVO> selectAllOrderById(){
		String sql = "SELECT tipoUsuario FROM TipoUsuarioVO tipoUsuario ORDER BY id";
		ArrayList<TipoUsuarioVO> list = (ArrayList<TipoUsuarioVO>) getHibernateTemplate().find(sql);
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
