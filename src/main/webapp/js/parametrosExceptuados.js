jQuery(document).ready(function () {
    $('#checkParametros').show();
    var mu = $("input[name=motivoUsuario]:checked").val();
    if (mu == null)
        $("#exceptuadoFiltro").attr('readonly', true);
});

function resetForm() {
    $(".message").html("");
    $("#motivoUsuario").val("");
    $("input[name=motivoUsuario]").prop('checked', false);
    $("#exceptuadoFiltro").val("");
    $("#exceptuadoFiltro").attr('readonly', true);
}

function habilitar() {
    $("#exceptuadoFiltro").attr('readonly', false);
}

function agregarExceptuado() {
    $("#addExceptuado").submit();
}

function modificarExcepcion(motivoUsuario) {
    $("#edit_" + motivoUsuario).submit();
}

function confirmEliminarExcepcion(motivoUsuario) {
    $("#delete_" + motivoUsuario).submit();
}