<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="login.title"/></title>
    <content tag="heading"><fmt:message key="login.heading"/></content>
    <meta name="menu" content="Login"/>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["theme"]}/layout-1col.css'/>" />
</head>

<body id="login"/>

<%-- Include the login form --%>
<jsp:include page="/loginForm.jsp"/>

<p><fmt:message key="login.passwordHint"/></p>