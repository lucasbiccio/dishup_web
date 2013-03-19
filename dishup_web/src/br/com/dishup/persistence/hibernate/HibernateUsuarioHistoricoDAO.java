package br.com.dishup.persistence.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.object.EventoVO;
import br.com.dishup.object.UsuarioVO;
import br.com.dishup.persistence.UsuarioHistoricoDAO;

// TODO ARRUMAR
public class HibernateUsuarioHistoricoDAO extends HibernateDaoSupport implements UsuarioHistoricoDAO{
	
	public void insert(Connection connection, UsuarioVO usuarioVO, EventoVO eventoVO) throws DatabaseException, TableFieldCheckException, TableFieldNullValueException{
		String sql = "INSERT INTO usuario_historico (id_usuario, dt_historico, email_usuario, id_tipo_usuario, id_status_usuario, dt_inclusao_usuario, dt_ultima_alteracao_usuario," +
				" dt_ultima_alteracao_senha_usuario, flag_solicitacao_alteracao_senha_usuario, id_evento) VALUES (?, current_timestamp, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement stmt;
		try{
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, usuarioVO.getId());
			//parametro 2 do insert e a data do sistema (data do historico - current_timestamp)
			stmt.setString(2, usuarioVO.getEmail());
			stmt.setInt(3, usuarioVO.getTipoUsuario().getIdTipoUsuario());
			stmt.setInt(4, usuarioVO.getStatusUsuario().getIdStatus());
			if(usuarioVO.getDataInclusao() != null)
				stmt.setTimestamp(5, Timestamp.valueOf(usuarioVO.getDataInclusao()));
			else
				stmt.setNull(5, Types.NULL);
			if(usuarioVO.getDataUltimaAlteracao() != null)
				stmt.setTimestamp(6, Timestamp.valueOf(usuarioVO.getDataUltimaAlteracao()));
			else
				stmt.setNull(6, Types.NULL);
			if(usuarioVO.getDataUltimaAlteracaoSenha() != null)
				stmt.setTimestamp(7, Timestamp.valueOf(usuarioVO.getDataUltimaAlteracaoSenha()));
			else
				stmt.setNull(7, Types.NULL);
			stmt.setBoolean(8, usuarioVO.isFlagSolicitacaoAlteracaoSenha());
			stmt.setInt(9, eventoVO.getId());
			stmt.execute();
		}catch(SQLException e){
				throw new DatabaseException("TABLE: usuario_historico SQLCODE: "+e.getErrorCode()+" SQLSTATE: "+e.getSQLState()+" SQLMESSAGE:"+e.getMessage());
		}
	}
}
