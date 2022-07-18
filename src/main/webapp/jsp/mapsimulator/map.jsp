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


        <div class="inputsection col-12">
            <form id="mainform" class="col-8 float-left" method="get">
                <label>FROM:</label> <input id="inp1" name="source" type="text" value="" required>
                <label>TO:</label> <input id="inp2" name="destination" type="text" value="" required>
                <button id="searchbtn" class="btn btn-success">FIND</button>
            </form>
            <div class="col-4 float-right">
                <a href="/" class="btn btn-info">BACK</a>
            </div>
        </div>
        <br/>
        <br/>

        <div id="map" class="col-12 ">

        </div>
        <input type="hidden" id="longitude" name="longitude" value=${longitude} />
        <input type="hidden" id="latitude" name="latitude" value=${latitude} />

        <script src="https://code.jquery.com/jquery-3.6.0.js"
                integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
                crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"
                integrity="sha256-T0Vest3yCU7pafRw9r+settMBX6JkKN06dqBnpQ8d30="
                crossorigin="anonymous"></script>


        <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
        <script src="${pageContext.request.contextPath}/mapsimulator/js/map.js"></script>


    </jsp:body>
</t:layout>