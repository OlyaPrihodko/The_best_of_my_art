package com.epam.prihodko.finaltask.exception.daoException;

public class DaoException extends Exception {
    private static final long serialVersionUID = 1L;
    private Exception hiddenException;

    public DaoException(String msg){
        super(msg);
    }
    public DaoException(String msg, Exception e){
        super(msg);
        hiddenException = e;
    }
    public Exception getHiddenException(){
        return hiddenException;
    }
}
