package com.group_finity.mascot.glossbird;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.io.jvm.AudioPlayer;
import com.group_finity.mascot.Main;
import com.group_finity.mascot.sound.SoundLoader;
import com.group_finity.mascot.sound.Sounds;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.openjdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class AudioManager {
    public static AudioManager instance = new AudioManager();
    public static AudioManager getInstance() {
        return instance;
    }

    public AudioManager()
    {
        super();
        LoadMascotSounds(Main.getInstance().getMainMascot().getImageSet());
    }

    AudioDispatcher adp = null;

    public void playSound(String soundName)
    {

       Audio play = new Audio();
       play.Play("ticksound");

    }
    void ManualLoad(String path)
    {


    }
    AdvancedPlayer player = null;
    boolean loaded = false;
    public void LoadMascotSounds(String mascot)
    {


loaded = true;

    }



}
