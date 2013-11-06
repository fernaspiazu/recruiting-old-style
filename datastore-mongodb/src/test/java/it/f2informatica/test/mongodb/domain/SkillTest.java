package it.f2informatica.test.mongodb.domain;

import it.f2informatica.mongodb.domain.Skill;
import it.f2informatica.test.mongodb.constants.SkillConstants;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SkillTest {

	Skill skillJava;
	Skill skillDotNet;

	@Before
	public void setUp() {
		skillJava = new Skill(SkillConstants.SKILL_JAVA, 5);
		skillDotNet = new Skill(SkillConstants.SKILL_DOTNET, 1);
	}

	@Test
	public void skillName() {
		assertThat(skillJava.getSkillName()).isEqualTo(SkillConstants.SKILL_JAVA);
	}

	@Test
	public void skillEquals() {
		assertThat(skillDotNet).isEqualTo(new Skill(SkillConstants.SKILL_DOTNET, 1));
	}

}
