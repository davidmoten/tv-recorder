package com.github.davidmoten.tv;

public final class Recording {

	public final String channel;
	public final String title;
	public final long start;
	public final long finish;

	public Recording(String channel, String title, long start, long finish) {
		this.channel = channel;
		this.title = title;
		this.start = start;
		this.finish = finish;
	}

	public boolean overlaps(Recording recording) {
		return Math.max(start, recording.start) < Math.min(finish, recording.finish);
	}
	
}
