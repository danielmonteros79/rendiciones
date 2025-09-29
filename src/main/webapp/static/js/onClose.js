function OnClose() {
        if (window.opener != null && !window.opener.closed) {
            window.opener.HideModalDiv();
        }
//        window.opener.location.reload();
}
window.onunload = OnClose;