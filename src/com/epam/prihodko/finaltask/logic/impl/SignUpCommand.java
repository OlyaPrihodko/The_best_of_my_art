package com.epam.prihodko.finaltask.logic.impl;

import com.epam.prihodko.finaltask.controller.JSPPageName;
import com.epam.prihodko.finaltask.bean.MapBean;
import com.epam.prihodko.finaltask.controller.RequestParameterName;
import com.epam.prihodko.finaltask.dao.entity.AccountDao;
import com.epam.prihodko.finaltask.dao.entity.CheckDao;
import com.epam.prihodko.finaltask.dao.entity.OrderDao;
import com.epam.prihodko.finaltask.dao.entity.PersonDao;
import com.epam.prihodko.finaltask.dao.factory.DAOFactory;
import com.epam.prihodko.finaltask.entity.*;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;
import com.epam.prihodko.finaltask.logic.ICommand;
import com.epam.prihodko.finaltask.exception.logicException.LogicException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
/**
 * Add new user
 * */
public class SignUpCommand implements ICommand {
    private final static DAOFactory MySQLDaoFactory = DAOFactory.getDAOFactory(DAOFactory.DataSourceName.MYSQL);
    private final static AccountDao accountDao = MySQLDaoFactory.getAccountDao();
    private final static PersonDao personDao = MySQLDaoFactory.getPersonDao();
    private final static OrderDao orderDao = MySQLDaoFactory.getOrderDao();
    private final static CheckDao checkDao = MySQLDaoFactory.getCheckDao();
    @Override
    public String execute(HttpServletRequest request) throws LogicException {
        String page;
        ArrayList<String> arrayList = new ArrayList<String>();
        String name = request.getParameter(RequestParameterName.PARAM_NAME_NAME);
        String surname = request.getParameter(RequestParameterName.PARAM_NAME_SURNAME);
        String email = request.getParameter(RequestParameterName.PARAM_NAME_EMAIL);
        String phone = request.getParameter(RequestParameterName.PARAM_NAME_PHONE);
        String login = request.getParameter(RequestParameterName.PARAM_NAME_LOGIN);
        String pass = (String)request.getAttribute(RequestParameterName.PARAM_NAME_PASSWORD);
        Object ob = request.getSession().getAttribute(RequestParameterName.LANGUAGE);
        ResourceBundle resourceBundle;
        if(!(ob instanceof Locale)){
            String lan = (String)ob;
            String language = lan.substring(0,2);
            String country = lan.substring(3,5);
            Locale locale = new Locale(language,country);
            resourceBundle =ResourceBundle.getBundle(RequestParameterName.LOCALE,locale);
        }
        else{
            resourceBundle = ResourceBundle.getBundle(RequestParameterName.LOCALE, (Locale) ob);
        }
        arrayList.add(name);
        arrayList.add(surname);
        arrayList.add(email);
        arrayList.add(phone);
        arrayList.add(login);
        int fl = 0;
        for( String element: arrayList){
            if(element.equals("")){
                fl++;
            }
        }
        /**
         * Check empty values ↓
         * */
        if(fl>0||pass==null){
            request.setAttribute(RequestParameterName.MISTAKE, resourceBundle.getString("locale.message.Message20"));
            return JSPPageName.REGISTR_PAGE;
        }
        Account account = new Account(login,pass,RequestParameterName.USER);
        Person person = new Person(name,surname,email,phone);
        try{
            accountDao.create(account);
            person.setAccountId(accountDao.getId(account));
            personDao.create(person);
            person = personDao.getByAccountId(person.getAccountId());
            request.getSession().setAttribute(RequestParameterName.ROLE_USER,RequestParameterName.ROLE_USER);
            request.getSession().setAttribute(RequestParameterName.PERSON_ID,person.getId());
            request.getSession().setAttribute(RequestParameterName.PERSON,person);
            Map<Integer,Order> mapOrder = orderDao.getOrderMapByPersonId(person.getId());
            MapBean mapBeanOrder = new MapBean(mapOrder);
            Map<Integer,Check> mapCheck = new HashMap<Integer, Check>();
            int mapBeanOrderSize = Integer.parseInt(mapBeanOrder.getSize());
            for(int i=0; i<mapBeanOrderSize;i++){
                mapBeanOrder.getElement();
                int ordId = mapBeanOrder.getId();
                Check check = checkDao.getCheckByOrderId(ordId);
                if(check!=null){
                    mapCheck.put(check.getId(),check);
                }
            }
            MapBean mapBeanCheck = new MapBean(mapCheck);
            request.getSession().setAttribute(RequestParameterName.MAP_BEAN_CHECK,mapBeanCheck);
            request.getSession().setAttribute(RequestParameterName.MAP_BEAN_ORDER,mapBeanOrder);
            page = JSPPageName.USER_PERSONAL_AREA_PAGE;
        }catch (DaoException e){
            throw new LogicException("SignUpCommand has problem with dao",e);

        }
    return page;
    }
}
