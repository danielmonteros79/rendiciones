var modalJournalLoadParams;

function modalJournalShow(idRendicion) {
	modalJournalLoadParams = {
		idRendicion: idRendicion
	};
	
	modalJournalLoad();
}

function modalJournalLoad() {
	callAjax('journal.do', modalJournalLoadParams, 'modalJournalLoadSuccess');
}

function modalJournalLoadSuccess(data) {
	showMessage('modalJournalMessage', data.message);
	$('#modalJournalIdRendicion').html(modalJournalLoadParams.idRendicion);
	modalJournalSetTabla(data);

	$('#modalJournal').modal('show');
}

function modalJournalSetTabla(data) {
	$('#modalJournalTabla tbody tr').remove();

	$('#modalJournalTabla').toggleClass('d-none', !data.filas);
	$('#modalJournalTablaVacia').toggleClass('d-none', data.filas.length > 0);
	
	$(data.filas).each(function(i, fila) {
		var cols = '<td class="text-right">' + fila.numeroAprob + '</td>';
		cols += '<td>' + fila.estado + '</td>';
		cols += '<td>' + fila.nombreUsuarioProx + '</td>';
		cols += '<td>' + fila.nombreUsuarioAprob + '</td>';
		cols += '<td class="text-center">' + formatDate(fila.fechaApr) + '</td>';
		$('#modalJournalTabla tbody').append('<tr>' + cols + '</tr>');
	});
}