package it.f2informatica.acceptance.page.consultant;

import it.f2informatica.acceptance.page.Page;
import org.openqa.selenium.WebDriver;

public class ProfileRegistrationPage extends Page {
	private static final String CONSULTANT_FULLNAME_H3_XPATH = "//h3[@id='consultantFullName']";
	private static final String EXPERIENCE_SECTION_DIV_XPATH = "//div[@class='mini-experience-box']";
	private static final String LANGUAGE_SECTION_DIV_XPATH = "//div[@class='mini-language-box']";
	private static final String SKILL_SECTION_DIV_XPATH = "//div[@class='mini-skill-box']";
	private static final String ADD_NEW_EXPERIENCE_BUTTON_XPATH = "//input[@id='addNewExperience']";
	private static final String EXPERIENCES_PRESENT_DIV_XPATH = "//div[@id='experiences']/*";
	private static final String SEE_ALL_LINK = "//a[contains(@href, '/consultant/profile/experiences/')]";

	public ProfileRegistrationPage(WebDriver driver, String baseUrl, String consultantId) {
		super(driver, baseUrl, "/consultant/profile/" + consultantId);
	}

	public String consultantWichWillBeAddedProfile() {
		return findElement(CONSULTANT_FULLNAME_H3_XPATH).getText();
	}

	public boolean isExperienceSectionPresent() {
		return findElement(EXPERIENCE_SECTION_DIV_XPATH).isDisplayed();
	}

	public boolean isLanguageSectionPresent() {
		return findElement(LANGUAGE_SECTION_DIV_XPATH).isDisplayed();
	}

	public boolean isSkillSectionPresent() {
		return findElement(SKILL_SECTION_DIV_XPATH).isDisplayed();
	}

	public void addNewExperience() {
		click(findElement(ADD_NEW_EXPERIENCE_BUTTON_XPATH));
	}

	public int experiencesNumber() {
		return findElements(EXPERIENCES_PRESENT_DIV_XPATH).size();
	}

	public ExperiencesPage clickOnSeeAllLink() {
		String consultantId = getValue(findElement("//input[@id='consIdProfileRegPage']"));
		click(findElement(SEE_ALL_LINK));
		return new ExperiencesPage(driver, baseUrl, consultantId);
	}
}
