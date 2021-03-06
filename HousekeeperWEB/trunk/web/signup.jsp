<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="signup.title"/></title>
<content tag="heading"><fmt:message key="signup.heading"/></content>
<body id="signup"/>

<f:view>
<f:loadBundle var="text" basename="#{signupForm.bundleName}"/>

<div class="separator"></div>

<h:form id="signupForm" onsubmit="return validateSignupForm(this)">

<h:panelGrid columns="3">

    <h:panelGroup styleClass="info">
        <h:outputText value="#{text['signup.message']}"/>
    </h:panelGroup>
    <h:outputText/><h:outputText/>

    <h:outputLabel for="username" styleClass="desc" value="#{text['user.username']}"/>
    <t:message for="username" styleClass="fieldError"/>
    <h:inputText value="#{signupForm.user.username}" id="username" required="true" styleClass="text large">
        <v:commonsValidator type="required" arg="#{text['user.username']}"/>
    </h:inputText>

    <h:panelGroup>
        <t:htmlTag value="div">
            <t:htmlTag value="div" styleClass="left">
                <h:outputLabel for="password" value="#{text['user.password']}" styleClass="desc"/>
                <t:message for="password" styleClass="fieldError"/>
                <h:inputSecret value="#{signupForm.user.password}" id="password"
                    redisplay="true" required="true" styleClass="text medium">
                    <v:commonsValidator type="required" arg="#{text['user.password']}"/>
                </h:inputSecret>
            </t:htmlTag>
            <t:htmlTag value="div">
                <h:outputLabel for="confirmPassword" value="#{text['user.confirmPassword']}" styleClass="desc"/>
                <t:message for="confirmPassword" styleClass="fieldError"/>
                <h:inputSecret value="#{signupForm.user.confirmPassword}" id="confirmPassword"
                    redisplay="true" required="true" styleClass="text medium">
                    <v:commonsValidator type="required" arg="#{text['user.confirmPassword']}"/>
                    <t:validateEqual for="password"/>
                </h:inputSecret>
            </t:htmlTag>
        </t:htmlTag>
    </h:panelGroup>
    <h:outputText/><h:outputText/>

    <h:outputLabel for="passwordHint" value="#{text['user.passwordHint']}" styleClass="desc"/>
    <t:message for="passwordHint" styleClass="fieldError"/>
    <h:inputText value="#{signupForm.user.passwordHint}" id="passwordHint" required="true" styleClass="text large">
        <v:commonsValidator type="required" arg="#{text['user.passwordHint']}"/>
    </h:inputText>

    <h:panelGroup>
        <t:htmlTag value="div">
            <t:htmlTag value="div" styleClass="left">
                <h:outputLabel for="firstName" value="#{text['user.firstName']}" styleClass="desc"/>
                <t:message for="firstName" styleClass="fieldError"/>
                <h:inputText id="firstName" value="#{signupForm.user.firstName}" maxlength="50" required="true" styleClass="text medium">
                    <v:commonsValidator type="required" arg="#{text['user.firstName']}"/>
                </h:inputText>
            </t:htmlTag>
            <t:htmlTag value="div">
                <h:outputLabel for="lastName" value="#{text['user.lastName']}" styleClass="desc"/>
                <t:message for="lastName" styleClass="fieldError"/>
                <h:inputText value="#{signupForm.user.lastName}" id="lastName" maxlength="50" required="true" styleClass="text medium">
                    <v:commonsValidator type="required" arg="#{text['user.lastName']}"/>
                </h:inputText>
            </t:htmlTag>
        </t:htmlTag>
    </h:panelGroup>
    <h:outputText/><h:outputText/>

    <h:panelGroup>
        <t:htmlTag value="div">
            <t:htmlTag value="div" styleClass="left">
                <h:outputLabel for="email" value="#{text['user.email']}" styleClass="desc"/>
                <t:message for="email" styleClass="fieldError"/>
                <h:inputText value="#{signupForm.user.email}" id="email" required="true" styleClass="text medium">
                    <f:validator validatorId="org.apache.myfaces.validator.Email"/>
                    <v:commonsValidator type="required" arg="#{text['user.email']}"/>
                    <v:commonsValidator type="email" arg="#{text['user.email']}"/>
                </h:inputText>
            </t:htmlTag>
            <t:htmlTag value="div">
                <h:outputLabel for="phoneNumber" value="#{text['user.phoneNumber']}" styleClass="desc"/>
                <t:message for="phoneNumber" styleClass="fieldError"/>
                <h:inputText value="#{signupForm.user.phoneNumber}" id="phoneNumber" styleClass="text medium">
                    <t:validateRegExpr pattern="^\(?(\d{3})\)?[-| ]?(\d{3})[-| ]?(\d{4})$"/>
                </h:inputText>
            </t:htmlTag>
        </t:htmlTag>
    </h:panelGroup>
    <h:outputText/><h:outputText/>

    <h:outputLabel for="website" value="#{text['user.website']}" styleClass="desc"/>
    <t:message for="website" styleClass="fieldError"/>
    <h:inputText value="#{signupForm.user.website}" id="website" required="true" styleClass="text large">
        <v:commonsValidator type="required" arg="#{text['user.website']}"/>
    </h:inputText>

    <h:panelGroup>
        <t:htmlTag value="label" styleClass="desc"><h:outputText value="#{text['user.address.address']}"/></t:htmlTag>
        <t:htmlTag value="div" styleClass="group">
            <t:htmlTag value="div">
                <h:inputText value="#{signupForm.user.address.address}" id="address" styleClass="text large"/>
                <t:message for="address" styleClass="fieldError"/>
                <t:htmlTag value="p"><h:outputLabel for="address" value="#{text['user.address.address']}"/></t:htmlTag>
            </t:htmlTag>
            <t:htmlTag value="div" styleClass="left">
                <h:inputText value="#{signupForm.user.address.city}" id="city" required="true" styleClass="text medium">
                    <v:commonsValidator type="required" arg="#{text['user.address.city']}"/>
                </h:inputText>
                <t:message for="city" styleClass="fieldError"/>
                <t:htmlTag value="p"><h:outputLabel for="city" value="#{text['user.address.city']}"/></t:htmlTag>
            </t:htmlTag>
            <t:htmlTag value="div">
                <h:inputText value="#{signupForm.user.address.province}" id="province" size="2" required="true" styleClass="text state">
                    <v:commonsValidator type="required" arg="#{text['user.address.province']}"/>
                </h:inputText>
                <t:message for="province" styleClass="fieldError"/>
                <t:htmlTag value="p"><h:outputLabel for="province" value="#{text['user.address.province']}"/></t:htmlTag>
            </t:htmlTag>
            <t:htmlTag value="div" styleClass="left">
                <h:inputText value="#{signupForm.user.address.postalCode}" id="postalCode" required="true" styleClass="text zip">
                    <v:commonsValidator type="required" arg="#{text['user.address.postalCode']}"/>
                    <t:validateRegExpr pattern="^\d{5}\d*$"/>
                </h:inputText>
                <t:message for="postalCode" styleClass="fieldError"/>
                <t:htmlTag value="p"><h:outputLabel for="postalCode" value="#{text['user.address.postalCode']}"/></t:htmlTag>
            </t:htmlTag>
            <t:htmlTag value="div">
                <h:selectOneMenu value="#{signupForm.country}" id="country" required="true" styleClass="select">
                    <f:selectItems value="#{signupForm.countries}"/>
                    <v:commonsValidator type="required" arg="#{text['user.address.country']}"/>
                </h:selectOneMenu>
                <t:message for="country" styleClass="fieldError"/>
                <t:htmlTag value="p"><h:outputLabel for="country" value="#{text['user.address.country']}"/></t:htmlTag>
            </t:htmlTag>
        </t:htmlTag>
    </h:panelGroup>
    <h:outputText/><h:outputText/>

    <h:panelGroup styleClass="buttonBar bottom">
        <h:commandButton value="#{text['button.register']}" action="#{signupForm.save}" id="save" styleClass="button"/>
        <h:commandButton value="#{text['button.cancel']}" action="cancel" immediate="true" id="cancel" styleClass="button" onclick="bCancel=true"/>
    </h:panelGroup>
    <h:outputText/><h:outputText/>
</h:panelGrid>
</h:form>

<v:validatorScript functionName="validateSignupForm"/>

<script type="text/javascript">
    Form.focusFirstElement($('signupForm'));
</script>

</f:view>
</html>