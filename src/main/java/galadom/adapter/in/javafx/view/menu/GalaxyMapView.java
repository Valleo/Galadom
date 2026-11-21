package galadom.adapter.in.javafx.view.menu;

import galadom.domain.galaxy.GalaxyGraph;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

final class GalaxyMapView extends BorderPane {
    private static final double WORLD_WIDTH = 1600;
    private static final double WORLD_HEIGHT = 1200;
    private static final double ZOOM_FACTOR = 1.12;
    private static final double EDGE_MARGIN = 28;
    private static final double EDGE_SPEED = 520;

    private final Canvas canvas = new Canvas();
    private final AnimationTimer edgePanTimer;
    private final GalaxyGraph graph;
    private final List<Point2D> stars;
    private double scale = 1;
    private double offsetX;
    private double offsetY;
    private double dragX;
    private double dragY;
    private double pointerX;
    private double pointerY;
    private boolean dragging;
    private long previousFrame;

    GalaxyMapView(GalaxyGraph graph, Runnable onBack) {
        this.graph = graph;
        stars = createPositions(graph.starCount());
        setStyle("-fx-background-color: rgba(3, 5, 20, 0.96);");

        Button back = new Button("Retour");
        back.setOnAction(event -> onBack.run());
        Label title = new Label("Galaxie");
        title.setStyle("-fx-font-family: 'Georgia'; -fx-font-size: 30px; -fx-text-fill: #f4edff;"
                + " -fx-effect: dropshadow(gaussian, #8e60ff, 16, 0.55, 0, 0);");
        StackPane header = new StackPane(title, back);
        StackPane.setAlignment(back, javafx.geometry.Pos.CENTER_LEFT);
        header.setStyle("-fx-padding: 16px;");
        setTop(header);
        Pane mapArea = new Pane(canvas);
        setCenter(mapArea);
        canvas.widthProperty().bind(mapArea.widthProperty());
        canvas.heightProperty().bind(mapArea.heightProperty());
        canvas.widthProperty().addListener((observable, oldValue, newValue) -> resizeView());
        canvas.heightProperty().addListener((observable, oldValue, newValue) -> resizeView());
        canvas.setOnScroll(event -> zoomAt(event.getX(), event.getY(), event.getDeltaY() > 0));
        canvas.setOnMouseMoved(event -> {
            pointerX = event.getX();
            pointerY = event.getY();
        });
        canvas.setOnMousePressed(event -> {
            pointerX = event.getX();
            pointerY = event.getY();
            if (event.getButton() == MouseButton.PRIMARY && scale > minimumScale()) {
                dragging = true;
                dragX = event.getX();
                dragY = event.getY();
            }
        });
        canvas.setOnMouseDragged(event -> {
            pointerX = event.getX();
            pointerY = event.getY();
            if (dragging) {
                offsetX += event.getX() - dragX;
                offsetY += event.getY() - dragY;
                dragX = event.getX();
                dragY = event.getY();
                constrainOffsets();
                draw();
            }
        });
        canvas.setOnMouseReleased(event -> dragging = false);

        edgePanTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                animateEdgePan(now);
            }
        };
        sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene == null) {
                edgePanTimer.stop();
            } else {
                edgePanTimer.start();
            }
        });
        resizeView();
    }

    private List<Point2D> createPositions(int count) {
        List<Point2D> positions = new ArrayList<>(count);
        java.util.Random random = new java.util.Random();
        double radius = Math.min(WORLD_WIDTH, WORLD_HEIGHT) * 0.43;
        for (int i = 0; i < count; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double distance = radius * Math.sqrt(random.nextDouble());
            positions.add(new Point2D(WORLD_WIDTH / 2 + Math.cos(angle) * distance,
                    WORLD_HEIGHT / 2 + Math.sin(angle) * distance));
        }
        return positions;
    }

    private void resizeView() {
        double minimum = minimumScale();
        if (scale == 1 || scale < minimum) {
            scale = minimum;
        }
        constrainOffsets();
        draw();
    }

    private double minimumScale() {
        if (canvas.getWidth() <= 0 || canvas.getHeight() <= 0) {
            return 1;
        }
        return Math.min(canvas.getWidth() / WORLD_WIDTH, canvas.getHeight() / WORLD_HEIGHT);
    }

    private void zoomAt(double x, double y, boolean zoomIn) {
        double minimum = minimumScale();
        double nextScale = zoomIn ? Math.min(minimum * 10, scale * ZOOM_FACTOR)
                : Math.max(minimum, scale / ZOOM_FACTOR);
        if (nextScale == scale) {
            return;
        }
        double worldX = (x - offsetX) / scale;
        double worldY = (y - offsetY) / scale;
        scale = nextScale;
        offsetX = x - worldX * scale;
        offsetY = y - worldY * scale;
        constrainOffsets();
        draw();
    }

    private void animateEdgePan(long now) {
        if (previousFrame == 0) {
            previousFrame = now;
            return;
        }
        double elapsed = Math.min(0.05, (now - previousFrame) / 1_000_000_000.0);
        previousFrame = now;
        if (scale <= minimumScale() || dragging || !canvas.isHover()) {
            return;
        }
        double dx = pointerX < EDGE_MARGIN ? 1 : pointerX > canvas.getWidth() - EDGE_MARGIN ? -1 : 0;
        double dy = pointerY < EDGE_MARGIN ? 1 : pointerY > canvas.getHeight() - EDGE_MARGIN ? -1 : 0;
        if (dx != 0 || dy != 0) {
            offsetX += dx * EDGE_SPEED * elapsed;
            offsetY += dy * EDGE_SPEED * elapsed;
            constrainOffsets();
            draw();
        }
    }

    private void constrainOffsets() {
        double min = minimumScale();
        if (scale <= min) {
            scale = min;
            offsetX = (canvas.getWidth() - WORLD_WIDTH * scale) / 2;
            offsetY = (canvas.getHeight() - WORLD_HEIGHT * scale) / 2;
            return;
        }
        offsetX = Math.min(0, Math.max(canvas.getWidth() - WORLD_WIDTH * scale, offsetX));
        offsetY = Math.min(0, Math.max(canvas.getHeight() - WORLD_HEIGHT * scale, offsetY));
    }

    private void draw() {
        if (canvas.getWidth() <= 0 || canvas.getHeight() <= 0) {
            return;
        }
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        graphics.setFill(Color.rgb(3, 5, 20));
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphics.save();
        graphics.translate(offsetX, offsetY);
        graphics.scale(scale, scale);
        graphics.setStroke(Color.rgb(132, 151, 226, 0.5));
        graphics.setLineWidth(1.2 / scale);
        for (int star = 0; star < graph.starCount(); star++) {
            Point2D from = stars.get(star);
            for (int neighbor : graph.neighbors().get(star)) {
                if (neighbor > star) {
                    Point2D to = stars.get(neighbor);
                    graphics.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
                }
            }
        }
        for (Point2D star : stars) {
            graphics.setFill(Color.rgb(161, 130, 255, 0.2));
            graphics.fillOval(star.getX() - 9, star.getY() - 9, 18, 18);
            graphics.setFill(Color.rgb(240, 233, 255));
            graphics.fillOval(star.getX() - 3.2, star.getY() - 3.2, 6.4, 6.4);
        }
        graphics.restore();
    }
}