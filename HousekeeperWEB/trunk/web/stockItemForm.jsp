<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="stockItemDetail.title"/></title>
    <content tag="heading"><fmt:message key="stockItemDetail.heading"/></content>
    <meta name="menu" content="StockItemMenu"/>
</head>

<f:view>
<f:loadBundle var="text" basename="#{stockItemForm.bundleName}"/>

<h:form id="stockItemForm" onsubmit="return validateStockItemForm(this)">

<h:inputHidden value="#{stockItemForm.stockItem.id}" id="id"/>

<h:panelGrid columns="3">

    <h:outputLabel styleClass="desc" for="category" value="#{text['stockItem.category']}"/>

	<h:selectOneMenu  styleClass="text medium" id="category"  value="#{stockItemForm.stockItem.category}" required="true" converter="#{stockItemForm.convert}">
		<f:selectItems value="#{stockItemForm.categories}"/>
	</h:selectOneMenu>
    <t:message for="category" styleClass="fieldError"/>

    <h:outputLabel styleClass="desc" for="description" value="#{text['stockItem.description']}"/>

    <h:inputText styleClass="text medium" id="description" value="#{stockItemForm.stockItem.description}" required="true">
        <v:commonsValidator type="required" arg="#{text['stockItem.description']}"/>
    </h:inputText>
    <t:message for="description" styleClass="fieldError"/>

    <h:outputLabel styleClass="desc" for="expiryDate" value="#{text['stockItem.expiryDate']}"/>

        <h:inputText styleClass="text medium" id="expiryDate" value="#{stockItemForm.stockItem.expiryDate}" required="true">
            <v:commonsValidator type="required" arg="#{text['stockItem.expiryDate']}"/>
            <f:convertDateTime pattern="#{text['date.format']}"/>
        </h:inputText>
    <t:message for="expiryDate" styleClass="fieldError"/>

    <h:outputLabel styleClass="desc" for="name" value="#{text['stockItem.name']}"/>

    <h:inputText styleClass="text medium" id="name" value="#{stockItemForm.stockItem.name}" required="true">
        <v:commonsValidator type="required" arg="#{text['stockItem.name']}"/>
    </h:inputText>
    <t:message for="name" styleClass="fieldError"/>

    <h:panelGroup styleClass="buttonBar bottom">
        <h:commandButton value="#{text['button.save']}" action="#{stockItemForm.save}" 
            id="save" styleClass="button"/>

        <c:if test="${not empty stockItemForm.stockItem.id}">
        <h:commandButton value="#{text['button.delete']}" action="#{stockItemForm.delete}" 
            id="delete" styleClass="button" onclick="bCancel=false; return confirmDelete('StockItem')"/>
        </c:if>

        <h:commandButton value="#{text['button.cancel']}" action="cancel" immediate="true"  
            id="cancel" styleClass="button" onclick="bCancel=true"/>
    </h:panelGroup>
    <h:outputText/><h:outputText/>
</h:panelGrid>
</h:form>

<v:validatorScript functionName="validateStockItemForm"/>

<script type="text/javascript">
    Form.focusFirstElement($('stockItemForm'));
</script>

</f:view>
