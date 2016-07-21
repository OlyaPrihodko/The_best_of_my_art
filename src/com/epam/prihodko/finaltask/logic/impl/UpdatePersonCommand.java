package com.epam.prihodko.finaltask.logic.impl;

import com.epam.prihodko.finaltask.controller.JSPPageName;
import com.epam.prihodko.finaltask.controller.RequestParameterName;
import com.epam.prihodko.finaltask.dao.entity.PersonDao;
import com.epam.prihodko.finaltask.dao.factory.DAOFactory;
import com.epam.prihodko.finaltask.entity.Person;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;
import com.epam.prihodko.finaltask.logic.ICommand;
import com.epam.prihodko.finaltask.exception.logicException.LogicException;
import javax.servlet.http.HttpServletRequest;
/**
 * UpdatePersonCommand updates selected person
 * */
public class UpdatePersonCommand implements ICommand {
    private final static DAOFactory MySQLDaoFactory = DAOFactory.getDAOFactory(DAOFactory.DataSourceName.MYSQL);
    private final static PersonDao personDao = MySQLDaoFactory.getPersonDao();
    @Override
    public String execute(HttpServletRequest request) throws LogicException {
        String page = JSPPageName.USER_PERSONAL_AREA_PAGE;
        Person person = (Person)request.getSession().getAttribute(RequestParameterName.PERSON);
        person.setName(request.getParameter(RequestParameterName.PARAM_NAME_NAME));
        person.setSurname(request.getParameter(RequestParameterName.PARAM_NAME_SURNAME));
        person.setEmail(request.getParameter(RequestParameterName.PARAM_NAME_EMAIL));
        person.setPhone(request.getParameter(RequestParameterName.PARAM_NAME_PHONE));
        try{
            personDao.update(person);
        }catch (DaoException e){
            throw new LogicException("UpdatePersonCommand has problem with dao",e);
        }
    return page;
    }
}
