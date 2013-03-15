package br.com.dishup.model;

import java.sql.Connection;
import br.com.dishup.codedata.CanalCD;
import br.com.dishup.exception.DishUpException;
import br.com.dishup.exception.EncryptException;
import br.com.dishup.exception.UsuarioNotFoundException;
import br.com.dishup.object.UsuarioVO;
import br.com.dishup.persistence.CanalDAO;
import br.com.dishup.persistence.ConnectionFactory;
import br.com.dishup.persistence.LogAutenticationDAO;
import br.com.dishup.persistence.PasswordDAO;
import br.com.dishup.persistence.UsuarioDAO;
import br.com.dishup.security.Cryptograph;

/**************************
 * @since 24/02/2013 
 * @author Lucas Biccio Ribeiro
 * @version 1.0 Class responsible for encapsulate the autentication and cryptographic logic
 **************************/
public class SignInModel {
	
	
	/********************
	 * Method resposible to autenticate some user
	 * @param email - user's e-mail
	 * @param password - user's password
	 * @return boolean flag that indicates if the user is autenticated or not
	 * @throws EncryptException
	 * @throws DishUpException 
	 ********************/
	private boolean autentication(Connection connection, UsuarioVO usuario, String password) throws EncryptException, DishUpException{
		boolean status = false;
		try {
			PasswordDAO passwordDAO = new PasswordDAO();
			if(Cryptograph.encrypt(password).equals(passwordDAO.selectById(connection, usuario.getId()).getAssinatura()))
				status = true;
		}catch (Throwable e) {
			throw new DishUpException(new SignInModel().getClass().getName(), "autentication(String email, String password)", "****EXIBICAO NAO AUTORIZADA****", e.getMessage(), e);
		}
		return status;
	}
	
	/**********************
	 * Method responsible responsible for autenticate an user.
	 * This class should be used by views componentes.
	 * @param email
	 * @param password
	 * @return boolean flag that indicates if the user is autenticated or not
	 * @throws DishUpException
	 **********************/
	public boolean autenticaUsuario(String email, String password, CanalCD canal) throws DishUpException{
		boolean status = false;
		LogAutenticationDAO autenticationDAO = new LogAutenticationDAO();
		CanalDAO canalDAO = new CanalDAO();
		ConnectionFactory connectionFactory = new ConnectionFactory();
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			UsuarioVO usuario = usuarioDAO.selectByEmail(connectionFactory.getConnection(),email);
			//se usuario autenticado no sistema
			if(autentication(connectionFactory.getConnection(), usuario, password)){
				//log o acesso do usuario na tabela de log_autenticacao
				autenticationDAO.insert(connectionFactory.getConnection(), usuario,canalDAO.selectById(connectionFactory.getConnection(),canal.getId()));
				//libera o acesso ao usuario
				status = true;
			}
		}catch(UsuarioNotFoundException e){
			throw new DishUpException(this.getClass().getName(), "autenticaUsuario(String email, String password, CanalCD canal)", "****EXIBICAO NAO AUTORIZADA****", 
					"AVISO: USUARIO COM E-MAIL INFORMADO NAO CADASTRADO NA DISH UP", e);
		}catch(Throwable e){
			throw new DishUpException(this.getClass().getName(), "autenticaUsuario(String email, String password, CanalCD canal)", "****EXIBICAO NAO AUTORIZADA****", e.getMessage(), e);
		}finally{
			connectionFactory.closeConnection();
		}
		return status;
	}
}
