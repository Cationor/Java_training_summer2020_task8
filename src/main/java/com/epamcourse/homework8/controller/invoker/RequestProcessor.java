package com.epamcourse.homework8.controller.invoker;

import com.epamcourse.homework8.controller.command.ActionCommand;
import com.epamcourse.homework8.controller.command.provider.ActionProvider;
import java.util.Map;

public class RequestProcessor {
    public Map<String, Object> doRequest(String action, Map<String, Object> params) {
        ActionCommand actionCommand = ActionProvider.defineAction(action);
        return actionCommand.execute(params);
    }
}
