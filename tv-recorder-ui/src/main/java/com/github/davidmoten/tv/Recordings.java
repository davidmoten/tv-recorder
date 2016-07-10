package com.github.davidmoten.tv;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Recordings {

	private List<Recording> recordings = new ArrayList<Recording>();

	private final Object lock = new Object();

	public void add(Recording recording) {
		synchronized (lock) {
			if (!recordings.contains(recording)) {
				recordings.add(recording);
			}
		}
	}

	public void remove(Recording recording) {
		synchronized (lock) {
			recordings.removeAll(recordings //
					.stream() //
					.filter(r -> r.title.equals(recording.title)) //
					.filter(r -> r.channel.equals(recording.channel)) //
					.filter(r -> r.start == recording.start && r.finish == recording.finish) // 
					.collect(Collectors.toList()));
		}
	}

	public List<Recording> conflicts(Recording recording) {
		synchronized (lock) {
			return recordings //
					.stream() //
					.filter(r -> r.overlaps(recording)) //
					.collect(Collectors.toList());
		}
	}

}
