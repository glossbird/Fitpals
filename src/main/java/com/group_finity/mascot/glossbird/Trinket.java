package com.group_finity.mascot.glossbird;

import java.awt.*;

public class Trinket {
    public String name;
    public int id;
    public String imagePath;
    private Image loadedImg;
    public boolean claimed;
    public boolean loaded;
    public MascotData associatedMascot;

    public Trinket()
    {
        this.name = "";
        this.id = -1;
        this.imagePath = "";
        this.claimed = false;
        this.loaded = false;
    }

    public void Load(String name, int id, String imagePath, boolean claimed, MascotData mascot)
    {
        this.name = name;
        this.id = id;
        this.imagePath = imagePath;
        this.claimed = claimed;
        this.loaded = true;
        this.associatedMascot = mascot;
    }

    public void LoadImage()
    {

    }





}
