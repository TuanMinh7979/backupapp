//Sign Up handle

//Input Address

function selectRoleHandle(ev) {
	//Label
	var textNode = document.createTextNode("Address*")
	var labelAddress = document.createElement("label")
	labelAddress.appendChild(textNode)
	labelAddress.className = "form-label text-light"
	labelAddress.id="label-address"
	//Input
	var inputNode = document.createElement("input")
	inputNode.id = "form-address"
	inputNode.name = "address"
	inputNode.className = "form-control"
	inputNode.type="text"
	inputNode.setAttribute('required','')
	//Container
	const container = document.querySelector("#sign-up-box .container")
	//Select on change event
	if (ev.target.value === "DRIVER"){
		const locationAdd = document.querySelector("#label-phone")
		container.style.height = "640px"
		document.querySelector("#form-sign-up").insertBefore(labelAddress, locationAdd)
		document.querySelector("#form-sign-up").insertBefore(inputNode,locationAdd)
	} else {
		// If value = CUSTOMER
		container.style.height = "570px"
		const childLable = document.getElementById("label-address")
		const childInput = document.getElementById("form-address")
		document.querySelector("#form-sign-up").removeChild(childLable)
		document.querySelector("#form-sign-up").removeChild(childInput)
	}
}

//Validate functions
//If input null
function nullValue(name) {
	return `Please type value into ${name}`
}
//Check regex
function regCheckString(reg, value) {
	let i
	for (i = 0; i < value.length; i++) {
		if (reg.exec(value.charAt(i))) {
			return false
		}
	}
	return true
}
//Check full name
function checkFullName(fullName) {
	const reg = /[A-Za-z\s]+/
	fullName = fullName.trim()
	let regS
	if (fullName.length === 0) {
		return nullValue("Full Name")
	} else {
		regS = reg.exec(fullName)[0]
	}
	if (regS != fullName) {
		return "Sorry but full name must contain just word, please type again!"
	}
	return ""
}
//Check phone
function checkPhone(phone) {
	const reg = /[^0-9]/
	phone = phone.trim()
	if (!phone) {
		return nullValue("Phone")
	}

	if (!regCheckString(reg, phone)) {
		return "Sorry but phone can't include char, please type again!"
	}

}
//Check user name
function checkUserName(username) {
	const reg = /[A-Za-z][A-Za-z0-9_]{7,29}$/
	if (!username) {
		return nullValue("User Name")
	}
	if (!reg.test(username)) {
		return "User Name must have 8-30 char, start with word and not have special char"
	}
}
//Check address
function checkAddress(address) {
	if (!address) {
		return nullValue("Address")
	}
}
//Check password
function checkPassword(password){
	//All char type and contain at least 8 char
	const reg = /.{8}$/
	if (!password){
		return nullValue("Password")
	}
	if (!reg.test(password)){
		return "Password must contain at least 8 characters"
	}
}
//Check confirm password
function checkConfirmPassword(confirm, password){
	if (confirm !== password){
		return "Confirm password must equal password"
	}
}

//Validate sign up
function validateSignup(ev) {
	const form = document.querySelector("#form-sign-up")
	const children = form.childNodes
	const password = document.querySelector("#form-sign-up input[name='password']")
	const confirmPassword = document.querySelector("#form-sign-up input[name='confirmPassword']")
	if (password.value!==confirmPassword.value){
		confirmPassword.setAttribute('pattern',password.value)
	}
}
//Listener button when submit
const buttonSubmit = document.querySelector("#js-btn-submit-sign-up")
buttonSubmit.addEventListener("click", (ev) => validateSignup(ev))
//Listener selector when change role
const selectorRole = document.querySelector("#form-sign-up select[name='role']")
selectorRole.addEventListener("change", (ev) => selectRoleHandle(ev))