<%@ include file="/common/taglibs.jsp"%>

<f:view>
<f:loadBundle var="text" basename="#{categoryList.bundleName}"/>

<head>
    <title><fmt:message key="categoryList.title"/></title>
    <content tag="heading"><fmt:message key="categoryList.heading"/></content>
    <meta name="menu" content="CategoryMenu"/>
</head>
<h:form id="editCategory">

<c:set var="buttons">
    <h:commandButton value="#{text['button.add']}" action="add" id="add" 
        immediate="true" styleClass="button"/>

    <input type="button" onclick="location.href='<c:url value="/mainMenu.html" />'"
        value="<fmt:message key="button.done"/>"/>
</c:set>

<c:out value="${buttons}" escapeXml="false"/>

<%-- Use a non-displayed dataTable to pull categoryList into request --%>
<h:dataTable var="category" value="#{categoryList.categorys}" style="display:none"/>

<display:table name="categoryList.categorys" cellspacing="0" cellpadding="0" requestURI=""
    id="categorys" pagesize="25" class="table categoryList" export="true">

    <display:column sortable="true" titleKey="category.id" media="html">
        <a href="javascript:viewCategory('<c:out value="${categorys.id}"/>')"><c:out value="${categorys.id}"/></a>
    </display:column>
    <display:column property="id" media="csv excel xml pdf" titleKey="category.id"/>
    <display:column property="name" escapeXml="true" titleKey="category.name" sortable="true"/>
    <display:column property="description" escapeXml="true" titleKey="category.description" sortable="true"/>
    <display:setProperty name="paging.banner.item_name" value="category"/>
    <display:setProperty name="paging.banner.items_name" value="categorys"/>
</display:table>

<c:out value="${buttons}" escapeXml="false"/>

<h:commandLink action="#{categoryForm.edit}" id="editCategoryLink">
    <f:param name="id" value=""/>
</h:commandLink>

<script type="text/javascript">
    function viewCategory(id) {
        var f = document.forms['editCategory'];
        f.elements['editCategory:_link_hidden_'].value='editCategory:editCategoryLink';
        f.elements['id'].value=id;
        f.submit();
    }
    highlightTableRows("categorys");
</script>
</h:form>

</f:view> 
