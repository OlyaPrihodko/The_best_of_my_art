package com.epam.prihodko.finaltask.logic.impl;

import com.epam.prihodko.finaltask.controller.JSPPageName;
import com.epam.prihodko.finaltask.bean.MapBean;
import com.epam.prihodko.finaltask.controller.RequestParameterName;
import com.epam.prihodko.finaltask.dao.entity.ApartmentDao;
import com.epam.prihodko.finaltask.dao.entity.CheckDao;
import com.epam.prihodko.finaltask.dao.entity.OrderDao;
import com.epam.prihodko.finaltask.dao.factory.DAOFactory;
import com.epam.prihodko.finaltask.entity.*;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;
import com.epam.prihodko.finaltask.logic.ICommand;
import com.epam.prihodko.finaltask.exception.logicException.LogicException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * ToReserveApartmentCommand reserves an apartment for user
 * */
public class ToReserveApartmentCommand implements ICommand {
    private static DAOFactory MySQLDaoFactory = DAOFactory.getDAOFactory(DAOFactory.DataSourceName.MYSQL);
    private static OrderDao orderDao = MySQLDaoFactory.getOrderDao();
    private static CheckDao checkDao = MySQLDaoFactory.getCheckDao();
    private static ApartmentDao apartmentDao = MySQLDaoFactory.getApartmentDao();
    @Override
    public String execute(HttpServletRequest request) throws LogicException {
        String page;
        String orderId = request.getSession().getAttribute(RequestParameterName.ORDER_ID).toString();
        int ordId = Integer.parseInt(orderId);
        String apartmentId = request.getParameter(RequestParameterName.APARTMENT_ID);
        int apId = Integer.parseInt(apartmentId);
        request.getSession().setAttribute(RequestParameterName.APARTMENT_ID, apId);
        try{
            Order order = orderDao.getById(ordId);
            order.setStatus(RequestParameterName.STATUS_ORDERED);
            orderDao.update(order);
            Apartment apartment = apartmentDao.getById(apId);
            if(apartment.getStatus().equals(RequestParameterName.APARTMENT_STATUS_AVAILABLE)){
                apartment.setStatus(RequestParameterName.APARTMENT_STATUS_NOT_AVAILABLE);
                apartmentDao.update(apartment);
            }
            Check check = new Check();
            check.setPrice(apartment.getPrice());
            check.setApatrmentId(apartment.getId());
            check.setOrderId(order.getId());
            checkDao.create(check);
            Map<Integer,Order> mapOrder = orderDao.getOrderMapByStatus(RequestParameterName.STATUS_NEW);
            MapBean mapBeanOrder = new MapBean(mapOrder);
            request.getSession().setAttribute(RequestParameterName.MAP_BEAN_ORDER,mapBeanOrder);
            page = JSPPageName.ADMIN_MAIN_PAGE;
        }
        catch (DaoException e){
            throw new LogicException("ToReserveApartmentCommand has problem with dao",e);
        }
        return  page;
    }
}
