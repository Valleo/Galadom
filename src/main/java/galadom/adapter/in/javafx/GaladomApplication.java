package galadom.adapter.in.javafx;

import galadom.adapter.in.javafx.view.GalaxyBackground;
import galadom.adapter.in.javafx.view.MainMenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GaladomApplication extends Application {
    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane(new GalaxyBackground(stage), new MainMenuView(stage::close));
        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("Galadom");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}