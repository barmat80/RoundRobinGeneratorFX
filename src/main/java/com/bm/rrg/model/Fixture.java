package com.bm.rrg.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains all the matches of the tournament
 *
 * @author mattia
 *
 */

public class Fixture {
	private HashMap<Integer, ArrayList<Match>> fixtures;

	public Fixture() {
		fixtures = new HashMap<>();
	}

	public void put(int matchday, ArrayList<Match> matches) {
		fixtures.put(matchday, matches);
	}

	public int getFixturesNumber() {
		return fixtures.size();
	}

	public String getMatchesAt(int index) {
		ArrayList<Match> matches = fixtures.get(index);
		return matchesToString(matches);
	}

	public ArrayList<Match> getMatchListAt(int index) {
		return fixtures.get(index);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean alreadyExists(int matchday, Match match) {
		Competitor p1 = match.getCompetitor1();
		Competitor p2 = match.getCompetitor2();

		boolean found = false;
		// Look for any occurrences of this match in all the previous days
		for (Map.Entry me : fixtures.entrySet()) {
			Integer day = (Integer) me.getKey();
			ArrayList<Match> matches = (ArrayList<Match>)me.getValue();
			if (day < matchday) {
				for (Match m : matches) {
					if (m.getCompetitor1().equals(p1) && m.getCompetitor2().equals(p2)
							|| m.getCompetitor1().equals(p2) && m.getCompetitor2().equals(p1)) {
						found = true;
						break;
					}
				}
			}

			if (found) {
				break;
			}
		}
		return found;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry me : fixtures.entrySet()) {
			Integer day = (Integer) me.getKey();
			sb.append("Matchday ").append(day).append("\n");
			sb.append(matchesToString((ArrayList<Match>) me.getValue()));
			sb.append("\n\n");
		}

		return sb.toString();
	}

	private String matchesToString(ArrayList<Match> matches) {
		StringBuilder sb = new StringBuilder();
		String restCompetitor = "";
		for (Match m : matches) {
			String matchStr = m.toString();
			if (matchStr.contains("Rest:")) {
				restCompetitor = matchStr;
			} else {
				sb.append(matchStr);
			}
		}

		// Add Rest always as last element
		if (!restCompetitor.isEmpty()) {
			sb.append(restCompetitor);
		}

		return sb.toString();
	}
}
