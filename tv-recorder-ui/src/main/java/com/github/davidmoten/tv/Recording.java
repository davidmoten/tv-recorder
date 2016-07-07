package com.github.davidmoten.tv;

public final class Recording {
	
	public final String channel;
	public final long start;
	public final long finish;

	public Recording(String channel, long start, long finish) {
		this.channel = channel;
		this.start = start;
		this.finish = finish;
	}
	
	public boolean overlaps(Recording recording) {
		return Math.max(start,  recording.start)< Math.min(finish, recording.finish);
	}
}
