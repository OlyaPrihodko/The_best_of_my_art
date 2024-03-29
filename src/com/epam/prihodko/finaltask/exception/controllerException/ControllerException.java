package com.epam.prihodko.finaltask.exception.controllerException;

public class ControllerException extends Exception {
    private static final long serialVersionUID = 1L;
    private Exception hiddenException;

    public ControllerException(String msg){
        super(msg);
    }
    public ControllerException(String msg, Exception e){
        super(msg);
        hiddenException = e;
    }
    public Exception getHiddenException(){
        return hiddenException;
    }
}
