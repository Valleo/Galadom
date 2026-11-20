package galadom.adapter.in.javafx.view;

import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.stage.Stage;

public class GalaxyBackground extends ImageView {
    public GalaxyBackground(Stage stage) {
        super(GalaxyImageGenerator.create(1600, 900));
        setPreserveRatio(false);
        setScaleX(1.4);
        setScaleY(1.4);
        fitWidthProperty().bind(stage.widthProperty());
        fitHeightProperty().bind(stage.heightProperty());
        RotateTransition rotation = new RotateTransition(Duration.minutes(4), this);
        rotation.setByAngle(360);
        rotation.setCycleCount(RotateTransition.INDEFINITE);
        rotation.play();
    }
}