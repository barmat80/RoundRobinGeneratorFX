package com.bm.rrgfx;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import com.bm.rrgfx.constant.CSS;
import com.bm.rrgfx.mvci.MainController;

import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

public class RRG extends Application {
	private static final double PREF_WIDTH = 1024;
	private static final double PREF_HEIGHT = 800;
	private static String currentTheme;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		var antialiasing = Platform.isSupported(ConditionalFeature.SCENE3D) ? SceneAntialiasing.BALANCED : SceneAntialiasing.DISABLED;
		var scene = new Scene(new MainController().getView(), PREF_WIDTH, PREF_HEIGHT, false, antialiasing);
		scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource(CSS.PATH + CSS.STYLE)).toExternalForm());// base stylesheet

		// preferred theme or CSS.NORD_LIGHT if undefined
		currentTheme = getTheme();
		Application.setUserAgentStylesheet(this.getClass().getResource(CSS.PATH + currentTheme).toExternalForm());

		//primaryStage.getIcons().addAll(getIconList());
		primaryStage.setTitle("Round Robin Generator FX");
		primaryStage.setScene(scene);

		Platform.runLater(() -> {
			primaryStage.show();
			primaryStage.requestFocus();
		});
	}
	
	private String getTheme() {
		Parameters p = getParameters();
		List<String> pList = p.getRaw();
		return pList.get(0);
	}

	@Override
	public void init() {
		
	}
	
	@Override
	public void stop() {
		saveTheme();
	}

	private void saveTheme() {
		try {
			Properties p = new Properties();
			p.put("theme", currentTheme);

			String prefFile = System.getProperties().getProperty("user.dir") + File.separator + "rrgfx.ini";

			Utility.writePropertiesFile(p, prefFile);
		} catch (IOException e) {
			System.exit(-1);
		}
	}
}
