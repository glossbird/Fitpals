package com.group_finity.mascot.action;

import com.group_finity.mascot.Mascot;
import com.group_finity.mascot.animation.Animation;
import com.group_finity.mascot.exception.VariableException;
import com.group_finity.mascot.script.VariableMap;

import java.util.*;
import java.util.logging.Logger;

/**
 * Looking action.
 *
 * @author Yuki Yamada of <a href="http://www.group-finity.com/Shimeji/">Group Finity</a>
 * @author Shimeji-ee Group
 */
public class Alarm extends Animate {
    private static final Logger log = Logger.getLogger(Look.class.getName());

    public static final Calendar alarmTime = Calendar.getInstance();
    public static final Timer timer = new Timer();

    public Alarm(ResourceBundle schema, final List<Animation> animations, final VariableMap context) {
        super(schema, animations, context);
    }


    @Override
    public void init(final Mascot mascot) throws VariableException {
        super.init(mascot);


    }


}
