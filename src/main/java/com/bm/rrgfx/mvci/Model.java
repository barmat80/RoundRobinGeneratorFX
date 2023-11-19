package com.bm.rrgfx.mvci;

import com.bm.rrg.model.Fixture;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {
	private StringProperty filePath = new SimpleStringProperty();
	private StringProperty lines = new SimpleStringProperty();
	private final SimpleObjectProperty<Fixture> fixtures = new SimpleObjectProperty<>();

	public String getFilePath() {
		return filePath.get();
	}

	public StringProperty filePathProperty() {
		return filePath;
	}

	public void setFilePath(String fp) {
		this.filePath.set(fp);
	}

	public String getLines() {
		return lines.get();
	}

	public StringProperty linesProperty() {
		return lines;
	}

	public void setLines(String l) {
		this.lines.set(l);
	}

	public Fixture getFixtures() {
		return fixtures.get();
	}

	public SimpleObjectProperty<Fixture> fixturesProperty() {
		return fixtures;
	}

	public void setFixtures(Fixture f) {
		this.fixtures.set(f);
	}

	public void clearFixtures() {
		if (fixtures.get() != null)
			fixtures.get().clear();
	}
}
