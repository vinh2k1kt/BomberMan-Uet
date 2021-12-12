package uet.oop.bomberman.sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import uet.oop.bomberman.Level;
import uet.oop.bomberman.util.Constants;

public class Sound {
    public Clip clip;
    public static Map<String, URL> soundCollection = new HashMap<>();

    public static void initSound() throws MalformedURLException {
        soundCollection.put("Souls", new File("res/sound/Credit.wav").toURI().toURL());
        soundCollection.put("BombExplode", new File("res/sound/BombExplode.wav").toURI().toURL());
        soundCollection.put("Placing", new File("res/sound/BombPlacing.wav").toURI().toURL());
        soundCollection.put("Walking", new File("res/sound/Footstep.wav").toURI().toURL());
        soundCollection.put("Item", new File("res/sound/Item.wav").toURI().toURL());
        soundCollection.put("Depths", new File("res/sound/Depths.wav").toURI().toURL());
        soundCollection.put("Elhana", new File("res/sound/Elhana.wav").toURI().toURL());
        soundCollection.put("Forest", new File("res/sound/Forest.wav").toURI().toURL());
        soundCollection.put("Elder", new File("res/sound/Elder.wav").toURI().toURL());

        Constants.soundTrack.put(0, "Depths");
        Constants.soundTrack.put(1, "Elhana");
        Constants.soundTrack.put(2, "Forest");
        Constants.soundTrack.put(3, "Depths");
        Constants.soundTrack.put(4, "Forest");
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
