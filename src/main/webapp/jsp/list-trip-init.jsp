<%@include file="/common/taglib.jsp" %>
<t:layout>
	  <jsp:attribute name="header">
  <style>
      @media ( max-width: 768px) {
          #container div {
              width: 100%;
              margin: 0 auto;
          }

      }

      @media only screen and (max-width: 760px),
      (min-device-width: 768px) and (max-device-width: 1024px) {

          table, thead, tbody, th, td, tr {
              display: block;
          }

          thead tr {
              position: absolute;
              top: -9999px;
              left: -9999px;
          }

          tr {
              border: 2px solid #ccc;
          }

          td {
              position: relative;
              width: 60% !important;
              display: flex;
              justify-content: right;
              margin: 0 auto;
          }

          td:before {

              position: absolute;
              top: 6px;
              left: 6px;
              width: 45%;
              padding-right: 10px;
              white-space: nowrap;
              font-weight: bold;
          }

          /*
          Label the data
          */
          td:nth-of-type(1):before {
              content: "Id";
          }

          td:nth-of-type(2):before {
              content: "Source";
          }

          td:nth-of-type(3):before {
              content: "Destination";
          }

          td:nth-of-type(4):before {
              content: "Price";
          }

          td:nth-of-type(5):before {
              content: "";
          }

      }

  </style>
</jsp:attribute>

    <jsp:body>
        <%@include file="/jsp/list-trip-init.html" %>
        <!-- <script src="/webjars/jquery/dist/jquery.min.js"></script>
        <script src="/webjars/sockjs-client/sockjs.min.js"></script>
        <script src="/webjars/stomp-websocket/stomp.min.js"></script>
        <script src="<c:url value='/resources/js/myjs.js'/>"></script> -->
    </jsp:body>
</t:layout>