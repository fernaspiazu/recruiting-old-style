package it.f2informatica.acceptance.page.consultant;

import org.openqa.selenium.WebDriver;

public class ExperienceCreationPage extends ExperienceFormPage {

	public ExperienceCreationPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl, "/consultant/profile/experiences/new");
	}

}
