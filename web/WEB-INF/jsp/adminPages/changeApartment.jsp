<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:requestEncoding value="utf-8"/>
<fmt:setBundle basename="/com.epam.prihodko.finaltask/localization.locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.message.Message23" var="Message23"/>
<fmt:message bundle="${loc}" key="locale.message.Back" var="Back"/>
<fmt:message bundle="${loc}" key="locale.message.Update" var="Update"/>
<fmt:message bundle="${loc}" key="locale.message.Logout" var="Logout"/>
<fmt:message bundle="${loc}" key="locale.message.FinalTask" var="FinalTask"/>
<fmt:message bundle="${loc}" key="locale.message.ApartmentClass1" var="ApartmentClass1"/>
<fmt:message bundle="${loc}" key="locale.message.ApartmentClass2" var="ApartmentClass2"/>
<fmt:message bundle="${loc}" key="locale.message.ApartmentClass3" var="ApartmentClass3"/>
<fmt:message bundle="${loc}" key="locale.message.Price" var="Price"/>
<fmt:message bundle="${loc}" key="locale.message.ApartmentClass" var="ApartmentClass"/>
<fmt:message bundle="${loc}" key="locale.message.RoomNumber" var="RoomNumber"/>
<fmt:message bundle="${loc}" key="locale.message.Couchette" var="Couchette"/>
<fmt:message bundle="${loc}" key="locale.message.Message24" var="Message24"/>
<fmt:message bundle="${loc}" key="locale.message.Message25" var="Message25"/>
<html lang="${language}">
<head>
    <title>${Message23}</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
</head>
<body onload="funct()">
<c:set scope="session" value="/WEB-INF/jsp/adminPages/apartmentTable.jsp" var="previous-page"/>
<jsp:useBean id="apartment" beanName="com.epam.prihodko.finaltask.entity.Apartment"
             type="com.epam.prihodko.finaltask.entity.Apartment" scope="session"/>
<script type="text/javascript">
    <!--
    function  funct(){
        var apCl= "<jsp:getProperty name="apartment" property="classId"/>";
        var rN = "<jsp:getProperty name="apartment" property="roomNumber"/>";
        var coch ="<jsp:getProperty name="apartment" property="couchette"/>";
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
<script type="text/javascript">
    <!--
    function  validate(){
        f_price=document.form.price.value;
        if (f_price<=0){
            alert('${Message24}');
            return false;
        }
        if (f_price>100000){
            alert('${Message25}');
            return false;
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
                       ${Message23}
                    </h2>
                </div>
                <br/>
                <div align="center" style="color: red"><c:out value="${mistake}"/></div>
                <div class="panel-body">
                    <form role="form" action="controller" method="post" name="form"
                          onsubmit="if(document.getElementById('command').value=='update-apartment')return validate();">
                        <input type="hidden" id="command" name="command" value=""/>
                        <input type="hidden" id="apartmentId" name="apartmentId" value=""/>
                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6 col-lg-10 col-lg-offset-1" >
                                <div class="form-group" style="margin: 1px">
                                    <label for="apartment-class" class="col-xs-5 control-label" style="color: #134aa8">${ApartmentClass}:</label>
                                    <div class="col-xs-7">
                                        <select class="form-control" name="apartment-class" id ="apartment-class" value="" >
                                            <option id="a1" value="1" onclick="document.getElementById('apartment-class').value='1'">${ApartmentClass1}</option>
                                            <option id="a2" value="2" onclick="document.getElementById('apartment-class').value='2'">${ApartmentClass2}</option>
                                            <option id="a3" value="3" onclick="document.getElementById('apartment-class').value='3'">${ApartmentClass3}</option>
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
                                        <label for="price" class="col-xs-5 control-label" style="color: #134aa8">${Price}:</label>
                                        <div class="col-xs-7">
                                            <input class="form-control" id="price" type="number" name="price" value="<jsp:getProperty name="apartment" property="price"/>" required=""/>
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
                                             onclick="document.getElementById('command').value='update-apartment';
                                             document.getElementById('price').required='required';">
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