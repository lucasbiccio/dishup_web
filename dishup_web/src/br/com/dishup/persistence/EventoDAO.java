package br.com.dishup.persistence;

import java.sql.Connection;
import java.util.ArrayList;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.object.EventoVO;

public interface EventoDAO {

	public void insert(EventoVO eventoVO);

	public EventoVO selectById(int id);

	public ArrayList<EventoVO> selectAllOrderById();

	public void deleteById(Connection connection, int id)
			throws DatabaseException;
}
