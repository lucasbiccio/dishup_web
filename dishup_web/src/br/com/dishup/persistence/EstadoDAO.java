package br.com.dishup.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.dishup.exception.CidadeNotFoundException;
import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.EstadoAlreadyExistException;
import br.com.dishup.exception.EstadoNotFoundException;
import br.com.dishup.exception.PaisNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableFieldTruncationException;
import br.com.dishup.exception.TableForeignKeyViolationException;
import br.com.dishup.object.EstadoVO;

/****************************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for encapsulate the Estado data access object 
 ****************************************/
public class EstadoDAO {
	
	/**********************************************
	 * SQL STATE CODE - POSTGRES - doc: http://www.postgresql.org/docs/8.3/static/errcodes-appendix.html
	 **********************************************/
	private final String SQLSTATE_CODE_22001 = "22001";//String Data Right Truncation
	private final String SQLSTATE_CODE_23505 = "23505";//Unique Violation
	private final String SQLSTATE_CODE_23502 = "23502";//Not Null Violation
	private final String SQLSTATE_CODE_23514 = "23514";//Check Violation
	private final String SQLSTATE_CODE_23503 = "23503";//Foreign Key Violation
	
	/**********************************************
	 * Method responsible for insert the estado values in the database
	 * @since 02/02/2013
	 * @param estado {@link EstadoVO}
	 * @throws TableFieldTruncationException
	 * @throws EstadoAlreadyExistException
	 * @throws TableFieldNullValueException
	 * @throws TableFieldCheckException
	 * @throws TableForeignKeyViolationException
	 * @throws DatabaseException 
	 **********************************************/
	public void insert(Connection connection, EstadoVO estado) throws TableFieldTruncationException, EstadoAlreadyExistException, TableFieldNullValueException, TableFieldCheckException, TableForeignKeyViolationException, DatabaseException{
		String sql = "INSERT INTO estado (id_estado, sigla_estado, nm_estado, id_pais) VALUES (?, ?, ?, ?);";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, estado.getId());
			stmt.setString(2, estado.getSigla());
			stmt.setString(3, estado.getNome());
			stmt.setInt(4, estado.getPais().getId());
			stmt.execute();
		}catch(SQLException e){
			if(e.getSQLState().equals(SQLSTATE_CODE_22001))
				throw new TableFieldTruncationException("Campo com tamanho maior do que o definido na tabela: "+estado.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23505))
				throw new EstadoAlreadyExistException("Registro ja existe no sistema: "+estado.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23502))
				throw new TableFieldNullValueException("Tabela nao aceita a insersao de valores nulos: "+estado.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23514))
				throw new TableFieldCheckException("Alguma regra (CHECK) foi violada: "+estado.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23503))
				throw new TableForeignKeyViolationException("Chave estrangeira nao existe na tabela pai: "+estado.toString());
			else
				throw new DatabaseException("TABLE: estado SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
	
	/**********************************
	 * Method responsible for select an Estado for database by an sigla value
	 * @since 02/02/2013
	 * @param sigla - the Estado's sigla
	 * @return {@link EstadoVO}
	 * @throws EstadoNotFoundException
	 * @throws DatabaseException 
	 * @throws PaisNotFoundException 
	 ***********************************/
	public EstadoVO selectBySigla(Connection connection, String sigla) throws EstadoNotFoundException, DatabaseException, PaisNotFoundException{
		String sql = "SELECT id_estado, sigla_estado, nm_estado, id_pais FROM estado WHERE sigla_estado = ?;";
		PreparedStatement stmt;
		ResultSet rs;
		EstadoVO estado = new EstadoVO();
		PaisDAO paisDAO = new PaisDAO();
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, sigla);
			rs = stmt.executeQuery();
			if(rs.next())
				estado = new EstadoVO(rs.getInt(1), rs.getString(2), rs.getString(3), paisDAO.selectById(connection,rs.getInt(4)));
			else
				throw new EstadoNotFoundException("Estado com a sigla: "+sigla+" nao encontrado.");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: estado SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return estado;
	}
	
	/**********************************
	 * Method responsible for select an Estado from database by an id value
	 * @since 02/02/2013
	 * @param id
	 * @return {@link EstadoVO}
	 * @throws EstadoNotFoundException
	 * @throws DatabaseException 
	 * @throws PaisNotFoundException 
	 ***********************************/
	public EstadoVO selectById(Connection connection , int id) throws EstadoNotFoundException, DatabaseException, PaisNotFoundException{
		String sql = "SELECT id_estado, sigla_estado, nm_estado, id_pais FROM estado WHERE id_estado = ?;";
		PreparedStatement stmt;
		ResultSet rs;
		EstadoVO estado = new EstadoVO();
		PaisDAO paisDAO = new PaisDAO();
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				estado = new EstadoVO(rs.getInt(1), rs.getString(2), rs.getString(3), paisDAO.selectById(connection,rs.getInt(4)));
			else
				throw new EstadoNotFoundException("Estado com o id: "+id+" nao encontrado.");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: estado SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return estado;
	}

	/*******************************************
	 * Method responsible for select all elements in database ordered by id
	 * @return {@link ArrayList} of {@link EstadoVO}
	 * @throws EmptyTableException
	 * @throws DatabaseException 
	 * @throws PaisNotFoundException 
	 *******************************************/
	public ArrayList<EstadoVO> selectAllOrderById(Connection connection) throws EmptyTableException, DatabaseException, PaisNotFoundException {
		String sql = "SELECT id_estado, sigla_estado, nm_estado, id_pais FROM estado ORDER BY id_estado";
		ArrayList<EstadoVO> lista = new ArrayList<EstadoVO>();
		PreparedStatement stmt;
		ResultSet rs;
		EstadoVO estado = new EstadoVO();
		PaisDAO paisDAO = new PaisDAO();
		try{
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				estado = new EstadoVO(rs.getInt(1), rs.getString(2), rs.getString(3), paisDAO.selectById(connection, rs.getInt(4)));
				lista.add(estado);
			}
			if(lista.isEmpty())
				throw new EmptyTableException("Tabela Estado esta vazia");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: estado SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return lista;
	}
	
	/***********************
	 * Method responsible for delete a Estado from database by an Id
	 * @param id - Estados's id
	 * @throws DatabaseException 
	 * @throws CidadeNotFoundException 
	 * @throws PaisNotFoundException 
	 * @throws EstadoNotFoundException 
	 **********************/
	public void deleteById(Connection connection, int id) throws DatabaseException, CidadeNotFoundException, EstadoNotFoundException, PaisNotFoundException{
		String sql = "DELETE FROM estado WHERE id_estado = ?;";
		PreparedStatement stmt;
		if(selectById(connection,id) != null){
			try{
				stmt = connection.prepareStatement(sql);
				stmt.setInt(1, id);
				stmt.execute();
			}catch(SQLException e){
				throw new DatabaseException("TABLE: estado SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
			}
		}else{
			throw new CidadeNotFoundException("Cidade com o ID: "+id+" nao encontrado.");
		}
	}

}
