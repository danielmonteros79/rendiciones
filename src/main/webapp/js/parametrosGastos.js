jQuery(document).ready(function() {
	$('#checkParametros').show();
});

function resetForm() {
	$(".message").html("");
	$("#gasto").val("");
}

function agregarGasto() {
	$("#addGasto").submit();
}

function modificarGasto(codigo) {
	$("#edit_" + codigo).submit();
}

function eliminarGasto(codigo) {
	$("#delete_" + codigo).submit();
}