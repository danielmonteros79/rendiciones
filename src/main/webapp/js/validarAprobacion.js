$(document).ready(function () {

    $.validator.addMethod("valueNotEquals", function (value, element, arg) {
        return arg != value;
    }, "");

    $("#formAprobacion").validate({
        rules: {

            id: {
                valueNotEquals: ""
            }

        },
        messages: {

            id: " *"

        }
    });
});
$.validator.setDefaults({

    submitHandler: function () {
        alert("Rendicion aprobada.");
        window.href.location = "aprobacion.do"

    }
});