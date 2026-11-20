package galadom.adapter.in.javafx.view;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainMenuView extends VBox {
    public MainMenuView(Runnable onQuit) {
        super(18);
        setAlignment(Pos.CENTER);
        setStyle("-fx-padding: 42px; -fx-background-color: rgba(5, 7, 24, 0.42);");

        Label title = new Label("Galadom");
        title.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 76px; -fx-font-weight: bold;"
                + " -fx-text-fill: #f4edff; -fx-effect: dropshadow(gaussian, #8e60ff, 24, 0.55, 0, 0);");

        Button newGame = menuButton("Nouveau Jeu");
        Button loadGame = menuButton("Charger Une Partie");
        Button credits = menuButton("Crédits");
        Button quit = menuButton("Quitter");
        newGame.setDisable(true);
        loadGame.setDisable(true);
        quit.setOnAction(event -> onQuit.run());
        credits.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Crédits");
            alert.setHeaderText("Galadom");
            alert.setContentText("Créateur : Valleo\nAnnée de développement : 2026");
            alert.showAndWait();
        });

        getChildren().addAll(title, newGame, loadGame, credits, quit);
    }

    private static Button menuButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(300);
        button.setPrefHeight(52);
        button.setStyle("-fx-font-size: 19px; -fx-text-fill: #f4edff;"
                + " -fx-background-color: rgba(49, 39, 86, 0.86);"
                + " -fx-border-color: rgba(194, 169, 255, 0.72); -fx-border-radius: 4px;"
                + " -fx-background-radius: 4px;");
        button.setOnMouseEntered(event -> {
            if (!button.isDisabled()) {
                button.setStyle("-fx-font-size: 20px; -fx-text-fill: white;"
                        + " -fx-background-color: rgba(110, 82, 180, 0.96);"
                        + " -fx-border-color: #e4d7ff; -fx-border-width: 2px;"
                        + " -fx-border-radius: 4px; -fx-background-radius: 4px;"
                        + " -fx-effect: dropshadow(gaussian, #a982ff, 18, 0.65, 0, 0);");
            }
        });
        button.setOnMouseExited(event -> button.setStyle("-fx-font-size: 19px; -fx-text-fill: #f4edff;"
                + " -fx-background-color: rgba(49, 39, 86, 0.86);"
                + " -fx-border-color: rgba(194, 169, 255, 0.72); -fx-border-radius: 4px;"
                + " -fx-background-radius: 4px;"));
        return button;
    }
}