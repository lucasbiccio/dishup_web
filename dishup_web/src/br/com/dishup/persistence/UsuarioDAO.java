package br.com.dishup.persistence;

import br.com.dishup.object.UsuarioVO;

public interface UsuarioDAO {

	public void insert(UsuarioVO usuario);
	
	public boolean isUsuario(String email);
	
	public UsuarioVO selectByEmail(String email);	
	
	public void update(UsuarioVO usuario);
}
