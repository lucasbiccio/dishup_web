package br.com.dishup.persistence.hibernate;

import java.util.ArrayList;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.dishup.object.StatusUsuarioVO;
import br.com.dishup.persistence.StatusUsuarioDAO;

public class HibernateStatusUsuarioDAO extends HibernateDaoSupport implements StatusUsuarioDAO {
	
	public void insert(StatusUsuarioVO statusUsuario){
		getHibernateTemplate().save(statusUsuario);
	}
	
	
	public StatusUsuarioVO selectById(int id) {
		String sql = "SELECT status FROM StatusUsuarioVO status WHERE id = " + id;
		StatusUsuarioVO status = (StatusUsuarioVO) getHibernateTemplate().find(sql);
		return status;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<StatusUsuarioVO> selectAllOrderById(){
		String sql = "SELECT status FROM StatusUsuarioVO status order by id";
		ArrayList<StatusUsuarioVO> lista = (ArrayList<StatusUsuarioVO>) getHibernateTemplate().find(sql);
		return lista;
	}
}
