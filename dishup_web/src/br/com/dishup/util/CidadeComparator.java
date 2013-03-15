package br.com.dishup.util;

import java.util.Comparator;

import br.com.dishup.object.CidadeVO;

public class CidadeComparator implements Comparator<CidadeVO> {

	@Override
	public int compare(CidadeVO c1, CidadeVO c2) {
		if(c1.getId() > c2.getId()) 
			return 1;
		else if(c1.getId() < c2.getId()) 
			return -1;
		else
			return 0;
	}

	
}
