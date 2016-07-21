package com.epam.prihodko.finaltask.dao.entity;

import com.epam.prihodko.finaltask.dao.GenericDao;
import com.epam.prihodko.finaltask.entity.Person;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;

public interface PersonDao extends GenericDao<Person> {
    public Person getById(int domainId) throws DaoException;
    public void create(Person person)throws DaoException;
    public void update (Person domain)throws DaoException;
    public void delete (Person domain)throws DaoException;
    public Person getByAccountId(int domainId) throws DaoException;


}
