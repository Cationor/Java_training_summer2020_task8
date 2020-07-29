package com.epamcourse.homework8.controller.command.impl;

import com.epamcourse.homework8.controller.command.param.RequestParam;
import com.epamcourse.homework8.controller.command.param.ResponseParam;
import com.epamcourse.homework8.controller.command.ActionCommand;
import com.epamcourse.homework8.model.entity.Book;
import com.epamcourse.homework8.exception.ServiceException;
import com.epamcourse.homework8.model.service.BookService;
import java.util.HashMap;
import java.util.Map;

public class AddBookCommand implements ActionCommand {
    @Override
    public Map<String, Object> execute(Map<String, Object> params) {
        Map<String, Object> response = new HashMap<>();
        Book book = new Book((String) params.get(RequestParam.BOOK_NAME), (int) params.get(RequestParam.NUMBER_OF_PAGE),
                (String[]) params.get(RequestParam.AUTHOR));
        try {
            new BookService().addBook(book);
            response.put(ResponseParam.RESPONSE_STATUS, ResponseParam.RESPONSE_STATUS_SUCCESS);
        } catch (ServiceException e) {
            response.put(ResponseParam.RESPONSE_STATUS, ResponseParam.RESPONSE_STATUS_FAIL);
        }
        return response;
    }
}
