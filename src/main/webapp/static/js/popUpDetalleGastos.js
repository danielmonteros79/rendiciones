function OnClose() {
			
	            if (window.opener != null && !window.opener.closed) {
	                window.opener.HideModalDiv();
	                
//	    	        window.close();

	            }
//		        window.opener.location.href="rendicionDetalleGastos.do?codigo="+$('#idRendicion').val();
	            window.opener.location.reload();
//			}
	        window.onunload = OnClose;
			 
}
	        