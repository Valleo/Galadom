package galadom.adapter.in.javafx.view;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GalaxyBackground extends ImageView {
    public GalaxyBackground(Stage stage) {
        super(GalaxyImageGenerator.create(1600, 900));
        setPreserveRatio(false);
        fitWidthProperty().bind(stage.widthProperty());
        fitHeightProperty().bind(stage.heightProperty());
    }
}