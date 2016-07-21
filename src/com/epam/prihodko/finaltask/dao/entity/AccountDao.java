package com.epam.prihodko.finaltask.dao.entity;


import com.epam.prihodko.finaltask.dao.GenericDao;
import com.epam.prihodko.finaltask.entity.Account;
import com.epam.prihodko.finaltask.exception.daoException.DaoException;

public interface AccountDao extends GenericDao<Account> {
    public Account getById(int domainId) throws DaoException;
    public void create(Account account)throws DaoException;
    public void update(Account account) throws DaoException;
    public void delete(Account account)throws DaoException;
    public boolean checkAccount (Account account)throws DaoException;
    public int getId(Account account)throws DaoException;
    public String getRole(Account account)throws DaoException;
}
