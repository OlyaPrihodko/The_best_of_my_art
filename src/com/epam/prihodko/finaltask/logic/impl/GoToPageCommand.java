package com.epam.prihodko.finaltask.logic.impl;

import com.epam.prihodko.finaltask.controller.CommandName;
import com.epam.prihodko.finaltask.controller.JSPPageName;
import com.epam.prihodko.finaltask.controller.RequestParameterName;
import com.epam.prihodko.finaltask.dao.entity.ApartmentDao;
import com.epam.prihodko.finaltask.dao.entity.OrderDao;
import com.epam.prihodko.finaltask.dao.factory.DAOFactory;
import com.epam.prihodko.finaltask.entity.Apartment;
import com.epam.prihodko.finaltask.bean.MapBean;
import com.epam.prihodko.finaltask.entity.Order;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;
import com.epam.prihodko.finaltask.logic.ICommand;
import com.epam.prihodko.finaltask.exception.logicException.LogicException;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
/**
 * GoToPageCommand returns the right page and executes the necessary operations if necessary
 * */
public class GoToPageCommand implements ICommand{
    private static DAOFactory MySQLDaoFactory = DAOFactory.getDAOFactory(DAOFactory.DataSourceName.MYSQL);
    private static OrderDao orderDao = MySQLDaoFactory.getOrderDao();
    private static ApartmentDao apartmentDao = MySQLDaoFactory.getApartmentDao();
    @Override
    public String execute(HttpServletRequest request) throws LogicException {
        CommandName commandName = CommandName.valueOf(request.getParameter(RequestParameterName.COMMAND_NAME).toUpperCase().replace("-","_"));
        switch (commandName){
        case GO_TO_REGISTR_PAGE:
            return JSPPageName.REGISTR_PAGE;

        case GO_TO_USER_PERSONAL_DATA_PAGE:
            return JSPPageName.USER_PERSONAL_DATA_PAGE;

        case GO_TO_INF_PAGE:
            return JSPPageName.INFORMATION_PAGE;

        case GO_TO_MAKE_AN_ORDER_PAGE:
            return JSPPageName.MAKE_AN_ORDER_PAGE;

        case GO_TO_CREATE_APARTMENT_PAGE:
            return JSPPageName.MAKE_AN_APARTMENT_PAGE;

        case GO_TO_CHANGE_APARTMENT_PAGE:
            String apId = request.getParameter(RequestParameterName.APARTMENT_ID);
            int apartmentId = Integer.parseInt(apId);
            try{
                Apartment apartment = apartmentDao.getById(apartmentId);
                if(apartment.getStatus().equals(RequestParameterName.APARTMENT_STATUS_NOT_AVAILABLE)){
                    Object obLan = request.getSession().getAttribute(RequestParameterName.LANGUAGE);
                    ResourceBundle resourceBundle;
                    if(!(obLan instanceof Locale)){
                        String lan = (String)obLan;
                        String language = lan.substring(0,2);
                        String country = lan.substring(3,5);
                        Locale locale = new Locale(language,country);
                        resourceBundle =ResourceBundle.getBundle(RequestParameterName.LOCALE,locale);
                    }
                    else{
                        resourceBundle = ResourceBundle.getBundle(RequestParameterName.LOCALE, (Locale) obLan);
                    }
                    request.setAttribute(RequestParameterName.MISTAKE, resourceBundle.getString("locale.message.Message22"));
                    return JSPPageName.APARTMENT_TABLE_PAGE;
                }
                else{
                    request.getSession().setAttribute(RequestParameterName.APARTMENT_ID,apartmentId);
                    request.getSession().setAttribute(RequestParameterName.APARTMENT, apartment);
                    return JSPPageName.CHANGE_APARTMENT_PAGE;
                }
            }catch (DaoException e){
                throw new LogicException("GoToPageCommand has problem with dao",e);
            }

        case GO_TO_APARTMENTTABLE_PAGE:
            try {
                Map<Integer,Apartment>mapApartment = apartmentDao.getAllApartmentMap();
                MapBean mapBeanApartment = new MapBean(mapApartment);
                request.getSession().setAttribute(RequestParameterName.MAP_BEAN_APARTMENT, mapBeanApartment);
                } catch (DaoException e) {
                    throw new LogicException("GoToPageCommand has problem with dao",e);
                }
            return JSPPageName.APARTMENT_TABLE_PAGE;

        case GO_TO_CHANGE_ORDER_PAGE:
            String ord = request.getParameter(RequestParameterName.ORDER_ID);
            int orderId = Integer.parseInt(ord);
            try{
                Order order = orderDao.getById(orderId);
                if(order.getStatus().equals(RequestParameterName.STATUS_ORDERED)){
                    Object obLan = request.getSession().getAttribute(RequestParameterName.LANGUAGE);
                    ResourceBundle resourceBundle;
                    if(!(obLan instanceof Locale)){
                        String lan = (String)obLan;
                        String language = lan.substring(0,2);
                        String country = lan.substring(3,5);
                        Locale locale = new Locale(language,country);
                        resourceBundle =ResourceBundle.getBundle(RequestParameterName.LOCALE,locale);
                    }
                    else{
                        resourceBundle = ResourceBundle.getBundle(RequestParameterName.LOCALE, (Locale) obLan);
                    }
                    request.setAttribute(RequestParameterName.MISTAKE, resourceBundle.getString("locale.message.Message18"));
                    return JSPPageName.USER_PERSONAL_AREA_PAGE;
                }
                else{
                    request.getSession().setAttribute(RequestParameterName.ORDER_ID,orderId);
                    request.getSession().setAttribute(RequestParameterName.ORDER,order);
                    return JSPPageName.CHANGE_ORDER_PAGE;
                }
            }catch (DaoException e){
                throw new LogicException("GoToPageCommand has problem with dao",e);
            }

        case PREVIOUS_PAGE:
            String str = (String)request.getSession().getAttribute(RequestParameterName.PREVIOUS_PAGE);
            if(str.equals(JSPPageName.LOGIN_PAGE)){
                return JSPPageName.LOGIN_PAGE;
            }
            if(str.equals(JSPPageName.REGISTR_PAGE)){
                return JSPPageName.REGISTR_PAGE;
            }
            if(str.equals(JSPPageName.USER_PERSONAL_AREA_PAGE)){
                return JSPPageName.USER_PERSONAL_AREA_PAGE;
            }
            if(str.equals(JSPPageName.APARTMENT_TABLE_PAGE)){
                return JSPPageName.APARTMENT_TABLE_PAGE;
            }
            if(str.equals(JSPPageName.USER_PERSONAL_DATA_PAGE)){
                return JSPPageName.USER_PERSONAL_DATA_PAGE;
            }
            if(str.equals(JSPPageName.ADMIN_MAIN_PAGE)){
                request.getSession().setAttribute(RequestParameterName.USER,RequestParameterName.ADMIN);
                try{
                    Map<Integer,Order> mapOrder = orderDao.getOrderMapByStatus(RequestParameterName.STATUS_NEW);
                    MapBean mapBeanOrder = new MapBean(mapOrder);
                    request.getSession().setAttribute(RequestParameterName.MAP_BEAN_ORDER,mapBeanOrder);
                }
                catch (DaoException e){
                    throw new LogicException("GoToPageCommand has problem with dao",e);
                }
                return JSPPageName.ADMIN_MAIN_PAGE;
            }
        }
    return null;
    }
}
