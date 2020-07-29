package com.epamcourse.homework8.controller.command.client;

import com.epamcourse.homework8.controller.command.ActionCommand;
import com.epamcourse.homework8.controller.command.impl.*;

public enum CommandType {
    ADD_BOOK(new AddBookCommand()),
    REMOVE_BOOK(new RemoveBookCommand()),
    FIND_BY_ID(new FindByIdCommand()),
    FIND_BY_NAME(new FindByNameCommand()),
    FIND_BY_AUTHOR(new FindByAuthorCommand()),
    FIND_BY_NUMBER_OF_PAGE(new FindByNumberOfPageCommand()),
    SORT_BY_NUMBER_OF_PAGE(new SortByNumberOfPageCommand()),
    SORT_BY_NAME(new SortByNameCommand());
    private final ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
