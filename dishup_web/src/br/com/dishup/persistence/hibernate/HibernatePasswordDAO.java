package br.com.dishup.persistence.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.dishup.object.PasswordVO;
import br.com.dishup.persistence.PasswordDAO;

public class HibernatePasswordDAO extends HibernateDaoSupport implements PasswordDAO {

	public void insert(PasswordVO password){
		getHibernateTemplate().save(password);
	}

	public PasswordVO selectById(int id){
		String sql = "SELECT id_usuario, assinatura_usuario FROM autenticacao_usuario WHERE id_usuario = " + id;
		PasswordVO password = (PasswordVO) getHibernateTemplate().find(sql);
		return password;
	}
}
