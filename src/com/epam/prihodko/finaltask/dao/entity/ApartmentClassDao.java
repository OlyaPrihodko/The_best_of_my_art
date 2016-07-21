package com.epam.prihodko.finaltask.dao.entity;

import com.epam.prihodko.finaltask.dao.GenericDao;
import com.epam.prihodko.finaltask.entity.ApartmentClass;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;

public interface ApartmentClassDao extends GenericDao<ApartmentClass> {
    public ApartmentClass getById(int domainId) throws DaoException;
    public void create(ApartmentClass apartmentClass)throws DaoException;
    public void update(ApartmentClass apartmentClass) throws DaoException;
    public void delete(ApartmentClass apartmentClass)throws DaoException;
    public int findIdByType(ApartmentClass apartmentClass)throws DaoException;
}