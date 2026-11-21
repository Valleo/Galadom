package galadom.adapter.in.javafx.view.menu;

import galadom.domain.galaxy.GalaxyGenerator;
import galadom.domain.galaxy.GalaxyGraph;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        screen.getChildren().setAll(new MainMenuView(() -> showNewGame(screen, stage), stage::close,
                () -> showCredits(screen, stage)));
    }

    private void showNewGame(Pane screen, Stage stage) {
        NewGameSetupView[] setup = new NewGameSetupView[1];
        setup[0] = new NewGameSetupView(() -> showMainMenu(screen, stage),
                size -> showGalaxy(screen, size, () -> screen.getChildren().setAll(setup[0])));
        screen.getChildren().setAll(setup[0]);
    }

    private void showGalaxy(Pane screen, int size, Runnable onBack) {
        try {
            GalaxyGraph galaxy = new GalaxyGenerator(java.util.random.RandomGenerator.getDefault()).generate(size);
            screen.getChildren().setAll(new GalaxyMapView(galaxy, onBack));
        } catch (RuntimeException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Génération impossible");
            alert.setHeaderText("La galaxie n’a pas pu être générée.");
            alert.setContentText(exception.getMessage() == null
                    ? "Une erreur inattendue est survenue pendant la génération." : exception.getMessage());
            alert.showAndWait();
        }
    }

    private void showCredits(Pane screen, Stage stage) {
        screen.getChildren().setAll(new CreditsView(() -> showMainMenu(screen, stage)));
    }
}