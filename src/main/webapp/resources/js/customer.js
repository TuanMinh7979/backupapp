//VIEW RATE
function changeStarView(star) {
	let i
	const starField = document.querySelector("div.writed-review_stars")
	noneStar(starField.children[4])
	for (i = 0; i < star; i++) {
		const child = starField.children[i]
		const starPath = child.children[0].children[0]
		starPath.setAttribute('fill', "#FDD835")
	}
}

function changeValueViewModal(star, content) {
	const textArea = document.getElementById("content-textarea")
	textArea.innerHTML = content
	changeStarView(star)
}
//Handle change view rate (MAIN)
const viewRateDivNodes = document.querySelectorAll(".js-div-view-modal")
viewRateDivNodes.forEach((node) => {
	let star
	let content
	star = node.children[1].innerHTML
	content = node.children[2].innerHTML
	node.children[0].addEventListener('click', (ev) => changeValueViewModal(star, content))
	//console.log(content)
})

//RATE
const rateDivNodes = document.querySelectorAll("button.js-btn-rate-modal")
rateDivNodes.forEach((node) => {
})

//STAR EFFECT
const rateStar = document.querySelector("div.write-review_stars")
//Function of star Effect
function fillStar(child) {
	let index = 0
	while (child.previousSibling) {
		index = index + 1
		child.children[0].children[0].setAttribute('fill', "#FDD835")
		child = child.previousSibling
	}
	setStarForInputForm(index)
}

function noneStar(child) {
	while (child.previousSibling) {
		child.children[0].children[0].setAttribute('fill', "none")
		child = child.previousSibling
	}
	setStarForInputForm(null)
}

function nodeStarNextNode(child) {
	while (child.nextSibling) {
		child = child.nextSibling
		if (child.nodeName !== "#text") {
			child.children[0].children[0].setAttribute('fill', "none")
		}
	}
}

//Renew children of node
function renewNode(node, length) {
	for (let i = 0; i < length; i++) {
		const child = node.children[i]
		child.replaceWith(child.cloneNode(true))
	}
}

function setStarForInputForm(index) {
	const spanStarModal = document.querySelector("#rate-form input[name='star']")
	spanStarModal.setAttribute('value', index)
}

function submitRateHandle(){
	const buttonSub = document.querySelector("#rate-form button[type='submit']")
	const star = document.querySelector("#rate-form input[name='star']")
	buttonSub.addEventListener('click',(ev)=>{
		if (star.value==="null"){
			const toast = new bootstrap.Toast(toastLiveExample)
			document.querySelector("#anoument div.toast-body").innerHTML="Please choose number star before rate"
			toast.show()
			ev.preventDefault()
		}
	})
}

function clickStarHandle(child) {
	if (child.nodeName !== "svg") {
		child = child.parentNode.parentNode
	} else {
		if (child.nodeName !== "span") {
			child = child.parentNode
		}
	}
	const parent = child.parentNode
	let index = 0
	while (child.previousSibling) {
		child = child.previousSibling
		if (child.nodeName === "#text") break;
		index = index + 1
	}
	//Replace new node for remove eventListener
	renewNode(parent, 5)
	//Add new eventListener
	for (let i = 0; i < 5; i++) {
		const node = parent.children[i]
		node.addEventListener('click', () => {
			fillStar(node)
			nodeStarNextNode(node)
		})
	}
}

//Star effect (main function)
function starEffect() {
	for (let i = 0; i < 5; i++) {
		const child = rateStar.children[i]
		//Change cursor to hand icon
		child.setAttribute('style', 'cursor:pointer')
		//hover
		child.addEventListener('mouseenter', (ev) => fillStar(ev.target))

		child.addEventListener('mouseleave', (ev) => noneStar(ev.target))

		child.addEventListener('click', (ev) => clickStarHandle(ev.target))
	}
}

//Rate button event (when click, the input[name='trip_id'] of rate form will change id)
submitRateHandle()
buttonRateEvent()
function buttonRateEvent() {
	const divsRate = document.querySelectorAll(".js-div-rate")
	const tripIdContainerChangeable = document.querySelector("#rate-form input[name='tripId']")
	divsRate.forEach((divRate) => {
		divRate.addEventListener('click', () => {
			tripIdContainerChangeable.setAttribute("value", divRate.children[1].innerHTML)
			renewNode(rateStar)
			noneStar(rateStar.children[4])
			starEffect()
			//Renew star
			setStarForInputForm(null)
			//Renew content
			const textArea = document.querySelector("#rate-form textarea[name='content']")
			textArea.value = ""
		})
	})
	//if (node) node.addEventListener('click',(ev)=>changeValueViewModal(star,content))
}

//Handle action when the driver don't pick the customer
function handleWaitTooLong() {
	const timeBook = document.getElementById("time-live-trip")
	if (timeBook) {
		const dateBook = new Date(timeBook.innerHTML)
		const distance = document.getElementById("distance-live-trip").innerHTML
		//Time enough for driver travel to customer (40km/h) => h
		const timeNeedToTravel = 30000 //1 phut

		const currentTime = new Date()
		const timeWait = (dateBook.getTime() + timeNeedToTravel) - currentTime.getTime()
		if (timeWait <= 0) {
			changeTripInLiveCanCancel()
		} else {
			setTimeout(()=>changeTripInLiveCanCancel("You can cancel this trip"), timeWait)
		}

	}
}

function changeTripInLiveCanCancel(anoument) {
	const toastLiveExample = document.querySelector('#anoument')
	const buttonCancel = document.createElement('button')
	buttonCancel.type="button"
	buttonCancel.classList="btn btn-danger js-show-annoument js-m-mess"
	buttonCancel.setAttribute("data-bs-toggle","modal")
	buttonCancel.setAttribute("data-bs-target","#confirmDeleteTripModal")
	buttonCancel.innerHTML = "Cancel Trip"
	if (anoument){
		const toast = new bootstrap.Toast(toastLiveExample)
		document.querySelector('#anoument div.toast-body').innerHTML = anoument
		toast.show()
	}

	const btnGroup = document.getElementById('btn-group-trip-in-live')
	btnGroup.appendChild(buttonCancel)
}
handleWaitTooLong()