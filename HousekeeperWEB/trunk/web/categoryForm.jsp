<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="categoryDetail.title"/></title>
    <content tag="heading"><fmt:message key="categoryDetail.heading"/></content>
    <meta name="menu" content="CategoryMenu"/>
</head>

<f:view>
<f:loadBundle var="text" basename="#{categoryForm.bundleName}"/>

<h:form id="categoryForm" onsubmit="return validateCategoryForm(this)">

<h:inputHidden value="#{categoryForm.category.id}" id="id"/>

<h:panelGrid columns="3">

    <h:outputLabel styleClass="desc" for="name" value="#{text['category.name']}"/>

    <h:inputText styleClass="text medium" id="name" value="#{categoryForm.category.name}" required="true">
        <v:commonsValidator type="required" arg="#{text['category.name']}"/>
    </h:inputText>
    <t:message for="name" styleClass="fieldError"/>

    <h:outputLabel styleClass="desc" for="description" value="#{text['category.description']}"/>

    <h:inputText styleClass="text medium" id="description" value="#{categoryForm.category.description}">
        
    </h:inputText>
    <t:message for="description" styleClass="fieldError"/>

    <h:panelGroup styleClass="buttonBar bottom">
        <h:commandButton value="#{text['button.save']}" action="#{categoryForm.save}" 
            id="save" styleClass="button"/>

        <c:if test="${not empty categoryForm.category.id}">
        <h:commandButton value="#{text['button.delete']}" action="#{categoryForm.delete}" 
            id="delete" styleClass="button" onclick="bCancel=false; return confirmDelete('Category')"/>
        </c:if>

        <h:commandButton value="#{text['button.cancel']}" action="cancel" immediate="true"  
            id="cancel" styleClass="button" onclick="bCancel=true"/>
    </h:panelGroup>
    <h:outputText/><h:outputText/>
</h:panelGrid>
</h:form>

<v:validatorScript functionName="validateCategoryForm"/>

<script type="text/javascript">
    Form.focusFirstElement($('categoryForm'));
</script>

</f:view>
