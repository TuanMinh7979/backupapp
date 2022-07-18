var form = document.getElementById("mainform");
// let longitude = document.getElementById("longitude").value;
// let latitude = document.getElementById("latitude").value;


document.getElementById("searchbtn").addEventListener("click", function (event) {


    event.preventDefault();
    let inp1Val = document.getElementById("inp1").value;
    let inp2Val = document.getElementById("inp2").value;

    document.getElementById("inp1").value = inp1Val.trim()
    document.getElementById("inp2").value = inp2Val.trim()

    form.submit();
});


// let curUrl = window.location.href;
// if (curUrl.indexOf("?") == -1) {
let mymap = L.map("map").setView([10.0457102, 105.7859481], 17);
//     L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
//         maxZoom: 19,
//         attribution: "© OpenStreetMap",
//     }).addTo(mymap);
// } else {
//     let idx = curUrl.indexOf("?")
//     let querystr = curUrl.substring(idx);
//     curUrl = curUrl.slice(0, idx)
//     console.log(curUrl)
//     curUrl += "/api/search/"
//
//     curUrl += querystr;
//     curUrl = decodeURI(curUrl);


// type: "get",
// url: curUrl,
// success: function (data) {
//     if (data.length == 0) {
//         alert("Khong co loi di");
//         window.location.href = "/map";
//     } else {
let latlngs = [];

L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    maxZoom: 19,
    attribution: "© OpenStreetMap",
}).addTo(mymap);


const newdata = [
    {lat: 10.0448715, lon: 105.7821788},
    {lat: 10.0455514, lon: 105.7829652},
    // {lat: 10.0455514, lon: 105.7829652},
    {lat: 10.0460535,  lon: 105.7836332}
    // {lat: 10.0465446, lon: 105.7843450}
]
//

newdata.map(function (pi) {

    latlngs.push([parseFloat(pi.lat), parseFloat(pi.lon)])

})

///
// let params = new URL(document.location).searchParams;
//
// let keys = {}
// let listPar = Array.from(params.keys());
// listPar.map((function (pari, index) {
//     keys[pari] = params.get(pari);
// }))
//
// document.getElementById("inp1").value = keys['source']
// document.getElementById("inp2").value = keys['destination']
//
// ///
//
//
// ///
// let srcPoint = data[0];
// let desPoint = data[data.length - 1];


var blueIcon = new L.Icon({
    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-blue.png',
    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41]
});
let polyline = new L.Polyline(latlngs, {
    color: "blue",
    weight: 2,
}).addTo(mymap);

L.marker([10.0448715, 105.7821788], {icon: blueIcon}).addTo(mymap);
L.marker([10.0455514, 105.7829652], {icon: blueIcon}).addTo(mymap);
// L.marker([10.0458208, 105.7833178], {icon: blueIcon}).addTo(mymap);
L.marker([10.0460535, 105.7836332], {icon: blueIcon}).addTo(mymap);
// L.marker([10.0465446, 105.7843450], {icon: blueIcon}).addTo(mymap);

///





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
