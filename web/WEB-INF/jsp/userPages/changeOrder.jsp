<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="utf-8"/>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="/com.epam.prihodko.finaltask/localization.locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.message.Back" var="Back"/>
<fmt:message bundle="${loc}" key="locale.message.ApartmentClass1" var="ApartmentClass1"/>
<fmt:message bundle="${loc}" key="locale.message.ApartmentClass2" var="ApartmentClass2"/>
<fmt:message bundle="${loc}" key="locale.message.ApartmentClass3" var="ApartmentClass3"/>
<fmt:message bundle="${loc}" key="locale.message.ApartmentClass" var="ApartmentClass"/>
<fmt:message bundle="${loc}" key="locale.message.RoomNumber" var="RoomNumber"/>
<fmt:message bundle="${loc}" key="locale.message.Couchette" var="Couchette"/>
<fmt:message bundle="${loc}" key="locale.message.Datein" var="Datein"/>
<fmt:message bundle="${loc}" key="locale.message.Dateout" var="Dateout"/>
<fmt:message bundle="${loc}" key="locale.message.FinalTask" var="FinalTask"/>
<fmt:message bundle="${loc}" key="locale.message.Message12" var="Message12"/>
<fmt:message bundle="${loc}" key="locale.message.Message21" var="Message21"/>
<fmt:message bundle="${loc}" key="locale.message.Message21" var="Message20"/>
<fmt:message bundle="${loc}" key="locale.message.Update" var="Update"/>
<fmt:message bundle="${loc}" key="locale.message.Logout" var="Logout"/>
<html lang="${language}">
<head>
    <title>${Message12}</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
</head>
<body onload="funct()">

<script type="text/javascript">
    <!--
    function  validate(){
        f_datein=new Date(document.form.datein.value.toString()).getTime();
        f_dateout=new Date(document.form.dateout.value.toString()).getTime();
        f_today=new Date().getTime();
        if (!(f_today<f_datein&&f_datein<f_dateout)){
            alert('${Message21}');
            return false;
        }
    }
    //-->
</script>
<c:set scope="session" value="/WEB-INF/jsp/userPages/userPersonalArea.jsp" var="previous-page"/>
<jsp:useBean id="order" beanName="com.epam.prihodko.finaltask.entity.Order"
             type="com.epam.prihodko.finaltask.entity.Order" scope="session"/>
<script type="text/javascript">
    <!--
    function  funct(){
        var apCl= "<jsp:getProperty name="order" property="apartmentClass"/>";
        var rN = "<jsp:getProperty name="order" property="roomNumber"/>";
        var coch ="<jsp:getProperty name="order" property="couchette"/>";
        if(document.getElementById('a1').value==apCl){
            document.getElementById('a1').selected="selected";
        }
        if(document.getElementById('a2').value==apCl){
            document.getElementById('a2').selected="selected";
        }
        if(document.getElementById('a3').value==apCl){
            document.getElementById('a3').selected="selected";
        }
                if(document.getElementById('r1').value==rN){
                    document.getElementById('r1').selected="selected";
                }
                if(document.getElementById('r2').value==rN){
                    document.getElementById('r2').selected="selected";
                }
                if(document.getElementById('r3').value==rN){
                    document.getElementById('r3').selected="selected";
                }
        if(document.getElementById('c1').value==coch){
            document.getElementById('c1').selected="selected";
        }
        if(document.getElementById('c2').value==coch){
            document.getElementById('c2').selected="selected";
        }
        if(document.getElementById('c3').value==coch){
            document.getElementById('c3').selected="selected";
        }
    }
    //-->
</script>
<div class="container-fluid" >
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-lg-offset-3 col-lg-6">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h2 class="panel-title text-center">
                        ${Message12}
                    </h2>
                </div>
                <br/>
                <div align="center" style="color: red"><c:out value="${mistake}"/></div>
                <div class="panel-body">
                    <form role="form" action="controller" method="post" name="form"
                          onsubmit="if(document.getElementById('command').value=='update-order')return validate();">
                        <input type="hidden" id="command" name="command" value=""/>
                        <input type="hidden" id="orderId" name="orderId" value=""/>
                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6 col-lg-10 col-lg-offset-1" >
                                <div class="form-group" style="margin: 1px">
                                    <label for="apartment-class" class="col-xs-5 control-label" style="color: #134aa8">${ApartmentClass}:</label>
                                    <div class="col-xs-7">
                                        <select class="form-control" name="apartment-class" id ="apartment-class" value="" >
                                            <option id="a1" value="Номер со спальней" onclick="document.getElementById('apartment-class').value='Номер со спальней'">${ApartmentClass1}</option>
                                            <option id="a2" value="Семейный номер" onclick="document.getElementById('apartment-class').value='Семейный номер'">${ApartmentClass2}</option>
                                            <option id="a3" value="Номер для молодоженов" onclick="document.getElementById('apartment-class').value='Номер для молодоженов'">${ApartmentClass3}</option>
                                        </select>
                                    </div>
                                    <div class="form-group" style="margin: 1px">
                                        <label for="room-number" class="col-xs-5 control-label" style="color: #134aa8">${RoomNumber}:</label>
                                        <div class="col-xs-7">
                                            <select class="form-control" name="room-number" id ="room-number">
                                                <option id="r1" value="1" onclick="document.getElementById('room-number').value='1'">1</option>
                                                <option id="r2" value="2" onclick="document.getElementById('room-number').value='2'">2</option>
                                                <option id="r3" value="3" onclick="document.getElementById('room-number').value='3'">3</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group" style="margin: 1px">
                                        <label for="couchette" class="col-xs-5 control-label" style="color: #134aa8">${Couchette}:</label>
                                        <div class="col-xs-7">
                                            <select class="form-control" name="couchette" id ="couchette">
                                                <option id="c1" value="1" onclick="document.getElementById('couchette').value='1'">1</option>
                                                <option id="c2" value="2" onclick="document.getElementById('couchette').value='2'">2</option>
                                                <option id="c3" value="3" onclick="document.getElementById('couchette').value='3'">3</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group" style="margin: 1px">
                                        <label for="datein" class="col-xs-5 control-label" style="color: #134aa8">${Datein}:</label>
                                        <div class="col-xs-7">
                                            <input class="form-control" id="datein" type="date" name="datein" placeholder="${DateFormat}" value="<jsp:getProperty name="order" property="date_in"/>" required=""/>
                                        </div>
                                    </div>
                                    <div class="form-group" style="margin: 1px">
                                        <label for="dateout" class="col-xs-5 control-label" style="color: #134aa8">${Dateout}:</label>
                                        <div class="col-xs-7">
                                            <input class="form-control" id="dateout" type="date" name="dateout" placeholder="${DateFormat}" value="<jsp:getProperty name="order" property="date_out"/>" required=""/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6 col-lg-8  col-lg-offset-3" >
                                <br/>
                                <div class="btn-group">
                                    <button  type="submit" class="btn btn-labeled btn-primary"
                                             onclick="document.getElementById('command').value='update-order';
                                             document.getElementById('datein').required='required';
                                             document.getElementById('dateout').required='required';">
                                      <span class="btn-label" ><i class="fa fa-edit"></i>
                                      </span> ${Update}
                                    </button>
                                    <button  type="submit" class="btn btn-labeled btn-danger"
                                             onclick="document.getElementById('command').value='logout';">
                                      <span class="btn-label" ><i class="fa fa-sign-out"></i>
                                      </span> ${Logout}
                                    </button>
                                    <button  type="submit" class="btn btn-labeled btn-primary"
                                             onclick="document.getElementById('command').value='previous-page';">
                                      <span class="btn-label" ><i class="fa fa-arrow-left"></i>
                                      </span> ${Back}
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <%@include file="/WEB-INF/jsp/footerPart.jsp"%>
            </div>
        </div>
    </div>
</div>



<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>
<%--
<table class="table table-bordered">
                                    <tr>
                                        <td><th>${ApartmentClass}</th></td>
                                        <td>
                                            <input class="input-small" type="text" name="apartment-class" value="<jsp:getProperty name="order" property="apartmentClass"/>"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><th>${RoomNumber}</th></td>
                                        <td>
                                            <input class="input-small" type="text" name="room-number" value="<jsp:getProperty name="order" property="roomNumber"/>">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><th>${Couchette}</th></td>
                                        <td>
                                            <input class="input-small" type="text" name="couchette" value="<jsp:getProperty name="order" property="couchette"/>" >
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><th>${Datein}</th></td>
                                        <td>
                                            <input class="input-small" type="text" name="datein" value="<jsp:getProperty name="order" property="date_in"/>" >
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><th>${Dateout}</th></td>
                                        <td>
                                            <input class="input-small" type="text" name="dateout" value="<jsp:getProperty name="order" property="date_out"/>" >
                                        </td>
                                    </tr>
                                </table>
--%>