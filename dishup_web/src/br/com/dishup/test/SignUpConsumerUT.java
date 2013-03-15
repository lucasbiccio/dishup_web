package br.com.dishup.test;

import br.com.dishup.codedata.CanalCD;
import br.com.dishup.exception.DishUpException;
import br.com.dishup.model.SignInModel;
import br.com.dishup.model.SignUpConsumidorModel;

public class SignUpConsumerUT {

	public void cadastro(){
		String email = "luiz.alves@todo.com.br";//não pode ser em branco
		String emailConfirmacao = "luiz.alves@todo.com.br";//ter que ser um e-mail valido
		String senha = "123456";//não pode ser em branco e deve ter no minimo 6 caracteres e no maximo 18.
		SignUpConsumidorModel signUpConsumidorModel = new SignUpConsumidorModel();
		try {
			signUpConsumidorModel.signUp(email,emailConfirmacao,senha);
			System.out.println("CADASTRO EFETUADO COM SUCESSO");
		} catch (DishUpException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			e.getExceptionThrown().getMessage();
			e.getExceptionThrown().printStackTrace();
		}
	}
	
	public void login(){
		try {
			SignInModel signInModel = new SignInModel();
			boolean status = signInModel.autenticaUsuario("teste@teste.com.br", "123456abc", CanalCD.DISHUP_CONSUMER_WEBSITE_CHANNEL);
			if(status)
				System.out.println("LOGIN FEITO COM SUCESSO");
			else
				System.out.println("USUARIO OU SENHA ESTAO INCORRETOS");
		}catch (DishUpException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			e.getExceptionThrown().getMessage();
		}
	}
	
	public static void main(String[] args) {
		SignUpConsumerUT ut = new SignUpConsumerUT();
		ut.cadastro();
		//ut.login();
	}
}
