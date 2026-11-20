package galadom.adapter.in.javafx.view.menu;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

final class MenuMusic {
    private static final String MUSIC_URL = "https://opengameart.org/sites/default/files/title.mp3";
    private static final double VOLUME = 0.25;

    private final MediaPlayer player;

    MenuMusic() {
        player = new MediaPlayer(new Media(MUSIC_URL));
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setVolume(VOLUME);
    }

    void play() {
        player.play();
    }

    void close() {
        player.dispose();
    }
}