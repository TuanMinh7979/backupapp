<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout>
  <jsp:attribute name="header">
               <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css"
              integrity="sha512-aOG0c6nPNzGk+5zjwyJaoRUgCdOrfSDhmMID2u4+OIslr0GjpLKo7Xm0Ao3xmpM4T8AmIouRkqwj1nrdVsLKEQ=="
              crossorigin="anonymous" referrerpolicy="no-referrer"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
              integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
              crossorigin="anonymous">
        <style>
            body {
                padding: 16px;
                margin: 0;
            }

            #map {
                z-index: 1;
                width: 100%;
                height: 800px;
            }

            .inputsection {
                z-index: 2;
            }


        </style>
</jsp:attribute>


    <jsp:body>


        <div class="inputsection  row">

            <div class="col-7">
                <form id="mainform" method="get">
                    <label>FROM:</label> <input id="inp1" name="source" type="text" value="" required>
                    <label>TO:</label> <input id="inp2" name="destination" type="text" value="" required>
                    <button id="searchbtn" class="btn btn-success">FIND</button>
                </form>
            </div>
            <div class="col-5" style="display: flex; justify-content: space-between">
                <span>DISTANCE:</span> <span id="distanceSpan"></span>
                <span>PRICE: </span><span id="priceSpan"></span>
                <button class="btn btn-info" id="createTripBtn">CREATE A TRIP</button>
            </div>


        </div>
        <br/>
        <br/>

        <div id="map" class="col-12 ">

        </div>
        <input type="hidden" id="longitude" name="longitude" value=${longitude} />
        <input type="hidden" id="latitude" name="latitude" value=${latitude} />


        <script src="https://code.jquery.com/jquery-3.6.0.min.js"
                integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
                crossorigin="anonymous"></script>
        <script
                src="https://code.jquery.com/ui/1.13.1/jquery-ui.min.js"
                integrity="sha256-eTyxS0rkjpLEo16uXTS0uVCS4815lc40K2iVpWDvdSY="
                crossorigin="anonymous"></script>

        <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
        <script src="${pageContext.request.contextPath}/../resources/js/global.js"></script>
        <script src="${pageContext.request.contextPath}/mapsimulator/js/customer-map.js"></script>


    </jsp:body>
</t:layout>