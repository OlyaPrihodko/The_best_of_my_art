package com.epam.prihodko.finaltask.logic.impl;

import com.epam.prihodko.finaltask.controller.JSPPageName;
import com.epam.prihodko.finaltask.controller.RequestParameterName;
import com.epam.prihodko.finaltask.dao.entity.ApartmentDao;
import com.epam.prihodko.finaltask.dao.entity.CheckDao;
import com.epam.prihodko.finaltask.dao.entity.OrderDao;
import com.epam.prihodko.finaltask.dao.factory.DAOFactory;
import com.epam.prihodko.finaltask.entity.Apartment;
import com.epam.prihodko.finaltask.entity.Check;
import com.epam.prihodko.finaltask.bean.MapBean;
import com.epam.prihodko.finaltask.entity.Order;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;
import com.epam.prihodko.finaltask.logic.ICommand;
import com.epam.prihodko.finaltask.exception.logicException.LogicException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/***
 * DeleteApartmentCommand removes selected apartment
 */
public class DeleteApartmentCommand implements ICommand {
    private static DAOFactory MySQLDaoFactory = DAOFactory.getDAOFactory(DAOFactory.DataSourceName.MYSQL);
    private static OrderDao orderDao = MySQLDaoFactory.getOrderDao();
    private static CheckDao checkDao = MySQLDaoFactory.getCheckDao();
    private static ApartmentDao apartmentDao = MySQLDaoFactory.getApartmentDao();
    @Override
    public String execute(HttpServletRequest request) throws LogicException {
        String page;
        String apartmentId = request.getParameter(RequestParameterName.APARTMENT_ID);
        int apId = Integer.parseInt(apartmentId);
        try{
            Apartment apartment = apartmentDao.getById(apId);
            if(apartment.getStatus().equals(RequestParameterName.APARTMENT_STATUS_AVAILABLE)){
                apartmentDao.delete(apartment);
            }else if (apartment.getStatus().equals(RequestParameterName.APARTMENT_STATUS_NOT_AVAILABLE)){
                Map<Integer,Check> mapCheck = checkDao.getCheckMapByApartmentId(apId);
                for (Map.Entry<Integer, Check> ch : mapCheck.entrySet()) {
                    Check check = ch.getValue();
                    Order order = orderDao.getById(check.getOrderId());
                    checkDao.deleteByOrderId(order.getId());
                    orderDao.delete(order);
                }
                apartmentDao.delete(apartment);
            }
            Map<Integer,Apartment>mapApartment = apartmentDao.getAllApartmentMap();
            MapBean mapBeanApartment = new MapBean(mapApartment);
            request.getSession().setAttribute(RequestParameterName.MAP_BEAN_APARTMENT, mapBeanApartment);
            page = JSPPageName.APARTMENT_TABLE_PAGE;
        }catch (DaoException e){
            throw new LogicException("DeleteApartmentCommand has problem with dao",e);
        }
        return page;
    }
}
