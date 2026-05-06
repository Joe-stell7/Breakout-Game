import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundManager {

    public void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);

            if (!soundFile.exists()) {
                System.out.println("Sound file not found: " + filePath);
                return;
            }

            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception e) {
            System.out.println("Could not play sound: " + filePath);
            e.printStackTrace();
        }
    }

    public void playBrickSound() {
        playSound("sounds/brick.wav");
    }

    public void playLifeLostSound() {
        playSound("sounds/life_lost.wav");
    }

    public void playGameOverSound() {
        playSound("sounds/game_over.wav");
    }
}