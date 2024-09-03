package com.group_finity.mascot.glossbird;

import com.group_finity.mascot.Main;

public class AudioManager {
    public static AudioManager instance = new AudioManager();
    public static AudioManager getInstance() {
        return instance;
    }

    public AudioManager()
    {
        super();
    }

    public void playSound(String soundName)
    {

    }

    public void LoadMascotSounds(String mascot)
    {
        String imageSet = Main.getInstance().getMainMascot().getImageSet();
        imageSet += "/sounds/";

     //   SoundLoader.load("tick",.5f);
    }



}
