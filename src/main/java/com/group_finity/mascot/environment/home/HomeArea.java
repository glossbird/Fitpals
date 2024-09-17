package com.group_finity.mascot.environment.home;

import com.group_finity.mascot.environment.Area;

public class HomeArea extends Area {


    HomeUI linked_UI;
    public HomeArea(HomeUI UI)
    {
        super();
        this.linked_UI = UI;
    }

    public void RefreshLocation()
    {
        this.set(linked_UI.GetBounds());
    }



}
