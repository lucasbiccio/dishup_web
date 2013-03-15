package br.com.dishup.model;

import java.sql.Connection;

import br.com.dishup.codedata.EventoCD;
import br.com.dishup.exception.DishUpException;
import br.com.dishup.object.EventoVO;
import br.com.dishup.object.UsuarioVO;
import br.com.dishup.persistence.EventoDAO;
import br.com.dishup.persistence.UsuarioHistoricoDAO;

public class UsuarioHistoryModel {
	
	public void loadHistoryUser(Connection connection, UsuarioVO usuarioVO, EventoCD evento) throws DishUpException{
		UsuarioHistoricoDAO usuarioHistoricoDAO = new UsuarioHistoricoDAO();
		EventoDAO eventoDAO = new EventoDAO();
		EventoVO eventoVO = new EventoVO();
		try {
			eventoVO = eventoDAO.selectById(connection, evento.getId());
			usuarioHistoricoDAO.insert(connection, usuarioVO, eventoVO);
		}catch (Throwable e) {
			throw new DishUpException(this.getClass().getName(), "loadHistoryUser(UsuarioVO usuarioVO, EventoVO eventoVO)", 
					"USUARIO("+usuarioVO.toString()+") EVENTO("+eventoVO.toString()+")", "ERRO: PROBLEMA NA GERACAO DE HISTORICO DO USUARIO, FAVOR CONTATAR A EQUIPE DISH UP", e);
		}
	}
}
