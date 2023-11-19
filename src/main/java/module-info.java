module RRG {
	// needed for JavaFX
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.swing;
	requires javafx.base;

	requires transitive javafx.graphics;

	requires java.desktop;

	requires atlantafx.base;
	/*-requires org.controlsfx.controls;*/
	requires org.kordamp.ikonli.core;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.ikonli.material2;

	/*-requires org.apache.logging.log4j;
	
	opens com.maemlab.commisfx.data.pojo to javafx.base;*/
	opens com.bm.rrgfx to javafx.fxml;
	// opens com.maemlab.commis.view to javafx.fxml;

	exports com.bm.rrgfx;
	exports com.bm.rrg.model;
}