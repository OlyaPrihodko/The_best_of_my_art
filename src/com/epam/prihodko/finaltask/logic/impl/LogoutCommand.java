package com.epam.prihodko.finaltask.logic.impl;

import com.epam.prihodko.finaltask.controller.JSPPageName;
import com.epam.prihodko.finaltask.logic.ICommand;
import com.epam.prihodko.finaltask.exception.logicException.LogicException;
import javax.servlet.http.HttpServletRequest;
/***
 * LogoutCommand performs logout
 */
public class LogoutCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request) throws LogicException {
        String page = JSPPageName.LOGIN_PAGE;
        request.getSession().invalidate();
        return page;
    }
}
