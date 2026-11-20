package galadom.adapter.in.javafx.view.menu;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

final class MainMenuSceneFactory {
    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;

    Scene create(Stage stage) {
        Pane screen = new StackPane();
        showMainMenu(screen, stage);
        StackPane root = new StackPane(new GalaxyBackground(stage), screen);
        return new Scene(root, WIDTH, HEIGHT);
    }

    private void showMainMenu(Pane screen, Stage stage) {
        screen.getChildren().setAll(new MainMenuView(stage::close, () -> showCredits(screen, stage)));
    }

    private void showCredits(Pane screen, Stage stage) {
        screen.getChildren().setAll(new CreditsView(() -> showMainMenu(screen, stage)));
    }
}