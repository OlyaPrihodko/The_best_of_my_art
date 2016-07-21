package com.epam.prihodko.finaltask.logic.impl;

import com.epam.prihodko.finaltask.controller.JSPPageName;
import com.epam.prihodko.finaltask.controller.RequestParameterName;
import com.epam.prihodko.finaltask.dao.entity.ApartmentDao;
import com.epam.prihodko.finaltask.dao.factory.DAOFactory;
import com.epam.prihodko.finaltask.entity.Apartment;
import com.epam.prihodko.finaltask.bean.MapBean;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;
import com.epam.prihodko.finaltask.logic.ICommand;
import com.epam.prihodko.finaltask.exception.logicException.LogicException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
/**
 *MakeAnApartmentCommand creates new apartment
 * */
public class MakeAnApartmentCommand implements ICommand {
    private final static DAOFactory MySQLDaoFactory = DAOFactory.getDAOFactory(DAOFactory.DataSourceName.MYSQL);
    private final static ApartmentDao apartmentDao = MySQLDaoFactory.getApartmentDao();
    @Override
    public String execute(HttpServletRequest request) throws LogicException {
        String page = JSPPageName.APARTMENT_TABLE_PAGE;
        String apartmentCl = request.getParameter(RequestParameterName.APARTMENT_CLASS);
        String roomNumber = request.getParameter(RequestParameterName.ROOM_NUMBER);
        String couchette = request.getParameter(RequestParameterName.PARAM_NAME_COUCHETTE);
        String price = request.getParameter(RequestParameterName.PRICE);
        Object ob = request.getSession().getAttribute(RequestParameterName.LANGUAGE);
        ResourceBundle resourceBundle;
        if(!(ob instanceof Locale)){
            String lan = (String)ob;
            String language = lan.substring(0,2);
            String country = lan.substring(3,5);
            Locale locale = new Locale(language,country);
            resourceBundle = ResourceBundle.getBundle(RequestParameterName.LOCALE,locale);
        }
        else{
            resourceBundle = ResourceBundle.getBundle(RequestParameterName.LOCALE, (Locale) ob);
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(apartmentCl);
        arrayList.add(couchette);
        arrayList.add(price);
        arrayList.add(roomNumber);
        /**
         * Check empty values ↓
         * */
        for(String element: arrayList){
            if(element.equals("")){
                request.setAttribute(RequestParameterName.MISTAKE, resourceBundle.getString("locale.message.Message20"));
                return JSPPageName.MAKE_AN_APARTMENT_PAGE;
            }
        }
        Apartment apartment = new Apartment();
        apartment.setPrice(Integer.parseInt(price));
        apartment.setClassId(Integer.parseInt(apartmentCl));
        apartment.setRoomNumber(Integer.parseInt(roomNumber));
        apartment.setCouchette(Integer.parseInt(couchette));
        apartment.setStatus(RequestParameterName.APARTMENT_STATUS_AVAILABLE);
        try {
            if(!apartmentDao.find(apartment)){
                apartmentDao.create(apartment);
            }
            else{
                request.setAttribute(RequestParameterName.MISTAKE, resourceBundle.getString("locale.message.Message26"));
                return JSPPageName.MAKE_AN_APARTMENT_PAGE;
            }
            Map<Integer,Apartment>mapApartment = apartmentDao.getAllApartmentMap();
            MapBean mapBeanApartment = new MapBean(mapApartment);
            request.getSession().setAttribute(RequestParameterName.MAP_BEAN_APARTMENT, mapBeanApartment);
        }catch (DaoException e){
            throw new LogicException("MakeAnApartmentCommand has problem with dao",e);
        }
        return page;
    }
}
