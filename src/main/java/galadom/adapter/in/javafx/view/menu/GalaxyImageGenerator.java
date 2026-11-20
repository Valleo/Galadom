package galadom.adapter.in.javafx.view.menu;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

final class GalaxyImageGenerator {
    private GalaxyImageGenerator() {
    }

    static WritableImage create(int width, int height) {
        WritableImage image = new WritableImage(width, height);
        double centerX = width * 0.52;
        double centerY = height * 0.47;
        double scaleX = width * 0.43;
        double scaleY = height * 0.39;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double dx = (x - centerX) / scaleX;
                double dy = (y - centerY) / scaleY;
                double radius = Math.sqrt(dx * dx + dy * dy);
                double angle = Math.atan2(dy, dx);
                double arms = Math.pow(Math.max(0, Math.cos(angle * 3 - radius * 9)), 9);
                double core = Math.exp(-radius * 5.2);
                double nebula = arms * Math.exp(-radius * 1.15);
                double stars = Math.random() > 0.997 ? 0.8 : 0;
                double intensity = Math.min(1, core * 0.82 + nebula * 0.5 + stars);

                Color color = Color.color(
                        0.012 + intensity * 0.72,
                        0.016 + intensity * 0.45,
                        0.055 + intensity * 0.92
                );
                image.getPixelWriter().setColor(x, y, color);
            }
        }
        return image;
    }
}