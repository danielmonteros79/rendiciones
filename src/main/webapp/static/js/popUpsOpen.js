function showMotivoRechazo() {
			popUpObj = window.open("MotivoRechazo.do", "ModalPopUp", "toolbar=no,"
					+ "scrollbars=no," + "location=no," + "statusbar=no,"
					+ "menubar=no," + "resizable=0," + "width=125,"
					+ "height=125," + "left = 275," + "right = 275,"
					+ "top=275," + "bottom = 275" );
			popUpObj.focus();
			LoadModalDiv();
		}
	
		function LoadModalDiv() {
			var bcgDiv = document.getElementById("divBackground");
			bcgDiv.style.display = "block";
		}
	
		function HideModalDiv() {
			var bcgDiv = document.getElementById("divBackground");
			bcgDiv.style.display = "none";
		}
		function OnUnload() {
			if (false == popUpObj.closed) {
				popUpObj.close();
			}
		}
		window.onunload = OnUnload;
		
	