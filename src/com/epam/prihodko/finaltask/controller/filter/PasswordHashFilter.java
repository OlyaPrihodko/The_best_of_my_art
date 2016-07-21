package com.epam.prihodko.finaltask.controller.filter;

import com.epam.prihodko.finaltask.controller.RequestParameterName;
import org.apache.log4j.Logger;
import javax.servlet.*;
import java.io.IOException;
import java.security.MessageDigest;
/**
 * Create hash of the password
 * */
public class PasswordHashFilter implements Filter {
    private static final Logger log = Logger.getLogger(PasswordHashFilter.class);

    public void init(FilterConfig filterConfig)throws ServletException{}
    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain)throws IOException,ServletException{
        String pass = request.getParameter(RequestParameterName.PARAM_NAME_PASSWORD);
        if(pass!=null){
             String password = getHash(pass);
             request.setAttribute(RequestParameterName.PARAM_NAME_PASSWORD, password);
        }
        chain.doFilter(request,response);
    }
    /**
     * Return right password
     * */
    private String getHash(String password) {
        try {
            return byteArrayToHexString(PasswordHashFilter.computeHash(password));
        }catch (Exception e) {
            log.error("PasswordHashFilter has problem in getHash method", e);
        }
        return null;
    }
    /**
     * Compute digest
     * SHA-1 algorithm
     * @param pass the password to hash
     * */
    private static byte[] computeHash(String pass)throws Exception
    {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        messageDigest.reset();
        messageDigest.update(pass.getBytes());
        return  messageDigest.digest();
    }
    /**
     * Convert array bytes to String based on the hex view
     * */
    private static String byteArrayToHexString(byte[] arrayByte){
        StringBuilder stringBuilder = new StringBuilder(arrayByte.length*2);
        for (byte element : arrayByte) {
            int value = element & 0xff;
            if (value < 16) {
                stringBuilder.append('0');
            }
            stringBuilder.append(Integer.toHexString(value));
        }
        return stringBuilder.toString().toUpperCase();
    }
    public void destroy(){}
}
