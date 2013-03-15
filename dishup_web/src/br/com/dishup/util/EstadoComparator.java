package br.com.dishup.util;

import java.util.Comparator;

import br.com.dishup.object.EstadoVO;

public class EstadoComparator implements Comparator<EstadoVO>{

	@Override
	public int compare(EstadoVO e1, EstadoVO e2) {
		if(e1.getId() > e2.getId()) 
			return 1;
		else if(e1.getId() < e2.getId()) 
			return -1;
		else
			return 0;
	}
	
	

}
