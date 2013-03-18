package br.com.dishup.persistence.hibernate.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.PaisAlreadyExistException;
import br.com.dishup.exception.PaisNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableFieldTruncationException;
import br.com.dishup.object.PaisVO;

/****************************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for encapsulate the Pais data access object 
 ****************************************/
public class PaisDAO {
	
	/**********************************************
	 * SQL STATE CODE - POSTGRES - doc: http://www.postgresql.org/docs/8.3/static/errcodes-appendix.html
	 **********************************************/
	private final String SQLSTATE_CODE_22001 = "22001";//String Data Right Truncation
	private final String SQLSTATE_CODE_23505 = "23505";//Unique Violation
	private final String SQLSTATE_CODE_23502 = "23502";//Not Null Violation
	private final String SQLSTATE_CODE_23514 = "23514";//Check Violation
	
	/**********************************************
	 * Method responsible for insert on Pais table
	 * @author Lucas Biccio Ribeiro
	 * @since 02/02/2013
	 * @param pais - {@link PaisVO}
	 * @throws TableFieldTruncationException 
	 * @throws PaisAlreadyExistException 
	 * @throws TableFieldNullValueException 
	 * @throws TableFieldCheckException 
	 * @throws DatabaseException 
	 **********************************************/
	public void insert(Connection connection, PaisVO pais) throws TableFieldTruncationException, PaisAlreadyExistException, TableFieldNullValueException, TableFieldCheckException, DatabaseException{
		String sql = "INSERT INTO pais(id_pais, sigla_pais, nm_pais) VALUES (?, ?, ?);";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, pais.getId());
			stmt.setString(2, String.valueOf(pais.getSigla()));
			stmt.setString(3, pais.getNome());
			stmt.execute();
		}catch(SQLException e){
			if(e.getSQLState().equals(SQLSTATE_CODE_22001))
				throw new TableFieldTruncationException("Campo com tamanho maior do que o definido na tabela: "+pais.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23505))
				throw new PaisAlreadyExistException("Registro ja existe no sistema: "+pais.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23502))
				throw new TableFieldNullValueException("Tabela nao aceita a insercao de valores nulos: "+pais.toString());
			else if(e.getSQLState().equals(SQLSTATE_CODE_23514))
				throw new TableFieldCheckException("Alguma regra (CHECK) foi violada: "+pais.toString());
			else
				throw new DatabaseException("TABLE: pais SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
	
	/**********************************************
	 * Method responsible for select a Pais from table pais by sigla
	 * @author Lucas Biccio Ribeiro
	 * @since 02/02/2013
	 * @param sigla 
	 * @throws PaisNotFoundException 
	 * @throws DatabaseException 
	 **********************************************/
	public PaisVO selectBySigla(Connection connection, String sigla) throws PaisNotFoundException, DatabaseException{
		String sql = "SELECT id_pais, sigla_pais, nm_pais FROM pais WHERE sigla_pais = ?;";
		PreparedStatement stmt;
		ResultSet rs;
		PaisVO pais = new PaisVO();
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, sigla);
			rs = stmt.executeQuery();
			if(rs.next())
				pais = new PaisVO(rs.getInt(1), rs.getString(2), rs.getString(3));
			else
				throw new PaisNotFoundException("Pais com a sigla: "+ sigla +"nao encontrado no sistema");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: pais SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return pais;
	}
	
	/**********************************************
	 * Method responsible for select a Pais from table pais by id
	 * @author Lucas Biccio Ribeiro
	 * @since 02/02/2013
	 * @param id
	 * @throws PaisNotFoundException 
	 * @throws DatabaseException 
	 **********************************************/
	public PaisVO selectById(Connection connection, int id) throws PaisNotFoundException, DatabaseException{
		String sql = "SELECT id_pais, sigla_pais, nm_pais FROM pais WHERE id_pais = ?;";
		PreparedStatement stmt;
		ResultSet rs;
		PaisVO pais = new PaisVO();
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if(rs.next())
				pais = new PaisVO(rs.getInt(1), rs.getString(2), rs.getString(3));
			else
				throw new PaisNotFoundException("Pais com o id: "+ id +" nao encontrado no sistema");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: pais SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return pais;
	}
	
	/*******************************************
	 * Method responsible for select all elements in database ordered by id
	 * @return {@link ArrayList} of {@link PaisVO}
	 * @throws EmptyTableException
	 * @throws DatabaseException 
	 *******************************************/
	public ArrayList<PaisVO> selectAllOrderById(Connection connection) throws EmptyTableException, DatabaseException{
		String sql = "SELECT id_pais, sigla_pais, nm_pais FROM pais ORDER BY id_pais;";
		ArrayList<PaisVO> lista = new ArrayList<PaisVO>();
		PreparedStatement stmt;
		ResultSet rs;
		PaisVO pais = new PaisVO();
		try{
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				pais = new PaisVO(rs.getInt(1), rs.getString(2), rs.getString(3));
				lista.add(pais);
			}
			if(lista.isEmpty())
				throw new EmptyTableException("Tabela Pais esta vazia");
		}catch(SQLException e){
			throw new DatabaseException("TABLE: pais SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
		return lista;
	}
	
	/***********************
	 * Method responsible for delete a Pais from database by an Id
	 * @param id - Pais'id
	 * @throws DatabaseException 
	 **********************/
	public void deleteById(Connection connection, int id) throws DatabaseException{
		String sql = "DELETE FROM pais WHERE id_pais = ?;";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
		}catch(SQLException e){
			throw new DatabaseException("TABLE: pais SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
}
