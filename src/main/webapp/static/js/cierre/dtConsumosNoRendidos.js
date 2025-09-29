let dtLink = 'rendicionDetalleGastos.do';

$(document).ready(function() 
{
	modalGastoSetCombos()

	$('.selectMotivosCP').trigger("chosen:updated");

	
	
})



$('.selectMotivosCP').change(function() {
    let $selectActual = $(this).closest('tr').find('.selectMotivosCP');
    let valorMotivoActual = $(this).val();
    
    let selectActualGasto = $(this).closest('tr').find('.selectGastosCP');

    setCombo('combos.do?action=getTiposGasto', selectActualGasto[0], { codMotivo: valorMotivoActual });

    // Activa el evento chosen:updated solo para el selector de gastos actual
    
	setTimeout(() => {
		$('.selectGastosCP').trigger("chosen:updated");
	}, 600);

    
});


function modalGastoSetCombos() {
	setCombo('combos.do?action=getMotivos', '.selectMotivosCP', { opcion: 4, glg: ""}, $('#codMotivo').val());


}