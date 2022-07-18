<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:layout>
 <jsp:attribute name="header">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
              integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
              crossorigin="anonymous">

        <style>
            body {
                background-color: darkslategray;
            }

            #form-section {
                padding: 10px;
                margin: 0 auto;
                border: 5px solid lightskyblue;
                height: 400px;
                width: 500px;
                border-radius: 6px;
            }

            #maps-section {
                padding: 10px;
                margin: 0 auto;
                border: 5px solid lightskyblue;
                height: 400px;
                width: 500px;
                background-color: snow;
                border-radius: 6px;


            }

            #maps-section #content, #command {
                display: flex;
                justify-content: space-around;
            }

            #command {
                margin-top: 100px;
            }




            .map {
                border: 2px solid darkcyan;
                font-size: 1.2rem;
                padding: 0px;
                height: 60px;
                max-width: 140px;
                text-align: center;
                align-items: center;
                border-radius: 20px;
                color: black;
                font-weight: bold;
                margin-top: 20px;

                display: flex;
                flex-direction: column;
                flex-wrap: wrap;
                justify-content: center;
                align-items: center;
            }

            .map:hover {
                background-color: cyan;
            }

            .active {
                border: 4px solid blue;
                color: orange;
            }


        </style>
</jsp:attribute>

    <jsp:body>
        <div id="form-section">
            <h3 style="color:white;" class="text-center">INPUT FOR MAP SIMULATOR</h3>
            <div style="background-color: snow; width: 100%;height:300px;padding:6px; ">
                <div class="col-12">
                    <form action="" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label><b>Osm file</b></label>
                            <input name="file" class="form-control" type="file">
                        </div>
                        <button class="btn btn-success" type="submit">Submit</button>
                    </form>

                    <span>Active Map: </span><span id="active-map-name-span">${activeMapName}</span>
                </div>


            </div>

        </div>
        <div id="maps-section">
            <div id="content" class="row ">
                <c:forEach items="${allMap}" var="mapi">
                    <div class=" col-4">
                        <div class="map"> ${mapi}</div>

                    </div>
                </c:forEach>
            </div>
            <div id="command">
                <button id="useMapBtn">Use this map</button>
                <button id="delMapBtn">Delete</button>
            </div>

        </div>

        <script src="https://code.jquery.com/jquery-3.6.0.js"
                integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
                crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"
                integrity="sha256-T0Vest3yCU7pafRw9r+settMBX6JkKN06dqBnpQ8d30="
                crossorigin="anonymous"></script>

        <script src="${pageContext.request.contextPath}/mapsimulator/js/setup.js"></script>
    </jsp:body>
</t:layout>