package com.epam.prihodko.finaltask.logic.impl;

import com.epam.prihodko.finaltask.controller.JSPPageName;
import com.epam.prihodko.finaltask.controller.RequestParameterName;
import com.epam.prihodko.finaltask.dao.entity.OrderDao;
import com.epam.prihodko.finaltask.dao.factory.DAOFactory;
import com.epam.prihodko.finaltask.bean.MapBean;
import com.epam.prihodko.finaltask.entity.Order;
import com.epam.prihodko.finaltask.entity.Person;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;
import com.epam.prihodko.finaltask.logic.ICommand;
import com.epam.prihodko.finaltask.exception.logicException.LogicException;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
/***
 * MakeAnOrderCommand creates new order
 */

public class MakeAnOrderCommand implements ICommand {
    private final static DAOFactory MySQLDaoFactory = DAOFactory.getDAOFactory(DAOFactory.DataSourceName.MYSQL);
    private final static OrderDao orderDao = MySQLDaoFactory.getOrderDao();
    private final static  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public String execute(HttpServletRequest request) throws LogicException {
        String page;
        String apartmentClass = request.getParameter(RequestParameterName.APARTMENT_CLASS);
        String couch = request.getParameter(RequestParameterName.PARAM_NAME_COUCHETTE);
        int couchette = Integer.parseInt(couch);
        String datein = request.getParameter(RequestParameterName.DATE_IN);
        String dateout = request.getParameter(RequestParameterName.DATE_OUT);
        String roomN = request.getParameter(RequestParameterName.ROOM_NUMBER);
        int roomNumber = Integer.parseInt(roomN);
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
        arrayList.add(apartmentClass);
        arrayList.add(couch);
        arrayList.add(datein);
        arrayList.add(dateout);
        arrayList.add(roomN);
        /**
         * Check empty values ↓
         * */
        for(String element: arrayList){
            if(element.equals("")){
                request.setAttribute(RequestParameterName.MISTAKE, resourceBundle.getString("locale.message.Message20"));
                return JSPPageName.MAKE_AN_ORDER_PAGE;
            }
        }
        try {
                Date dateIn = simpleDateFormat.parse(datein);
                Date dateOut = simpleDateFormat.parse(dateout);
                Date currentDate = new Date();
                java.sql.Date dateSqlIn = new java.sql.Date(dateIn.getTime());
                java.sql.Date dateSqlOut = new java.sql.Date(dateOut.getTime());
                /**
                 * Check correct data ↓
                 * */
                if(!(dateSqlIn.after(new Date(currentDate.getTime()))&&dateSqlOut.after(dateSqlIn))){
                    request.setAttribute(RequestParameterName.MISTAKE, resourceBundle.getString("locale.message.Message21"));
                    return JSPPageName.MAKE_AN_ORDER_PAGE;
                }
            Order order = new Order(apartmentClass,roomNumber,couchette,dateSqlIn,dateSqlOut,RequestParameterName.STATUS_NEW);
            Person person = (Person)request.getSession().getAttribute(RequestParameterName.PERSON);
            order.setPersonId(person.getId());
            orderDao.create(order);
            Map<Integer,Order> orderMap = orderDao.getOrderMapByPersonId(person.getId());
            MapBean mapBeanOrder = new MapBean(orderMap);
            request.getSession().setAttribute(RequestParameterName.MAP_BEAN_ORDER,mapBeanOrder);
            page = JSPPageName.USER_PERSONAL_AREA_PAGE;
        }
        catch (DaoException e){
            throw new LogicException("MakeAnOrderCommand has problem with dao",e);
        }catch (ParseException e) {
            throw new LogicException("MakeAnOrderCommand has problem with parse",e);
        }
        return page;
    }
}
