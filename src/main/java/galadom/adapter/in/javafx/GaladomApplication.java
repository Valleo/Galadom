package galadom.adapter.in.javafx;

import galadom.adapter.in.javafx.view.menu.MainMenuWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class GaladomApplication extends Application {
    private MainMenuWindow mainMenuWindow;

    @Override
    public void start(Stage stage) {
        mainMenuWindow = new MainMenuWindow(stage);
        mainMenuWindow.show();
    }

    @Override
    public void stop() {
        if (mainMenuWindow != null) {
            mainMenuWindow.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}