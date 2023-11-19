package com.bm.rrg.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bm.rrg.model.Competitor;
import com.bm.rrg.model.Fixture;
import com.bm.rrg.model.Match;

/**
 * Generate all the matches for the season in Round-Robin format, implementing
 * circle method
 *
 * @see https://en.wikipedia.org/wiki/Round-robin_tournament
 *
 * @author mattia
 *
 */

public class RoundRobinGenerator {
	private ArrayList<Competitor> competitorList;
	private Fixture tournamentMatches;

	public RoundRobinGenerator(ArrayList<Competitor> l) {
		if (l.size() % 2 != 0) {// odd number of competitors -> let's add a fake player, the "REST" one
			l.add(new Competitor("Rest:", true));
		}

		Collections.shuffle(l);
		competitorList = l;
		tournamentMatches = new Fixture();
	}

	public void create() {
		// How it works:
		// 1. Split the list in 2 lists, so we can pick a competitor from one list and
		// his opponent from the other one
		// 2. For each matchday, generate n/2 matches and add all the matches to
		// tournament list
		// 3. After every matchday creation, reset the competitor lists ("pick" flag)
		// and rotate clockwise the elements of the two lists:
		// move the first element of away list to second position in home list and
		// move the last element of home list to last position of away list

		int half = competitorList.size() / 2;
		List<Competitor> homeCompetitors = sublist(competitorList, 0, half);
		Collections.shuffle(homeCompetitors);
		List<Competitor> awayCompetitors = sublist(competitorList, half, competitorList.size());
		Collections.shuffle(awayCompetitors);

		int numberOfMatchDay = competitorList.size() - 1;
		int numberOfMatchPerDay = competitorList.size() / 2;

		// for each matchday, generate n/2 matches
		for (int md = 1; md <= numberOfMatchDay; md++) {
			// Utilities.debug("MatchDay " + md);

			ArrayList<Match> matches = new ArrayList<>();

			// for each match to generate, select 2 competitors: 1 from home list and 1 from
			// away list
			for (int p = 1; p <= numberOfMatchPerDay; p++) {
				Match m = createNewMatch(md, tournamentMatches, homeCompetitors, awayCompetitors);
				matches.add(m);
			}

			tournamentMatches.put(md, matches);

			// reset "pick" flag
			resetPick(homeCompetitors);
			resetPick(awayCompetitors);

			// rotate clockwise the lists
			rotate(homeCompetitors, awayCompetitors);
			// Utilities.debug("\n\n");
		}
	}

	public Fixture getMatches() {
		return tournamentMatches;
	}

	public void writeToFile(String f) throws IOException {
		Path path = Paths.get(f);
		byte[] strToBytes = tournamentMatches.toString().getBytes();
		Files.write(path, strToBytes);
	}

	private Match createNewMatch(int matchday, Fixture sm, List<Competitor> homePlayers, List<Competitor> awayPlayers) {
		Competitor c1 = pickCompetitor(homePlayers);
		Competitor c2 = pickCompetitor(awayPlayers);

		if (c1 != null && c2 != null) {// found both competitors, we can try to create the match
			Match match = new Match(c1, c2);

			// Utilities.debug(c1.getName() + " v " + c2.getName());

			c1.setPicked();
			c2.setPicked();

			return match;
		} else {
			// At least one competitor is not correctly picked...this should never happen
			if (c1 == null) {
				// Utilities.debug("No player 1 selected on matchday " + matchday);
			}

			if (c2 == null) {
				// Utilities.debug("No player 2 selected on matchday " + matchday);
			}
		}
		return null;// this should never happen
	}

	private Competitor pickCompetitor(List<Competitor> competitorList) {
		Competitor cmpt = null;
		for (Competitor c : competitorList) {
			if (!c.isPicked()) {// pick the 1st competitor not yet picked
				cmpt = c;
			}
		}
		return cmpt;
	}

	private void resetPick(List<Competitor> l) {
		for (Competitor p : l) {
			p.setUnPicked();
		}
	}

	private void rotate(List<Competitor> homeCompetitors, List<Competitor> awayCompetitors) {
		// Extract the first element of the away list, remove it and add to the home
		// list as second element
		Competitor firstAwayComp = awayCompetitors.get(0);
		awayCompetitors.remove(firstAwayComp);
		homeCompetitors.add(1, firstAwayComp);

		// Extract the last element of the home list, remove it and add to the away list
		// as last element
		Competitor lastHomeComp = homeCompetitors.get(homeCompetitors.size() - 1);
		homeCompetitors.remove(lastHomeComp);
		awayCompetitors.add(lastHomeComp);
	}

	private ArrayList<Competitor> sublist(ArrayList<Competitor> baseList, int begin, int end) {
		ArrayList<Competitor> outList = new ArrayList<>();
		for (int i = begin; i < end; i++) {
			outList.add(baseList.get(i));
		}
		return outList;
	}

}
