package com.bm.rrg.model;

/**
 * Represents a match between two competitors
 *
 * @author mattia
 *
 */
public class Match {
	private Competitor c1;
	private Competitor c2;

	public Match(Competitor cc1, Competitor cc2) {
		c1 = cc1;
		c2 = cc2;
	}

	public Competitor getCompetitor1() {
		return c1;
	}

	public Competitor getCompetitor2() {
		return c2;
	}

	public boolean isRest() {
		return getCompetitor1().isRest() || getCompetitor2().isRest();
	}

	public Competitor getFakeCompetitor() {
		return getCompetitor1().isRest() ? getCompetitor1() : getCompetitor2();
	}

	public Competitor getRealCompetitor() {
		return getCompetitor1().isRest() ? getCompetitor2() : getCompetitor1();
	}

	@Override
	public String toString() {
		String c1 = getCompetitor1().getName();
		String c2 = getCompetitor2().getName();

		String s = "";
		if (getCompetitor1().isRest()) {
			s = "Rest: " + c2;
		} else if (getCompetitor2().isRest()) {
			s = "Rest: " + c1;
		} else {
			s = c1 + " v " + c2;
		}

		return s + "\n";
	}
}
