
// <%--// let formElement = document.getElementById("addform");--%>
//     <%--// let addBtn = document.getElementById("addbtn");--%>
//     <%--// addBtn.onclick = () => {--%>
//     <%--// let formData = new FormData(formElement);--%>
//     <%--// const value = Object.fromEntries(formData.entries());--%>
//     <%--// console.log( value );-- %>
var getFormData = (formId) => {
    let formIdSelector = "#" + formId;
    var formData = $(formIdSelector).serializeArray();
    let data={}
    $.each(formData, function (i, v) {
        data["" + v.name + ""] = v.value;
    });
    return data;
}