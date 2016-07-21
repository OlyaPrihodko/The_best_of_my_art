package com.epam.prihodko.finaltask.logic;

import com.epam.prihodko.finaltask.exception.logicException.LogicException;
import javax.servlet.http.HttpServletRequest;
public interface ICommand {
    /**
     * There is a method for each Command that can be created.
     * */
    public String execute(HttpServletRequest request) throws LogicException;
}
