package br.com.dishup.util;

import java.util.Comparator;
import br.com.dishup.object.PaisVO;

public class PaisComparator implements Comparator<PaisVO> {

	@Override
	public int compare(PaisVO p1, PaisVO p2) {
		if(p1.getId() > p2.getId()) 
			return 1;
		else if(p1.getId() < p2.getId()) 
			return -1;
		else
			return 0;
	}
}
