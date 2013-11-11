/**
 * @author Fernando Aspiazu
 */

var contextRoot = evaluateContextRoot();

$(document).ready(function() {
	logout();
	mantainLocaleSelected();
	evaluateSelectedLocale();
	disableSelectElementsWithClassSelectReadOnly();
});

function evaluateContextRoot() {
	var rootUrl = location.protocol;
	rootUrl = rootUrl + "//" + location.host;
	rootUrl = rootUrl + "/" + location.pathname.split("/")[1];
	console.log("Final root URL > " + rootUrl);
	return rootUrl;
}

function logout() {
	$("#logout").click(function() {
		location.href = contextRoot + "/logout";
	});
}

function disableSelectElementsWithClassSelectReadOnly() {
	$.each($(".select-readOnly"), function(index, element) {
		var inputHidden = $("<input/>", {
			type : "hidden",
			name : $(element).attr("name"),
			value : $(element).val()
		});
		// Disable select element
		$(element).attr("disabled", "disabled").before(inputHidden);
	});
}

function evaluateSelectedLocale() {
	$("#select-language").change(function() {
		location.href = "?siteLanguage=" + $(this).val();
	});
}

function mantainLocaleSelected() {
	var currentLocaleValue = readCookie("CURRENT_LOCALE");
	$("#select-language option").each(function() {
		if ($(this).val() == currentLocaleValue) {
			$(this).attr("selected", "selected");
			return;
		}
	});
}

function readCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0; i < ca.length; i++) {
		var c = ca[i].trim();
		if (c.indexOf(nameEQ) == 0) {
			return c.substring(nameEQ.length, c.length);
		}
	}
	return getCurrentNavigatorLanguage();
}

function getCurrentNavigatorLanguage() {
	if (navigator.userLanguage) {
		return navigator.userLanguage;
	}
	return navigator.language;
}