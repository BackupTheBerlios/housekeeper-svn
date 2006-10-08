<%@ include file="/common/taglibs.jsp"%>

<f:view>
<f:loadBundle var="text" basename="#{stockItemList.bundleName}"/>

<head>
    <title><fmt:message key="stockItemList.title"/></title>
    <content tag="heading"><fmt:message key="stockItemList.heading"/></content>
    <meta name="menu" content="StockItemMenu"/>
</head>
<h:form id="editStockItem">

<c:set var="buttons">
    <h:commandButton value="#{text['button.add']}" action="add" id="add" 
        immediate="true" styleClass="button"/>

    <input type="button" onclick="location.href='<c:url value="/mainMenu.html" />'"
        value="<fmt:message key="button.done"/>"/>
</c:set>

<c:out value="${buttons}" escapeXml="false"/>

<%-- Use a non-displayed dataTable to pull stockItemList into request --%>
<h:dataTable var="stockItem" value="#{stockItemList.stockItems}" style="display:none"/>

<display:table name="stockItemList.stockItems" cellspacing="0" cellpadding="0" requestURI=""
    id="stockItems" pagesize="25" class="table stockItemList" export="true" >

    <display:column sortable="true" titleKey="stockItem.id" media="html">
        <a href="javascript:viewStockItem('<c:out value="${stockItems.id}"/>')"><c:out value="${stockItems.id}"/></a>
    </display:column>
    <display:column property="id" media="csv excel xml pdf" titleKey="stockItem.id"/>
    <display:column property="name" escapeXml="true" titleKey="stockItem.name" sortable="true" />
    <display:column property="description" escapeXml="true" titleKey="stockItem.description" sortable="true"/>
    <display:column property="expiryDate" escapeXml="true" titleKey="stockItem.expiryDate" sortable="true"/>
    <display:column property="category.name" escapeXml="true" titleKey="stockItem.category" sortable="true"/>
    <display:setProperty name="paging.banner.item_name" value="stockItem"/>
    <display:setProperty name="paging.banner.items_name" value="stockItems"/>
</display:table>

<c:out value="${buttons}" escapeXml="false"/>

<h:commandLink action="#{stockItemForm.edit}" id="editStockItemLink">
    <f:param name="id" value=""/>
</h:commandLink>

<script type="text/javascript">
    function viewStockItem(id) {
        var f = document.forms['editStockItem'];
        f.elements['editStockItem:_link_hidden_'].value='editStockItem:editStockItemLink';
        f.elements['id'].value=id;
        f.submit();
    }
    highlightTableRows("stockItems");
</script>
</h:form>

</f:view> 
