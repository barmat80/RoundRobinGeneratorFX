package com.bm.rrgfx;

import java.util.Objects;

import com.bm.rrgfx.constant.CSS;

import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

public class RRG extends Application {
	private static final double PREF_WIDTH = 1024;
	private static final double PREF_HEIGHT = 800;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		var antialiasing = Platform.isSupported(ConditionalFeature.SCENE3D) ? SceneAntialiasing.BALANCED : SceneAntialiasing.DISABLED;
		var scene = new Scene(new MainController().getView(), PREF_WIDTH, PREF_HEIGHT, false, antialiasing);
		scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource(CSS.PATH + CSS.STYLE)).toExternalForm());

		Application.setUserAgentStylesheet(this.getClass().getResource(CSS.PATH + CSS.NORD_LIGHT).toExternalForm());

		//primaryStage.getIcons().addAll(getIconList());
		primaryStage.setTitle("Round Robin Generator FX");
		primaryStage.setScene(scene);

		Platform.runLater(() -> {
			primaryStage.show();
			primaryStage.requestFocus();
		});
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void stop() {
		
	}
}
