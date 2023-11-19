package com.bm.rrg.model;

/**
 * Represents a competitor (player/team...)
 *
 * @author mattia
 *
 */

public class Competitor {
	private String name;
	private boolean picked;
	private boolean isRest;

	public Competitor(String n) {
		name = n;
	}

	public Competitor(String n, boolean isRest) {
		name = n;
		this.isRest = isRest;
	}

	public String getName() {
		return name;
	}

	public boolean isPicked() {
		return picked;
	}

	public void setPicked() {
		picked = true;
	}

	public void setUnPicked() {
		picked = false;
	}

	public boolean isRest() {
		return isRest;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		Competitor that = (Competitor) obj;

		return getName().equals(that.getName());
	}

	@Override
	public String toString() {
		return name;
	}

}
