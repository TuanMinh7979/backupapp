//Toast annoument show
const toastTrigger = document.querySelectorAll('.js-show-annoument')
const toastLiveExample = document.querySelector('#anoument')

function addMessToToast(){
    sessionStorage.setItem("annoument","have")
}

if (toastTrigger) {
    const toast = new bootstrap.Toast(toastLiveExample)
    if (sessionStorage.getItem("annoument")) {
        console.log(sessionStorage.getItem("annoument"))
        toast.show()
        sessionStorage.removeItem("annoument")
    }
    toastTrigger.forEach((one)=>{
        one.addEventListener('click',addMessToToast)
    })
}
if (document.querySelector("#socketChatting")){
    const messChatLineArea = document.querySelector("#message")  
    document.querySelector("#socketChatting").value=""
    messChatLineArea.scrollTop = messChatLineArea.scrollHeight
}
//Main effect of mess
function messEffect(){
    const chatArea = document.querySelector("#socketChatting")
    const sendBtn = document.querySelector("#send")
    chatArea.value=""
    const messChatLineArea = document.querySelector("#message")  
    sendBtn.addEventListener('click',()=>{
        chatArea.value=""
    })
    messChatLineArea.scrollTop = messChatLineArea.scrollHeight
} 
//Effect of button toggle mess collapse
function effectMessCollapse(){
    const messToggle = document.querySelector("#mess-toggle")
    const messChatLineArea = document.querySelector("#message")  
    messToggle.addEventListener('click',()=>{
        messChatLineArea.scrollTop = messChatLineArea.scrollHeight
    })
}

if (document.querySelector("#mess-toggle")){
    effectMessCollapse()
}

//Effect socket chatting area text when not thing in this
function btnCanSendWhenAreaTextHaveText(){
    const textAreaChattingSocket = document.querySelector("#socketChatting")
    const btn = document.querySelector("#send")
    textAreaChattingSocket.addEventListener('change',(ev)=>{
        if (!ev.target.value.trim()){
            ev.preventDefault()
        }
    })
    btn.addEventListener('click',(ev)=>{
        if (!textAreaChattingSocket.value || /^\s*$/.test(textAreaChattingSocket.value)){
            alert(textAreaChattingSocket.value)
            ev.preventDefault()
        }
    })
}