package galadom.adapter.in.javafx.view.menu;

import javafx.stage.Stage;

public final class MainMenuWindow {
    private final Stage stage;
    private final MenuMusic menuMusic;
    private final MainMenuSceneFactory sceneFactory;

    public MainMenuWindow(Stage stage) {
        this.stage = stage;
        menuMusic = new MenuMusic();
        sceneFactory = new MainMenuSceneFactory();
    }

    public void show() {
        stage.setTitle("Galadom");
        stage.setScene(sceneFactory.create(stage));
        stage.setFullScreen(true);
        stage.show();
        menuMusic.play();
    }

    public void close() {
        menuMusic.close();
    }
}