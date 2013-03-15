package br.com.dishup.util;

import java.util.Comparator;

import br.com.dishup.object.EventoVO;

public class EventoComparator implements Comparator<EventoVO> {

	@Override
	public int compare(EventoVO o1, EventoVO o2) {
		if(o1.getId() > o2.getId())
			return 1;
		else if(o1.getId() < o2.getId())
			return -1;
		return 0;
	}
}
