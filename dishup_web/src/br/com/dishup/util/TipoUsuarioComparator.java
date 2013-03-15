package br.com.dishup.util;

import java.util.Comparator;

import br.com.dishup.object.TipoUsuarioVO;

public class TipoUsuarioComparator implements Comparator<TipoUsuarioVO> {

	@Override
	public int compare(TipoUsuarioVO o1, TipoUsuarioVO o2) {
		if(o1.getIdTipoUsuario() > o2.getIdTipoUsuario()) 
			return 1;
		else if(o1.getIdTipoUsuario() < o2.getIdTipoUsuario()) 
			return -1;
		else
			return 0;
	}
}
