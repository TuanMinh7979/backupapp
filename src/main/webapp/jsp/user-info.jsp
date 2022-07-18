<%@include file="/common/taglib.jsp" %>
<t:layout>
    <jsp:attribute name="header">

        <link rel="stylesheet" href="<c:url value='/resources/css/customer.css'/>"/>
</jsp:attribute>
    <jsp:body>


        <%@include file="/jsp/user-info.html" %>


        <script src="/webjars/jquery/dist/jquery.min.js"></script>
        <script src="/webjars/sockjs-client/sockjs.min.js"></script>
        <script src="/webjars/stomp-websocket/stomp.min.js"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/socket_customer.js' />"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/customer.js' />"></script>

        <script src="<c:url value='/resources/js/trip_info.js' />"></script>
    </jsp:body>
</t:layout>