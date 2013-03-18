package br.com.dishup.persistence;

import java.sql.Connection;
import java.util.ArrayList;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.object.CanalVO;

public interface CanalDAO {

	public void insert(CanalVO canalVO);

	public CanalVO selectById(int id);

	public ArrayList<CanalVO> selectAllOrderById();

	public void deleteById(Connection connection, int id)
			throws DatabaseException;
}
