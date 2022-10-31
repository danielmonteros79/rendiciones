jQuery(document).ready(function () {

    $('#checkRendiciones').show();
    var estado = $('#estadoRendicion').val();
//	$("#estadoView").show();
    if (estado.trim() == "PENDI") {
        $('#circulo1').css("background-color", "yellow");
        $('#estadoView').css("left", "250px");

    } else if (estado.trim() == "ESCAN") {
        $('#circulo1').css("background-color", "lightblue");
        $('#estadoView').css("left", "250px");
    } else if (estado.trim() == "PSUP") {
        $('#circulo1').css("background-color", "#5cb85c");
        $('#circulo2').css("background-color", "lightblue");
        $('#estadoView').css("left", "326px");
    } else if (estado.trim() == "PFIRM") {
        $('#circulo1').css("background-color", "#5cb85c");
        $('#circulo2').css("background-color", "#5cb85c");
        $('#circulo3').css("background-color", "lightblue");
        $('#estadoView').css("left", "395px");
    } else if (estado.trim() == "PGLG") {
        $('#circulo1').css("background-color", "#5cb85c");
        $('#circulo2').css("background-color", "#5cb85c");
        $('#circulo3').css("background-color", "#5cb85c");
        $('#circulo4').css("background-color", "lightblue");
        $('#estadoView').css("left", "470px");
    } else if (estado.trim() == "OBSER") {
        $('#circulo1').css("background-color", "#5cb85c");
        $('#circulo2').css("background-color", "#5cb85c");
        $('#circulo3').css("background-color", "#5cb85c");
        $('#circulo4').css("background-color", "yellow");
        $('#estadoView').css("left", "470px");
    } else if (estado.trim() == "APROB") {
        $('#circulo1').css("background-color", "#5cb85c");
        $('#circulo2').css("background-color", "#5cb85c");
        $('#circulo3').css("background-color", "#5cb85c");
        $('#circulo4').css("background-color", "#5cb85c");
        $('#circulo5').css("background-color", "lightblue");
        $('#estadoView').css("left", "540px");
    } else if (estado.trim() == "ORDPG") {
        $('#circulo1').css("background-color", "#5cb85c");
        $('#circulo2').css("background-color", "#5cb85c");
        $('#circulo3').css("background-color", "#5cb85c");
        $('#circulo4').css("background-color", "#5cb85c");
        $('#circulo5').css("background-color", "#5cb85c");
        $('#estadoView').css("left", "540px");
    } else if (estado.trim() == "SUSPE") {
        $('#circulo1').css("background-color", "#5cb85c");
        $('#circulo2').css("background-color", "#5cb85c");
        $('#circulo3').css("background-color", "#5cb85c");
        $('#circulo4').css("background-color", "#5cb85c");
        $('#circulo5').css("background-color", "yellow");
        $('#estadoView').css("left", "540px");
    } else {
        $('#circulo1').css("background-color", "red");
        $('#circulo2').css("background-color", "red");
        $('#circulo3').css("background-color", "red");
        $('#circulo4').css("background-color", "red");
        $('#circulo5').css("background-color", "red");
        $('#estadoView').css("left", "540px");
    }

    $("#circulo1").hover(function () {
        $('#estadoCronolog').css("left", "250px");
        if (estado.trim() == "ESCAN") {
            $("#estadoCronolog").text("PENDI");
        } else {
            $("#estadoCronolog").text("INGRESO");
        }
        $("#estadoCronolog").show();
    }, function () {
        $("#estadoCronolog").hide();
    });
    $("#circulo2").hover(function () {
        $('#estadoCronolog').css("left", "326px");
        $("#estadoCronolog").text("SUPERVISION");
        $("#estadoCronolog").show();
    }, function () {
        $("#estadoCronolog").hide();
    });
    $("#circulo3").hover(function () {
        $('#estadoCronolog').css("left", "395px");
        $("#estadoCronolog").text("FIRMANTE");
        $("#estadoCronolog").show();
    }, function () {
        $("#estadoCronolog").hide();
    });
    $("#circulo4").hover(function () {
        $('#estadoCronolog').css("left", "470px");
        $("#estadoCronolog").text("CTRL GLG");
        $("#estadoCronolog").show();
    }, function () {
        $("#estadoCronologestadoCronolog").hide();
    });
    $("#circulo5").hover(function () {
        $('#estadoCronolog').css("left", "540px");
        if (estado.trim() == "APROB") {
            $("#estadoCronolog").text("CUENTAS A PAGAR");
        } else if (estado.trim() == "RECHA") {
            $("#estadoCronolog").text("Cuentas a Pagar");
        } else if (estado.trim() == "ORDPG") {
            $("#estadoCronolog").text("Cuentas a Pagar");
        } else if (estado.trim() == "SUSPE") {

            $("#estadoCronolog").text("Cuentas a Pagar");
        } else {
            $("#estadoCronolog").text("Cuentas a Pagar");
        }
        $("#estadoCronolog").show();
    }, function () {
        $("#estadoCronolog").hide();
    });
});