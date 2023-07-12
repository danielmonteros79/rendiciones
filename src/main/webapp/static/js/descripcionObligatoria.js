$(document).ready(function() {
	jQuery.validator.addMethod("dateDMY", function(value, element) {
		try{jQuery.datepicker.parseDate( 'dd/mm/yy', value);return true;}
        catch(e){return false;}
    }, "La fecha debe tener formato DD/MM/AAAA.");

	$("#DescripcionObligatoriaForm").validate({
		lang: 'es_AR',
		rules : {
			FEC1 : {
				dateDMY : true
			},
			FEC2 : {
				dateDMY : true
			}
		}
	});
});

$.validator.setDefaults({
	submitHandler : function() {
//		alert("Descripci\u00f3n obligatoria guardada.");
		window.href.location = "rendicionDetalleGastos.do";
	}
});

function exportPDF() {
	var columns = Array();
	var rows = Array();

	$("#tableDescripciones thead tr th").each(function(i, v){
		columns[i] = $(this).text();
	});
	
	$("#tableDescripciones tbody tr").each(function(i, v){
		rows[i] = Array();
	    $(this).children('td').each(function(ii, vv){
	    	rows[i][ii] = $(this).text();
	    }); 
	})
	var doc = new jsPDF();
	doc.autoTable(columns, rows);
	doc.save('observaciones.pdf');
}