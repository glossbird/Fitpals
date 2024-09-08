package com.group_finity.mascot.glossbird;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.Manager;
import com.group_finity.mascot.Mascot;
import com.group_finity.mascot.environment.MascotEnvironment;
import com.group_finity.mascot.glossbird.eggs.Juggle;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EggManager {

    List<String> pastActions = new ArrayList<>();
    Mascot mainMascot;
    MascotEnvironment environment;
    boolean fullscreenAppOpened;
    String fullscreenName;
    Juggle activeJuggle;
    String focusedWindow = "";
    float focusTime = 0;
    float speedTime = 0;
    float holdTime = 0;
    Point lastPos;


    boolean fullscreenEggActive = false;
    public EggManager()
    {
        super();
        mainMascot = Main.getInstance().getMainMascot();
        environment = mainMascot.getEnvironment();
    }

    public void CheckForEggs()
    {
        FocusCheck();
        if(HoldCheck())
        {
            SpeedCheck();
        }
        if(JuggleCheck())
        {
            activeJuggle = new Juggle(this);
        }
        if(!fullscreenAppOpened && FullscreenCheck())
        {
            fullscreenAppOpened = true;
            fullscreenName = Main.getInstance().fullscreenAppName();
            if(fullscreenName.isEmpty())
            {
                fullscreenAppOpened = false;
            }
        }
        if(fullscreenAppOpened)
        {
            WhichFullscreenApp(fullscreenName);
        }
        if(fullscreenEggActive)
        {
            if(!OngoingDialogue() && !fullscreenAppOpened)
            {
                fullscreenEggActive = false;
            }
        }
    }

    public boolean OngoingDialogue()
    {
        return Main.getInstance().getDiagMan().active;
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

    public String FocusedWindow()
    {
        WinDef.HWND foregroundWindow = Main.User32.INSTANCE.GetForegroundWindow();
        byte[] windowText = new byte[512];
        Main.User32.INSTANCE.GetWindowTextA(foregroundWindow.getPointer(), windowText, 512);
        String wText = Native.toString(windowText).trim();
        return wText;
    }

    public boolean HoldCheck()
    {
        if(Objects.equals(mainMascot.getBehavior().toString(), "Behavior(Dragged)"))
           {
               holdTime+=1;
                if(holdTime > 25 * 40)
                {
                    Main.getInstance().getDiagMan().safeSetActiveMessage("Please. Seriously. Put me down.");
                }
                else if(holdTime> 25 * 30)
                {
                    Main.getInstance().getDiagMan().safeSetActiveMessage("I'm gonna be sick, and throw up all over your windows, and it's going to be your fault.");
                }
                else if(holdTime >  25 *20)
                {
                    Main.getInstance().getDiagMan().safeSetActiveMessage("Seriously, put me down.");
                }
                else if(holdTime >  25 * 10)
                {
                    Main.getInstance().getDiagMan().safeSetActiveMessage("Put me down!");
                }
               return true;
           }
           else
           {
               holdTime = 0;
               return false;
           }
    }

    public void SpeedCheck()
    {
        if(lastPos == null)
        {
            lastPos = MouseInfo.getPointerInfo().getLocation();
        }
        else
        {
            Point newPos =  MouseInfo.getPointerInfo().getLocation();
            Double dist = newPos.distance(lastPos);
            if(dist > 50)
            {
                speedTime += .1f;
                if(speedTime > 2)
                {
                    Main.getInstance().getDiagMan().safeSetActiveMessage("Woah, fast huh!");
                }
            }
            else
            {
                speedTime = 0;
            }
            lastPos = newPos;
        }
    }



    public void FocusCheck()
    {
        if(Objects.equals(FocusedWindow(), focusedWindow))
        {
            focusTime += 1f;
        }
        else
        {
            focusedWindow = FocusedWindow();
            focusTime = 0;
        }

        if(focusTime > Manager.TICK_INTERVAL * 25)
        {
            if(focusedWindow.contains("Edge") && !OngoingDialogue())
            {
                Main.getInstance().getDiagMan().safeSetActiveMessage("So much internet..... AAA");
            }
        }

    }

    public boolean FullscreenCheck()
    {
        if(Main.isAppInFullScreen())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void WhichFullscreenApp(String name)
    {
        if(fullscreenEggActive)
        {
            return;
        }
        if(name.contains("Microsoft") && name.contains("Edge"))
        {
            Main.getInstance().getDiagMan().safeSetActiveMessage("Wow... Edgy, huh?");
            fullscreenEggActive = true;
        }
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
