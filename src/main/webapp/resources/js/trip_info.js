let a = document.getElementById("tripInfoBtn");
if (a != undefined) {
    a.addEventListener("click", function () {
        let sourceStr = document.getElementById("sourceTh").innerText;
        let destinationStr = document.getElementById("destinationTh").innerText;
        var curUrl = window.location.href;
        curUrl = curUrl.trim();
        curUrl = curUrl.replace("customer", "");
        curUrl += "map?source=";
        curUrl += sourceStr;
        curUrl += "&destination=";
        curUrl += destinationStr;
        curUrl = curUrl.replaceAll(" ", "+")
        window.location.href = curUrl;
    })
}

let b = document.getElementById("bookTripBtn");
if (b){
    b.addEventListener("click", function () {
        const reg = /\/customer.*/gi
        var curUrl = window.location.href;
        curUrl = curUrl.replace(reg, "")
        curUrl += "/map/customer";
        window.location.href = curUrl;
    })
}