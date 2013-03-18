package br.com.dishup.persistence.hibernate;

import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.dishup.exception.CidadeNotFoundException;
import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EstadoNotFoundException;
import br.com.dishup.exception.PaisNotFoundException;
import br.com.dishup.object.EstadoVO;
import br.com.dishup.persistence.EstadoDAO;

public class HibernateEstadoDAO extends HibernateDaoSupport implements EstadoDAO{
	
	public void insert(EstadoVO estado){
		getHibernateTemplate().save(estado);
	}
	
	public EstadoVO selectBySigla(String sigla) {
		String sql = "SELECT estado FROM EstadoVO estado WHERE sigla = \"" + sigla + "\"";
		EstadoVO estado = (EstadoVO) getHibernateTemplate().find(sql);
		return estado;
	}
	
	public EstadoVO selectById(int id) {
		String sql = "SELECT estado FROM EstadoVO estado WHERE id = \"" + id + "\"";
		EstadoVO estado = (EstadoVO) getHibernateTemplate().find(sql);
		return estado;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<EstadoVO> selectAllOrderById() {
		String sql = "SELECT estado FROM EstadoVO estado ORDER BY id";
		ArrayList<EstadoVO> lista = (ArrayList<EstadoVO>) getHibernateTemplate().find(sql);
		return lista;
	}

	@Override
	public void deleteById(Connection connection, int id)
			throws DatabaseException, CidadeNotFoundException,
			EstadoNotFoundException, PaisNotFoundException {
		// TODO Auto-generated method stub
		
	}
	
}
