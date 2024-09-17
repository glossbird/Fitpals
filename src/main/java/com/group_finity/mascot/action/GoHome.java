package com.group_finity.mascot.action;

import com.group_finity.mascot.Main;
import com.group_finity.mascot.Mascot;
import com.group_finity.mascot.animation.Animation;
import com.group_finity.mascot.environment.home.HomeUI;
import com.group_finity.mascot.exception.LostGroundException;
import com.group_finity.mascot.exception.VariableException;
import com.group_finity.mascot.glossbird.HomeData;
import com.group_finity.mascot.script.VariableMap;

import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Logger;

/**
 * Looking action.
 *
 * @author Yuki Yamada of <a href="http://www.group-finity.com/Shimeji/">Group Finity</a>
 * @author Shimeji-ee Group
 */
public class GoHome extends Animate {
    private static final Logger log = Logger.getLogger(Look.class.getName());

    public static final HomeUI home = Main.getInstance().getHome();
    public GoHome(ResourceBundle schema, final List<Animation> animations, final VariableMap context) {
        super(schema, animations, context);


    }

    @Override
    protected void tick() throws LostGroundException, VariableException {
        super.tick();
    }

    @Override
    public void init(final Mascot mascot) throws VariableException {
        super.init(mascot);

    }




}
