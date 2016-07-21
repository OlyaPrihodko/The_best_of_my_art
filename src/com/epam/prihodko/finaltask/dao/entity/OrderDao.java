package com.epam.prihodko.finaltask.dao.entity;

import com.epam.prihodko.finaltask.dao.GenericDao;
import com.epam.prihodko.finaltask.entity.Order;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;
import java.util.Map;

public interface OrderDao extends GenericDao<Order> {
    public Order getById(int domainId) throws DaoException;
    public void create(Order order)throws DaoException;
    public void update(Order order) throws DaoException;
    public void delete(Order order)throws DaoException;
    public Map<Integer,Order> getOrderMapByPersonId(int personId)throws DaoException;
    public Map<Integer,Order> getOrderMapByStatus(String status)throws DaoException;
    public Map<Integer,Order> getAllOrdersMap()throws DaoException;


}
