function exportDataGridPDF() {

    var title = 'Detalle Rendicion de Gastos';
    var printContent = '';
    printContent += exportDataGridToHtml(title, 'Rtable');
    $('#nameFile').val(title);
    $('#html').val(printContent);
    $('#fileType').val('.pdf');
//			$('#RendicionForm').get(0).submit();  
}

function exportDataGridToHtml(title, grid) {

    var html =
            ' <html> <head><title>' + title + '</title></head> ' +
            ' <body> <h1>' + title + '</h1>' +
            '<table style="height: 40% width: 100%;">' +
            '<tr> <td style="width: 30%;" align="left"><strong>Usuario</strong></td> <td style="width: 20%;" align="left"><strong>ID-Rendicion</strong></td> <td style="width: 20%;" align="left"><strong>C.Costos</strong></td> <td style="width: 30%;" align="left"><strong>Sector</strong></td> </tr> ' +
            ' <tr> <td style="width: 25%;" align="left">' + $('#TDuser').val() + ' - ' + $('#TDnombreUsuario').val() + '</td><td style="width: 20%;" align="left">' + $('#idRendicion').val() + '</td> <td style="width: 20%;" align="left">' + $('#Costos').val() + '</td> <td style="width: 30%;" align="left">' + $('#Sector').val() + '</td> </tr> ' +
            ' <tr> <td style="width: 50%;"align="left" colspan="2"><h3>RENDICION</h3></td>  <td style="width: 25%;" align="left" colspan="2"><h3>PERIODO </h3></td></tr>' +
            '<tr> <td colspan="2" align="left"><strong>Motivo</strong></td> <td align="left"><strong>Fe.Desde</strong></td> <td align="left"><strong>Fe.Hasta</strong></td> </tr> ' +
            ' <tr> <td colspan="2" style="width: 50%; align="left">' + $('#tdmotivo').val() + '</td><td style="width: 25%; align="left">' + $('#tdfeDesde').val() + '</td> <td style="width: 25%; align="left">' + $('#tdfeHasta').val() + '</td> </tr> ' +
            '<tr> <td colspan="2" style="width: 50%; align="left"><strong>Descripcion/Observaciones</strong></td> </tr> ' +
            ' <tr> <td colspan="2" style="width: 50%; align="left">' + $('#descripcion').val() + '</td> </tr> ' +
            '</table></body> </html>'

    return html;
}
