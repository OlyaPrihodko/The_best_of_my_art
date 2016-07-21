
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:requestEncoding value="utf-8"/>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/com.epam.prihodko.finaltask/localization.locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.message.Datein" var="Datein"/>
<fmt:message bundle="${loc}" key="locale.message.Dateout" var="Dateout"/>
<fmt:message bundle="${loc}" key="locale.message.Logout" var="Logout"/>
<fmt:message bundle="${loc}" key="locale.message.ApartmentClass" var="ApartmentClass"/>
<fmt:message bundle="${loc}" key="locale.message.RoomNumber" var="RoomNumber"/>
<fmt:message bundle="${loc}" key="locale.message.Couchette" var="Couchette"/>
<fmt:message bundle="${loc}" key="locale.message.ToOrder" var="ToOrder"/>
<fmt:message bundle="${loc}" key="locale.message.DateFormat" var="DateFormat"/>
<fmt:message bundle="${loc}" key="locale.message.Back" var="Back"/>
<fmt:message bundle="${loc}" key="locale.message.FinalTask" var="FinalTask"/>
<fmt:message bundle="${loc}" key="locale.message.Message15" var="Message15"/>
<fmt:message bundle="${loc}" key="locale.message.ApartmentClass1" var="ApartmentClass1"/>
<fmt:message bundle="${loc}" key="locale.message.ApartmentClass2" var="ApartmentClass2"/>
<fmt:message bundle="${loc}" key="locale.message.ApartmentClass3" var="ApartmentClass3"/>
<fmt:message bundle="${loc}" key="locale.message.Message21" var="Message21"/>
<fmt:message bundle="${loc}" key="locale.message.Message20" var="Message20"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
    <title>${Message15}</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/bootstrap-theme.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/bootstrap-datetimepicker.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/font-awesome.min.css"/>" rel="stylesheet">

</head>
<body>
<script type="text/javascript">
    <!--
            function check(){
                f_datein=new Date(document.form.datein.value.toString()).getTime();
                f_dateout=new Date(document.form.dateout.value.toString()).getTime();
                f_today=new Date().getTime();
                if (!(f_today<f_datein&&f_datein<f_dateout)){
                    alert('${Message21}');
                    return false;
                }
            }
    -->
</script>
<c:set scope="session" value="/WEB-INF/jsp/userPages/userPersonalArea.jsp" var="previous-page"/>
<div class="container-fluid" >
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-lg-offset-3 col-lg-6">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h2 class="panel-title text-center">
                        ${Message15}
                    </h2>
                </div>
                <br/>
                <div align="center" style="color: red"><c:out value="${mistake}"/></div>
                <div class="panel-body">
                    <form role="form" action="controller" method="post" class="form-horizontal"  name="form"
                          onsubmit="if(document.getElementById('command').value=='make-an-order')return check();">
                        <input type="hidden" id="command" name="command" value=""/>
                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-8 col-lg-10 col-lg-offset-1">
                                <div class="form-group" style="margin: 1px">
                                    <label for="apartment-class" class="col-xs-5 control-label" style="color: #134aa8">${ApartmentClass}:</label>
                                    <div class="col-xs-7">
                                        <select class="form-control" name="apartment-class" id ="apartment-class" >
                                            <option value="Номер со спальней" onclick="document.getElementById('apartment-class').value='Номер со спальней'">${ApartmentClass1}</option>
                                            <option value="Семейный номер" onclick="document.getElementById('apartment-class').value='Семейный номер'">${ApartmentClass2}</option>
                                            <option value="Номер для молодоженов" onclick="document.getElementById('apartment-class').value='Номер для молодоженов'">${ApartmentClass3}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group" style="margin: 1px">
                                    <label for="room-number" class="col-xs-5 control-label" style="color: #134aa8">${RoomNumber}:</label>
                                    <div class="col-xs-7">
                                        <select class="form-control" name="room-number" id ="room-number">
                                            <option value="1" onclick="document.getElementById('room-number').value='1'">1</option>
                                            <option value="2" onclick="document.getElementById('room-number').value='2'">2</option>
                                            <option value="3" onclick="document.getElementById('room-number').value='3'">3</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group" style="margin: 1px">
                                    <label for="couchette" class="col-xs-5 control-label" style="color: #134aa8">${Couchette}:</label>
                                    <div class="col-xs-7">
                                        <select class="form-control" name="couchette" id ="couchette">
                                            <option value="1" onclick="document.getElementById('couchette').value='1'">1</option>
                                            <option value="2" onclick="document.getElementById('couchette').value='2'">2</option>
                                            <option value="3" onclick="document.getElementById('couchette').value='3'">3</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group" style="margin: 1px">
                                    <label for="datein" class="col-xs-5 control-label" style="color: #134aa8">${Datein}:</label>
                                    <div class="col-xs-7">
                                        <input class="form-control" id="datein" type="date" name="datein" placeholder="${DateFormat}" value="" required=""/>
                                    </div>
                                </div>
                                <div class="form-group" style="margin: 1px">
                                    <label for="dateout" class="col-xs-5 control-label" style="color: #134aa8">${Dateout}:</label>
                                    <div class="col-xs-7">
                                        <input class="form-control" id="dateout" type="date" name="dateout" placeholder="${DateFormat}" value="" required=""/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6 col-lg-offset-4" >
                                <br/>
                                <div class="btn-group">
                                    <button  type="submit" class="btn btn-labeled btn-primary"
                                             onclick="document.getElementById('command').value='make-an-order';">
                                      <span class="btn-label" ><i class="fa fa-edit"></i>
                                      </span> ${ToOrder}
                                    </button>
                                    <button  type="submit" class="btn btn-labeled btn-danger"
                                             onclick="document.getElementById('command').value='logout';
                                             document.getElementById('datein').required='';
                                             document.getElementById('dateout').required='';">
                                      <span class="btn-label" ><i class="fa fa-sign-out"></i>
                                      </span> ${Logout}
                                    </button>
                                    <button  type="submit" class="btn btn-labeled btn-primary"
                                             onclick="document.getElementById('command').value='previous-page';
                                             document.getElementById('datein').required='';
                                             document.getElementById('dateout').required='';">
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
</div>
<script type="text/javascript" src="js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="js/moment-with-locales.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>

</body>
</html>
<%--


<%--
<table class="table-bordered">
                                    <tr>
                                        <td><th>${ApartmentClass}</th></td>
                                        <td>
                                            <select class="form-control" name="apartment-class" id ="apartment-class" >
                                                <option value="Номер со спальней" onclick="document.getElementById('apartment-class').value='Номер со спальней'">${ApartmentClass1}</option>
                                                <option value="Семейный номер" onclick="document.getElementById('apartment-class').value='Семейный номер'">${ApartmentClass2}</option>
                                                <option value="Номер для молодоженов" onclick="document.getElementById('apartment-class').value='Номер для молодоженов'">${ApartmentClass3}</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><th>${RoomNumber}</th></td>
                                        <td>
                                            <select class="form-control" name="room-number" id ="room-number">
                                                <option value="1" onclick="document.getElementById('room-number').value='1'">1</option>
                                                <option value="2" onclick="document.getElementById('room-number').value='2'">2</option>
                                                <option value="3" onclick="document.getElementById('room-number').value='3'">3</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><th>${Couchette}</th></td>
                                        <td>
                                            <select class="form-control" name="couchette" id ="couchette">
                                                <option value="1" onclick="document.getElementById('couchette').value='1'">1</option>
                                                <option value="2" onclick="document.getElementById('couchette').value='2'">2</option>
                                                <option value="3" onclick="document.getElementById('couchette').value='3'">3</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><th>${Datein}</th></td>
                                        <td>
                                            <input id="datein" type="text" name="datein" placeholder="${DateFormat}" value="" required=""/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><th>${Dateout}</th></td>
                                        <td>
                                            <input id="dateout" type="text" name="dateout" placeholder="${DateFormat}" value="" required=""/>
                                        </td>
                                    </tr>
                                </table>
                                --%>

<%--

/*var datein = document.forms["form"]["datein"].value;
        var dateout = document.forms["form"]["dateout"].value;
        var today = new Date();
        if(datein.getTime()<today.getTime()){
            alert('${Message21}');
            return false;
        }
        if(dateout.getTime()<datein.getTime()){
            alert('${Message21}');
            return false;
        }
        if(dateout.getTime()<today.getTime()){
            alert('${Message21}');
            return false;
        }*/
--%>