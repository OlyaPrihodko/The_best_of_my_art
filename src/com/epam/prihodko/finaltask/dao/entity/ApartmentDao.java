package com.epam.prihodko.finaltask.dao.entity;

import com.epam.prihodko.finaltask.dao.GenericDao;
import com.epam.prihodko.finaltask.entity.Apartment;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;

import java.util.Map;

public interface ApartmentDao extends GenericDao<Apartment> {
    public Apartment getById(int entityId) throws DaoException;
    public void create(Apartment apartment)throws DaoException;
    public void update(Apartment apartment) throws DaoException;
    public void delete(Apartment apartment)throws DaoException;
    public boolean find(Apartment apartment)throws DaoException;
    public Map<Integer,Apartment> getSuitableApartmentMap(Apartment apartment)throws DaoException;
    public Map<Integer,Apartment> getAllApartmentMap()throws DaoException;
}
