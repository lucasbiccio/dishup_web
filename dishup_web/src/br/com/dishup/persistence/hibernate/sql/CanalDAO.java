package br.com.dishup.persistence.hibernate.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.dishup.exception.CanalAlreadyExistException;
import br.com.dishup.exception.CanalNotFoundException;
import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.object.CanalVO;

public class CanalDAO extends HibernateDaoSupport {

	/**********************************************
	 * SQL STATE CODE - POSTGRES - doc:
	 * http://www.postgresql.org/docs/8.3/static/errcodes-appendix.html
	 **********************************************/
	private final String SQLSTATE_CODE_23505 = "23505";// Unique Violation
	private final String SQLSTATE_CODE_23502 = "23502";// Not Null Violation
	private final String SQLSTATE_CODE_23514 = "23514";// Check Violation

	public void insert(Connection connection, CanalVO canalVO)
			throws CanalAlreadyExistException, TableFieldCheckException,
			TableFieldNullValueException, DatabaseException {
		String sql = "INSERT INTO canal (id_canal, nm_canal, desc_canal) VALUES (?,?,?);";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, canalVO.getId());
			stmt.setString(2, canalVO.getNome());
			stmt.setString(3, canalVO.getDescricao());
			stmt.execute();
		} catch (SQLException e) {
			if (e.getSQLState().equals(SQLSTATE_CODE_23505))
				throw new CanalAlreadyExistException(
						"Canal ja existe no sistema: " + canalVO.toString());
			else if (e.getSQLState().equals(SQLSTATE_CODE_23514))
				throw new TableFieldCheckException(
						"Violacao (CHECK) em algum campo: "
								+ canalVO.toString());
			else if (e.getSQLState().equals(SQLSTATE_CODE_23502))
				throw new TableFieldNullValueException(
						"Campos nao podem ser nulos: " + canalVO.toString());
			else
				throw new DatabaseException("TABLE: canal SQLCODE: "
						+ e.getErrorCode() + " SQLSTATE: " + e.getSQLState()
						+ " SQLMESSAGE:" + e.getMessage());
		}
	}
	
	public CanalVO selectById(Connection connection, int id)
			throws DatabaseException, CanalNotFoundException {
		CanalVO canalVO = new CanalVO();
		String sql = "SELECT id_canal, nm_canal, desc_canal FROM canal WHERE id_canal = ?;";
		PreparedStatement stmt;
		ResultSet rs;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next())
				canalVO = new CanalVO(rs.getInt(1), rs.getString(2),
						rs.getString(3));
			else
				throw new CanalNotFoundException("Canal com o ID: " + id
						+ " nao encontrado no sistema.");
		} catch (SQLException e) {
			throw new DatabaseException("TABLE: canal SQLCODE: "
					+ e.getErrorCode() + " SQLSTATE: " + e.getSQLState()
					+ " SQLMESSAGE:" + e.getMessage());
		}
		return canalVO;
	}

	public ArrayList<CanalVO> selectAllOrderById(Connection connection)
			throws DatabaseException, EmptyTableException {
		ArrayList<CanalVO> list = new ArrayList<CanalVO>();
		String sql = "SELECT id_canal, nm_canal, desc_canal FROM canal ORDER BY id_canal;";
		PreparedStatement stmt;
		ResultSet rs;
		try {
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next())
				list.add(new CanalVO(rs.getInt(1), rs.getString(2), rs
						.getString(3)));
			if (list.isEmpty())
				throw new EmptyTableException("Tabela canal esta vazia.");
		} catch (SQLException e) {
			throw new DatabaseException("TABLE: canal SQLCODE: "
					+ e.getErrorCode() + " SQLSTATE: " + e.getSQLState()
					+ " SQLMESSAGE:" + e.getMessage());
		}
		return list;
	}

	public void deleteById(Connection connection, int id)
			throws DatabaseException {
		String sql = "DELETE FROM canal WHERE id_canal = ?;";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		} catch (SQLException e) {
			throw new DatabaseException("TABLE: canal SQLCODE: "
					+ e.getErrorCode() + " SQLSTATE: " + e.getSQLState()
					+ " SQLMESSAGE:" + e.getMessage());
		}
	}
}
