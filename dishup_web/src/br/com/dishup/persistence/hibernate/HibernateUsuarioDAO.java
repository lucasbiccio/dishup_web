package br.com.dishup.persistence.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.dishup.object.UsuarioVO;
import br.com.dishup.persistence.UsuarioDAO;

public class HibernateUsuarioDAO extends HibernateDaoSupport implements
		UsuarioDAO {

	public void insert(UsuarioVO usuario) {
		getHibernateTemplate().save(usuario);
	}

	public boolean isUsuario(String email) {
		UsuarioVO usuario = selectByEmail(email);
		if (usuario == null)
			return false;
		else
			return true;
	}

	public UsuarioVO selectByEmail(String email) {
		String sql = "select usuario from UsuarioVO usuario where email = \""
				+ email + "\"";
		UsuarioVO usuario = (UsuarioVO) getHibernateTemplate().find(sql);
		return usuario;
	}

	public void update(UsuarioVO usuario) {
		// TODO ATUALIZAR USUARIO
	}
}
