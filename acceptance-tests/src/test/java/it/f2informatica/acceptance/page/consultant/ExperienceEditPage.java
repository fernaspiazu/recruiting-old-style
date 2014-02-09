package it.f2informatica.acceptance.page.consultant;

import org.openqa.selenium.WebDriver;

public class ExperienceEditPage extends ExperienceFormPage {

	public ExperienceEditPage(WebDriver driver, String baseUrl, String parameters) {
		super(driver, baseUrl, "/consultant/profile/experiences/edit/" + parameters);
	}

}
