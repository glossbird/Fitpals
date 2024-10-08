package com.group_finity.mascot.glossbird;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.GainProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.io.jvm.AudioPlayer;
import com.group_finity.mascot.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.File;

public class Audio implements Runnable{

    public String file;
    AudioDispatcher adp;
    Clip clip;
    public void run() {
        playSound(file);
    }
    public void Play(String file)
    {
        this.file = file;
        Thread newT = new Thread(this);
        newT.start();
    }

    void RandomShift()
    {

    }
    public void Stop()
    {
        adp.stop();
        clip.close();
    }

    private void playSoundClassic(String file)
    {
        String imageSet = String.valueOf(Main.IMAGE_DIRECTORY.resolve(Main.getInstance().mainMascot.getImageSet()).resolve("sounds"));
        imageSet+= "\\" + file + ".wav";

        try
        {
            File soundFile = new File(imageSet);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Play the sound
            clip.start();

        }

        catch (Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }

    }


    private void playSound(String file)
    {
        playSoundClassic(file);
        String imageSet = String.valueOf(Main.IMAGE_DIRECTORY.resolve(Main.getInstance().mainMascot.getImageSet()).resolve("sounds"));
        imageSet+= "\\" + file + ".mp3";

        return;
        /*
        try {
            adp = AudioDispatcherFactory.fromPipe((imageSet), 44100, 2048, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        final AudioProcessor playerProcessor;
        try {
            playerProcessor = new AudioPlayer(adp.getFormat(), 22050);

        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        final AudioProcessor gain;
        try {
            gain = new GainProcessor(.6);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            adp.addAudioProcessor(playerProcessor);
            adp.addAudioProcessor(gain);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        adp.run();

*/
    }

}
