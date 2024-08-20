package com.group_finity.mascot.action;

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
public class AlarmAction extends InstantAction {
    private static final Logger log = Logger.getLogger(Look.class.getName());

    public static final Calendar alarmTime = Calendar.getInstance();
    public static final Timer timer = new Timer();
    public AlarmAction(ResourceBundle schema, final VariableMap context) {
        super(schema, context);

    }

    @Override
    protected void apply() throws VariableException {
        getMascot();
    }


}
