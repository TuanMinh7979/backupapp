var stompClient = null;
var socket = new SockJS('/hello');

//socket accept trip
if($("#socketAccept").val()=="true"){

	stompClient = Stomp.over(socket);
	console.log($("#phone_customer_socket_accept").val());
	console.log(stompClient)
	setTimeout(function(){
			stompClient.send("/app/hello/accept/"+$("#phone_customer_socket_accept").val(), {}, $("#socketAccept").val());
			// stompClient.disconnect();
		}	,5000
	);

};

//socket_complete trip
if($("#socket").val()=="true"){
	stompClient = Stomp.over(socket);
	console.log($("#phone_customer_socket").val());
	console.log(stompClient)
	setTimeout(function(){
			stompClient.send("/app/hello/"+$("#phone_customer_socket").val(), {}, $("#socket").val());
			stompClient.disconnect();
		},1000
	);

};



//send chat to customer
$(function() {
	//stompClient = Stomp.over(socket);
	$("#form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#send").click(function() {
		if ($("#socketChatting").val().trim()){
			stompClient.send('/app/hello/driverchat/'+ $("#phone_customer_socket_chatting").val() + '/trip/' +$("#trip_id").val(), {}, $("#socketChatting").val());
		}
		//stompClient.disconnect();
	});

});


let msg = ""
if (sessionStorage.getItem("message2")){
	msg = sessionStorage.getItem("message2");
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
	sessionStorage.removeItem("message2")
}

$(document).ready(function() {
	connect();
});
function connect() {
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function() {
		console.log('Web Socket is connected');
		// recive chat from customer
		stompClient.subscribe('/topic/hello/reciveCustomerChat/'+$("#phone_driver_socket_chatting").val(), function(message) {
			//alert(message)
			$("#message").append("<div class='d-flex justify-content-start receiver'><span>"+message.body+"</span></div>");
			messEffect()

		});
		// recive chat from mine
		stompClient.subscribe('/topic/hello/reciveDriverChat/'+$("#phone_customer_socket_chatting").val(), function(message) {
			//alert(message.body)
			$("#message").append("<div class='d-flex justify-content-end sender'><span>"+message.body+"</span></div>");
			messEffect()
		});

		//cancel subscribe

		stompClient.subscribe("/topic/hello/cancel/"+$("#phone_driver_socket_cancel").val(),function(message){
			sessionStorage.setItem("message2", message.body);
			location.reload();
			//stompClient.disconnect();
		});
		// $("#message").text(message.body)


		//complete
		stompClient.subscribe('/topic/hello/'+$("#phone_customer_socket_chatting").val(), function(message) {
			sessionStorage.setItem("message1", message.body);
			location.reload();
			//stompClient.disconnect();
		});
	})};