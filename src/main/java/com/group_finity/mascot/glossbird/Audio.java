package com.group_finity.mascot.glossbird;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.io.jvm.AudioPlayer;
import com.group_finity.mascot.Main;

import javax.sound.sampled.LineUnavailableException;

public class Audio implements Runnable{

    public String file;
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


    private void playSound(String file)
    {
        String imageSet = String.valueOf(Main.IMAGE_DIRECTORY.resolve(Main.getInstance().mainMascot.getImageSet()).resolve("sounds"));
        AudioDispatcher adp;
        imageSet+= "\\" + file + ".mp3";

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

        try {
            adp.addAudioProcessor(playerProcessor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        adp.run();


    }

}
