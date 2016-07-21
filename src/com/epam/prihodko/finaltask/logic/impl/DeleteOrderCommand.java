package com.epam.prihodko.finaltask.logic.impl;

import com.epam.prihodko.finaltask.controller.JSPPageName;
import com.epam.prihodko.finaltask.controller.RequestParameterName;
import com.epam.prihodko.finaltask.dao.Transaction;
import com.epam.prihodko.finaltask.dao.entity.CheckDao;
import com.epam.prihodko.finaltask.dao.entity.OrderDao;
import com.epam.prihodko.finaltask.dao.factory.DAOFactory;
import com.epam.prihodko.finaltask.entity.Check;
import com.epam.prihodko.finaltask.bean.MapBean;
import com.epam.prihodko.finaltask.entity.Order;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;
import com.epam.prihodko.finaltask.logic.ICommand;
import com.epam.prihodko.finaltask.exception.logicException.LogicException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
/***
 * DeleteOrderCommand removes selected order
 */
public class DeleteOrderCommand implements ICommand {
    private final static DAOFactory MySQLDaoFactory = DAOFactory.getDAOFactory(DAOFactory.DataSourceName.MYSQL);
    private final static OrderDao orderDao = MySQLDaoFactory.getOrderDao();
    private final static CheckDao checkDao = MySQLDaoFactory.getCheckDao();
    private final static Transaction transaction = new Transaction();

    @Override
    public String execute(HttpServletRequest request) throws LogicException {
        String ordId = request.getParameter(RequestParameterName.ORDER_ID);
        int orderId = Integer.parseInt(ordId);
        try{
            Order order = orderDao.getById(orderId);
            if(order.getStatus().equals(RequestParameterName.STATUS_NEW)){
                orderDao.delete(order);
            }
            else if (order.getStatus().equals(RequestParameterName.STATUS_ORDERED)){
                transaction.deleteOrderWithStatusOrdered(orderId);
            }
            /**
             * Get orders by person ID if man is user
             * or
             * get orders by status new if man is admin
             * */
            if(request.getSession().getAttribute(RequestParameterName.ROLE_USER).equals(RequestParameterName.ROLE_USER)){
                String personId = request.getSession().getAttribute(RequestParameterName.PERSON_ID).toString();
                Map<Integer,Order> mapOrder = orderDao.getOrderMapByPersonId(Integer.parseInt(personId));
                MapBean mapBeanOrder = new MapBean(mapOrder);
                request.getSession().setAttribute(RequestParameterName.MAP_BEAN_ORDER,mapBeanOrder);
                Map<Integer,Check> mapCheck = new HashMap<Integer, Check>();
                int mapBeanOrderSize = Integer.parseInt(mapBeanOrder.getSize());
                for(int i=0; i<mapBeanOrderSize;i++){
                    mapBeanOrder.getElement();
                    orderId = mapBeanOrder.getId();
                    Check check = checkDao.getCheckByOrderId(orderId);
                    if(check!=null){
                        mapCheck.put(check.getId(),check);
                    }
                }
                MapBean mapBeanCheck = new MapBean(mapCheck);
                request.getSession().setAttribute(RequestParameterName.MAP_BEAN_CHECK,mapBeanCheck);
                return JSPPageName.USER_PERSONAL_AREA_PAGE;
            }
            else if (request.getSession().getAttribute(RequestParameterName.ROLE_USER).equals(RequestParameterName.ROLE_ADMIN)){
                Map<Integer,Order> mapOrder = orderDao.getOrderMapByStatus(RequestParameterName.STATUS_NEW);
                MapBean mapBeanOrder = new MapBean(mapOrder);
                request.getSession().setAttribute(RequestParameterName.MAP_BEAN_ORDER,mapBeanOrder);
                return JSPPageName.ADMIN_MAIN_PAGE;
            }
        }catch (DaoException e){
            throw new LogicException("DeleteOrderCommand has problem with dao",e);
        }
        return null;
    }
}