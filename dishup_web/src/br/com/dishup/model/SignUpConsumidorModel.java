package br.com.dishup.model;

import br.com.dishup.codedata.EventoCD;
import br.com.dishup.codedata.StatusUsuarioCD;
import br.com.dishup.codedata.TipoUsuarioCD;
import br.com.dishup.exception.DishUpException;
import br.com.dishup.exception.FieldValidatorException;
import br.com.dishup.exception.UsuarioAlreadyExistException;
import br.com.dishup.object.PasswordVO;
import br.com.dishup.object.StatusUsuarioVO;
import br.com.dishup.object.TipoUsuarioVO;
import br.com.dishup.object.UsuarioVO;
import br.com.dishup.persistence.ConnectionFactory;
import br.com.dishup.persistence.hibernate.sql.PasswordDAO;
import br.com.dishup.persistence.hibernate.sql.StatusUsuarioDAO;
import br.com.dishup.persistence.hibernate.sql.TipoUsuarioDAO;
import br.com.dishup.persistence.hibernate.sql.UsuarioDAO;
import br.com.dishup.util.FieldValidator;

public class SignUpConsumidorModel {
	
	public boolean signUp(String email, String emailConfirmacao, String password) throws DishUpException{
		boolean status = false;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();
		StatusUsuarioDAO statusUsuarioDAO = new StatusUsuarioDAO();
		UsuarioVO usuario;
		
		//validar campos de entrada
		if((!FieldValidator.isEmail(email)) || (FieldValidator.isEmpty(email)))
			throw new DishUpException(this.getClass().getName(), "signUp(String email, String emailConfirmacao, String password)", "E-MAIL("+email+") E-MAIL CONFIRMACAO("+emailConfirmacao+") PASSWORD(*** INFO NAO DEVE SER EXIBIDA ****)", 
					"AVISO: CAMPO E-MAIL DEVE SER VALIDO", new FieldValidatorException("AVISO: CAMPO E-MAIL DEVE SER VALIDO"));
		if((!FieldValidator.isEmail(emailConfirmacao)) || (FieldValidator.isEmpty(emailConfirmacao)))
			throw new DishUpException(this.getClass().getName(), "signUp(String email, String emailConfirmacao, String password)", "E-MAIL("+email+") E-MAIL CONFIRMACAO("+emailConfirmacao+") PASSWORD(*** INFO NAO DEVE SER EXIBIDA ****)", 
					"AVISO: CAMPO E-MAIL CONFIRMACAO DEVE SER VALIDO", new FieldValidatorException("AVISO: CAMPO E-MAIL DEVE SER VALIDO"));
		if((FieldValidator.isEmpty(password)) || (!FieldValidator.isValidPassword(password)))
			throw new DishUpException(this.getClass().getName(), "signUp(String email, String emailConfirmacao, String password)", "E-MAIL("+email+") E-MAIL CONFIRMACAO("+emailConfirmacao+") PASSWORD(*** INFO NAO DEVE SER EXIBIDA ****)", 
					"AVISO: CAMPO PASSWORD DEVE TER ENTRE 6 A 18 CARACTERES E NAO TER ESPAÇOS EM BRANCO", new FieldValidatorException("AVISO: CAMPO PASSWORD DEVE TER ENTRE 6 A 18 CARACTERES E NAO TER ESPAÇOS EM BRANCO"));
		if(!email.equals(emailConfirmacao))
			throw new DishUpException(this.getClass().getName(), "signUp(String email, String emailConfirmacao, String password)", "E-MAIL("+email+") E-MAIL CONFIRMACAO("+emailConfirmacao+") PASSWORD(*** INFO NAO DEVE SER EXIBIDA ****)", 
					"AVISO: CAMPO E-MAIL DIFERENTE DO CAMPO E-MAIL CONFIRMACAO", new FieldValidatorException("AVISO: CAMPO E-MAIL DIFERENTE DO CAMPO E-MAIL CONFIRMACAO"));
		
		//abrindo conexao com o banco de dados
		ConnectionFactory connectionFactory = new ConnectionFactory();
		//setando o commit manual
		connectionFactory.setAutoCommitConnection(false);
		
		try {
			//verifica se determinado usuario existe no sistema
			if(!usuarioDAO.isUsuario(connectionFactory.getConnection(), email)){
				String emailUser = email;
				TipoUsuarioVO tipoUsuario = tipoUsuarioDAO.selectById(connectionFactory.getConnection(),TipoUsuarioCD.CONSUMIDOR.getId());//usuario tipo consumidor
				StatusUsuarioVO statusUsuario = statusUsuarioDAO.selectById(connectionFactory.getConnection(), StatusUsuarioCD.ACTIVE.getId());//status igual a ativo
				usuario = new UsuarioVO(emailUser, tipoUsuario, statusUsuario, "DATA SERA GERADA PELO BD", null, null, false);
				//inclui o usuario na base
				usuarioDAO.insert(connectionFactory.getConnection(), usuario);
				//obtendo as informacoes atualizadas do usuario, como por exemplo, a data de inclusao gerada pelo banco de dados
				usuario = usuarioDAO.selectByEmail(connectionFactory.getConnection(), email);
			}
			//se existir o usuario solta a excecao
			else
				throw new DishUpException(this.getClass().getName(), "signUp(String email, String emailConfirmacao, String password)", "E-MAIL("+email+") E-MAIL CONFIRMACAO("+emailConfirmacao+") PASSWORD(*** INFO NAO DEVE SER EXIBIDA ****)", 
						"AVISO: USUARIO JA CADASTRADO NO SISTEMA", new UsuarioAlreadyExistException("AVISO: USUARIO JA CADASTRADO NO SISTEMA"));
			//gerando e inserindo a assinatura do usuario
			PasswordDAO passwordDAO = new PasswordDAO();
			PasswordVO signture = new PasswordVO();
			passwordDAO.insert(connectionFactory.getConnection(),signture);
			//gerando o historico do usuario
			UsuarioHistoryModel usuarioHistoryModel = new UsuarioHistoryModel();
			usuarioHistoryModel.loadHistoryUser(connectionFactory.getConnection(), usuario, EventoCD.USER_SIGN_UP);
			//se tudo ok ate aqui, cadastro efetuado com sucesso e commita as insercoes
			connectionFactory.commitConnection();
			status = true;
		} 
		catch (DishUpException e){
			//desfaz as insercoes ate o momento
			connectionFactory.rollbackConnection();
			throw e; 
		}
		catch (Throwable e) {
			//desfaz as insercoes ate o momento
			connectionFactory.rollbackConnection();
			throw new DishUpException(this.getClass().getName(), "signUp(String email, String emailConfirmacao, String password)", "E-MAIL("+email+") E-MAIL CONFIRMACAO("+emailConfirmacao+") PASSWORD(*** INFO NAO DEVE SER EXIBIDA ****)", 
					"ERRO: PROBLEMA NO CADASTRO, FAVOR CONTATAR A EQUIPE DISH UP", e);
		}finally{
			//fecha conexao
			connectionFactory.closeConnection();
		}
		return status;
	}
}
