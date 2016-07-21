<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mytag" uri="customtags" %>
<fmt:requestEncoding value="utf-8"/>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/com.epam.prihodko.finaltask/localization.locale" var="loc"/>
<fmt:message bundle="${loc}" key="locale.message.FinalTask" var="FinalTask"/>
<fmt:message bundle="${loc}" key="locale.message.Logout" var="Logout"/>
<fmt:message bundle="${loc}" key="locale.message.Message10" var="Message10"/>
<fmt:message bundle="${loc}" key="locale.message.Message11" var="Message11"/>
<fmt:message bundle="${loc}" key="locale.message.Apartments" var="Apartments"/>
<html lang="${language}">
<head>
    <title>${Message11}</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
<c:set scope="session" value="/WEB-INF/jsp/login.jsp" var="previous-page"/>
<jsp:useBean id="mapBeanOrder" beanName="com.epam.prihodko.finaltask.bean.MapBean"
             type="com.epam.prihodko.finaltask.bean.MapBean" scope="session"/>
<div class="container-fluid" >
    <div class="row">
        <div class="col-xs-12 col-sm-12 col-lg-offset-2 col-lg-8">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h2 class="panel-title text-center">
                        ${Message11}
                    </h2>
                </div>
                <div class="panel-body">
                    <form role="form" action="controller" method="post">
                        <input type="hidden" id="command" name="command" value=""/>
                        <input type="hidden" id="orderId" name="orderId" value=""/>
                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6  col-lg-offset-4">
                                <div class="btn-group">
                                    <button  type="submit" class="btn btn-labeled btn-danger"
                                             onclick="document.getElementById('command').value='logout';">
                                    <span class="btn-label" ><i class="fa fa-sign-out"></i>
                                    </span> ${Logout}
                                    </button>
                                    <button  type="submit" class="btn btn-labeled btn-primary"
                                             onclick="document.getElementById('command').value='go-to-apartmentTable-page';">
                                    <span class="btn-label" ><i class="fa fa-list"></i>
                                    </span> ${Apartments}
                                    </button>
                                </div>
                            </div>
                        </div><br/>
                        <div class="row text-center">
                            <h3>${Message10}</h3>
                        </div>
                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6 col-lg-12">
                                <mytag:maptag map="${mapBeanOrder}" lang="${language}" user="${sessionScope.user}" action=""/>
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
