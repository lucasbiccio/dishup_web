package br.com.dishup.persistence.hibernate.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.dishup.exception.CidadeAlreadyExistException;
import br.com.dishup.exception.CidadeNotFoundException;
import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.EstadoNotFoundException;
import br.com.dishup.exception.PaisNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableFieldTruncationException;
import br.com.dishup.exception.TableForeignKeyViolationException;
import br.com.dishup.object.CidadeVO;

/****************************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for encapsulate the Cidade data access object 
 ****************************************/
public class CidadeDAO {

	/**********************************************
	 * SQL STATE CODE - POSTGRES - doc: http://www.postgresql.org/docs/8.3/static/errcodes-appendix.html
	 **********************************************/
	private final String SQLSTATE_CODE_22001 = "22001";//String Data Right Truncation
	private final String SQLSTATE_CODE_23505 = "23505";//Unique Violation
	private final String SQLSTATE_CODE_23502 = "23502";//Not Null Violation
	private final String SQLSTATE_CODE_23514 = "23514";//Check Violation
	private final String SQLSTATE_CODE_23503 = "23503";//Foreign Key Violation
	
	/***********************************************
	 * Method responsible for insert the cidade's value in database
	 * @param cidade {@link Cidade}
	 * @throws TableFieldTruncationException
	 * @throws CidadeAlreadyExistException
	 * @throws TableFieldNullValueException
	 * @throws TableFieldCheckException
	 * @throws TableForeignKeyViolationException
	 * @throws DatabaseException 
	 **********************************************/
	public void insert(Connection connection, CidadeVO cidade) throws TableFieldTruncationException, CidadeAlreadyExistException, TableFieldNullValueException, TableFieldCheckException, TableForeignKeyViolationException, DatabaseException{
		String sql = "INSERT INTO cidade(id_cidade, nm_cidade, id_pais, id_estado) VALUES (?, ?, ?, ?);";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, cidade.getId());
			stmt.setString(2, cidade.getNome());
			stmt.setInt(3, cidade.getPais().getId());
			stmt.setInt(4, cidade.getEstado().getId());
			stmt.execute();
		}catch(SQLException e){
			if(e.getSQLState().equals(SQLSTATE_CODE_22001))
				throw new TableFieldTruncationException("Campo com tamanho maior do que o definido na tabela: "+cidade.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23505))
				throw new CidadeAlreadyExistException("Registro ja existe no sistema: "+cidade.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23502))
				throw new TableFieldNullValueException("Tabela n‹o aceita a insersao de valores nulos: "+cidade.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23514))
				throw new TableFieldCheckException("Alguma regra (CHECK) foi violada: "+cidade.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23503))
				throw new TableForeignKeyViolationException("Chave estrangeira nao existe na tabela pai: "+cidade.toString());
			else
				throw new DatabaseException("TABLE: cidade SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
			
		}
	}
	
	/****************************************
	 * Method responsible for select a Cidade from database by an id value
	 * @since 02/02/2013
	 * @param id
	 * @return {@link Cidade}
	 * @throws CidadeNotFoundException
	 * @throws DatabaseException 
	 * @throws PaisNotFoundException 
	 * @throws EstadoNotFoundException 
	 ***************************************/
	public CidadeVO selectById(Connection connection, int id) throws CidadeNotFoundException, DatabaseException, EstadoNotFoundException, PaisNotFoundException{
		String sql = "SELECT id_cidade, nm_cidade, id_pais, id_estado FROM cidade WHERE id_cidade = ?;";
		PreparedStatement stmt;
		ResultSet rs;
		CidadeVO cidade = new CidadeVO();
		PaisDAO paisDAO = new PaisDAO();
		EstadoDAO estadoDAO = new EstadoDAO();
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				cidade = new CidadeVO(rs.getInt(1), rs.getString(2), estadoDAO.selectById(connection,rs.getInt(3)), paisDAO.selectById(connection,rs.getInt(3)));
			else
				throw new CidadeNotFoundException("Cidade com o id: "+id+" nao encontrada.");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: cidade SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return cidade;
	}
	
	/*******************************************
	 * Method responsible for select all elements in database ordered by id
	 * @return {@link ArrayList} of {@link Cidade}
	 * @throws EmptyTableException
	 * @throws DatabaseException 
	 * @throws PaisNotFoundException 
	 * @throws EstadoNotFoundException 
	 *******************************************/
	public ArrayList<CidadeVO> selectAllOrderById(Connection connection) throws EmptyTableException, DatabaseException, EstadoNotFoundException, PaisNotFoundException {
		String sql = "SELECT id_cidade, nm_cidade, id_pais, id_estado FROM cidade ORDER BY id_cidade";
		ArrayList<CidadeVO> lista = new ArrayList<CidadeVO>();
		PreparedStatement stmt;
		ResultSet rs;
		CidadeVO cidade = new CidadeVO();
		PaisDAO paisDAO = new PaisDAO();
		EstadoDAO estadoDAO = new EstadoDAO();
		try{
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				cidade = new CidadeVO(rs.getInt(1), rs.getString(2), estadoDAO.selectById(connection,rs.getInt(4)), paisDAO.selectById(connection,rs.getInt(3)));
				lista.add(cidade);
			}
			if(lista.isEmpty())
				throw new EmptyTableException("Tabela Cidade esta vazia");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: cidade SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return lista;
	}
	
	/***********************
	 * Method responsible for delete a Cidade from database by an Id
	 * @param id - Cidade's id
	 * @throws DatabaseException 
	 **********************/
	public void deleteById(Connection connection, int id) throws DatabaseException{
		String sql = "DELETE FROM cidade WHERE id_cidade = ?;";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		}catch(SQLException e){
			throw new DatabaseException("TABLE: cidade SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
}
