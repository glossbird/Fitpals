package com.group_finity.mascot.action;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.Manager;
import com.group_finity.mascot.Mascot;
import com.group_finity.mascot.animation.Animation;
import com.group_finity.mascot.environment.Area;
import com.group_finity.mascot.environment.home.BackgroundPanel;
import com.group_finity.mascot.environment.home.UI.CustomTitlebar;
import com.group_finity.mascot.exception.LostGroundException;
import com.group_finity.mascot.exception.VariableException;
import com.group_finity.mascot.glossbird.eggs.DestroyWindow;
import com.group_finity.mascot.script.VariableMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Destroy extends Animate {
    private static final Logger log = Logger.getLogger(Select.class.getName());
    public static final String PARAMETER_DESTROYEND = "End";

    private static final String DEFAULT_DESTROYEND = "";

    boolean userClicked = false;
    boolean openedOverlay;
    private long activeWindowId = 0;

    public Destroy(ResourceBundle schema, List<Animation> animations, VariableMap context) {
        super(schema, animations, context);
    }

    private String getDestroyInfo() throws VariableException {
        return eval(getSchema().getString(PARAMETER_DESTROYEND), String.class, DEFAULT_DESTROYEND);
    }


    /*public Destroy(ResourceBundle schema, VariableMap context, Action... actions) {
        super(schema, context, actions);
        userClicked = false;
        log.log(Level.INFO,"Creating destroy");
    }*/


    @Override
    public void init(final Mascot mascot) throws VariableException {
        super.init(mascot);
        log.log(Level.INFO,"Init destroy " + getDestroyInfo());
        activeWindowId = getEnvironment().getActiveWindowId();
        if(getDestroyInfo().equals("End"))
        {
            System.out.println("It was the end");
            DestroyWindow.getInstance().CloseWindow();
            Main.getInstance().eggMan.CallEasterEgg("EGG:destroy","close");
        }
        else if(getDestroyInfo().equals("Start"))
        {
            Main.getInstance().eggMan.CallEasterEgg("EGG:destroy","open");
        }

    }

    @Override
    public boolean hasNext() throws VariableException {
        if (!Boolean.parseBoolean(Main.getInstance().getProperties().getProperty("Throwing", "true"))) {
            return false;
        }
        if(!userClicked)
        {
            return  true;
        }

        final boolean isSameWindow = activeWindowId == getEnvironment().getActiveWindowId();
        final boolean ieVisible = getEnvironment().getActiveIE().isVisible();

        return super.hasNext() && isSameWindow && ieVisible;
    }

    boolean checkUserClicked()
    {

        if(getMascot().IsWindowFocused())
        {
            if(getEnvironment().getActiveIETitle().equals("NOODS"))
            {
                Main.getInstance().getManager().setBehaviorAll("GoHome");
            }
            else
            {
                activeWindowId = getEnvironment().getActiveWindowId();
                DestroyWindow.getInstance().OpenWindow(activeWindowId);
                log.log(Level.INFO, "User clicked");
            }

            return true;
        }

        return false;
    }



    @Override
    protected void tick() throws LostGroundException, VariableException {
        if(!userClicked)
        {
            userClicked = checkUserClicked();
        }

        final Area activeIE = getEnvironment().getActiveIE();

        if (!activeIE.isVisible()) {
            log.log(Level.INFO, "IE was hidden ({0}, {1})", new Object[]{getMascot(), this});
            throw new LostGroundException();
        }

    }


}
