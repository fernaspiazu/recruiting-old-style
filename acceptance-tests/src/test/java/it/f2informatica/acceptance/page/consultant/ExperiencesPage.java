package it.f2informatica.acceptance.page.consultant;

import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

public class ExperiencesPage extends Page {
	private static final String EDIT_EXPERIENCE_ICON_XPATH = "//a[contains(@href, '/consultant/profile/experiences/edit/')]";
	private static final String FIRST_EXPERIENCE_DIV_XPATH = "//div[@id='experience1']//span[@id='companyNameSpan']";

	public ExperiencesPage(WebDriver driver, String baseUrl, String consultantId) {
		super(driver, baseUrl, "/consultant/profile/experiences/" + consultantId);
	}

	public String getConsultantIdValue() {
		return getValue(findElement("//input[@id='consultantId-1']"));
	}

	public String getExperienceIdValue() {
		return getValue(findElement("//input[@id='experienceId-1']"));
	}

	public void editExperience() {
		click(findElement(EDIT_EXPERIENCE_ICON_XPATH));
	}

	public String getCompanyNameFromFirstExperience() {
		return findElement(FIRST_EXPERIENCE_DIV_XPATH).getText();
	}

}
