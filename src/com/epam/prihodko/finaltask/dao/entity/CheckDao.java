package com.epam.prihodko.finaltask.dao.entity;

import com.epam.prihodko.finaltask.dao.GenericDao;
import com.epam.prihodko.finaltask.entity.Check;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;

import java.util.Map;

public interface CheckDao extends GenericDao<Check> {
    public Check getById(int domainId) throws DaoException;
    public void create(Check check)throws DaoException;
    public void update(Check check) throws DaoException;
    public void delete(Check check)throws DaoException;
    public void deleteByOrderId(int orderId)throws DaoException;
    public Check getCheckByOrderId(int orderId)throws DaoException;
    public Map<Integer, Check> getCheckMapByApartmentId(int orderId)throws DaoException;
}
