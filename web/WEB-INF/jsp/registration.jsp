<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:requestEncoding value="utf-8"/>
<fmt:setBundle basename="/com.epam.prihodko.finaltask/localization.locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.message.Name" var="Name"/>
<fmt:message bundle="${loc}" key="locale.message.Surname" var="Surname"/>
<fmt:message bundle="${loc}" key="locale.message.Email" var="Email"/>
<fmt:message bundle="${loc}" key="locale.message.Phone" var="Phone"/>
<fmt:message bundle="${loc}" key="locale.message.Password" var="Password"/>
<fmt:message bundle="${loc}" key="locale.message.Login" var="Login"/>
<fmt:message bundle="${loc}" key="locale.message.Signup" var="Signup"/>
<fmt:message bundle="${loc}" key="locale.message.Back" var="Back"/>
<fmt:message bundle="${loc}" key="locale.message.FinalTask" var="FinalTask"/>
<fmt:message bundle="${loc}" key="locale.message.Registration" var="Registration"/>
<fmt:message bundle="${loc}" key="locale.message.Message1" var="Message1"/>
<fmt:message bundle="${loc}" key="locale.message.Message3" var="Message3"/>
<fmt:message bundle="${loc}" key="locale.message.Message4" var="Message4"/>
<fmt:message bundle="${loc}" key="locale.message.Message5" var="Message5"/>

<html lang="${language}">
<head>
    <title>${Registration}</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">


</head>
<body>
<script type="text/javascript">
    <!--
    function check(){
        f_name=document.inputform.name.value.toString();
        if (f_name==''){
            alert('${Message4} "${Name}"');
            document.inputform.name.focus();
            return false;
        }else{
            if(f_name.length<2){
                alert('${Message3}');
                document.inputform.name.focus();
                return false;
            }
        }
        f_surname=document.inputform.surname.value.toString();
        if (f_surname==''){
            alert('${Message4} "${Surname}"');
            document.inputform.surname.focus();
            return false;
        }else{
            if(f_surname.length<2){
                alert('${Message3}');
                document.inputform.surname.focus();
                return false;
            }
        }
        f_email=document.inputform.email.value.toString();
        if (f_email==''){
            alert('${Message4} "${Email}"');
            document.inputform.email.focus();
            return false;
        }else{
            dog=f_email.indexOf('@');
            if ((f_email.indexOf('.')==-1) || (dog==-1) || (dog < 1) ||
                    (dog > f_email.length - 5) || (f_email.charAt(dog - 1)=='.') || (f_email.charAt(dog + 1)=='.')){
                alert('${Message5}');
                document.inputform.email.focus();
                return false;
            }
        }
        f_phone=document.inputform.phone.value.toString();
        if (f_phone==''){
            alert('${Message4} "${Phone}"');
            document.inputform.phone.focus();
            return false;
        }else{
            if(f_phone.length<7){
                alert('${Message3}');
                document.inputform.phone.focus();
                return false;
            }
        }
        f_login=document.inputform.login.value.toString();
        if (f_login==''){
            alert('${Message4} "${Login}"');
            document.inputform.login.focus();
            return false;
        }else{
            if(f_login.length<2){
                alert('${Message3}');
                document.inputform.login.focus();
                return false;
            }
        }
        f_password=document.inputform.password.value.toString();
        if (f_password==''){
            alert('${Message4} "${Password}"');
            document.inputform.password.focus();
            return false;
        }else{
            if(f_password.length<4){
                alert('${Message3}');
                document.inputform.password.focus();
                return false;
            }
        }
    }
    //-->
</script>
<c:set scope="session" value="/WEB-INF/jsp/login.jsp" var="previous-page"/>
<div class="container-fluid" >
    <div class="row">
        <div class="col-xs-6 col-sm-6 col-md-6 col-md-offset-3 col-lg-offset-3 col-lg-6">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h2 class="panel-title text-center">
                        ${Registration}
                    </h2>
                </div>
                <div class="panel-body">
                    <br/>
                    <div align="center" style="color: red"><c:out value="${mistake}"/></div>
                    <form role="form" action="controller" method="post" name="inputform" class="form-horizontal"
                         onsubmit="if(document.getElementById('command').value=='signup')return check();">
                        <input type="hidden" id="command" name="command" value=""/>
                        <div class="row">
                            <div class="col-xs-12 col-12-6 col-md-6 col-lg-6  col-md-offset-3 col-lg-offset-3">
                                <div class="form-group" style="margin: 1px">
                                    <label for="inputName" class="col-xs-5 control-label" style="color: #134aa8">${Name}:</label>
                                    <div class="col-xs-7">
                                        <input type="text" name = "name" class="form-control" id="inputName" value="" autofocus style="background-color: #d4f1ec">
                                    </div>
                                </div>
                                <div class="form-group" style="margin: 1px">
                                    <label for="inputSurname" class="col-xs-5 control-label" style="color: #134aa8">${Surname}:</label>
                                    <div class="col-xs-7">
                                        <input type="text" name = "surname" class="form-control" id="inputSurname" value="" style="background-color: #d4f1ec">
                                    </div>
                                </div>
                                <div class="form-group" style="margin: 1px">
                                    <label for="inputEmail" class="col-xs-5 control-label" style="color: #134aa8">${Email}:</label>
                                    <div class="col-xs-7">
                                        <input type="email" name="email"  class="form-control" id="inputEmail" value="" style="background-color: #d4f1ec">
                                    </div>
                                </div>
                                <div class="form-group" style="margin: 1px">
                                    <label for="inputPhone" class="col-xs-5 control-label" style="color: #134aa8">${Phone}:</label>
                                    <div class="col-xs-7">
                                        <input type="text" name = "phone" class="form-control" id="inputPhone" value="" style="background-color: #d4f1ec">
                                    </div>
                                </div>
                                <div class="form-group" style="margin: 1px">
                                    <label for="inputLogin" class="col-xs-5 control-label" style="color: #134aa8">${Login}:</label>
                                    <div class="col-xs-7">
                                        <input type="text" name = "login" class="form-control" id="inputLogin" value="" style="background-color: #d4f1ec">
                                    </div>
                                </div>
                                <div class="form-group" style="margin: 1px">
                                    <label for="inputPassword" class="col-xs-5 control-label" style="color: #134aa8">${Password}:</label>
                                    <div class="col-xs-7">
                                        <input type="password" name = "password" class="form-control" id="inputPassword" value="" style="background-color: #d4f1ec">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6 col-lg-8  col-lg-offset-4" >
                                <br/>
                                <div class="btn-group">
                                    <button  type="submit" class="btn btn-labeled btn-success"
                                             onclick="document.getElementById('command').value='signup';">
                                      <span class="btn-label" ><i class="glyphicon glyphicon-ok"></i>
                                      </span> ${Signup}
                                    </button>
                                    <button  type="submit" class="btn btn-labeled btn-primary"
                                             onclick="document.getElementById('command').value='logout';">
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
                                        <td><strong>${Name}</strong></td>
                                        <td><input class="form-control span3" type="text" name="name" value=""/></td>
                                    </tr>
                                    <tr>
                                        <td><strong>${Surname}</strong></td>
                                        <td><input class="form-control span3" type="text" name="surname"  value=""/></td>
                                    </tr>
                                    <tr>
                                        <td><strong>${Email}</strong></td>
                                        <td><input class="form-control span3" type="text" name="email"  value=""/></td>
                                    </tr>
                                    <tr>
                                        <td><strong>${Phone}</strong></td>
                                        <td><input class="form-control span3" type="text" name="phone"  value=""/></td>
                                    </tr>
                                    <tr>
                                        <td><strong>${Login}</strong></td>
                                        <td><input class="form-control span3" type="text" name="login"  value=""/></td>
                                    </tr>
                                    <tr>
                                        <td><strong>${Password}</strong></td>
                                        <td><input class="form-control span3" type="password" name="password"  value=""/></td>
                                    </tr>
                                </table>
--%>