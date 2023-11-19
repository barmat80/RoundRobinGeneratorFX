package com.bm.rrgfx.mvci;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;

import com.bm.rrg.model.Competitor;
import com.bm.rrg.model.Match;

import atlantafx.base.controls.Card;
import atlantafx.base.controls.Tile;
import atlantafx.base.layout.DeckPane;
import atlantafx.base.theme.Styles;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Builder;
import javafx.util.Duration;

public class MainViewBuilder implements Builder<Region> {
	private static final int NODES_INSETS_TOP = 5;
	private static final int NODES_INSETS_RIGHT = 30;
	private static final int NODES_INSETS_BOTTOM = 0;
	private static final int NODES_INSETS_LEFT = 30;

	private static final int CENTRAL_CELL_HEIGHT = 350;
	private static final int CENTRAL_CELL_HEIGHT1 = 300;

	private final static double DEFAULT_BTN_WIDTH = 34.0;

	private GridPane galleryPane;

	private final Model model;
	private final Consumer<Runnable> runAction;
	private Runnable pdfAction;
	private Runnable txtAction;

	public MainViewBuilder(Model model, Consumer<Runnable> runAction, Runnable pdfAction, Runnable txtAction) {
		this.model = model;
		this.runAction = runAction;
		this.pdfAction = pdfAction;
		this.txtAction = txtAction;
	}

	@Override
	public Region build() {
		var topPane = buildTop();

		var sep = new Separator(Orientation.HORIZONTAL);
		sep.setPadding(new Insets(3, 0, 3, 0));

		buildGalleryPane();

		return buildVBox(topPane, sep, galleryPane);
	}

	private VBox buildVBox(Node... nodes) {
		var vbox = new VBox(nodes);
		// vbox.setPrefHeight(CONTAINER_HEIGHT);
		// vbox.setPrefWidth(CONTAINER_WIDTH);
		setMarginOnNodes(nodes);
		return vbox;
	}

	private void setMarginOnNodes(Node... nodes) {
		Insets insets = new Insets(NODES_INSETS_TOP, NODES_INSETS_RIGHT, NODES_INSETS_BOTTOM, NODES_INSETS_LEFT);
		for (Node n : nodes) {
			VBox.setMargin(n, insets);
		}
	}

	private Node buildTop() {
		var titleText = new Text("Competitors");
		titleText.getStyleClass().addAll(Styles.TITLE_3);

		var caption = new Text("Add one Competitor per line");
		caption.getStyleClass().add(Styles.TEXT_SMALL);

		var stackPane = buildStackPane();

		var runBtn = buildRunButton();

		GridPane gridPane = createGridPane(CENTRAL_CELL_HEIGHT);
		gridPane.add(titleText, 0, 0);
		gridPane.add(caption, 0, 1);
		gridPane.add(stackPane, 1, 1);
		gridPane.add(runBtn, 2, 1);
		GridPane.setMargin(caption, new Insets(10, 0, 0, 0));
		GridPane.setValignment(caption, VPos.TOP);
		GridPane.setHalignment(caption, HPos.RIGHT);

		return gridPane;
	}

	private StackPane buildStackPane() {
		var textArea = new TextArea();

		textArea.setPromptText("Add one Competitor per line");
		textArea.setWrapText(true);
		textArea.getStyleClass().addAll(Styles.TEXT_SMALL);
		textArea.managedProperty().bind(textArea.visibleProperty());
		textArea.textProperty().bindBidirectional(model.linesProperty());

		var stackPane = new StackPane();
		stackPane.getChildren().add(textArea);
		return stackPane;
	}

	private Node buildRunButton() {
		// TODO: to disable buttons while running action see
		// https://www.pragmaticcoding.ca/javafx/Mvci-Introduction#the-viewbuilder-1
		var btn = new Button(null, new FontIcon(Material2MZ.PLAY_ARROW));
		btn.getStyleClass().addAll(Styles.BUTTON_ICON, Styles.BUTTON_OUTLINED, Styles.SUCCESS);
		btn.setContentDisplay(ContentDisplay.RIGHT);
		btn.setOnAction(e -> {
			runAction.accept(null);
		});
		btn.setMaxWidth(DEFAULT_BTN_WIDTH);
		btn.setMinWidth(DEFAULT_BTN_WIDTH);
		btn.setPrefWidth(DEFAULT_BTN_WIDTH);
		GridPane.setValignment(btn, VPos.BOTTOM);
		GridPane.setHalignment(btn, HPos.LEFT);
		return btn;
	}

	private GridPane createGridPane(int centralCellHeight) {
		var gridPane = new GridPane();
		gridPane.setHgap(5);// horizontal gap between columns
		gridPane.setVgap(5);// vertical gap between columns
		// gridPane.setGridLinesVisible(true);

		gridPane.getColumnConstraints().addAll(getColumnsConstraints(Integer.valueOf(15), Integer.valueOf(70), Integer.valueOf(15)));
		gridPane.getRowConstraints().addAll(getRowConstraints(centralCellHeight));
		return gridPane;
	}

	private List<ColumnConstraints> getColumnsConstraints(Integer... percent) {
		List<ColumnConstraints> l = new ArrayList<>();
		for (Integer p : percent) {
			ColumnConstraints cc = new ColumnConstraints();
			cc.setPercentWidth(p);
			l.add(cc);
		}

		return l;
	}

	private List<RowConstraints> getRowConstraints(int centralCellHeight) {
		RowConstraints defaultRowConstraint = new RowConstraints();
		RowConstraints centralCellConstraint = new RowConstraints(centralCellHeight);

		List<RowConstraints> l = new ArrayList<>();
		l.add(defaultRowConstraint);
		l.add(centralCellConstraint);
		l.add(defaultRowConstraint);

		return l;
	}

	private void buildGalleryPane() {
		var titleText = new Text("Rounds");
		titleText.getStyleClass().addAll(Styles.TITLE_3);

		// ToolBar toolbar = new ToolBar();
		// toolbar.addGeneric("Export as PDF", pdfAction, Material2SharpMZ.PICTURE_AS_PDF, Styles.BUTTON_ICON, Styles.FLAT);
		// toolbar.addGeneric("Export as TXT", txtAction, Material2SharpMZ.TEXT_SNIPPET, Styles.BUTTON_ICON, Styles.FLAT);

		var deck = new DeckPane();
		deck.setAnimationDuration(Duration.millis(350));

		// circularly returns the next item from the deck
		Supplier<Node> nextItem = buildSupplier(deck);

		var rightBtn = buildNavButton("forward", deck, nextItem);
		var leftBtn = buildNavButton("back", deck, nextItem);

		galleryPane = createGridPane(CENTRAL_CELL_HEIGHT1);
		galleryPane.add(titleText, 0, 0);
		galleryPane.add(leftBtn, 0, 1);
		galleryPane.add(deck, 1, 1);
		galleryPane.add(rightBtn, 2, 1);
		// galleryPane.add(toolbar, 2, 0);
		GridPane.setValignment(rightBtn, VPos.CENTER);
		GridPane.setHalignment(rightBtn, HPos.LEFT);
		GridPane.setValignment(leftBtn, VPos.CENTER);
		GridPane.setHalignment(leftBtn, HPos.RIGHT);
		// GridPane.setHalignment(toolbar, HPos.LEFT);
	}

	private Supplier<Node> buildSupplier(DeckPane deck) {
		return () -> {
			if (deck.getChildren().size() > 0) {
				var topNode = deck.getTopNode();
				topNode.setVisible(false);

				int nextIndex = (deck.getChildren().indexOf(topNode) + 1) % deck.getChildren().size();
				var nextNode = deck.getChildren().get(nextIndex);
				nextNode.setVisible(true);
				return nextNode;
			}
			return null;
		};
	}

	private Node buildNavButton(String navDirection, DeckPane deck, Supplier<Node> nextItem) {
		FontIcon fi = navDirection.equals("forward") ? new FontIcon(Material2AL.ARROW_FORWARD_IOS) : new FontIcon(Material2AL.ARROW_BACK_IOS);
		var btn = new Button(null, fi);
		btn.getStyleClass().addAll(Styles.BUTTON_ICON, Styles.FLAT);
		btn.setOnAction(e -> {
			if (navDirection.equals("forward")) {
				deck.slideRight(nextItem.get());
			} else {
				deck.slideLeft(nextItem.get());
			}
		});
		btn.setMaxWidth(DEFAULT_BTN_WIDTH);
		btn.setMinWidth(DEFAULT_BTN_WIDTH);
		btn.setPrefWidth(DEFAULT_BTN_WIDTH);
		return btn;
	}

	public void setGalleryPaneHeader(int numOfRounds) {
		ObservableList<Node> nodes = galleryPane.getChildren();
		for (Node n : nodes) {
			if (n instanceof Text t) {
				t.setText("Rounds: " + numOfRounds);
			}
		}
	}

	public void resetCards() {
		ObservableList<Node> nodes = galleryPane.getChildren();
		for (Node n : nodes) {
			if (n instanceof DeckPane dp) {
				ObservableList<Node> cards = dp.getChildren();
				ObservableList<Node> cardsToRemove = FXCollections.observableArrayList();
				for (Node card : cards) {
					card.setVisible(false);
					cardsToRemove.add(card);
				}
				cards.removeAll(cardsToRemove);
			}
		}
	}

	public void addCard(String cardHeader, ArrayList<Match> l) {
		Tile header = new Tile(cardHeader, "");

		var body = new VBox();

		GridPane restCompetitorRow = null;
		for (Match m : l) {
			if (m.isRest()) {
				restCompetitorRow = buildRow(m.getFakeCompetitor(), m.getRealCompetitor());
			} else {
				var row = buildRow(m.getCompetitor1(), m.getCompetitor2());
				var sep = new Separator(Orientation.HORIZONTAL);
				sep.setPadding(new Insets(2, 0, 2, 0));
				body.getChildren().addAll(row, sep);
			}
		}

		// Add Rest always as last element
		if (restCompetitorRow != null) {
			var sep = new Separator(Orientation.HORIZONTAL);
			sep.setPadding(new Insets(2, 0, 2, 0));
			body.getChildren().addAll(restCompetitorRow, sep);
		}

		var card = new Card();
		card.getStyleClass().add(Styles.ELEVATED_3);
		card.setHeader(header);
		card.setBody(body);

		AnchorPane.setTopAnchor(card, 40.0);
		AnchorPane.setRightAnchor(card, 30.0);
		AnchorPane.setBottomAnchor(card, 20.0);
		AnchorPane.setLeftAnchor(card, 40.0);

		setUnvisible(card);
	}

	private GridPane buildRow(Competitor competitor1, Competitor competitor2) {
		var row = new GridPane();
		// row.setGridLinesVisible(true);
		row.getColumnConstraints().addAll(getColumnsConstraints(Integer.valueOf(40), Integer.valueOf(20), Integer.valueOf(40)));

		Text l1 = new Text(competitor1.getName());
		GridPane.setHalignment(l1, HPos.RIGHT);
		row.add(l1, 0, 0);

		Text l2 = new Text(competitor2.getName());
		GridPane.setHalignment(l2, HPos.LEFT);

		// System.out.println("found rest: " + (competitor1.isRest() || competitor2.isRest()));
		if (competitor1.isRest() || competitor2.isRest()) {
			l1.getStyleClass().add(Styles.TEXT_MUTED);
			l2.getStyleClass().add(Styles.TEXT_MUTED);

			row.add(l2, 1, 0, 2, 1);
		} else {
			Text v = new Text("v");
			v.getStyleClass().addAll(Styles.TEXT_BOLDER, Styles.TEXT_MUTED);
			GridPane.setHalignment(v, HPos.CENTER);

			row.add(v, 1, 0);
			row.add(l2, 2, 0);
		}

		return row;
	}

	private void setUnvisible(Card card) {
		ObservableList<Node> nodes = galleryPane.getChildren();
		for (Node n : nodes) {
			if (n instanceof DeckPane dp) {
				dp.getChildren().add(card);

				if (dp.getChildren().size() > 1) {
					card.setVisible(false);
				}
			}
		}
	}

}
