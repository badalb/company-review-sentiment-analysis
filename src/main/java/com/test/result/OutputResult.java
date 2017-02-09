package com.test.result;

public class OutputResult {

	private String aspact;

	private int positive;

	private int negative;

	private int neutral;

	public OutputResult() {
		this.negative = 0;
		this.positive = 0;
		this.neutral = 0;
	}

	public String getAspact() {
		return aspact;
	}

	public void setAspact(String aspact) {
		this.aspact = aspact;
	}

	public int getPositive() {
		return positive;
	}

	public void setPositive(int positive) {
		this.positive = positive;
	}

	public int getNegative() {
		return negative;
	}

	public void setNegative(int negative) {
		this.negative = negative;
	}

	public int getNeutral() {
		return neutral;
	}

	public void setNeutral(int neutral) {
		this.neutral = neutral;
	}

	@Override
	public String toString() {
		return "OutputResult [aspact=" + aspact + ", positive=" + positive + ", negative=" + negative + ", neutral="
				+ neutral + "]";
	}

	
}
