package br.com.dishup.persistence;

import java.util.ArrayList;

import br.com.dishup.object.StatusUsuarioVO;

public interface StatusUsuarioDAO {

	public void insert(StatusUsuarioVO statusUsuario);

	public StatusUsuarioVO selectById(int id);

	public ArrayList<StatusUsuarioVO> selectAllOrderById();
}
