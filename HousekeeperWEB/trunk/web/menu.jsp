<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="WEB-INF/classes/cssHorizontalMenu.vm" permissions="rolesAdapter">
<ul id="primary-nav" class="menuList clearfix">
    <li class="pad">&nbsp;</li>
    <c:if test="${empty pageContext.request.remoteUser}"><li><strong><a href="<c:url value="/login.jsp"/>"><fmt:message key="login.title"/></a></strong></li></c:if>
    <menu:displayMenu name="MainMenu"/>
    <!--Category-START-->
    <menu:displayMenu name="CategoryMenu"/>
    <!--Category-END-->
    <!--StockItem-START-->
    <menu:displayMenu name="StockItemMenu"/>
    <!--StockItem-END-->
    <menu:displayMenu name="UserMenu"/>
    <menu:displayMenu name="FileUpload"/>
    <menu:displayMenu name="AdminMenu"/>
    <menu:displayMenu name="Logout"/>
</ul>
</menu:useMenuDisplayer>

<script type="text/javascript">
/*<![CDATA[*/
var navItems = document.getElementById("primary-nav").getElementsByTagName("li");

for (var i=0; i<navItems.length; i++) {
    if(navItems[i].className == "menubar") {
        navItems[i].onmouseover=function() { this.className += " over"; }
        navItems[i].onmouseout=function() { this.className = "menubar"; }
    }
}
/*]]>*/
</script>