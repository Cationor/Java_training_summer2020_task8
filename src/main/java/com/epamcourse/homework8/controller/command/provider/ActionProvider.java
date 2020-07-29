package com.epamcourse.homework8.controller.command.provider;

import com.epamcourse.homework8.controller.command.ActionCommand;
import com.epamcourse.homework8.controller.command.client.CommandType;
import com.epamcourse.homework8.controller.command.impl.EmptyCommand;

public class ActionProvider {
    private ActionProvider() {
    }

    public static ActionCommand defineAction(String action) {
        ActionCommand command;
        try {
            command = CommandType.valueOf(action.toUpperCase()).getCurrentCommand();
        } catch (IllegalArgumentException e) {
            command = new EmptyCommand();
        }
        return command;
    }
}
