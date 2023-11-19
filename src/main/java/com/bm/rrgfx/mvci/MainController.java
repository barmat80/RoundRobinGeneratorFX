package com.bm.rrgfx.mvci;

import com.bm.rrg.model.Fixture;

import javafx.concurrent.Task;
import javafx.scene.Parent;

public class MainController {
	private final Model model;
	private final MainViewBuilder mainViewBuilder;
	private final MainInteractor interactor;
	
	public MainController() {
		this.model = new Model();
		this.interactor = new MainInteractor(model);
		this.mainViewBuilder = new MainViewBuilder(model, /* this::chooseFile, */ this::runGeneration, this::exportAsPdf, this::exportAsTxt);
	}

	public void chooseFile(Runnable innerRunnable) {
		// TODO
		// FileChooser fileChooser = new FileChooser();
		// fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		// File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());
		// if (selectedFile == null) {
		// return;
		// }
		// SET FILE TO MODEL
	}

	public void runGeneration(Runnable innerRunnable) {
		Task<Void> task = new Task<>() {

			@Override
			protected Void call() throws Exception {
				if (!model.getLines().isEmpty()) {
					interactor.runGenerationFromLines();
				} else {
					interactor.runGenerationFromFile();
				}
				return null;
			}
		};

		task.setOnSucceeded(evt -> {
			interactor.updateModelAfterGeneration();

			mainViewBuilder.resetCards();

			Fixture fixtures = model.getFixtures();
			mainViewBuilder.setGalleryPaneHeader(fixtures.getFixturesNumber());
			for (int i = 1; i <= fixtures.getFixturesNumber(); i++) {
				String matchday = "Matchday " + i;
				mainViewBuilder.addCard(matchday, fixtures.getMatchListAt(i));
			}

		});

		task.setOnFailed(evt -> {
			// TODO: error managing...
		});

		Thread taskThread = new Thread(task);
		taskThread.start();
	}

	public void exportAsPdf() {

	}

	public void exportAsTxt() {

	}

	public Parent getView() {
		return mainViewBuilder.build();
	}

}
