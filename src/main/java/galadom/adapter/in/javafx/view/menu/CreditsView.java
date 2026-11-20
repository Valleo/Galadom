package galadom.adapter.in.javafx.view.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

final class CreditsView extends BorderPane {
    CreditsView(Runnable onBack) {
        setStyle("-fx-padding: 32px; -fx-background-color: rgba(5, 7, 24, 0.72);");

        Button back = new Button("Retour");
        back.setStyle("-fx-font-size: 17px; -fx-text-fill: #f4edff;"
                + " -fx-background-color: rgba(49, 39, 86, 0.9);"
                + " -fx-border-color: rgba(194, 169, 255, 0.72); -fx-border-radius: 4px;"
                + " -fx-background-radius: 4px;");
        back.setOnMouseEntered(event -> back.setStyle("-fx-font-size: 18px; -fx-text-fill: white;"
                + " -fx-background-color: rgba(110, 82, 180, 0.96);"
                + " -fx-border-color: #e4d7ff; -fx-border-width: 2px;"
                + " -fx-border-radius: 4px; -fx-background-radius: 4px;"
                + " -fx-effect: dropshadow(gaussian, #a982ff, 18, 0.65, 0, 0);"));
        back.setOnMouseExited(event -> back.setStyle("-fx-font-size: 17px; -fx-text-fill: #f4edff;"
                + " -fx-background-color: rgba(49, 39, 86, 0.9);"
                + " -fx-border-color: rgba(194, 169, 255, 0.72); -fx-border-radius: 4px;"
                + " -fx-background-radius: 4px;"));
        back.setOnAction(event -> onBack.run());

        StackPane backContainer = new StackPane(back);
        backContainer.setAlignment(Pos.TOP_RIGHT);
        setTop(backContainer);

        Label title = new Label("Crédits");
        title.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 56px; -fx-font-weight: bold;"
                + " -fx-text-fill: #f4edff; -fx-effect: dropshadow(gaussian, #8e60ff, 24, 0.55, 0, 0);");
        Label creator = new Label("Créateur : Valleo\nAnnée de développement : 2026");
        creator.setStyle("-fx-font-size: 24px; -fx-text-fill: #f4edff; -fx-text-alignment: center;");
        VBox credits = new VBox(24, title, creator);
        credits.setAlignment(Pos.CENTER);
        setCenter(credits);
    }
}