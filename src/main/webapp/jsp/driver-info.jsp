<%@include file="/common/taglib.jsp" %>
<t:layout>
    <jsp:attribute name="header">
           <link rel="stylesheet" href="<c:url value='/resources/css/driver.css'/>"/>
    </jsp:attribute>
    <jsp:body>
        <%@include file="/jsp/driver-info.html" %>

        <script type="text/javascript">
            let a = document.getElementById("useMapBtn");
            let sourceStr = document.getElementById("sourceTh").innerText;
            let destinationStr = document.getElementById("destinationTh").innerText;

            a.addEventListener("click", function () {
                var curUrl = window.location.href;
                curUrl = curUrl.trim();
                curUrl = curUrl.replace("driver", "");
                curUrl += "map?source=";
                curUrl += sourceStr;
                curUrl += "&destination=";
                curUrl += destinationStr;
                // console.log(encodeURI(curUrl));
                console.log(curUrl)

                curUrl = curUrl.replaceAll(" ", "+")

                window.location.href = curUrl;
            })
        </script>
        <script src="/webjars/jquery/dist/jquery.min.js"></script>
        <script src="/webjars/sockjs-client/sockjs.min.js"></script>
        <script src="/webjars/stomp-websocket/stomp.min.js"></script>
        <script src="<c:url value='/resources/js/myjs.js' />"></script>

    </jsp:body>
</t:layout>