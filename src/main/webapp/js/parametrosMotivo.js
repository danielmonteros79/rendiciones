jQuery(document).ready(function() {
	$('#checkParametros').show();
});

function resetForm() {
	$(".message").html("");
	$("#codigo").val("");
}

function agregarMotivo() {
	$("#addMotivo").submit();
}

function modificarMotivo(codMotivo) {
	$("#edit_" + codMotivo).submit();
}

function eliminarMotivo(codMotivo) {
	$("#delete_" + codMotivo).submit();
}