package com.group_finity.mascot.glossbird;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.Mascot;
import com.group_finity.mascot.environment.MascotEnvironment;
import com.group_finity.mascot.glossbird.eggs.Juggle;

import java.util.ArrayList;
import java.util.List;

public class EggManager {

    List<String> pastActions = new ArrayList<>();
    Mascot mainMascot;
    MascotEnvironment environment;

    Juggle activeJuggle;
    public EggManager()
    {
        super();
        mainMascot = Main.getInstance().getMainMascot();
        environment = mainMascot.getEnvironment();
    }

    public void CheckForEggs()
    {
        if(JuggleCheck())
        {
            activeJuggle = new Juggle(this);
        }
    }

    public boolean RecentActionExist(String checkAction)
    {
        boolean exists = false;

        for(int i = pastActions.size()-10; i < (pastActions.size()-1); i++)
        {
            if(i < 0)
            {
                return false;
            }
            if(pastActions.get(i).equals(checkAction))
            {
                exists = true;
                return exists;
            }
        }


        return exists;
    }


    public boolean JuggleCheck()
    {
        if(activeJuggle != null && activeJuggle.juggling)
        {
            return false;
        }

        if(RecentActionExist("Behavior(Thrown)") && RecentActionExist("Behavior(Dragged)"))
        {
            if(!environment.getFloor(true).isOn(mainMascot.getAnchor()))
            {
                return true;
            }
        }
            return false;
    }

    public void tick()
    {
        pastActions.add(mainMascot.getBehavior().toString());
        CheckForEggs();
        if(activeJuggle != null && activeJuggle.juggling)
        {
            activeJuggle.tick();
        }
    }





}
