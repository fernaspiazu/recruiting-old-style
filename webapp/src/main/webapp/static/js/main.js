/**
 * @author Fernando Aspiazu
 */

var contextRoot = evaluateContextRoot();

$(document).ready(function() {
	logout();
	disableSelectElementsWithClassSelectReadOnly();
});

function disableSelectElementsWithClassSelectReadOnly() {
	$.each($(".select-readOnly"), function(index, element) {
		var inputHidden = $("<input/>", { // Create a hidden input to store select value
			type : "hidden",
			name : $(element).attr("name"),
			value : $(element).val()
		});
		// Disable select element
		$(element).attr("disabled", "disabled").before(inputHidden);
	});
}

function logout() {
	$("#logout").click(function() {
		location.href = contextRoot + "/logout";
	});
}

function evaluateContextRoot() {
	var rootUrl = location.protocol;
	rootUrl = rootUrl + "//" + location.host;
	rootUrl = rootUrl + "/" + location.pathname.split("/")[1];
	console.log("Final root URL > " + rootUrl);
	return rootUrl;
}