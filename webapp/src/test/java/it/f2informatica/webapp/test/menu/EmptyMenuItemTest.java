package it.f2informatica.webapp.test.menu;

import com.google.common.collect.Sets;
import it.f2informatica.webapp.menu.EmptyMenuItem;
import it.f2informatica.webapp.menu.MenuItemException;
import it.f2informatica.mongodb.domain.constants.Authority;
import org.junit.Test;

public class EmptyMenuItemTest {

	private EmptyMenuItem emptyMenuItem = new EmptyMenuItem();

	@Test(expected = MenuItemException.class)
	public void emptyMenuItemGetLabel() {
		emptyMenuItem.getLabel();
	}

	@Test(expected = MenuItemException.class)
	public void emptyMenuItemGetUrl() {
		emptyMenuItem.getUrl();
	}

	@Test(expected = MenuItemException.class)
	public void verifyExceptionWhenAdding() {
		emptyMenuItem.add(new EmptyMenuItem());
	}

	@Test(expected = MenuItemException.class)
	public void verifyExceptionWhenAddingWithAuthority() {
		emptyMenuItem.add(new EmptyMenuItem(), null);
	}

	@Test(expected = MenuItemException.class)
	public void verifyExceptionWhenAddingWithAuthorities() {
		emptyMenuItem.addWithAuthorities(new EmptyMenuItem(), Sets.<Authority>newHashSet());
	}

}
