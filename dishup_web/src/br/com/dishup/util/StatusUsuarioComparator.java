package br.com.dishup.util;

import java.util.Comparator;
import br.com.dishup.object.StatusUsuarioVO;

public class StatusUsuarioComparator implements Comparator<StatusUsuarioVO>{

	@Override
	public int compare(StatusUsuarioVO s1, StatusUsuarioVO s2) {
		if(s1.getIdStatus() > s2.getIdStatus()) 
			return 1;
		else if(s1.getIdStatus() < s2.getIdStatus()) 
			return -1;
		else
			return 0;
	}
}
