package com.epam.prihodko.finaltask.customtag;

import com.epam.prihodko.finaltask.bean.MapBean;
import com.epam.prihodko.finaltask.controller.RequestParameterName;
import com.epam.prihodko.finaltask.entity.*;
import org.apache.log4j.Level;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class EntityTableTag extends TagSupport {
    private final static Logger logger = Logger.getLogger("EntityTableTag");
    private MapBean map;
    private String lang;
    private String user;
    private String action;

    public void setLang(String lang){
            this.lang=lang;
        }
    public void setMap(MapBean map){
        this.map=map;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setAction(String action) {
        this.action = action;
    }

    public String getLang(){return lang;}
    public MapBean getMap(){
    return map;
    }
    public String getUser() {
        return user;
    }
    public String getAction() {
        return action;
    }

    @Override
    public int doStartTag() throws JspTagException {
        String language = getLang().substring(0,2);
        String country = getLang().substring(3,5);
        Locale locale = new Locale(language,country);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(RequestParameterName.LOCALE,locale);
        int size = new Integer(map.getSize());
        try{
            JspWriter out = pageContext.getOut();
            if(Integer.parseInt(map.getSize())==0){
                    String noRecords = resourceBundle.getString("locale.message.Message9");
                    out.write("<h4>"+noRecords+"</h4>");
            }
            else {
            out.write("<table class=\"table table-bordered table-striped\">");
            Object first = map.getElement();
            if(first instanceof Order){
                String apClass = resourceBundle.getString("locale.message.ApartmentClass");
                String roomNumber = resourceBundle.getString("locale.message.RoomNumber");
                String couchette = resourceBundle.getString("locale.message.Couchette");
                String datein = resourceBundle.getString("locale.message.Datein");
                String dateout = resourceBundle.getString("locale.message.Dateout");
                String status = resourceBundle.getString("locale.message.Status");
                String action = resourceBundle.getString("locale.message.Action");
                if(getAction().equals("no")){
                    out.write("<thead class=\"text-center\"><tr>" +
                            "<th>"+apClass+"</th>" +
                            "<th>"+roomNumber+"</th>"+
                            "<th>"+couchette+"</th>"+
                            "<th>"+datein+"</th>"+
                            "<th>"+dateout+"</th>" +
                            "<th>"+status+"</th>"+
                            "</tr></thead>");
                }else{
                    out.write("<thead class=\"text-center\"><tr>" +
                        "<th>"+apClass+"</th>" +
                        "<th>"+roomNumber+"</th>"+
                        "<th>"+couchette+"</th>"+
                        "<th>"+datein+"</th>"+
                        "<th>"+dateout+"</th>" +
                        "<th>"+status+"</th>"+
                        "<th>"+action+"</th>"+
                        "</tr></thead>");
                }
                out.write("<tbody>");
                for(int i=0;i<size;i++){
                    Order order;
                    if(i==0){
                        order= (Order)first;
                    }
                    else{
                        order = (Order)map.getElement();
                    }
                    if(order.getApartmentClass().equals(RequestParameterName.BEDROOM)){
                        order.setApartmentClass(resourceBundle.getString("locale.message.ApartmentClass1"));
                    }
                    if(order.getApartmentClass().equals(RequestParameterName.FAMILYROON)){
                        order.setApartmentClass(resourceBundle.getString("locale.message.ApartmentClass2"));
                    }
                    if (order.getApartmentClass().equals(RequestParameterName.HONEYMOONROOM)){
                        order.setApartmentClass(resourceBundle.getString("locale.message.ApartmentClass3"));
                    }
                    if(order.getStatus().equals(RequestParameterName.STATUS_NEW)){
                        order.setStatus(resourceBundle.getString("locale.message.StatusNew"));
                    }
                    if (order.getStatus().equals(RequestParameterName.STATUS_ORDERED)){
                        order.setStatus(resourceBundle.getString("locale.message.StatusOrdered"));
                    }
                    out.write("<tr>"+order.toString());
                    String addValueToOrderId = " document.getElementById('"+RequestParameterName.ORDER_ID+"').value='"+map.getId()+"'";
                    if(getUser().equals(RequestParameterName.ROLE_USER)&&!getAction().equals("no")){
                        out.write("<td>" +
                                "<div class=\"btn-group btn-group-vertical\">" +
                                "<button  type=\"submit\" class=\"btn btn-labeled btn-primary btn-mini\"\n" +
                                "onclick=\"document.getElementById('command').value='go-to-change-order-page';" +
                                addValueToOrderId+ ";\">\n" +
                                "<span class=\"btn-label\" ><i class=\"fa fa-edit\"></i>\n" +
                                "</span> " +resourceBundle.getString("locale.message.Update")+" "+
                                "</button>");

                        out.write("<button  type=\"submit\" class=\"btn btn-labeled btn-danger btn-mini\"\n" +
                                "onclick=\"document.getElementById('command').value='remove-order';" +
                                addValueToOrderId+";\">\n" +
                                "<span class=\"btn-label\" ><i class=\"glyphicon glyphicon-remove\"></i>\n" +
                                "</span> " +resourceBundle.getString("locale.message.Remove")+" "+
                                "</button>" +
                                "</div>" +
                                "</td>" +
                                "</tr>");
                    }
                    else if(getUser().equals(RequestParameterName.ROLE_ADMIN)&&!getAction().equals("no")){
                            out.write("<td>" +
                                    "<div class=\"btn-group btn-group-vertical\">" +
                                    "<button  type=\"submit\" class=\"btn btn-labeled btn-primary btn-mini\"\n" +
                                    "onclick=\"document.getElementById('command').value='find-apartment';" +
                                    addValueToOrderId+ ";\">\n" +
                                    "<span class=\"btn-label\" ><i class=\"glyphicon glyphicon-search\"></i>\n" +
                                    "</span> " +resourceBundle.getString("locale.message.Find")+" "+
                                    "</button>");

                            out.write("<button  type=\"submit\" class=\"btn btn-labeled btn-danger btn-mini\"\n" +
                                    "onclick=\"document.getElementById('command').value='remove-order';" +
                                    addValueToOrderId+";\">\n" +
                                    "<span class=\"btn-label\" ><i class=\"glyphicon glyphicon-remove\"></i>\n" +
                                    "</span> " +resourceBundle.getString("locale.message.Remove")+" "+
                                    "</button>" +
                                    "</div>" +
                                    "</td>" +
                                    "</tr>");
                        }
                    }
            }
                if(first instanceof Apartment){
                    String price = resourceBundle.getString("locale.message.Price");
                    String couchette = resourceBundle.getString("locale.message.Couchette");
                    String roomNumber = resourceBundle.getString("locale.message.RoomNumber");
                    String status = resourceBundle.getString("locale.message.Status");
                    String apClass = resourceBundle.getString("locale.message.ApartmentClass");
                    String action = resourceBundle.getString("locale.message.Action");
                    String choose = resourceBundle.getString("locale.message.Choose");
                    String change = resourceBundle.getString("locale.message.Update");
                    out.write("<thead class=\"text-center\"><tr>" +
                            //"<th>"+id+"</th>" +
                            "<th>"+price+"</th>" +
                            "<th>"+roomNumber+"</th>"+
                            "<th>"+couchette+"</th>"+
                            "<th>"+status+"</th>"+
                            "<th>"+apClass+"</th>" +
                            "<th>"+action+"</th>"+
                            "</tr></thead>");

                    out.write("<tbody>");
                    for(int i=0;i<size;i++){
                        Apartment apartment;
                        if(i==0){
                            apartment= (Apartment)first;
                        }
                        else{
                            apartment = (Apartment)map.getElement();
                        }
                        if(apartment.getStatus().equals(RequestParameterName.APARTMENT_STATUS_AVAILABLE)){
                            apartment.setStatus(resourceBundle.getString("locale.message.StatusAv"));
                        }
                        if(apartment.getStatus().equals(RequestParameterName.APARTMENT_STATUS_NOT_AVAILABLE)){
                            apartment.setStatus(resourceBundle.getString("locale.message.StatusNotAv"));
                        }
                        //map.updateElement(map.getId(), apartment);
                        out.write("<tr>"+apartment.toString());
                        String addValueToApartmentId = " document.getElementById('"+RequestParameterName.APARTMENT_ID+"').value='"+map.getId()+"'";
                        if(!getAction().equals("")&&getAction().equals("change")){
                            out.write("<td>" +
                                    "<div class=\"btn-group btn-group-vertical\">" +
                                    "<button  type=\"submit\" class=\"btn btn-labeled btn-primary btn-small\"\n" +
                                    "onclick=\"document.getElementById('command').value='go-to-change-apartment-page';" +
                                    addValueToApartmentId+ ";\">\n" +
                                    "<span class=\"btn-label\" ><i class=\"fa fa-edit\"></i>\n" +
                                    "</span> "+change+"\n" +
                                    "</button>");

                            out.write("<button  type=\"submit\" class=\"btn btn-labeled btn-danger btn-mini\"\n" +
                                    "onclick=\"document.getElementById('command').value='remove-apartment';" +
                                    addValueToApartmentId+";\">\n" +
                                    "<span class=\"btn-label\" ><i class=\"glyphicon glyphicon-remove\"></i>\n" +
                                    "</span> " +resourceBundle.getString("locale.message.Remove")+" "+
                                    "</button>" +
                                    "</div>" +
                                    "</td>" +
                                    "</tr>");
                        }
                        else{
                            out.write("<td>" +
                                    "<div class=\"btn-group btn-group-vertical\">" +
                                    "<button  type=\"submit\" class=\"btn btn-labeled btn-success btn-small\"\n" +
                                    "onclick=\"document.getElementById('command').value='to-reserve-apartment';" +
                                    addValueToApartmentId+ ";\">\n" +
                                    "<span class=\"btn-label\" ><i class=\"fa fa-check\"></i>\n" +
                                    "</span> "+choose+"\n" +
                                    "</button>"+
                                    "</div>" +
                                    "</td>" +
                                    "</tr>");
                        }
                    }
                }
                if(first instanceof Check){
                    String price = resourceBundle.getString("locale.message.Price");
                    String apartmentId = resourceBundle.getString("locale.message.ApartmentId");
                    String orderId = resourceBundle.getString("locale.message.OrderId");
                    out.write("<thead class=\"text-center\"><tr>" +
                          //  "<th>"+id+"</th>" +
                            "<th>"+price+"</th>" +
                            "<th>"+apartmentId+"</th>"+
                            "<th>"+orderId+"</th>"+
                            "</tr></thead>");

                    out.write("<tbody>");
                    for(int i=0;i<size;i++){
                        Check check;
                        if(i==0){
                            check= (Check)first;
                        }
                        else{
                            check = (Check)map.getElement();
                        }
                        out.write("<tr>"+check.toString()+"</tr>");
                    }
                }
            out.write("</tbody>");
            out.write("</table>");
            }
        }
        catch (IOException e) {
            logger.info(Level.ERROR+" EntityTaleTag has problem "+e);
    }
        return SKIP_BODY;
    }

}
