package uet.oop.bomberman.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Sound {
    public Clip clip;
    public static Map<String, URL> soundCollection = new HashMap<>();

    public Sound() throws MalformedURLException {
        soundCollection.put("Main", new File("res/sound/03_Stage Theme.wav").toURI().toURL());
        soundCollection.put("BombExplode", new File("res/sound/BombExplode.wav").toURI().toURL());
        soundCollection.put("Placing", new File("res/sound/BombPlacing.wav").toURI().toURL());
        soundCollection.put("Walking", new File("res/sound/Walk.wav").toURI().toURL());
    }

    public void setFile(String title) {
        try {
            AudioInputStream audioFile = AudioSystem.getAudioInputStream(soundCollection.get(title));
            clip = AudioSystem.getClip();
            clip.open(audioFile);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
