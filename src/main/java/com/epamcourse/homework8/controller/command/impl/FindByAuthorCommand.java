package com.epamcourse.homework8.controller.command.impl;

import com.epamcourse.homework8.controller.command.param.RequestParam;
import com.epamcourse.homework8.controller.command.param.ResponseParam;
import com.epamcourse.homework8.controller.command.ActionCommand;
import com.epamcourse.homework8.model.entity.Book;
import com.epamcourse.homework8.exception.ServiceException;
import com.epamcourse.homework8.model.service.BookService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindByAuthorCommand implements ActionCommand {
    @Override
    public Map<String, Object> execute(Map<String, Object> params) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Book> book = new BookService().findByAuthor((String[]) params.get(RequestParam.AUTHOR));
            response.put(ResponseParam.RESPONSE_STATUS, ResponseParam.RESPONSE_STATUS_SUCCESS);
            response.put(ResponseParam.RESPONSE_RESULT, book);
        } catch (ServiceException e) {
            response.put(ResponseParam.RESPONSE_STATUS, ResponseParam.RESPONSE_STATUS_FAIL);
        }
        return response;
    }
}
