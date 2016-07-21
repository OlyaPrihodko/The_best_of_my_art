package com.epam.prihodko.finaltask.exception.logicException;

public class LogicException extends Exception {
    private static final long serialVersionUID = 1L;
    private Exception hiddenException;

    public LogicException(String msg){
        super(msg);
    }
    public LogicException(String msg, Exception e){
        super(msg);
        hiddenException = e;
    }
    public Exception getHiddenException(){
        return hiddenException;
    }
}
