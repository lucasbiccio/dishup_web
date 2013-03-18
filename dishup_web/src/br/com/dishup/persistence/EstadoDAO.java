package br.com.dishup.persistence;

import java.sql.Connection;
import java.util.ArrayList;

import br.com.dishup.exception.CidadeNotFoundException;
import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EstadoNotFoundException;
import br.com.dishup.exception.PaisNotFoundException;
import br.com.dishup.object.EstadoVO;

public interface EstadoDAO {
	
	public void insert(EstadoVO estado);
	
	public EstadoVO selectBySigla(String sigla);
	
	public EstadoVO selectById(int id);
	
	public ArrayList<EstadoVO> selectAllOrderById();
	
	public void deleteById(Connection connection, int id) throws DatabaseException, CidadeNotFoundException, EstadoNotFoundException, PaisNotFoundException;
}
