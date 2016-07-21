package com.epam.prihodko.finaltask.bean;

import java.util.Iterator;
import java.util.Map;
/**
 * Show map entity on JSP page
 * */
public class MapBean implements java.io.Serializable{
    private Map map;
    private int id;
    private Iterator<Map.Entry<Integer,Object>> iterator;

    public MapBean() {}
    public MapBean(Map map) {
        this.map = map;
    }
    public String getSize(){
        iterator = map.entrySet().iterator();
        return Integer.toString(map.size());
    }
    public Object getElement(){
        if(iterator.hasNext()){
            Map.Entry<Integer,Object> m = iterator.next();
            this.setId(m.getKey());
            return m.getValue();
        }
        return null;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
}
