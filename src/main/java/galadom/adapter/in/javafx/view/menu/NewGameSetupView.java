package galadom.adapter.in.javafx.view.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.function.IntConsumer;

final class NewGameSetupView extends BorderPane {
    private final GalaxySizeSelection selection = new GalaxySizeSelection();
    private final Label size = new Label();

    NewGameSetupView(Runnable onBack, IntConsumer onStart) {
        setStyle("-fx-padding: 32px; -fx-background-color: rgba(5, 7, 24, 0.72);");

        Button back = new Button("Retour");
        configureHover(back);
        back.setOnAction(event -> onBack.run());
        StackPane backContainer = new StackPane(back);
        backContainer.setAlignment(Pos.TOP_LEFT);
        setTop(backContainer);

        Label title = new Label("Nouvelle Partie");
        title.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 56px; -fx-font-weight: bold;"
                + " -fx-text-fill: #f4edff; -fx-effect: dropshadow(gaussian, #8e60ff, 24, 0.55, 0, 0);");
        Button previous = new Button("‹");
        Button next = new Button("›");
        configureHover(previous);
        configureHover(next);
        size.setStyle("-fx-font-size: 30px; -fx-text-fill: #f4edff;");
        previous.setOnAction(event -> {
            selection.selectPrevious();
            updateSize();
        });
        next.setOnAction(event -> {
            selection.selectNext();
            updateSize();
        });
        updateSize();

        HBox sizePicker = new HBox(24, previous, size, next);
        sizePicker.setAlignment(Pos.CENTER);
        VBox setup = new VBox(32, title, sizePicker);
        setup.setAlignment(Pos.CENTER);
        setCenter(setup);

        Button start = new Button("Start");
        start.setStyle(buttonStyle());
        start.setOnAction(event -> onStart.accept(selection.selectedSize()));
        StackPane startContainer = new StackPane(start);
        startContainer.setAlignment(Pos.BOTTOM_RIGHT);
        setBottom(startContainer);
    }

    private void updateSize() {
        size.setText(selection.selectedSize() + " étoiles");
    }

    private static String buttonStyle() {
        return "-fx-font-size: 19px; -fx-text-fill: #f4edff;"
                + " -fx-background-color: rgba(49, 39, 86, 0.9);"
                + " -fx-border-color: rgba(194, 169, 255, 0.72); -fx-border-radius: 4px;"
                + " -fx-background-radius: 4px;";
    }

    private static void configureHover(Button button) {
        button.setStyle(buttonStyle());
        button.setOnMouseEntered(event -> button.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"
                + " -fx-background-color: rgba(110, 82, 180, 0.96);"
                + " -fx-border-color: #e4d7ff; -fx-border-width: 2px;"
                + " -fx-border-radius: 4px; -fx-background-radius: 4px;"
                + " -fx-effect: dropshadow(gaussian, #a982ff, 18, 0.65, 0, 0);"));
        button.setOnMouseExited(event -> button.setStyle(buttonStyle()));
    }
}