var contextRoot = evaluateContextRoot();

$(document).ready(function() {

});

function evaluateContextRoot() {
	var rootUrl = location.protocol;
	rootUrl = rootUrl + "//" + location.host;
	rootUrl = rootUrl + "/" + location.pathname.split("/")[1];
	return rootUrl;
}