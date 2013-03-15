package br.com.dishup.util;

import java.util.Comparator;
import br.com.dishup.object.CanalVO;

public class CanalComparator implements Comparator<CanalVO> {

	@Override
	public int compare(CanalVO o1, CanalVO o2) {
		if(o1.getId() > o2.getId())
			return 1;
		else if(o1.getId() < o2.getId())
			return -1;
		else
			return 0;
	}
}
