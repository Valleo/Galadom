package galadom.adapter.in.javafx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GaladomApplication extends Application {
    @Override
    public void start(Stage stage) {
        ImageView galaxy = new ImageView(createGalaxyImage(1600, 900));
        galaxy.setPreserveRatio(false);
        galaxy.fitWidthProperty().bind(stage.widthProperty());
        galaxy.fitHeightProperty().bind(stage.heightProperty());

        Button newGame = menuButton("Nouveau Jeu");
        Button loadGame = menuButton("Charger Une Partie");
        Button credits = menuButton("Crédits");
        Button quit = menuButton("Quitter");
        newGame.setDisable(true);
        loadGame.setDisable(true);
        credits.setDisable(true);
        quit.setOnAction(event -> stage.close());

        javafx.scene.control.Label title = new javafx.scene.control.Label("Galadom");
        title.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 76px; -fx-font-weight: bold;"
                + " -fx-text-fill: #f4edff; -fx-effect: dropshadow(gaussian, #8e60ff, 24, 0.55, 0, 0);");

        VBox menu = new VBox(18, title, newGame, loadGame, credits, quit);
        menu.setAlignment(Pos.CENTER);
        menu.setStyle("-fx-padding: 42px; -fx-background-color: rgba(5, 7, 24, 0.42);");

        javafx.scene.layout.StackPane root = new javafx.scene.layout.StackPane(galaxy, menu);
        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("Galadom");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    private static Button menuButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(300);
        button.setPrefHeight(52);
        button.setStyle("-fx-font-size: 19px; -fx-text-fill: #f4edff;"
                + " -fx-background-color: rgba(49, 39, 86, 0.86);"
                + " -fx-border-color: rgba(194, 169, 255, 0.72); -fx-border-radius: 4px;"
                + " -fx-background-radius: 4px;");
        return button;
    }

    private static WritableImage createGalaxyImage(int width, int height) {
        WritableImage image = new WritableImage(width, height);
        double centerX = width * 0.52;
        double centerY = height * 0.47;
        double scaleX = width * 0.43;
        double scaleY = height * 0.39;
        double armOffset = 0.0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double dx = (x - centerX) / scaleX;
                double dy = (y - centerY) / scaleY;
                double radius = Math.sqrt(dx * dx + dy * dy);
                double angle = Math.atan2(dy, dx);
                double arms = Math.pow(Math.max(0, Math.cos(angle * 3 - radius * 9 + armOffset)), 9);
                double core = Math.exp(-radius * 5.2);
                double nebula = arms * Math.exp(-radius * 1.15);
                double stars = Math.random() > 0.997 ? 0.8 : 0;
                double intensity = Math.min(1, core * 0.82 + nebula * 0.5 + stars);

                Color color = Color.color(
                        0.012 + intensity * 0.72,
                        0.016 + intensity * 0.45,
                        0.055 + intensity * 0.92
                );
                image.getPixelWriter().setColor(x, y, color);
            }
        }
        return image;
    }

    public static void main(String[] args) {
        launch(args);
    }
}