package com.epam.prihodko.finaltask.dao;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;

public interface GenericDao <T>{
    public T getById(int domainId) throws DaoException;
    public void create(T domain)  throws DaoException;
    public void update (T domain) throws DaoException;
    public void delete (T domain) throws DaoException;

}
