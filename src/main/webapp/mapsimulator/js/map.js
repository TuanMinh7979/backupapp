var form = document.getElementById("mainform");
let longitude = document.getElementById("longitude").value;
let latitude = document.getElementById("latitude").value;


document.getElementById("searchbtn").addEventListener("click", function (event) {


    event.preventDefault();
    let inp1Val = document.getElementById("inp1").value;
    let inp2Val = document.getElementById("inp2").value;

    document.getElementById("inp1").value = inp1Val.trim()
    document.getElementById("inp2").value = inp2Val.trim()

    form.submit();
});


let curUrl = window.location.href;
if (curUrl.indexOf("?") == -1) {
    let mymap = L.map("map").setView([latitude, longitude], 17);
    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        maxZoom: 20,
        attribution: "© OpenStreetMap",
    }).addTo(mymap);
} else {
    let idx = curUrl.indexOf("?")
    let querystr = curUrl.substring(idx);
    curUrl = curUrl.slice(0, idx)
    console.log(curUrl)
    curUrl += "/api/search/"

    curUrl += querystr;
    curUrl = decodeURI(curUrl);

    $.ajax({
        type: "get",
        url: curUrl,
        success: function (data) {
            if (data.length == 0) {
                alert("Khong co loi di");
                window.location.href = "/map";
            } else {
                let latlngs = [];
                let mynewmap = L.map("map").setView([latitude, longitude], 17);

                L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
                    maxZoom: 20,
                    attribution: "© OpenStreetMap",
                }).addTo(mynewmap);


                data.map(function (pi) {
                    latlngs.push([parseFloat(pi.lat), parseFloat(pi.lon)])

                })

                ///
                let params = new URL(document.location).searchParams;

                let keys = {}
                let listPar = Array.from(params.keys());
                listPar.map((function (pari, index) {
                    keys[pari] = params.get(pari);
                }))

                document.getElementById("inp1").value = keys['source']
                document.getElementById("inp2").value = keys['destination']

                ///


                ///
                let srcPoint = data[0];
                let desPoint = data[data.length - 1];

                var blueIcon = new L.Icon({
                    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-blue.png',
                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                    iconSize: [25, 41],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [41, 41]
                });

                L.marker([srcPoint.lat, srcPoint.lon], {icon: blueIcon}).addTo(mynewmap);

                var greenIcon = new L.Icon({
                    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                    iconSize: [25, 41],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [41, 41]
                });

                L.marker([desPoint.lat, desPoint.lon], {icon: greenIcon}).addTo(mynewmap);
                ///


                let polyline = new L.Polyline(latlngs, {
                    color: "blue",
                    weight: 6,
                }).addTo(mynewmap);


            }

        },
        error: function () {
            alert("Error occur")
        }
    });

}

$("#inp1").autocomplete({
    source: "/map/ajax/place-search-data",
});
$("#inp2").autocomplete({
    source: "/map/ajax/place-search-data",
});


// var myGeoJson = {
//
// };
//
// L.geoJSON(myGeoJson).addTo(map);