package com.github.davidmoten.tv;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class Recordings {

	private final List<Recording> recordings = new ArrayList<Recording>();
	
	public void add(Recording recording) {
		recordings.add(recording);
	}
	 
	public Stream<Recording> conflicts(Recording recording){
		return recordings.stream().filter(r -> r.overlaps(recording));
	}
}
