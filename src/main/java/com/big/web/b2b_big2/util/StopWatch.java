package com.big.web.b2b_big2.util;

public class StopWatch {
	private long last;
	private long elapsed;

	public StopWatch() {
		this.last = 0L;
		this.elapsed = 0L;
	}

	public StopWatch(long startFrom) {
		this.last = startFrom;
		this.elapsed = 0L;
	}

	public static StopWatch AutoStart() {
		StopWatch sw = new StopWatch();
		sw.start();
		return sw;
	}

	public void start() {
		this.last = getTickCount();
	}

	public synchronized void stop() {
		this.elapsed = (getTickCount() - this.last);
	}

	public long stopAndGet() {
		stop();
		return getElapsedTime();
	}

	public String stopAndGetAsString() {
		stop();
		return getElapsedTimeInString();
	}

	public long getElapsedTime() {
		return this.elapsed;
	}

	public String getElapsedTimeInString() {
		return millisecondsToString(this.elapsed);
	}

	public static long getTickCount() {
		return System.currentTimeMillis();
	}

	public void reset() {
		this.elapsed = 0L;
	}

	public long getStartTime() {
		return this.last;
	}

	public static String millisecondsToString(long time) {
		int milliseconds = (int) (time % 1000L);
		int seconds = (int) (time / 1000L % 60L);
		int minutes = (int) (time / 60000L % 60L);
		int hours = (int) (time / 3600000L % 24L);
		String millisecondsStr = (milliseconds < 100 ? "0"
				: milliseconds < 10 ? "00" : "")
				+ milliseconds;
		String secondsStr = (seconds < 10 ? "0" : "") + seconds;
		String minutesStr = (minutes < 10 ? "0" : "") + minutes;
		String hoursStr = (hours < 10 ? "0" : "") + hours;
		return new String(hoursStr + ":" + minutesStr + ":" + secondsStr + "."
				+ millisecondsStr);
	}

	public void stopAndPrint(String title) {
		System.out.println((title == null ? "" : (title + " ")) + stopAndGetAsString());
		System.out.flush();
	}
}