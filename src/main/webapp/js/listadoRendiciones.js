$(document).ready(function () {
    $("#imageCal1").click(function () {
        $('#fechaDesde').datepicker("show");
    });
    $("#imageCal2").click(function () {
        $("#fechaHasta").datepicker("show");
    });
    $("#imageCal3").click(function () {
        $('#fecha1').datepicker("show");
    });
    $("#imageCal4").click(function () {
        $("#fecha2").datepicker("show");
    });
    $("#imageCal5").click(function () {
        $("#fechagastos").datepicker("show");
    });

    $('#altaRendicion').balloon().showBalloon({
        position: "left",
        css: cssDefaultBalloon
    });
});