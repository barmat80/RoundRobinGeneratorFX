package com.bm.widgets;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;

import atlantafx.base.theme.Styles;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Tooltip;

public class ToolBar extends ButtonBar {
	private final static double DEFAULT_WIDTH = 34.0;
	private double width;

	public ToolBar() {
		this(DEFAULT_WIDTH);
	}

	public ToolBar(double w) {
		width = w;
		setButtonMinWidth(width);
	}

	public void addNew(Runnable action) {
		addNew("", action);
	}

	public void addNew(String tooltip, Runnable action) {
		addGeneric(tooltip, action, Material2AL.ADD, Styles.FLAT, Styles.ACCENT);
	}

	public void addEdit(Runnable editWindowLauncher) {
		addEdit("", editWindowLauncher);
	}

	public void addEdit(String tooltip, Runnable action) {
		addGeneric(tooltip, action, Material2AL.EDIT, Styles.FLAT, Styles.ACCENT);
	}

	public void addDelete(Runnable action) {
		addDelete("", action);
	}

	public void addDelete(String tooltip, Runnable action) {
		addGeneric(tooltip, action, Material2AL.DELETE, Styles.FLAT, Styles.DANGER);
	}

	public void addGeneric(String label, Runnable action, Ikon ico, String... btnStyles) {
		Button btn = createButton(label, action, ico, btnStyles);
		getButtons().add(btn);
	}

	private Button createButton(String tooltip, Runnable action, Ikon ico, String... btnStyles) {
		FontIcon fi = new FontIcon(ico);
		Button btn = new Button();

		if (tooltip != null && !tooltip.isEmpty()) {
			Tooltip tt = new Tooltip(tooltip);
			btn.setTooltip(tt);
		}

		btn.setMaxWidth(width);
		btn.setMinWidth(width);
		btn.setPrefWidth(width);
		btn.setGraphic(fi);
		btn.getStyleClass().addAll(btnStyles);

		if (action != null) {
			btn.setOnAction(evt -> {
				action.run();
			});
		}

		return btn;
	}

}
