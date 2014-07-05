var contextRoot = evaluateContextRoot();

$(document).ready(function () {

});

function evaluateContextRoot() {
	var rootUrl = location.protocol;
	rootUrl = rootUrl + "//" + location.host;
	rootUrl = rootUrl + "/" + location.pathname.split("/")[1];
	return rootUrl;
}

function clearFormInputs(form) {
	$(form).find('input[type="text"], textarea, select').val('');
	$(form).find('input[type="checkbox"], input[type="radio"]').removeAttr('checked');
}