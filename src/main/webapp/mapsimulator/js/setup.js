$(".map").on("click", function () {
    $(".map").removeClass("active");
    $(this).toggleClass("active");
})


$("#useMapBtn").on("click", function () {
    let mapname = $(".active").text().trim();
    console.log(mapname)
    let url = `/map/setup/setup-csvdata/${mapname}`;
    console.log(url);
    $.ajax({
        type: "get",
        url: url,
        success: function (data) {
            location.reload();
        },
        error: function (err) {
            console.log(err)
            alert("Something wrong")
        }


    })
})
$("#delMapBtn").on("click", function () {
    let mapname = $(".active").text().trim();
    let currentActiveMapName = $("#active-map-name-span").text().trim()
    console.log(mapname, currentActiveMapName);
    if (mapname === currentActiveMapName) {
        alert("Can not delete active map!")
        return false;
    }
    let url = `/map/setup/delete-csvdata/${mapname}`;

    Swal.fire({

        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.value) {
            $.ajax({
                type: "get",
                url: url,
                success: function (data) {
                    location.reload();
                },
                error: function (err) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Some thing wrong',
                        // text: 'Something went wrong!',

                    })
                }


            })
        }
    })
})