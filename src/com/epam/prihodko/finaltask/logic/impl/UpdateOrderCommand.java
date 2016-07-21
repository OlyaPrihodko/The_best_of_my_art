package com.epam.prihodko.finaltask.logic.impl;

import com.epam.prihodko.finaltask.controller.JSPPageName;
import com.epam.prihodko.finaltask.controller.RequestParameterName;
import com.epam.prihodko.finaltask.dao.entity.OrderDao;
import com.epam.prihodko.finaltask.dao.factory.DAOFactory;
import com.epam.prihodko.finaltask.bean.MapBean;
import com.epam.prihodko.finaltask.entity.Order;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;
import com.epam.prihodko.finaltask.logic.ICommand;
import com.epam.prihodko.finaltask.exception.logicException.LogicException;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
/**
 * UpdateOrderCommand updates selected order
 * */
public class UpdateOrderCommand implements ICommand {
    private final static String DATE_FORMAT = "yyyy-MM-dd";
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
    private final static DAOFactory MySQLDaoFactory = DAOFactory.getDAOFactory(DAOFactory.DataSourceName.MYSQL);
    private final static OrderDao orderDao = MySQLDaoFactory.getOrderDao();
    @Override
    public String execute(HttpServletRequest request) throws LogicException {
        String page = JSPPageName.USER_PERSONAL_AREA_PAGE;
        Order order = (Order)request.getSession().getAttribute(RequestParameterName.ORDER);
        String ordId = request.getSession().getAttribute(RequestParameterName.ORDER_ID).toString();
        String apartmentCl = request.getParameter(RequestParameterName.APARTMENT_CLASS);
        String roomNumber = request.getParameter(RequestParameterName.ROOM_NUMBER);
        String couchette = request.getParameter(RequestParameterName.PARAM_NAME_COUCHETTE);
        String datein = request.getParameter(RequestParameterName.DATE_IN);
        String dateout = request.getParameter(RequestParameterName.DATE_OUT);
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
        arrayList.add(datein);
        arrayList.add(dateout);
        arrayList.add(roomNumber);
        /**
         * Check empty values ↓
         * */
        for(String element: arrayList){
            if(element.equals("")){
                request.setAttribute(RequestParameterName.MISTAKE, resourceBundle.getString("locale.message.Message20"));
                return JSPPageName.CHANGE_ORDER_PAGE;
            }
        }
        int orderId = Integer.parseInt(ordId);
        order.setId(orderId);
        order.setApartmentClass(apartmentCl);
        order.setRoomNumber(Integer.parseInt(roomNumber));
        order.setCouchette(Integer.parseInt(couchette));
        try {
            Date dateIn = new Date(simpleDateFormat.parse(datein).getTime());
            Date dateOut = new Date(simpleDateFormat.parse(dateout).getTime());
            java.util.Date currentDate = new java.util.Date();
            if(!(dateIn.after(new java.util.Date(currentDate.getTime()))&&dateOut.after(dateIn))){
                request.setAttribute(RequestParameterName.MISTAKE, resourceBundle.getString("locale.message.Message21"));
                return JSPPageName.CHANGE_ORDER_PAGE;
            }
            order.setDate_in(dateIn);
            order.setDate_out(dateOut);
            orderDao.update(order);
            String personId = request.getSession().getAttribute(RequestParameterName.PERSON_ID).toString();
            Map<Integer,Order> mapOrder = orderDao.getOrderMapByPersonId(Integer.parseInt(personId));
            MapBean mapBeanOrder = new MapBean(mapOrder);
            request.getSession().setAttribute(RequestParameterName.MAP_BEAN_ORDER,mapBeanOrder);
        }catch (DaoException e){
            throw new LogicException("UpdateOrderCommand has problem with dao",e);
        }catch (ParseException e) {
            throw new LogicException("UpdateOrderCommand has problem with parse date",e);
        }
        return page;
    }
}
