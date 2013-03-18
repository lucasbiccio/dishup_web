package br.com.dishup.persistence.hibernate.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.dishup.exception.DatabaseException;

/******************************
 * @author Lucas Biccio Ribeiro
 * @since 05/04/2012
 * @version 1.0
 * Class responsible to construct the connections to the database. 
 * It follows the factory design pattern, and actually, connect only to postgresSQL database 
 * @author Lucas Biccio Ribeiro
 * @since 24/02/2013
 * @version 2.0
 * Changed the thrown exception when there is a problem at database connection
 ******************************/
public class ConnectionFactory {

	/*********************
	 * Static object connection - singleton pattern
	 *********************/
	private Connection connectionPostgres;
	
	/*********************
	 * Postgres connection parameters
	 *********************/
	private final String url = "jdbc:postgresql://localhost:5432/dishup";
	private final String user = "postgres";
	private final String password = "bi2404";
	
	/***********************
	 * Method who controls the connection though the connection pool
	 * @return {@link Connection}
	 * @throws DatabaseException 
	 ***********************/
	public Connection getConnection()
	{
		return this.connectionPostgres;
	}
	
	public void setAutoCommitConnection(boolean value){
		try{
			this.connectionPostgres.setAutoCommit(value);
		}catch (SQLException e) {
			DatabaseException databaseException = new DatabaseException("CLOSE CONNECTION: postgresSQL SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
			System.out.println(databaseException.getMessage());
		}
	}
	
	public void closeConnection(){
		try{
			this.connectionPostgres.close();
		}catch (SQLException e) {
			DatabaseException databaseException = new DatabaseException("CLOSE CONNECTION: postgresSQL SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
			System.out.println(databaseException.getMessage());
		}
	}
	
	public void rollbackConnection(){
		try{
			this.connectionPostgres.rollback();
		}catch (SQLException e) {
			DatabaseException databaseException = new DatabaseException("ROLLBACK CONNECTION: postgresSQL SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
			System.out.println(databaseException.getMessage());
		}
	}
	
	public void commitConnection(){
		try{
			this.connectionPostgres.commit();
		}catch (SQLException e) {
			DatabaseException databaseException = new DatabaseException("COMMIT CONNECTION: postgresSQL SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
			System.out.println(databaseException.getMessage());
		}
	}
	
	/********************
	 * Method who controls the connection pools and return a valid connection
	 * @param driver - database driver
	 * @param url - database URL
	 * @param user - database user
	 * @param password - database password
	 * @return {@link Connection}
	 * @throws DatabaseException
	 ********************/
	public ConnectionFactory()
	{
		try{
			this.connectionPostgres = DriverManager.getConnection(url,user,password);
		}catch (SQLException e) {
			DatabaseException databaseException = new DatabaseException("CONNECTION: postgresSQL SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
			System.out.println(databaseException.getMessage());
		}
	}
}
