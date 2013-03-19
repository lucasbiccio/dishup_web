package br.com.dishup.persistence;

import java.sql.Connection;
import java.util.ArrayList;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.object.TipoUsuarioVO;

public interface TipoUsuarioDAO {
	
	
	public void insert(TipoUsuarioVO tipoUsuario);
	
	public TipoUsuarioVO selectById(int id);
	
	public ArrayList<TipoUsuarioVO> selectAllOrderById();
	
	public void deleteById(Connection connection, int id) throws DatabaseException;
}
