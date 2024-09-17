package com.group_finity.mascot.glossbird;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.Manager;
import com.group_finity.mascot.Mascot;
import com.group_finity.mascot.environment.MascotEnvironment;
import com.group_finity.mascot.glossbird.eggs.Juggle;
import com.group_finity.mascot.glossbird.eggs.ProcessChecker;
import com.sun.jna.Native;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;
import java.util.*;
import java.util.List;

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

    Map<String, Integer> focusMap = new HashMap<String, Integer>();

    List<String> blacklist = List.of(new String[]{"java","idea64","textinputhost","ApplicationFrameHost","explorer"});

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

        ProcessUpdate();
        ProcessCheck();
        if(HoldCheck())
        {
            SpeedCheck();
        }
        if(JuggleCheck())
        {
            activeJuggle = new Juggle(this);
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
        if(foregroundWindow == null)
        {
            return "";
        }
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
                    Main.getInstance().getDiagMan().safeSetActiveMessage("Woah, that's some serious speed!");
                }
            }
            else
            {
                speedTime = 0;
            }
            lastPos = newPos;
        }
    }

    public void IdleCheck()
    {

    }

    public String justExe(String input)
    {
        String delim = "\\\\";
        String[] filtered = input.split(delim);
        String withExe = filtered[filtered.length-1];
        withExe = withExe.split("\\.")[0];
        return withExe;
    }

    Map<String, ProcessChecker> processCheckerMap;

    public ProcessChecker GetProcessFromTitle(String title)
    {
        ProcessChecker empty = null;
        for (Map.Entry<String, ProcessChecker> entry : processCheckerMap.entrySet())
        {
            if(entry.getValue().getName().equals(title))
            {
                empty = entry.getValue();
            }
        }
        return empty;
    }

    public void ProcessUpdate()
    {
        String currentFocus = FocusedWindow();
        boolean firstUpdate;
        if(processCheckerMap == null)
        {
            processCheckerMap = new HashMap<String, ProcessChecker>();
            firstUpdate = true;
        } else {
            firstUpdate = false;
        }
        List<DesktopWindow> windows = WindowUtils.getAllWindows(true);
        processCheckerMap.forEach((name,process) -> {
            process.setChecked(false);
            if(firstUpdate)
            {
                process.setNewOpened(false);
            }
        });

        for (DesktopWindow window : windows) {
            ProcessChecker process;
            String exeName = (justExe(window.getFilePath()));
            if (processCheckerMap.containsKey(exeName)) {
                process = ((ProcessChecker) processCheckerMap.get(exeName));
            } else {
                process = new ProcessChecker(exeName);
                process.setName(window.getTitle());
                if(firstUpdate)
                {
                    process.setNewOpened(false);
                }
                processCheckerMap.put(exeName, process);
            }

            process.increaseTimeOpen(1);
            if (currentFocus.equals(window.getTitle())) {
                process.increaseTimeFocus(1);
                process.setFocused(true);
            } else {
                process.setFocused(false);
            }

            process.setChecked(true);

        }

        for (Map.Entry<String, ProcessChecker> entry : processCheckerMap.entrySet())
        {
            ProcessChecker process = entry.getValue();
            if(!process.isChecked() && process.isOpen()) // Program is closed
            {
                process.setOpen(false);
                process.setFocused(false);
                process.setFullscreen(false);
                process.setChecked(true);
                // OPTIONAL: If you want to reset run statistics on close, do so here!
            }
        }

    }

    public boolean CallEasterEgg(String handle, String condition)
    {
        if(OngoingDialogue())
        {
            return false;
        }
        Main.getInstance().getDiagMan().EasterEggTrigger(handle, condition);
        return true;
    }

    public boolean CallEasterEggConditionally(String handle, String condition, float number)
    {
        List<Speech> potentials = Main.getInstance().getDiagMan().GetAllSpeechWithCondition(handle,condition);
        potentials = Main.getInstance().getDiagMan().SortSpeechListByCondition(potentials);
        if(potentials.isEmpty())
        {
            return  false;
        }
        for(int i = 0; i< potentials.size(); i++)
        {
            if(potentials.get(i).getConditionNumber() < number)
            {
                Main.getInstance().getDiagMan().PlaySpeech(potentials.get(i));
                return  true;
            }
        }
        return false;
    }



    public void ProcessCheck()
    {
        for (Map.Entry<String, ProcessChecker> entry : processCheckerMap.entrySet())
        {
            ProcessChecker process = entry.getValue();
            if(process == null)
            {
                continue;
            }

            if (blacklist.contains(process.getHandle()))
            {
                continue;
            }

          //  System.out.println("checking " + process.ToString());

            if((process.isNewOpened() && process.isOpen()))
            {
                if(CallEasterEgg(process.getHandle(), "open"))
                {
                    process.setNewOpened(false);
                }
            }
            else if(!process.isOpen() && !process.isNewClosed())
            {
                if(CallEasterEgg(process.getHandle(), "close"))
                {
                    process.setNewClosed(true);
                    process.setNewOpened(true);
                }
            }
            else if(process.isOpen() && process.isFocused())
            {
                Speech check = Main.getInstance().getDiagMan().GetSpeechFromCondition(process.getHandle(), "runtime");
                if (check == null)
                {
                    continue;
                }
                CallEasterEggConditionally(process.getHandle(), "runtime",process.getTimeOpen());
            }
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
        focusMap.putIfAbsent("Browser", 0);

        if((focusedWindow.contains("Microsoft") && focusedWindow.contains("Edge")) || focusedWindow.contains("Firefox") || focusedWindow.contains("Chrome"))
        {
            focusMap.put("Browser", focusMap.get("Browser")+1);
        }

        focusMap.putIfAbsent(focusedWindow, 0);
        focusMap.put(focusedWindow, (int)focusMap.get(focusedWindow)+1);

        if(focusMap.containsKey("Browser") && focusMap.get("Browser") > 30)
        {
            Main.getInstance().getDiagMan().safeSetActiveMessage("Lots of internet for today, huh?");
            fullscreenEggActive = true;
        }

        if(focusedWindow.contains("Discord") && !OngoingDialogue())
        {

           // Main.getInstance().getDiagMan().EasterEggTrigger("discord","open");
        }

        if(focusTime > Manager.TICK_INTERVAL * 25)
        {
            if(focusedWindow.contains("Edge") && !OngoingDialogue())
            {
                Main.getInstance().getDiagMan().safeSetActiveMessage("So much internet..... AAA");
            }
        }

    }

    public void AppClosedCheck()
    {

    }


    public boolean FullscreenCheck()
    {
        if(Main.isAppInFullScreen())
        {
            Main.getInstance().getDiagMan().setFullscreen(true);
            return true;
        }
        else
        {
            Main.getInstance().getDiagMan().setFullscreen(false);
            return false;
        }
    }

    public void WhichFullscreenApp(String name)
    {
        if(fullscreenEggActive)
        {
            return;
        }

        if(name.contains("Fortnite"))
        {
            Main.getInstance().getDiagMan().safeSetActiveMessage("Let's get a battle royale!!");
        }
        else if(name.contains("Minecraft"))
        {
            Main.getInstance().getDiagMan().safeSetActiveMessage("Remember: don't dig straight down!");
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
