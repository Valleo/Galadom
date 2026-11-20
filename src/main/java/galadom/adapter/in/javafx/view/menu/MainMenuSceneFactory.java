package galadom.adapter.in.javafx.view.menu;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

final class MainMenuSceneFactory {
    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;

    Scene create(Stage stage) {
        StackPane root = new StackPane(new GalaxyBackground(stage), new MainMenuView(stage::close));
        return new Scene(root, WIDTH, HEIGHT);
    }
}