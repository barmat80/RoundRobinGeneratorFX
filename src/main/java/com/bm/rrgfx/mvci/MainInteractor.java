package com.bm.rrgfx.mvci;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import com.bm.rrg.logic.RoundRobinGenerator;
import com.bm.rrg.model.Competitor;
import com.bm.rrg.model.Fixture;
import com.bm.rrgfx.Utility;

public class MainInteractor {
	private final Model model;

	private Fixture fixtures;

	public MainInteractor(Model model) {
		this.model = model;
	}

	public void runGenerationFromLines() {
		try {
			Thread.sleep(Duration.ofMillis(2000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		run(Utility.linesToCompetitors(model.getLines()));
	}

	public void runGenerationFromFile() throws IOException {
		try {
			Thread.sleep(Duration.ofMillis(2000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		run(Utility.fileToCompetitors(model.getFilePath()));
	}

	private void run(ArrayList<Competitor> competitors) {
		RoundRobinGenerator rrg = new RoundRobinGenerator(competitors);
		rrg.create();
		fixtures = rrg.getMatches();
	}

	public void updateModelAfterGeneration() {
		model.clearFixtures();
		model.setFixtures(fixtures);
	}
}
