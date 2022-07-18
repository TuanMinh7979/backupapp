var stompClient = null;

let msg = ""
if (sessionStorage.getItem("message1")){
    msg = sessionStorage.getItem("message1");
}
console.log(msg)
if(msg){
    console.log(document.getElementById("anoument"))
    const toastLiveExample = document.querySelector("#anoument")
    const toast = new bootstrap.Toast(toastLiveExample)
    const toastBody = document.querySelector("#anoument div.toast-body")
    console.log(toastLiveExample)
    toastBody.innerHTML = msg
    toast.show()
    sessionStorage.removeItem("message1")
}
$(document).ready(function() {
    connect();
});


function connect() {
    var socket = new SockJS('/hello');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function() {
        console.log('Web Socket is connected');
        //complete
        stompClient.subscribe('/topic/hello/'+$("#phone_customer_socket_chatting").val(), function(message) {
            sessionStorage.setItem("message1", message.body);
            location.reload();
            //stompClient.disconnect();
        });
        // recive chat of mine

        stompClient.subscribe('/topic/hello/reciveCustomerChat/'+$("#phone_driver_socket_chatting").val(), function(message) {
            // alert(message.body)
            $("#message").append("<div class='d-flex justify-content-end sender'><span>"+message.body+"</span></div>");
            messEffect()
            //stompClient.disconnect();
        });


        // recive chat of driver
        stompClient.subscribe('/topic/hello/reciveDriverChat/'+$("#phone_customer_socket_chatting").val(), function(message) {
            //alert(message.body)
            $("#message").append("<div class='d-flex justify-content-start receiver'><span>"+message.body+"</span></div>");
            messEffect()
            //stompClient.disconnect();
        });


        // accept
        stompClient.subscribe("/topic/hello/accept/"+$("#phone_customer_socket_chatting").val(), function(message) {
            sessionStorage.setItem("message1", message.body);
            location.reload();
            //stompClient.disconnect();
        });

        //cancel subscribe
        stompClient.subscribe("/topic/hello/cancel/"+$("#phone_driver_socket_chatting").val(),function(message){
            //alert(message.body)
            $("#message").text(message.body)
        });
    })
};

//Cancel trip accepted by driver

if($("#socket_cancel").val()=="true"){
    var socket = new SockJS('/hello');
    stompClient = Stomp.over(socket);
    console.log($("#phone_customer_socket").val());
    console.log(stompClient)
    setTimeout(function(){
            stompClient.send("/app/hello/cancel/"+$("#phone_driver_socket_cancel").val(), {}, $("#socket_cancel").val());
            //stompClient.disconnect();
        },1000
    );
};


//send to driver
$(function() {
    $("#form_user").on('submit', function(e) {
        e.preventDefault();
    });
    $("#send").click(function() {
        if ($("#socketChatting").val().trim()){
            stompClient.send('/app/hello/customerchat/'+ $("#phone_driver_socket_chatting").val() + '/trip/' +$("#trip_id").val(), {}, $("#socketChatting").val());
        }
        // stompClient.disconnect();
    });

});