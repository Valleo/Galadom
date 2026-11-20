package galadom.adapter.in.javafx;

import galadom.adapter.in.javafx.view.GalaxyBackground;
import galadom.adapter.in.javafx.view.MainMenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class GaladomApplication extends Application {
    private MediaPlayer menuMusic;

    @Override
    public void start(Stage stage) {
        menuMusic = new MediaPlayer(new Media("https://opengameart.org/sites/default/files/title.mp3"));
        menuMusic.setCycleCount(MediaPlayer.INDEFINITE);
        menuMusic.setVolume(0.25);
        menuMusic.play();
        StackPane root = new StackPane(new GalaxyBackground(stage), new MainMenuView(stage::close));
        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("Galadom");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    @Override
    public void stop() {
        if (menuMusic != null) {
            menuMusic.dispose();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}